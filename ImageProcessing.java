import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

import view.ProcessorGUI;
import controller.ControllerInterface;
import controller.Controller;
import model.ImageProcessor;
import model.Processor;

/**
 * A class containing the main method to run our code.
 */
public class ImageProcessing {

  /**
   * Handles the command line inputs.
   *
   * @param args command line arguments
   * @throws IllegalArgumentException if the command is not in the list of commands
   */
  public static void main(String[] args) throws IllegalArgumentException {

    ImageProcessor processor = new Processor();
    //Controller controller = new Controller(processor);

    processor.load("mario", "images/mario.ppm");

    //add functionality for GUI
    Readable rd;

    ControllerInterface controller;
    if (args.length == 0) {
      new ProcessorGUI(processor);
      return;
    }
    if (args.length > 2) {
      throw new IllegalArgumentException(
          "Too many commands inputted. Only one command is supported at one time. Check " +
              "documentation to correctly interact with the program.");
    }
    if (args[0].equalsIgnoreCase("-text") && args.length == 1) {
      controller = new Controller(processor);
    } else if (args[0].equalsIgnoreCase("-file") && args.length == 2) {
      try {
        rd = new FileReader(args[1]);
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException(args[1] + " does not exist.");
      }
      controller = new Controller(processor, rd);
    } else {
      throw new IllegalArgumentException(
          "Inputted commands are invalid. Read the documentation from the readme on how to " +
              "properly use Image Processing.");
    }
    controller.startProcessor();
  }
}