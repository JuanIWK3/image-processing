import java.util.Collections;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Filters {
  private int[][] matR;
  private int[][] matG;
  private int[][] matB;

  // first image should be bigger than the second image
  public Vector<int[][]> insertImage(Vector<int[][]> firstfRGB, Vector<int[][]> secondRGB) {
    int[][] matR = firstfRGB.elementAt(0);
    int[][] matG = firstfRGB.elementAt(1);
    int[][] matB = firstfRGB.elementAt(2);

    int[][] matR2 = secondRGB.elementAt(0);
    int[][] matG2 = secondRGB.elementAt(1);
    int[][] matB2 = secondRGB.elementAt(2);

    // the second image is a person and a white background
    // we need to remove the white background
    // and insert the person in the first image at the center of the second image

    // check if the second image is bigger
    if (matR.length < matR2.length || matR[0].length < matR2[0].length) {
      System.out.println("The first image should be bigger than the second image");
      return null;
    }

    int[][] newR = new int[matR.length][matR[0].length];
    int[][] newG = new int[matR.length][matR[0].length];
    int[][] newB = new int[matR.length][matR[0].length];

    for (int i = 0; i < matR.length; i++) {
      for (int j = 0; j < matR[0].length; j++) {
        if (i >= (matR.length - matR2.length) / 2 && i < (matR.length + matR2.length) / 2
            && j >= (matR[0].length - matR2[0].length) / 2
            && j < (matR[0].length + matR2[0].length) / 2) {

          int srcI = i - (matR.length - matR2.length) / 2;
          int srcJ = j - (matR[0].length - matR2[0].length) / 2;

          if (matR2[srcI][srcJ] < 245 || matG2[srcI][srcJ] < 245 || matB2[srcI][srcJ] < 245) {
            newR[i][j] = matR2[srcI][srcJ];
            newG[i][j] = matG2[srcI][srcJ];
            newB[i][j] = matB2[srcI][srcJ];
          } else {
            newR[i][j] = matR[i][j];
            newG[i][j] = matG[i][j];
            newB[i][j] = matB[i][j];
          }
        } else {
          newR[i][j] = matR[i][j];
          newG[i][j] = matG[i][j];
          newB[i][j] = matB[i][j];
        }
      }
    }

    return new Vector<int[][]>(3) {
      {
        add(newR);
        add(newG);
        add(newB);
      }
    };
  }

  public int[][] gradientFilter(Vector<int[][]> rgbMat) {
    matR = rgbMat.elementAt(0);

    int[][] mat = new int[matR.length][matR[0].length];

    int[][] gradientX = { { -1, 0, 1 }, { -1, 0, 1 }, { -1, 0, 1 } };
    int[][] gradientY = { { -1, -1, -1 }, { 0, 0, 0 }, { 1, 1, 1 } };

    for (int i = 0; i < matR.length; i++) {
      for (int j = 0; j < matR[0].length; j++) {
        int sumX = 0;
        int sumY = 0;

        for (int k = i - 1; k <= i + 1; k++) {
          for (int l = j - 1; l <= j + 1; l++) {
            if (k >= 0 && k < matR.length && l >= 0 && l < matR[0].length) {
              sumX += matR[k][l] * gradientX[k - i + 1][l - j + 1];
              sumY += matR[k][l] * gradientY[k - i + 1][l - j + 1];
            }
          }
        }

        mat[i][j] = (int) Math.sqrt(sumX * sumX + sumY * sumY);
      }
    }

    return mat;
  }

  public int[][] sobel(Vector<int[][]> rgbMat) {
    int[][] binary = binary(rgbMat);
    matR = binary;

    int[][] mat = new int[matR.length][matR[0].length];
    int[][] mat2 = new int[matR.length][matR[0].length];

    int[][] sobelX = { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };
    int[][] sobelY = { { -1, -2, -1 }, { 0, 0, 0 }, { 1, 2, 1 } };

    for (int i = 0; i < matR.length; i++) {
      for (int j = 0; j < matR[0].length; j++) {
        int sumX = 0;
        int sumY = 0;

        for (int k = i - 1; k <= i + 1; k++) {
          for (int l = j - 1; l <= j + 1; l++) {
            if (k >= 0 && k < matR.length && l >= 0 && l < matR[0].length) {
              sumX += matR[k][l] * sobelX[k - i + 1][l - j + 1];
              sumY += matR[k][l] * sobelY[k - i + 1][l - j + 1];
            }
          }
        }

        mat[i][j] = (int) Math.sqrt(sumX * sumX + sumY * sumY);
        mat2[i][j] = (int) Math.sqrt(sumX * sumX + sumY * sumY);
      }
    }

    return mat;
  }

  public int[][] smoothByMedian(Vector<int[][]> rgbMat, int size) {
    int[][] matR = rgbMat.elementAt(0);

    int[][] mat = new int[matR.length][matR[0].length];

    for (int i = 0; i < matR.length; i++) {
      for (int j = 0; j < matR[0].length; j++) {
        Vector<Integer> neighborhood = new Vector<>();

        for (int k = i - size / 2; k <= i + size / 2; k++) {
          for (int l = j - size / 2; l <= j + size / 2; l++) {
            if (k >= 0 && k < matR.length && l >= 0 && l < matR[0].length) {
              neighborhood.add(matR[k][l]);
            }
          }
        }

        Collections.sort(neighborhood);
        int medianIndex = neighborhood.size() / 2;
        mat[i][j] = neighborhood.get(medianIndex);
      }
    }

    return mat;
  }

  public int[][] smoothByAverage(Vector<int[][]> rgbMat, int size) {
    matR = rgbMat.elementAt(0);
    matG = rgbMat.elementAt(1);
    matB = rgbMat.elementAt(2);

    int[][] mat = new int[matR.length][matR[0].length];

    for (int i = 0; i < matR.length; i++) {
      for (int j = 0; j < matR[0].length; j++) {
        int sumR = 0;
        int count = 0;

        for (int k = i - size / 2; k <= i + size / 2; k++) {
          for (int l = j - size / 2; l <= j + size / 2; l++) {
            if (k >= 0 && k < matR.length && l >= 0 && l < matR[0].length) {
              sumR += matR[k][l];
              count++;
            }
          }
        }

        mat[i][j] = (int) (sumR / count);
      }
    }

    return mat;
  }

  public Vector<int[][]> gaussianFilter(Vector<int[][]> rgbMat, int size) {
    matR = rgbMat.elementAt(0);
    matG = rgbMat.elementAt(1);
    matB = rgbMat.elementAt(2);

    int[][] mat = new int[matR.length][matR[0].length];
    int[][] mat2 = new int[matR.length][matR[0].length];
    int[][] mat3 = new int[matR.length][matR[0].length];

    double[][] gaussian = { { 1, 2, 1 }, { 2, 4, 2 }, { 1, 2, 1 } };

    for (int i = 0; i < matR.length; i++) {
      for (int j = 0; j < matR[0].length; j++) {
        double sumR = 0;
        double sumG = 0;
        double sumB = 0;

        double sum = 0;

        for (int k = i - size / 2; k <= i + size / 2; k++) {
          for (int l = j - size / 2; l <= j + size / 2; l++) {
            if (k >= 0 && k < matR.length && l >= 0 && l < matR[0].length) {
              sumR += matR[k][l] * gaussian[Math.abs(k - i + 1) % 3][Math.abs(l - j + 1) % 3];
              sumG += matG[k][l] * gaussian[Math.abs(k - i + 1) % 3][Math.abs(l - j + 1) % 3];
              sumB += matB[k][l] * gaussian[Math.abs(k - i + 1) % 3][Math.abs(l - j + 1) % 3];
              sum += gaussian[Math.abs(k - i + 1) % 3][Math.abs(l - j + 1) % 3];
            }

          }
        }

        mat[i][j] = (int) (sumR / sum);
        mat2[i][j] = (int) (sumG / sum);
        mat3[i][j] = (int) (sumB / sum);
      }
    }

    return new Vector<int[][]>(3) {
      {
        add(mat);
        add(mat2);
        add(mat3);
      }
    };
  }

  public int[][] grayScale(Vector<int[][]> rgbMat) {
    matR = rgbMat.elementAt(0);
    matG = rgbMat.elementAt(1);
    matB = rgbMat.elementAt(2);

    int[][] mat = new int[matR.length][matR[0].length];

    for (int i = 0; i < matR.length; i++) {
      for (int j = 0; j < matR[0].length; j++) {
        mat[i][j] = (matR[i][j] + matG[i][j] + matB[i][j]) / 3;
      }
    }

    return mat;
  }

  public int[][] darkGrayScale(Vector<int[][]> rgbMat) {
    matR = rgbMat.elementAt(0);
    matG = rgbMat.elementAt(1);
    matB = rgbMat.elementAt(2);

    int[][] mat = new int[matR.length][matR[0].length];

    for (int i = 0; i < matR.length; i++) {
      for (int j = 0; j < matR[0].length; j++) {
        if (matR[i][j] < matG[i][j] && matR[i][j] < matB[i][j]) {
          mat[i][j] = matR[i][j];
        } else if (matG[i][j] < matR[i][j] && matG[i][j] < matB[i][j]) {
          mat[i][j] = matG[i][j];
        } else {
          mat[i][j] = matB[i][j];
        }
      }
    }

    return mat;
  }

  public int[][] lightGrayScale(Vector<int[][]> rgbMat) {
    matR = rgbMat.elementAt(0);
    matG = rgbMat.elementAt(1);
    matB = rgbMat.elementAt(2);

    int[][] mat = new int[matR.length][matR[0].length];

    for (int i = 0; i < matR.length; i++) {
      for (int j = 0; j < matR[0].length; j++) {
        if (matR[i][j] > matG[i][j] && matR[i][j] > matB[i][j]) {
          mat[i][j] = matR[i][j];
        } else if (matG[i][j] > matR[i][j] && matG[i][j] > matB[i][j]) {
          mat[i][j] = matG[i][j];
        } else {
          mat[i][j] = matB[i][j];
        }
      }
    }

    return mat;
  }

  public int[][] binary(Vector<int[][]> rgbMat) {
    matR = rgbMat.elementAt(0);
    matG = rgbMat.elementAt(1);
    matB = rgbMat.elementAt(2);

    int[][] mat = new int[matR.length][matR[0].length];

    for (int i = 0; i < matR.length; i++) {
      for (int j = 0; j < matR[0].length; j++) {
        if (matR[i][j] > 127) {
          mat[i][j] = 255;
        } else {
          mat[i][j] = 0;
        }
      }
    }

    return mat;
  }

  public int[][] negative(Vector<int[][]> rgbMat) {
    matR = rgbMat.elementAt(0);
    matG = rgbMat.elementAt(1);
    matB = rgbMat.elementAt(2);

    int[][] mat = new int[matR.length][matR[0].length];

    for (int i = 0; i < matR.length; i++) {
      for (int j = 0; j < matR[0].length; j++) {
        mat[i][j] = 255 - matR[i][j];
      }
    }

    return mat;
  }

  public int[][][] dominantColor(Vector<int[][]> rgbMat) {
    matR = rgbMat.elementAt(0);
    matG = rgbMat.elementAt(1);
    matB = rgbMat.elementAt(2);

    int[][] mat = new int[matR.length][matR[0].length];
    int[][] mat2 = new int[matR.length][matR[0].length];
    int[][] mat3 = new int[matR.length][matR[0].length];

    for (int i = 0; i < matR.length; i++) {
      for (int j = 0; j < matR[0].length; j++) {
        if (matR[i][j] > matG[i][j] && matR[i][j] > matB[i][j]) {
          mat[i][j] = 255;
          mat2[i][j] = 0;
          mat3[i][j] = 0;
        } else if (matG[i][j] > matR[i][j] && matG[i][j] > matB[i][j]) {
          mat[i][j] = 0;
          mat2[i][j] = 255;
          mat3[i][j] = 0;
        } else {
          mat[i][j] = 0;
          mat2[i][j] = 0;
          mat3[i][j] = 255;
        }
      }
    }

    return new int[][][] { mat, mat2, mat3 };
  }

  public int[][][] userFilter(Vector<int[][]> rgbMat) {
    matR = rgbMat.elementAt(0);
    matG = rgbMat.elementAt(1);
    matB = rgbMat.elementAt(2);

    int[][] mat = new int[matR.length][matR[0].length];
    int[][] mat2 = new int[matR.length][matR[0].length];
    int[][] mat3 = new int[matR.length][matR[0].length];

    String[] options = { "R", "G", "B" };
    int response = JOptionPane.showOptionDialog(null, "Escolha uma cor", "Filtro",
        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    String option = options[response];

    for (int i = 0; i < matR.length; i++) {
      for (int j = 0; j < matR[0].length; j++) {
        if (option == "R") {
          if (matR[i][j] > 167) {
            mat[i][j] = matR[i][j];
            mat2[i][j] = matG[i][j];
            mat3[i][j] = matB[i][j];
          } else {
            mat[i][j] = (int) (0.3 * matR[i][j] + 0.59 * matG[i][j] + 0.11 * matB[i][j]);
            mat2[i][j] = mat[i][j];
            mat3[i][j] = mat[i][j];
          }
        } else if (option == "G") {
          if (matG[i][j] > 167) {
            mat[i][j] = matR[i][j];
            mat2[i][j] = matG[i][j];
            mat3[i][j] = matB[i][j];
          } else {
            mat[i][j] = (int) (0.3 * matR[i][j] + 0.59 * matG[i][j] + 0.11 * matB[i][j]);
            mat2[i][j] = mat[i][j];
            mat3[i][j] = mat[i][j];
          }
        } else {
          if (matB[i][j] > 167) {
            mat[i][j] = matR[i][j];
            mat2[i][j] = matG[i][j];
            mat3[i][j] = matB[i][j];
          } else {
            mat[i][j] = (int) (0.3 * matR[i][j] + 0.59 * matG[i][j] + 0.11 * matB[i][j]);
            mat2[i][j] = mat[i][j];
            mat3[i][j] = mat[i][j];
          }
        }
      }
    }

    return new int[][][] { mat, mat2, mat3 };
  }

  public int[][][] removeColor(Vector<int[][]> rgbMat) {
    matR = rgbMat.elementAt(0);
    matG = rgbMat.elementAt(1);
    matB = rgbMat.elementAt(2);

    int[][] mat = new int[matR.length][matR[0].length];
    int[][] mat2 = new int[matR.length][matR[0].length];
    int[][] mat3 = new int[matR.length][matR[0].length];

    String[] options = { "R", "G", "B" };
    int response = JOptionPane.showOptionDialog(null, "Escolha uma cor", "Filtro",
        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    String option = options[response];

    for (int i = 0; i < matR.length; i++) {
      for (int j = 0; j < matR[0].length; j++) {
        if (option == "R") {
          mat[i][j] = 0;
          mat2[i][j] = matG[i][j];
          mat3[i][j] = matB[i][j];
        } else if (option == "G") {
          mat[i][j] = matR[i][j];
          mat2[i][j] = 0;
          mat3[i][j] = matB[i][j];
        } else {
          mat[i][j] = matR[i][j];
          mat2[i][j] = matG[i][j];
          mat3[i][j] = 0;
        }
      }
    }

    return new int[][][] { mat, mat2, mat3 };
  }

  public int[][][] removeDominantColor(Vector<int[][]> rgbMat) {
    matR = rgbMat.elementAt(0);
    matG = rgbMat.elementAt(1);
    matB = rgbMat.elementAt(2);

    int[][] mat = new int[matR.length][matR[0].length];
    int[][] mat2 = new int[matR.length][matR[0].length];
    int[][] mat3 = new int[matR.length][matR[0].length];

    String[] options = { "R", "G", "B" };
    int response = JOptionPane.showOptionDialog(null, "Escolha uma cor", "Filtro",
        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    String option = options[response];

    for (int i = 0; i < matR.length; i++) {
      for (int j = 0; j < matR[0].length; j++) {
        if (option == "R") {
          if (matR[i][j] > matG[i][j] && matR[i][j] > matB[i][j]) {
            mat[i][j] = (int) (0.3 * matR[i][j] + 0.59 * matG[i][j] + 0.11 * matB[i][j]);
            mat2[i][j] = mat[i][j];
            mat3[i][j] = mat[i][j];
          } else {
            mat[i][j] = matR[i][j];
            mat2[i][j] = matG[i][j];
            mat3[i][j] = matB[i][j];
          }
        } else if (option == "G") {
          if (matG[i][j] > matR[i][j] && matG[i][j] > matB[i][j]) {
            mat[i][j] = (int) (0.3 * matR[i][j] + 0.59 * matG[i][j] + 0.11 * matB[i][j]);
            mat2[i][j] = mat[i][j];
            mat3[i][j] = mat[i][j];
          } else {
            mat[i][j] = matR[i][j];
            mat2[i][j] = matG[i][j];
            mat3[i][j] = matB[i][j];
          }
        } else {
          if (matB[i][j] > matR[i][j] && matB[i][j] > matG[i][j]) {
            mat[i][j] = (int) (0.3 * matR[i][j] + 0.59 * matG[i][j] + 0.11 * matB[i][j]);
            mat2[i][j] = mat[i][j];
            mat3[i][j] = mat[i][j];
          } else {
            mat[i][j] = matR[i][j];
            mat2[i][j] = matG[i][j];
            mat3[i][j] = matB[i][j];
          }
        }
      }
    }

    return new int[][][] { mat, mat2, mat3 };
  }
}
