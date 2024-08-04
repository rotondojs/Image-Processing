package model;


import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * This class represents and image with the dimensions and ability to edit pixels.
 */
public class Image {

  private final int[][][] image;
  private final int height;
  private final int width;
  private final int maxVal;

  /**
   * Constructor with 3 parameters for an image.
   *
   * @param height the height of the image.
   * @param width  the width of the image.
   * @param maxVal the max position of the image.
   */
  Image(int height, int width, int maxVal) throws IllegalArgumentException {
    if (height < 1 || width < 1 || maxVal < 1) {
      throw new IllegalArgumentException("Image cannot have any parameters less than 1.");
    }
    this.height = height;
    this.width = width;
    this.maxVal = maxVal;
    this.image = new int[height][width][3];
  }

  /**
   * Constructor with one parameter. Used for copying an image before transforming.
   *
   * @param img new copied image.
   */
  Image(Image img) {
    this(img.getHeight(), img.getWidth(), img.getMaxVal());
  }

  /**
   * Constructor that takes a buffered image and turns it into an image object.
   *
   * @param img image you are converting.
   */
  Image(BufferedImage img) {
    height = img.getHeight();
    width = img.getWidth();
    maxVal = 255;
    image = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        image[i][j] = this.getPixel(i, j);
      }
    }
  }

  /**
   * Method for assigning pixel color.
   *
   * @param y   is the y coordinate of the pixel
   * @param x   is the x coordinate of the pixel
   * @param rgb is the color you are assigning to the pixel.
   */
  public void setPixelRGB(int y, int x, int[] rgb) {
    this.image[y][x] = rgb;
  }

  /**
   * Method for retrieving the color value at the given coordinates.
   *
   * @param y is the y coordinate of the pixel
   * @param x is the x coordinate of the pixel
   * @return returns the array of rgb components of the pixel
   */
  public int[] getPixel(int y, int x) throws IllegalArgumentException {
    if (x >= width || y >= height || x < 0 || y < 0) {
      throw new IllegalArgumentException("Invalid Pixel Location");
    }
    return this.image[y][x];
  }

  /**
   * Returns the max position of the image.
   *
   * @return returns the max position
   */
  public int getMaxVal() {
    return maxVal;
  }

  /**
   * Method for getting the height of the image.
   *
   * @return the height of the image
   */
  public int getHeight() {
    return height;
  }

  /**
   * Method for getting the width of the image.
   *
   * @return the width of the image
   */
  public int getWidth() {
    return width;
  }

  public int getMaxValue() {
    return maxVal;
  }

  /**
   * Converts the Image from Image class to BufferedImage.
   *
   * @return BufferedImage
   */
  public BufferedImage toBufferedImage() {
    BufferedImage out = new BufferedImage(width, height, 8);
    int[] p;
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        p = image[y][x];
        if (image[y][x] == null) {
          p = new int[]{0, 0, 0};
        }
        out.setRGB(x, y, new Color(p[0], p[1], p[2]).getRGB());
      }
    }
    return out;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Image)) {
      return false;
    }
    Image img = (Image) o;
    if (this.width != img.getWidth() || this.height != img.getHeight()
        || this.maxVal != img.maxVal) {
      return false;
    }
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          if (img.getPixel(i, j)[k] != this.image[i][j][k]) {
            System.out.println(i + j + k);
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    int out = width * 5 + 7 * height + 13 * maxVal;
    for (int i = 0; i < width * height; i++) {
      out += image[i / width][i % width].hashCode() * 1024 * i / (width * height);
    }
    return out;
  }
}
