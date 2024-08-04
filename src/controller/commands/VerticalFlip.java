package controller.commands;

import model.ImageProcessor;

/**
 * Contains the constructor for VerticalFlip.
 */
public class VerticalFlip implements CommandInterface {
  String name;
  String newName;

  /**
   * Constructor for the method VerticalFlip.
   *
   * @param name    the name of the file
   * @param newName the new name of the file
   */
  public VerticalFlip(String name, String newName) {
    this.name = name;
    this.newName = newName;
  }

  @Override
  public void runCommand(ImageProcessor ip) {
    ip.vertFlip(name, newName);
  }
}
