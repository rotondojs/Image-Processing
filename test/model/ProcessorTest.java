package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the Processor class.
 */
public class ProcessorTest {
  ImageProcessor image1;
  ImageProcessor image2;
  ImageProcessor image3;

  /**
   * Constructs examples of Processor.
   */
  public ProcessorTest() {
    image1 = new Processor();
    image2 = new Processor();
    image3 = new Processor();
  }

  /**
   * Constructs examples of Images.
   */
  private void initData() {
    image1.load("koala", "images1/Koala.ppm");
    image2.load("redCloudImage.ppm", "images1/redCloudImage.ppm");
    image3.load("mario.ppm", "images/mario.ppm");
  }

  @Test
  public void testSaveDownscaling() {
    initData();
    image3.load("marioMask", "marioMask.png");
    image3.manipulate("mario.ppm", "marioManipulate.png", image3.getLoad("marioMask"),
        "Brighten", 100);
    image3.save("marioManipulate.png", "images/marioManipulate.png");
  }

  @Test
  public void testDownscaling() {
    initData();
    image3.downscaling("mario.ppm", "marioDownscaled.ppm", 0.5, 0.5);
    assertEquals(ImageUtil.readPPM("images/marioDownscaling.ppm"),
        image3.getLoad("marioDownscaled.ppm"));
  }

  @Test
  public void testManipulate() {
    initData();
    image3.load("marioMask", "marioMask.png");
    image3.manipulate("mario.ppm", "marioManipulate.ppm", image3.getLoad("marioMask"),
        "Brighten", 20);
    assertEquals(ImageUtil.readPPM("images/marioManipulate.ppm"),
        image3.getLoad("marioManipulate.ppm"));
  }

  @Test
  public void testLoad() {
    initData();
    assertEquals(ImageUtil.readPPM("images/Koala.ppm"), image1.getLoad("koala"));
  }

  @Test
  public void testSave() {
    initData();
    image1.save("koala", "images/koalaTest.ppm");
    assertEquals(ImageUtil.readPPM("images/koalaTest.ppm"), image1.getLoad("koala"));
  }

  @Test
  public void testBlur() {
    initData();
    image1.blur("koala", "blurKoala");
    assertEquals(ImageUtil.readPPM("images/blurKoalaTest.ppm"),
        image1.getLoad("blurKoala"));

    image2.blur("redCloudImage.ppm", "blurRedCloud");
    assertEquals(ImageUtil.readPPM("images/blurRedCloudTest.ppm"),
        image2.getLoad("blurRedCloud"));

    image3.blur("mario.ppm", "blurMario");
    assertEquals(ImageUtil.readPPM("images/blurMarioTest.ppm"),
        image3.getLoad("blurMario"));
  }

  @Test
  public void testSharpen() {
    initData();
    image1.sharpen("koala", "sharpenKoala");
    assertEquals(ImageUtil.readPPM("images/sharpen-koala.ppm"),
        image1.getLoad("sharpenKoala"));

    image2.sharpen("redCloudImage.ppm", "sharpenRedCloud");
    assertEquals(ImageUtil.readPPM("images/sharpenRedCloudTest.ppm"),
        image2.getLoad("sharpenRedCloud"));

    image3.sharpen("mario.ppm", "sharpenMario");
    assertEquals(ImageUtil.readPPM("images/sharpenMarioTest.ppm"),
        image3.getLoad("sharpenMario"));
  }

  @Test
  public void testGreyscaleFilter() {
    initData();
    image1.greyscaleByFilter("koala", "koalaTransformGreyscale");
    assertEquals(ImageUtil.readPPM("images/koala-luma-greyscale.ppm"),
        image1.getLoad("koalaTransformGreyscale"));

    image2.greyscaleByFilter("redCloudImage.ppm", "greyFilterRedCloud");
    assertEquals(ImageUtil.readPPM("images/greyFilterRedCloudTest.ppm"),
        image2.getLoad("greyFilterRedCloud"));

    image3.greyscaleByFilter("mario.ppm", "greyFilterMario");
    assertEquals(ImageUtil.readPPM("images/greyFilterMarioTest.ppm"),
        image3.getLoad("greyFilterMario"));
  }

  @Test
  public void testSepiaFilter() {
    initData();
    image1.sepiaFilter("koala", "koalaSepia");
    assertEquals(ImageUtil.readPPM("images/koala-sepia.ppm"),
        image1.getLoad("koalaSepia"));

    image2.sepiaFilter("redCloudImage.ppm", "sepiaRedCloud");
    assertEquals(ImageUtil.readPPM("images/sepiaRedCloudTest.ppm"),
        image2.getLoad("sepiaRedCloud"));

    image3.sepiaFilter("mario.ppm", "sepiaMario");
    assertEquals(ImageUtil.readPPM("images/sepiaMarioTest.ppm"),
        image3.getLoad("sepiaMario"));
  }

  @Test
  public void testBrighten() {
    initData();
    image1.brighten("koala", "brightBy50Koala", 50);
    assertEquals(ImageUtil.readPPM("images/koala-brighter-by-50.ppm"),
        image1.getLoad("brightBy50Koala"));

    image2.brighten("redCloudImage.ppm", "brightBy50RedCloud.ppm", 50);
    assertEquals(ImageUtil.readPPM("images/redCloudBrightBy50Test.ppm"),
        image2.getLoad("brightBy50RedCloud.ppm"));

    image3.brighten("mario.ppm", "brightBy50Mario.ppm", 50);
    assertEquals(ImageUtil.readPPM("images/marioBrightBy50Test.ppm"),
        image3.getLoad("brightBy50Mario.ppm"));
  }

  @Test
  public void testVertFlip() {
    initData();
    image1.vertFlip("koala", "vertKoala");
    assertEquals(ImageUtil.readPPM("images/koala-vertical.ppm"),
        image1.getLoad("vertKoala"));

    image2.vertFlip("redCloudImage.ppm", "vertFlipRedCloud.ppm");
    assertEquals(ImageUtil.readPPM("images/redCloudVertFlipTest.ppm"),
        image2.getLoad("vertFlipRedCloud.ppm"));

    image3.vertFlip("mario.ppm", "vertFlipMario.ppm");
    assertEquals(ImageUtil.readPPM("images/marioVertFlipTest.ppm"),
        image3.getLoad("vertFlipMario.ppm"));
  }

  @Test
  public void testHorizFlip() {
    initData();
    image1.horizFlip("koala", "horizKoala");
    assertEquals(ImageUtil.readPPM("images/koala-horizontal.ppm"),
        image1.getLoad("horizKoala"));

    image2.horizFlip("redCloudImage.ppm", "horizFlipRedCloud.ppm");
    assertEquals(ImageUtil.readPPM("images/redCloudHorizFlipTest.ppm"),
        image2.getLoad("horizFlipRedCloud.ppm"));

    image3.horizFlip("mario.ppm", "horizFlipMario.ppm");
    assertEquals(ImageUtil.readPPM("images/marioHorizFlipTest.ppm"),
        image3.getLoad("horizFlipMario.ppm"));
  }

  @Test
  public void testGreyscale() {
    initData();
    image1.greyscale("koala", "greyscaleKoala", 1);
    assertEquals(ImageUtil.readPPM("images/koala-green-greyscale.ppm"),
        image1.getLoad("greyscaleKoala"));

    image2.greyscale("redCloudImage.ppm", "greyscaleRedCloud.ppm", 1);
    assertEquals(ImageUtil.readPPM("images/redCloudGreyscaleTest.ppm"),
        image2.getLoad("greyscaleRedCloud.ppm"));

    image3.greyscale("mario.ppm", "greyscaleMario.ppm", 1);
    assertEquals(ImageUtil.readPPM("images/marioGreyscaleTest.ppm"),
        image3.getLoad("greyscaleMario.ppm"));
  }

  @Test
  public void testLuma() {
    initData();
    image1.luma("koala", "lumaKoala");
    assertEquals(ImageUtil.readPPM("images/koala-luma-greyscale.ppm"),
        image1.getLoad("lumaKoala"));

    image2.luma("redCloudImage.ppm", "lumaRedCloud.ppm");
    assertEquals(ImageUtil.readPPM("images/redCloudLumaTest.ppm"),
        image2.getLoad("lumaRedCloud.ppm"));

    image3.luma("mario.ppm", "lumaMario.ppm");
    assertEquals(ImageUtil.readPPM("images/marioLumaTest.ppm"),
        image3.getLoad("lumaMario.ppm"));
  }

  @Test
  public void testValue() {
    initData();
    image1.value("koala", "valueKoala");
    assertEquals(ImageUtil.readPPM("images/koala-value-greyscale.ppm"),
        image1.getLoad("valueKoala"));

    image2.value("redCloudImage.ppm", "valueRedCloud.ppm");
    assertEquals(ImageUtil.readPPM("images/redCloudValueTest.ppm"),
        image2.getLoad("valueRedCloud.ppm"));

    image3.value("mario.ppm", "valueMario.ppm");
    assertEquals(ImageUtil.readPPM("images/marioValueTest.ppm"),
        image3.getLoad("valueMario.ppm"));
  }

  @Test
  public void testIntensity() {
    initData();
    image1.intensity("koala", "intensityKoala");
    assertEquals(ImageUtil.readPPM("images/koala-intensity-greyscale.ppm"),
        image1.getLoad("intensityKoala"));

    image2.intensity("redCloudImage.ppm", "intensityRedCloud.ppm");
    assertEquals(ImageUtil.readPPM("images/redCloudIntensityTest.ppm"),
        image2.getLoad("intensityRedCloud.ppm"));

    image3.intensity("mario.ppm", "intensityMario.ppm");
    assertEquals(ImageUtil.readPPM("images/marioIntensityTest.ppm"),
        image3.getLoad("intensityMario.ppm"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLoadInvalid() {
    image1.getLoad("NoFile");
  }

}