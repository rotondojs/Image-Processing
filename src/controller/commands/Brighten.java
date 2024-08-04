package controller.commands;

import model.ImageProcessor;

/**
 * Contains the constructor for brighten.
 */
public class Brighten implements CommandInterface {

  String name;
  String newName;
  int brightVal;

  /**
   * Constructor for the method brighten.
   *
   * @param brightVal the brightness value
   * @param name      the name of the file
   * @param newName   the new name of the file after the edit
   */
  public Brighten(int brightVal, String name, String newName) {
    this.name = name;
    this.newName = newName;
    this.brightVal = brightVal;
  }

  @Override
  public void runCommand(ImageProcessor ip) {
    ip.brighten(name, newName, brightVal);
  }
}
