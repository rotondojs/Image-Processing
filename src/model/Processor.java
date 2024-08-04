package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Contains all the methods to edit images.
 */
public class Processor implements ImageProcessor {

  private final Map<String, Image> imageMap = new HashMap<>();
  private final double[][] gaussianBlur = {{1. / 16, 1. / 8, 1. / 16},
      {1. / 8, 1. / 4, 1. / 8},
      {1. / 16, 1. / 8, 1. / 16}};

  private final double[][] sharpenFilter = {{-1. / 8, -1. / 8, -1. / 8, -1. / 8, -1. / 8},
      {-1. / 8, 1. / 4, 1. / 4, 1. / 4, -1. / 8},
      {-1. / 8, 1. / 4, 1., 1. / 4, -1. / 8},
      {-1. / 8, 1. / 4, 1. / 4, 1. / 4, -1. / 8},
      {-1. / 8, -1. / 8, -1. / 8, -1. / 8, -1. / 8}};

  private final double[][] lumaGreyscaleFilter = {{.2126, .7152, .0722},
      {.2126, .7152, .0722},
      {.2126, .7152, .0722}};

  private final double[][] sepiaFilter = {{.393, .769, .189},
      {.349, .686, .168},
      {.272, .534, .131}};


  @Override
  public void downscaling(String name, String newName, double height, double width)
      throws IllegalArgumentException{
    if (height <= 0 || width <= 0 || height > 1 || width > 1) {
      throw new IllegalArgumentException("The height and width have to be greater than zero," +
          " and less than or equal to one.");
    }
    Image img = imageMap.get(name);
    int newHeight = (int) (img.getHeight() * height);
    int newWidth = (int) (img.getWidth() * width);
    Image result = new Image(newHeight, newWidth, img.getMaxVal());

    for (int j = 0; j < result.getHeight(); j++) {
      for (int i = 0; i < result.getWidth(); i++) {
        double x = i/width, y = j/height;

        double doublePixHeight = i * (1/height) * height;
        double doublePixWidth = j * (1/width) * width;

        int[] xs = new int[]{Math.min(Math.max((int) x, 0), img.getWidth() - 1),
            Math.min(Math.max((int) Math.floor(x) + 1, 0), img.getWidth() - 1)};

        int[] ys = new int[]{Math.min(Math.max((int) y, 0), img.getHeight() - 1),
            Math.min(Math.max((int) Math.floor(y) + 1, 0), img.getHeight() - 1)};

        int[] A = img.getPixel(ys[0], xs[0]);
        int[] B = img.getPixel(ys[0], xs[1]);
        int[] C = img.getPixel(ys[1], xs[0]);
        int[] D = img.getPixel(ys[1], xs[1]);

        double[] m = new double[3];
        double[] n = new double[3];
        double[] cp = new double[3];
        for (int k = 0; k < 3; k++) {
          m[k] = B[k]*(x - Math.floor(x)) + A[k]*(Math.floor(x) + 1 - x);
          n[k] = D[k]*(x - Math.floor(x)) + C[k]*(Math.floor(x) + 1 - x);
          cp[k] = n[k]*(y - Math.floor(y)) + m[k]*(Math.floor(y) + 1 - y);
        }

        int[] out = new int[3];
        for (int l = 0; l < 3; l++) {
          out[l] = (int) cp[l];
        }

        int pixHeight = (int) doublePixHeight;
        int pixWidth = (int) doublePixWidth;

        result.setPixelRGB(pixWidth, pixHeight, out);

      }
    }
    imageMap.put(newName, result);
  }

  @Override
  public void manipulate(String name, String newName, Image mi, String filter, int val)
      throws IllegalArgumentException {
    if (mi == null || filter == null || val < 0) {
      throw new IllegalArgumentException("MI image and filter cannot be null, " +
          "and val has to be positive.");
    }
    Image img = imageMap.get(name);
    Image result = new Image(img);
    Image temp = new Image(img);
    imageMap.put("temp", temp);

    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        imageMap.get("temp").setPixelRGB(i, j, img.getPixel(i, j));
      }
    }

    switch (filter) {
      case "Brighten":
        brighten("temp", "tempo", val);
        break;
      case "Horizontal Flip":
        horizFlip("temp", "tempo");
        break;
      case "Vertical Flip":
        vertFlip("temp", "tempo");
        break;
      case "Blur":
        blur("temp", "tempo");
        break;
      case "Sharpen":
        sharpen("temp", "tempo");
        break;
      case "Intensity":
        intensity("temp", "tempo");
        break;
      case "Luma":
        luma("temp", "tempo");
        break;
      case "Greyscale":
        greyscale("temp", "tempo", val);
        break;
      case "Sepia":
        sepiaFilter("temp", "tempo");
        break;
      case "Value":
        value("temp", "tempo");
        break;
      default:
        break;
    }

    for (int i = 0; i < result.getHeight(); i++) {
      for (int j = 0; j < result.getWidth(); j++) {
        int[] miRGB = new int[3];
        int[] test1 = {0,0, 0};
        for (int k = 0; k < 3; k++) {
          miRGB[k] = mi.getPixel(i, j)[k];
        }
        int total = 0;
        for (int l = 0; l < 3; l++) {
          if (miRGB[l] == test1[l]) {
            total = total + 1;
          } else {
            total = 200;
          }
        }
        if (total == 3) {
          result.setPixelRGB(i, j, imageMap.get("tempo").getPixel(i, j));
        } else {
          result.setPixelRGB(i, j, img.getPixel(i, j));
        }
      }
    }
    imageMap.remove("temp");
    imageMap.remove("tempo");
    imageMap.put(newName, result);
  }

  @Override
  public void load(String name, String path) {
    imageMap.put(name, ImageUtil.readImage(path));
  }

  @Override
  public Image getLoad(String name) throws IllegalArgumentException {
    Image img = imageMap.getOrDefault(name, null);
    if (img == null) {
      throw new IllegalArgumentException(
          name + " is not loaded in the image. Make sure that the specified image is loaded and " +
              "that the name is correctly spelled.");
    }
    return img;
  }

  @Override
  public int getLoadSize() {
    return imageMap.size();
  }

  @Override
  public void save(String name, String path) {
    ImageUtil.writeImage(path, getLoad(name));
  }

  @Override
  public void brighten(String name, String newName, int brightVal) {
    Image img = imageMap.get(name);
    Image result = new Image(img);
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        int[] rgb = img.getPixel(i, j);
        int[] rgbBright = new int[3];
        for (int k = 0; k < rgbBright.length; k++) {
          rgbBright[k] = rgb[k] + brightVal;
          if (rgbBright[k] > 255) {
            rgbBright[k] = 255;
          } else if (rgbBright[k] < 0) {
            rgbBright[k] = 0;
          }
        }
        result.setPixelRGB(i, j, rgbBright);
      }
    }
    imageMap.put(newName, result);
  }


  @Override
  public void vertFlip(String name, String newName) {
    Image img = imageMap.get(name);
    Image result = new Image(img);
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        result.setPixelRGB(img.getHeight() - 1 - i, j, img.getPixel(i, j));
      }
    }
    imageMap.put(newName, result);
  }

  @Override
  public void horizFlip(String name, String newName) {
    Image img = imageMap.get(name);
    Image result = new Image(img);
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        result.setPixelRGB(i, img.getWidth() - 1 - j, img.getPixel(i, j));
      }
      imageMap.put(newName, result);
    }
  }

  @Override
  public Image greyscale(String name, String newName, int color) {
    Image img = imageMap.get(name);
    Image result = new Image(img);
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        int[] pixel = img.getPixel(i, j);
        int greyVal = pixel[color];
        result.setPixelRGB(i, j, new int[]{greyVal, greyVal, greyVal});
      }
    }
    imageMap.put(newName, result);
    return result;
  }


  @Override
  public void luma(String name, String newName) {
    Image img = imageMap.get(name);
    Image result = new Image(img);
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        int[] pixel = img.getPixel(i, j);
        int lumaPixel = (int) (pixel[0] * 0.2126 + pixel[1] * 0.7152 + pixel[2] * 0.0722);
        result.setPixelRGB(i, j, new int[]{lumaPixel, lumaPixel, lumaPixel});
      }
    }
    imageMap.put(newName, result);
  }

  @Override
  public void value(String name, String newName) {
    Image img = imageMap.get(name);
    Image result = new Image(img);
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        int[] pixel = img.getPixel(i, j);
        int maxPixel = Math.max(pixel[2], Math.max(pixel[0], pixel[1]));
        result.setPixelRGB(i, j, new int[]{maxPixel, maxPixel, maxPixel});
      }
    }
    imageMap.put(newName, result);
  }

  @Override
  public Image intensity(String name, String newName) {
    Image img = imageMap.get(name);
    Image result = new Image(img);
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        int[] pixel = img.getPixel(i, j);
        int intensityPixel = (pixel[0] + pixel[1] + pixel[2]) / 3;
        result.setPixelRGB(i, j, new int[]{intensityPixel, intensityPixel, intensityPixel});
      }
    }
    imageMap.put(newName, result);
    return result;
  }

  @Override
  public void blur(String name, String newName) {
    imageMap.put(newName, applyFilter(name, gaussianBlur, 1));
  }

  @Override
  public void sharpen(String name, String newName) {
    imageMap.put(newName, applyFilter(name, sharpenFilter, 2));
  }

  @Override
  public void greyscaleByFilter(String name, String newName) {
    imageMap.put(newName, colorTransformation(name, lumaGreyscaleFilter));
  }

  @Override
  public void sepiaFilter(String name, String newName) {
    imageMap.put(newName, colorTransformation(name, sepiaFilter));
  }

  /**
   * A helper method for lumaGreyscale and sepia filters.
   *
   * @param name   is the name of the image.
   * @param filter is the filter depending on the kernel.
   * @return an image.
   */
  private Image colorTransformation(String name, double[][] filter) {
    Image img = imageMap.get(name);
    Image result = new Image(img);
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        double[][] rgb = {{img.getPixel(i, j)[0]},
            {img.getPixel(i, j)[1]},
            {img.getPixel(i, j)[2]}};
        double[][] rgbResult = MatrixUtil.multiplication(filter, rgb);
        int r = (int) rgbResult[0][0];
        int g = (int) rgbResult[1][0];
        int b = (int) rgbResult[2][0];
        result.setPixelRGB(i, j, new int[]{r, g, b});
      }
    }
    return result;
  }

  /**
   * A helper method for blur and sharpen image.
   *
   * @param name   is the name of the image.
   * @param filter is the filter depending on the kernel.
   * @param r      the radius of the matrix.
   * @return an image.
   */
  private Image applyFilter(String name, double[][] filter, int r) {
    Image img = imageMap.get(name);
    Image result = new Image(img);
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        int[] rgb = new int[3];
        for (int k = 0; k < 3; k++) {
          rgb[k] = MatrixUtil.sumElements(MatrixUtil.multiplyEachElement(
              MatrixUtil.constructMatrix(img, r, k, i, j), filter));
        }
        result.setPixelRGB(i, j, rgb);
      }
    }
    return result;
  }

  @Override
  public List<String> getLoadKeys() {
    List<String> keys = new ArrayList<>(imageMap.keySet());
    return keys;
  }

  @Override
  public int[] getHistogramRed(String name) {
    return getHistogram(greyscale(name, "red", 0));
  }

  @Override
  public int[] getHistogramGreen(String name) {
    return getHistogram(greyscale(name, "green", 1));
  }

  @Override
  public int[] getHistogramBlue(String name) {
    return getHistogram(greyscale(name, "blue", 2));
  }

  @Override
  public int[] getHistogramIntensity(String name) {
    return getHistogram(intensity(name, "intensity"));
  }

  private int[] getHistogram(Image img) throws IllegalArgumentException {
    int[] out = new int[256];
    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {
        int[] p = img.getPixel(y, x);
        if (p[0] != p[1] || p[1] != p[2]) {
          throw new IllegalArgumentException("Not a greyscale image.");
        }
        out[p[1] * 256 / (img.getMaxValue() + 1)] += 1;
      }
    }
    return out;
  }
}
