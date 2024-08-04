package controller.commands;

import model.ImageProcessor;

/**
 * Contains the constructor for Luma.
 */
public class LumaComponent implements CommandInterface {

  String name;
  String newName;

  /**
   * Constructor for the method Luma.
   *
   * @param name    the name of the file
   * @param newName the new name of the file
   */
  public LumaComponent(String name, String newName) {
    this.name = name;
    this.newName = newName;
  }

  @Override
  public void runCommand(ImageProcessor ip) {
    ip.luma(name, newName);
  }
}
