package controller.commands;

import model.ImageProcessor;

/**
 * Contains the constructor for greyscale.
 */
public class GreyscaleComponent implements CommandInterface {

  String name;
  String newName;
  int color;

  /**
   * Constructor for the method Greyscale.
   *
   * @param color   the int of the color grey
   * @param name    the name of the file
   * @param newName the new name of the file after the edit
   */
  public GreyscaleComponent(String name, String newName, int color) {
    this.name = name;
    this.newName = newName;
    this.color = color;
  }

  @Override
  public void runCommand(ImageProcessor ip) {
    ip.greyscale(name, newName, color);
  }
}
