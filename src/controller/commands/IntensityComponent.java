package controller.commands;

import model.ImageProcessor;

/**
 * Contains the constructor for Intensity.
 */
public class IntensityComponent implements CommandInterface {

  String name;
  String newName;

  /**
   * Constructor for the method Intensity.
   *
   * @param name    the name of the file
   * @param newName the new name of the file after the edit
   */
  public IntensityComponent(String name, String newName) {
    this.name = name;
    this.newName = newName;
  }

  @Override
  public void runCommand(ImageProcessor ip) {
    ip.intensity(name, newName);
  }
}
