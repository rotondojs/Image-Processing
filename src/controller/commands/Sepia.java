package controller.commands;

import model.ImageProcessor;

/**
 * Contains the constructor for Sepia.
 */
public class Sepia implements CommandInterface {

  String name;
  String newName;

  /**
   * Constructor for the class Sepia.
   *
   * @param name    the name of the file
   * @param newName the new name of the file
   */
  public Sepia(String name, String newName) {
    this.name = name;
    this.newName = newName;
  }

  @Override
  public void runCommand(ImageProcessor ip) {
    ip.sepiaFilter(name, newName);
  }
}

