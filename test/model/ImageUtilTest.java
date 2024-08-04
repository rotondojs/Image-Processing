package model;

import org.junit.Test;

import static model.ImageUtil.readPPM;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Test class for the ImageUtil class.
 */
public class ImageUtilTest {

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidReadPPM() {
    readPPM("panda");
  }

  @Test
  public void testReadPPM() {
    Image img = ImageUtil.readPPM("images/testPPM.ppm");
    assertEquals(2, img.getHeight());
    assertEquals(4, img.getWidth());
    assertEquals(7, img.getMaxVal());
    for (var i = 0; i < 8; i++) {
      assertArrayEquals(new int[]{i, i, i}, img.getPixel(i / 4, i % 4));
    }
  }
}