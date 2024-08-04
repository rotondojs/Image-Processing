package controller;

import org.junit.Test;

import java.io.StringReader;

import model.ImageProcessor;
import model.ImageUtil;
import model.Processor;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the Controller class.
 */
public class ControllerTest {
  ImageProcessor processor;
  Controller controller;


  /**
   * Constructor for image controller tests.
   */
  public ControllerTest() {
    processor = new Processor();
  }

  @Test
  public void testLoad() {
    Readable rd = new StringReader("load images/koala.ppm koala");
    controller = new Controller(processor, rd);
    controller.startProcessor();
    assertEquals(1, processor.getLoadSize());
    assertEquals(ImageUtil.readPPM("images/koala.ppm"), processor.getLoad("koala"));
  }

  @Test
  public void testOverwriteLoad() {
    Readable rd = new StringReader("load images/koala.ppm koala\nload images/mario.ppm koala");
    controller = new Controller(processor, rd);
    controller.startProcessor();
    assertEquals(1, processor.getLoadSize());
    assertEquals(ImageUtil.readPPM("images/mario.ppm"), processor.getLoad("koala"));
  }

  @Test
  public void testComponentCommands() {
    String[] diff = new String[]{"red", "green", "blue", "value", "luma", "intensity"};
    for (String c : diff) {
      Readable rd = new StringReader(
          "load images/koala.ppm koala\n" + c + "-component koala koala-" + c);
      processor = new Processor();
      controller = new Controller(processor, rd);
      controller.startProcessor();
      assertEquals(2, processor.getLoadSize());
      assertEquals(
          ImageUtil.readPPM("images/koala-" + c + "-greyscale.ppm"),
          processor.getLoad("koala-" + c)
      );
    }
  }

  @Test
  public void testBrightenCommand() {
    Readable rd = new StringReader("load images/mario.ppm mario\nbrighten 50 mario " +
        "mario-brighter");
    controller = new Controller(processor, rd);
    controller.startProcessor();
    assertEquals(2, processor.getLoadSize());
    assertEquals(
        ImageUtil.readPPM("images/marioBrightBy50Test.ppm"),
        processor.getLoad("mario-brighter"));
  }

  @Test
  public void testFlipCommands() {
    Readable rd = new StringReader(
        "load images/mario.ppm mario\nhorizontal-flip mario mario-hflip\nvertical-flip mario " +
            "mario-vflip");
    controller = new Controller(processor, rd);
    controller.startProcessor();
    assertEquals(3, processor.getLoadSize());
    assertEquals(ImageUtil.readPPM("res/marioHorizFlipTest.ppm"),
        processor.getLoad("mario-hflip"));
    assertEquals(ImageUtil.readPPM("res/marioVertFlipTest.ppm"),
        processor.getLoad("mario-vflip"));
  }
}