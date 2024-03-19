public class App {

    public static void main(String[] args) throws Exception {
        int[][] rainhas = new int[8][8];

        printMatrix(rainhas);

        for (int i = 0; i < rainhas.length; i++) {
            for (int j = 0; j < rainhas.length; j++) {
                if (rainhas[i][j] == 0) {
                    addQueen(rainhas, i, j);
                    printMatrix(rainhas);
                }
            }
        }

        System.out.println("Queens placed:" + queensPlaced(rainhas));

    }

    static void printMatrix(int[][] rainhas) {
        for (int i = 0; i < rainhas.length; i++) {
            for (int j = 0; j < rainhas.length; j++) {
                if (rainhas[i][j] == 1) {
                    System.out.print(ANSI_GREEN + rainhas[i][j] + " " + ANSI_WHITE);
                } else if (rainhas[i][j] == 2) {
                    System.out.print(ANSI_RED + rainhas[i][j] + " " + ANSI_WHITE);
                } else {
                    System.out.print(rainhas[i][j] + " ");
                }
            }
            System.out.println();
        }

        System.out.println("Empty: " + heuristica(rainhas) + "\n");
    }

    static int heuristica(int[][] rainhas) {
        int count = 0;

        for (int i = 0; i < rainhas.length; i++) {
            for (int j = 0; j < rainhas.length; j++) {
                if (rainhas[i][j] == 0) {
                    count++;
                }
            }
        }

        return count;
    }

    static int[][] addQueen(int[][] rainhas, int line, int col) {
        for (int i = 0; i < rainhas.length; i++) {
            for (int j = 0; j < rainhas.length; j++) {
                if (i == line ||
                        j == col ||
                        (Math.abs(line - i) == Math.abs(col - j))) {
                    rainhas[i][j] = 2;
                }
            }
        }

        rainhas[line][col] = 1;

        return rainhas;
    }

    static int queensPlaced(int[][] rainhas) {
        int count = 0;

        for (int i = 0; i < rainhas.length; i++) {
            for (int j = 0; j < rainhas.length; j++) {
                if (rainhas[i][j] == 1) {
                    count++;
                }
            }
        }

        return count;
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
}
