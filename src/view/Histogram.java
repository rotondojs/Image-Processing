package view;

import model.ImageProcessor;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

/**
 * Class that constructs the histogram.
 */
public class Histogram extends JPanel {
  private final int maxHeight;
  private final int opacity = 80;
  private int[] intensity;
  private int[] red;
  private int[] green;
  private int[] blue;

  /**
   * Constructor for the histogram.
   *
   * @param processor The image processor.
   * @param imageName The name of the image.
   * @param height    The height of the image.
   */
  public Histogram(ImageProcessor processor, String imageName, int height) {
    this.setHistograms(processor, imageName);
    this.maxHeight = height;
    this.setMinimumSize(new Dimension(this.getWidth(), height));
  }

  /**
   * Sets the histograms colors and intensity.
   *
   * @param processor The image processor.
   * @param imageName The name of the image.
   */
  private void setHistograms(ImageProcessor processor, String imageName) {
    intensity = processor.getHistogramIntensity(imageName);
    red = processor.getHistogramRed(imageName);
    green = processor.getHistogramGreen(imageName);
    blue = processor.getHistogramBlue(imageName);
  }

  /**
   * Sets the colors of the graphics.
   *
   * @param g The graphics.
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int width = this.getWidth() / 256;
    int maxIntensity = maxValue(intensity);
    int maxRed = maxValue(red);
    int maxGreen = maxValue(green);
    int maxBlue = maxValue(blue);
    int height;
    for (int i = 0; i < 256; i++) {
      height = maxHeight * intensity[i] / maxIntensity;
      g.setColor(new Color(100, 100, 100, opacity));
      g.fillRect(i * width, maxHeight - height, width, height);
      height = maxHeight * red[i] / maxRed;
      g.setColor(new Color(255, 0, 0, opacity));
      g.fillRect(i * width, maxHeight - height, width, height);
      height = maxHeight * green[i] / maxGreen;
      g.setColor(new Color(0, 255, 0, opacity));
      g.fillRect(i * width, maxHeight - height, width, height);
      height = maxHeight * blue[i] / maxBlue;
      g.setColor(new Color(0, 0, 255, opacity));
      g.fillRect(i * width, maxHeight - height, width, height);
    }
  }

  /**
   * Takes in a list of data and returns the max value.
   *
   * @param data List of data.
   */
  private int maxValue(int[] data) {
    int max = 0;
    for (int v : data) {
      max = Math.max(v, max);
    }
    return max;
  }

  /**
   * Gets the preferred size of an image.
   *
   * @return a dimension.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(this.getWidth(), maxHeight + 10);
  }

  public void updateData(ImageProcessor processor, String name) {
    setHistograms(processor, name);
  }
}
