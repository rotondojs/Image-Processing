package controller.commands;

import model.ImageProcessor;

/**
 * Contains the constructor for Sharpen.
 */
public class Sharpen implements CommandInterface {
  String name;
  String newName;

  /**
   * Constructor for the class Sepia.
   *
   * @param name    the name of the file
   * @param newName the new name of the file
   */
  public Sharpen(String name, String newName) {
    this.name = name;
    this.newName = newName;
  }

  @Override
  public void runCommand(ImageProcessor ip) {
    ip.sharpen(name, newName);
  }
}
