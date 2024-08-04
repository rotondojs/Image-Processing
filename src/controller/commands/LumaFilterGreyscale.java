package controller.commands;

import model.ImageProcessor;

/**
 * Contains the constructor for LumaFilterGreyscale.
 */
public class LumaFilterGreyscale implements CommandInterface {
  String name;
  String newName;

  /**
   * Constructor for the class Sepia.
   *
   * @param name    the name of the file
   * @param newName the new name of the file
   */
  public LumaFilterGreyscale(String name, String newName) {
    this.name = name;
    this.newName = newName;
  }

  @Override
  public void runCommand(ImageProcessor ip) {
    ip.greyscaleByFilter(name, newName);
  }
}
