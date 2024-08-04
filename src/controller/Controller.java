package controller;

import java.io.InputStreamReader;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import controller.commands.CommandInterface;
import controller.commands.Downscaling;
import controller.commands.Load;
import controller.commands.LumaFilterGreyscale;
import controller.commands.Save;
import controller.commands.Brighten;
import controller.commands.HorizontalFlip;
import controller.commands.VerticalFlip;
import controller.commands.GreyscaleComponent;
import controller.commands.IntensityComponent;
import controller.commands.ValueComponent;
import controller.commands.LumaComponent;
import controller.commands.Sepia;
import controller.commands.Sharpen;
import controller.commands.Blur;

import model.ImageProcessor;

/**
 * Class that accesses a certain method based on strings and parameters.
 */
public class Controller implements ControllerInterface {

  private final Map<String, Function<Scanner, CommandInterface>> commandMap = Map.ofEntries(
      Map.entry("load", sc -> new Load(sc.next(), sc.next())),
      Map.entry("save", sc -> new Save(sc.next(), sc.next())),
      Map.entry("brighten", sc -> new Brighten(sc.nextInt(), sc.next(), sc.next())),
      Map.entry("horizontal-flip", sc -> new HorizontalFlip(sc.next(), sc.next())),
      Map.entry("vertical-flip", sc -> new VerticalFlip(sc.next(), sc.next())),
      Map.entry("red-component", sc -> new GreyscaleComponent(sc.next(), sc.next(), 0)),
      Map.entry("green-component", sc -> new GreyscaleComponent(sc.next(), sc.next(), 1)),
      Map.entry("blue-component", sc -> new GreyscaleComponent(sc.next(), sc.next(), 2)),
      Map.entry("intensity-component", sc -> new IntensityComponent(sc.next(), sc.next())),
      Map.entry("value-component", sc -> new ValueComponent(sc.next(), sc.next())),
      Map.entry("luma-component", sc -> new LumaComponent(sc.next(), sc.next())),
      Map.entry("sepia", sc -> new Sepia(sc.next(), sc.next())),
      Map.entry("blur", sc -> new Blur(sc.next(), sc.next())),
      Map.entry("sharpen", sc -> new Sharpen(sc.next(), sc.next())),
      Map.entry("luma-filter-greyscale", sc -> new LumaFilterGreyscale(sc.next(), sc.next())),
      Map.entry("downscaling", sc -> new Downscaling(sc.next(), sc.next(), sc.nextDouble(),
          sc.nextDouble()))
  );

  private final ImageProcessor processor;
  private Readable rd;

  /**
   * Constructs the controller.
   *
   * @param processor processes the image
   */
  public Controller(ImageProcessor processor) {
    this(processor, new InputStreamReader(System.in));
  }

  /**
   * Constructs the controller.
   *
   * @param processor processes the image
   * @param rd        readable
   */
  public Controller(ImageProcessor processor, Readable rd) {
    this.processor = processor;
    this.rd = rd;
  }

  @Override
  public void startProcessor() throws IllegalArgumentException {
    Scanner sc = new Scanner(rd);
    while (sc.hasNext()) {
      Function<Scanner, CommandInterface> c = commandMap.getOrDefault(sc.next(), null);
      if (c != null) {
        c.apply(sc).runCommand(processor);
      }
    }
    sc.close();
  }
}
