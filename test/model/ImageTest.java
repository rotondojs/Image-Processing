package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the Image class.
 */
public class ImageTest {

  Image invalidImage1;
  Image invalidImage2;
  Image image1;
  Image image2;
  Image image3;

  /**
   * Constructs Images to be tested.
   */
  public ImageTest() {
    image1 = new Image(2, 2, 4);
    image2 = new Image(2, 2, 4);
    image3 = new Image(2, 2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidImage() {
    invalidImage1 = new Image(-1, 2, 4);
    invalidImage2 = new Image(2, 0, 4);
  }

  @Test
  public void testGetHeight() {
    assertEquals(2, image1.getHeight());
  }

  @Test
  public void testGetWidth() {
    assertEquals(2, image1.getWidth());
  }

  @Test
  public void testGetMaxValue() {
    assertEquals(4, image1.getMaxVal());
  }

  @Test
  public void testEqualsFalse() {
    assertEquals(false, image1.equals(image3));
    assertEquals(false, image1.equals("string"));
  }

  @Test
  public void testEqualsTrue() {
    assertEquals(true, image1.equals(image2));
  }
}