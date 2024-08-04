package model;

/**
 * Contains all the methods that uses matrices.
 */
public class MatrixUtil {

  /**
   * Constructs a matrix that based on the radius.
   *
   * @param img the image being used.
   * @param r   the radius of the matrix.
   * @param rgb the value of rgb.
   * @param y   the y coordinate of the main pixel.
   * @param x   the x coordinate of the main pixel.
   * @return a new matrix.
   */
  public static double[][] constructMatrix(Image img, int r, int rgb, int y, int x) {
    double[][] matrix = new double[2 * r + 1][2 * r + 1];
    for (int i = 0; i <= 2 * r; i++) {
      for (int j = 0; j <= 2 * r; j++) {
        // checks if pixel is in bounds else sets value to 0
        try {
          matrix[i][j] = img.getPixel(y + i - r, x + j - r)[rgb];
        } catch (IllegalArgumentException e) {
          matrix[i][j] = 0;
        }
      }
    }
    return matrix;
  }

  /**
   * Multiplies corresponding values between two matrices.
   *
   * @param matrix1 one of the matrices.
   * @param matrix2 one of the matrices.
   * @return a matrix with all the new values.
   */
  public static double[][] multiplyEachElement(double[][] matrix1, double[][] matrix2)
      throws IllegalArgumentException {
    if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
      throw new IllegalArgumentException("Matrices must be equal in size.");
    }
    double[][] res = new double[matrix1.length][matrix1[0].length];
    for (int i = 0; i < matrix1.length; i++) {
      for (int j = 0; j < matrix1[0].length; j++) {
        res[i][j] = matrix1[i][j] * matrix2[i][j];
      }
    }
    return res;
  }

  /**
   * Adds up all the elements in a matrix.
   *
   * @param matrix the matrix with all the values.
   * @return the sum of all the elements as an int.
   */
  public static int sumElements(double[][] matrix) {
    double sum = 0;
    for (double[] doubles : matrix) {
      for (double e : doubles) {
        sum += e;
      }
    }
    if (sum > 255) {
      sum = 255;
    } else if (sum < 0) {
      sum = 0;
    }
    return (int) sum;
  }

  /**
   * Multiplies two matrices.
   *
   * @param matrix1 one of the matrices.
   * @param matrix2 one of the matrices.
   * @return a matrix.
   */
  public static double[][] multiplication(double[][] matrix1, double[][] matrix2)
      throws IllegalArgumentException {
    if (matrix1[0].length != matrix2.length) {
      throw new IllegalArgumentException(
          "Invalid multiplication. Dimensions are not compatible for multiplication.");
    }
    int n = matrix2.length;
    double[][] res = new double[matrix1.length][matrix2[0].length];
    for (int i = 0; i < res.length; i++) {
      for (int j = 0; j < res[0].length; j++) {
        for (int k = 0; k < n; k++) {
          res[i][j] += (matrix1[i][k] * matrix2[k][j]);
        }
      }
    }
    return res;
  }
}
