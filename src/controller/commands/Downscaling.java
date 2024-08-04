package controller.commands;

import model.ImageProcessor;

/**
 * Contains the constructor for downscaling.
 */
public class Downscaling implements CommandInterface {

  String name;
  String newName;
  double height;
  double width;

  /**
   * Constructor for downscaling an image.
   *
   * @param name is the name of the image
   * @param newName is the new name of the image
   * @param height is the amount to downscale the height by
   * @param width is the amount to downscale the width by
   */
  public Downscaling(String name, String newName, double height, double width) {
    this.name = name;
    this.newName = newName;
    this.height  = height;
    this.width  = width;
  }

  @Override
  public void runCommand(ImageProcessor ip) {
    ip.downscaling(name, newName, height, width);
  }
}
