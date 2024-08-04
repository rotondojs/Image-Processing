package controller.commands;

import model.ImageProcessor;

/**
 * Contains the constructor for Value.
 */
public class ValueComponent implements CommandInterface {

  String name;
  String newName;

  /**
   * Constructor for the method Value.
   *
   * @param name    the name of the file
   * @param newName the new name of the file
   */
  public ValueComponent(String name, String newName) {
    this.name = name;
    this.newName = newName;
  }

  @Override
  public void runCommand(ImageProcessor ip) {
    ip.value(name, newName);
  }
}
