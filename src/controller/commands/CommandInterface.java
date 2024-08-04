package controller.commands;

import model.ImageProcessor;

/**
 * The Command interface.
 */
public interface CommandInterface {

  /**
   * Takes in a constructor and the parameters and accesses the map.
   *
   * @param ip takes in the parameters
   */
  void runCommand(ImageProcessor ip);
}
