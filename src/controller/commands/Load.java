package controller.commands;

import model.ImageProcessor;

/**
 * Contains the constructor for Load.
 */
public class Load implements CommandInterface {

  String path;
  String name;

  /**
   * Constructor for the method Load.
   *
   * @param name the name of the file
   * @param path the path of the file
   */
  public Load(String path, String name) {
    this.path = path;
    this.name = name;
  }

  @Override
  public void runCommand(ImageProcessor ip) {
    ip.load(name, path);
  }
}
