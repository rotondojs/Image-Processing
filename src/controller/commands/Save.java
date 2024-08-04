package controller.commands;

import model.ImageProcessor;

/**
 * Contains the constructor for Save.
 */
public class Save implements CommandInterface {

  String path;
  String name;

  /**
   * Constructor for the method Save.
   *
   * @param name the name of the file
   * @param path the path of the file
   */
  public Save(String name, String path) {
    this.path = path;
    this.name = name;
  }

  @Override
  public void runCommand(ImageProcessor ip) {
    ip.save(name, path);
  }
}
