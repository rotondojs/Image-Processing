package controller.commands;

import model.ImageProcessor;

/**
 * Contains the constructor for Blur.
 */
public class Blur implements CommandInterface {
  String name;
  String newName;

  /**
   * Constructor for the class Sepia.
   *
   * @param name    the name of the file
   * @param newName the new name of the file
   */
  public Blur(String name, String newName) {
    this.name = name;
    this.newName = newName;
  }

  @Override
  public void runCommand(ImageProcessor ip) {
    ip.blur(name, newName);
  }
}
