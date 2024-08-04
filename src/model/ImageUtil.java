package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  private static final String[] validExtensions = new String[]{
      "ppm", "png", "jpg", "bmp"};

  /**
   * Given a file path it checks whether the image type is valid.
   *
   * @param path the path of the image
   * @return whether the image is of a valid type
   */
  public static boolean isValidType(String path) {
    String type = getExtension(path);
    for (String v : validExtensions) {
      if (v.equalsIgnoreCase(type)) {
        return true;
      }
    }
    return false;
  }

  private static String getExtension(String filename) throws IllegalArgumentException {
    int ending = filename.lastIndexOf('.');
    if (ending <= 0) {
      throw new IllegalArgumentException("File has no ending");
    }
    return filename.substring(ending + 1);
  }

  /**
   * Method for reading Image files. Checks the file extensions and calls the corresponding
   * read functions.
   *
   * @param filename is the file path.
   * @return the image read from the path.
   */
  public static Image readImage(String filename) {
    if (getExtension(filename).equalsIgnoreCase("ppm")) {
      return readPPM(filename);
    } else {
      return readImageFile(filename);
    }
  }

  private static Image readImageFile(String filename) throws IllegalArgumentException,
      IllegalStateException {
    BufferedImage img;
    try {
      img = ImageIO.read(new File(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException(filename + " is an invalid file path.");
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    if (img == null) {
      throw new IllegalArgumentException(
          "Invalid File. Must be a supported file " +
              "format and file path. Make sure that the image"
              + " is not corrupted.");
    }
    return new Image(img);
  }

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   */
  public static Image readPPM(String filename) throws IllegalArgumentException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 255): " + maxValue);

    Image image = new Image(height, width, maxValue);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        image.setPixelRGB(i, j, new int[]{r, g, b});
      }
    }
    return image;
  }

  /**
   * Writes an image to a ppm.
   *
   * @param path the path you are writing the image to
   * @param img  the image you are writing
   */
  public static void writePPM(String path, Image img) {
    OutputStreamWriter out;
    try {
      out = new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.US_ASCII);
      out.write("P3\n");
      out.write(img.getWidth() + " " + (img.getHeight()) + "\n" + img.getMaxVal() + "\n");
      for (int i = 0; i < img.getHeight(); i++) {
        for (int j = 0; j < img.getWidth(); j++) {
          for (int k = 0; k < 3; k++) {
            out.write(img.getPixel(i, j)[k] + " ");
          }
        }
      }
      out.close();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * Writes images for types jpg, png, and bmp.
   *
   * @param dest destination path
   * @param img  Image you are writing
   * @throws IllegalArgumentException if the path does not exist
   * @throws IllegalStateException    if the image is invalid
   */
  public static void writeImage(String dest, Image img) throws IllegalArgumentException,
      IllegalStateException {
    String ext = getExtension(dest);
    if (ext.equalsIgnoreCase("ppm")) {
      writePPM(dest, img);
      return;
    }
    try {
      ImageIO.write(img.toBufferedImage(), ext, new FileOutputStream(dest));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e + "is an invalid path.");
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
}

