package view;

import org.junit.Test;

import model.ImageProcessor;
import model.Processor;

/**
 * Testing for processorGUI.
 */
public class ProcessorGUITest {
  ImageProcessor processor;
  ProcessorGUI view;


  public ProcessorGUITest() {
    processor = new Processor();
    view = new ProcessorGUI(processor);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullTest() {
    new ProcessorGUI(null);
  }
}