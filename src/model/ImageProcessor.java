package model;

import java.util.List;

/**
 * The image processor interface.
 */
public interface ImageProcessor {

  /**
   * Method for downscaling an image.
   *
   * @param name is the name of the image
   * @param newName is the new name of the image
   * @param height is the amount to downscale the height by
   * @param width is the amount to downscale the width by
   * @throws IllegalArgumentException throws IllegalArgumentException
   * if the height or width are less than 0.
   */
  void downscaling(String name, String newName, double height, double width)
      throws IllegalArgumentException;

  /**
   * Method for manipulating part of an image.
   *
   * @param name is the name of the image
   * @param newName is the new name of the image
   * @param mi is the black and white mask image
   * @param filter is the filter that is being applied to the image
   * @param val is the value for the filter if one is needed
   * @throws IllegalArgumentException throws IllegalArgumentException
   * if the mi or filter are null, or if val is less than zero.
   */
  void manipulate(String name, String newName, Image mi, String filter, int val)
      throws IllegalArgumentException;

  /**
   * Method for adding a new image to the map of images.
   *
   * @param name is the name of the image
   * @param path is the path of the image
   */
  void load(String name, String path);

  /**
   * Loads an image from the image map.
   *
   * @param name is the name of the image used as a key in the map
   * @return returns the image with the key name
   * @throws IllegalArgumentException throws IllegalArgumentException if the image is not in the map
   */
  Image getLoad(String name) throws IllegalArgumentException;

  /**
   * Returns the size of the image map.
   *
   * @return size of the image map
   */
  int getLoadSize();

  /**
   * Method for writing pmm files.
   *
   * @param name is the name you want to save the image to.
   * @param path is the path you save the image to.
   */
  void save(String name, String path);

  /**
   * Method to brighten (increase rgb values for every component) images.
   *
   * @param name      name of the file in the map you want to brighten.
   * @param newName   name for the new copied image with the corresponding change.
   * @param brightVal is the value to increase rgb by.
   */
  void brighten(String name, String newName, int brightVal);

  /**
   * Method to flip the image vertically.
   *
   * @param name    is the name of the image to flip
   * @param newName is the name of the new flipped image.
   */
  void vertFlip(String name, String newName);

  /**
   * Method to flip the image horizontally.
   *
   * @param name    is the name of the image to flip
   * @param newName is the name of the new flipped image.
   */
  void horizFlip(String name, String newName);

  /**
   * Creates a grayscale image for the given color (R,G,B).
   *
   * @param name    is the name of the image
   * @param newName name of the greyscale image
   * @param color   color to produce greyscale image for given as an integer
   *                0(red), 1(green), 2(blue)
   */
  Image greyscale(String name, String newName, int color);

  /**
   * Method for adjusting the luma value of the image. Luma is the weighted sum of the rgb values.
   *
   * @param name    is the name of the image.
   * @param newName is the name of the new image with the corresponding luma adjustment.
   */
  void luma(String name, String newName);

  /**
   * Method for producing a value image. Value takes the max of the three
   * components and sets all components to that max.
   *
   * @param name    is the name of the image you.
   * @param newName is the name of the new image of the corresponding value image
   */
  void value(String name, String newName);

  /**
   * Method for producing an intensity image of the given image. Intensity takes
   * the average of all three components(RGB) and replaces the components with this average.
   *
   * @param name    is the name of the image.
   * @param newName is the name of the intensity image.
   */
  Image intensity(String name, String newName);

  /**
   * Method for blurring an image.
   * It takes the average of all three components and makes the main pixel RGB value the average.
   *
   * @param name    is the name of the image.
   * @param newName is the name of the blurred image.
   */
  void blur(String name, String newName);

  /**
   * Method for sharpening an image. Enhances the definition of the edges in an image.
   *
   * @param name    is the name of the image.
   * @param newName is the name of the blurred image.
   */
  void sharpen(String name, String newName);

  /**
   * Method for making an image composed exclusively of shades of gray.
   * The contrast ranges from black at the weakest intensity to white at the strongest.
   *
   * @param name    is the name of the image.
   * @param newName is the name of the blurred image.
   */
  void greyscaleByFilter(String name, String newName);

  /**
   * Method for sepia toning an image.
   * Changes the color of the resulting image,
   * and makes the photo more resistant to environmental pollutants.
   *
   * @param name    is the name of the image.
   * @param newName is the name of the blurred image.
   */
  void sepiaFilter(String name, String newName);

  /**
   * Gets the key that was pushed.
   */
  List<String> getLoadKeys();

  /**
   * Gets the red color of the histogram.
   *
   * @param name The name of the image.
   */
  int[] getHistogramRed(String name);

  /**
   * Gets the green color of the histogram.
   *
   * @param name The name of the image.
   */
  int[] getHistogramGreen(String name);

  /**
   * Gets the blue color of the histogram.
   *
   * @param name The name of the image.
   */
  int[] getHistogramBlue(String name);

  /**
   * Gets the intensity of the histogram.
   *
   * @param name The name of the image.
   */
  int[] getHistogramIntensity(String name);
}
