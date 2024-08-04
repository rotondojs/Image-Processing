package controller.commands;

import model.ImageProcessor;

/**
 * Contains the constructor for HorizontalFlip.
 */
public class HorizontalFlip implements CommandInterface {

  String name;
  String newName;

  /**
   * Constructor for the method HorizontalFlip.
   *
   * @param name    the name of the file
   * @param newName the new name of the file after the edit
   */
  public HorizontalFlip(String name, String newName) {
    this.name = name;
    this.newName = newName;
  }

  @Override
  public void runCommand(ImageProcessor ip) {
    ip.horizFlip(name, newName);
  }
}
