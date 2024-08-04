package model;

import org.junit.Test;

import static model.MatrixUtil.multiplication;
import static model.MatrixUtil.multiplyEachElement;
import static model.MatrixUtil.sumElements;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the MatrixUtil class.
 */
public class MatrixUtilTest {

  double[][] m1 = {{2, 2, 2},
      {2, 2, 2},
      {2, 2, 2}};

  double[][] m2 = {{1, 1, 1},
      {1, 1, 1},
      {1, 1, 1}};

  double[][] m1and2 = {{2, 2, 2},
      {2, 2, 2},
      {2, 2, 2}};

  double[][] mult1 = {{1, 2, 3},
      {1, 2, 3},
      {1, 2, 3}};

  double[][] mult2 = {{0, 0, 1},
      {0, 1, 0},
      {1, 0, 0}};

  double[][] multResult = {{3, 2, 1},
      {3, 2, 1},
      {3, 2, 1}};

  @Test
  public void testMultiplyEachElement() {
    assertEquals(m1and2, multiplyEachElement(m1, m2));
  }

  @Test
  public void testSumElements() {
    assertEquals(18, sumElements(m1and2));
  }

  @Test
  public void testMultiplication() {
    assertEquals(multResult, multiplication(mult1, mult2));
  }

}