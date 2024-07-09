import java.util.Vector;

import javax.swing.JOptionPane;

public class Filters {
  private int[][] matR;
  private int[][] matG;
  private int[][] matB;

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
