import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Solver {

    private static int[][] load(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner input = new Scanner(file);
        int[][] matrix = new int[9][9];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                matrix[i][j] = input.nextInt();
        input.close();
        return matrix;
    }

    public static void save(int[][] matrix, String filename) throws FileNotFoundException {
        File file = new File(filename);
        PrintWriter output = new PrintWriter(file);
        for (int i = 0; i < 9; i++) {
            output.println();
            for (int j = 0; j < 9; j++)
                output.print(matrix[i][j]);
        }
    }

    public static boolean valid(int[][][] presenceMatrix, int k) {
        for (int i = 0; i < 9; i++) {
            int presencePos = ((k % 9) / 3) + ((k / 9) / 3);
//            System.out.println(presenceMatrix[0][k / 9][i] > 1);
//            System.out.println(presenceMatrix[1][k % 9][i] > 1);
//            System.out.println(presenceMatrix[2][presencePos][i] > 1);
            if (presenceMatrix[0][k / 9][i] > 1 || presenceMatrix[1][k % 9][i] > 1 || presenceMatrix[2][presencePos][i] > 1)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Starting");
        boolean canRun = true;
        int[][] matrix = new int[9][9];
        try {
            matrix = load("matrixInput.txt");
            System.out.println("File loaded");
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file, stopping");
            canRun = false;
        }
        if (canRun) {
            int[][] canModify = new int[9][9];
            for (int i = 0; i < 9; i++)
                System.arraycopy(matrix[i], 0, canModify[i], 0, 9);
            int[][][] presence = new int[3][9][9];
            for (int k = 0; k < 81; k++) {
                int presencePos = ((k % 9) / 3) + ((k / 9) / 3);
                if (matrix[k / 9][k % 9] != 0) {
                    presence[0][k / 9][matrix[k / 9][k % 9] - 1] = 1;
                    presence[1][k % 9][matrix[k / 9][k % 9] - 1] = 1;
                    presence[2][presencePos][matrix[k / 9][k % 9] - 1] = 1;
                }
            }

            //backtracking start
            boolean solutionFound = false;
            int k = 0;
            while (k >= 0 && !solutionFound) {
                System.out.println(k);
                if (matrix[k / 9][k % 9] < 9) {
                    if (canModify[k / 9][k % 9] == 0) {
                        int presencePos = ((k % 9) / 3) + ((k / 9) / 3);
                        if (matrix[k / 9][k % 9] > 0) {
                            presence[0][k / 9][matrix[k / 9][k % 9] - 1]--;
                            presence[1][k % 9][matrix[k / 9][k % 9] - 1]--;
                            presence[2][presencePos][matrix[k / 9][k % 9] - 1]--;
                        }
                        matrix[k / 9][k % 9]++;
                        presence[0][k / 9][matrix[k / 9][k % 9] - 1]++;
                        presence[1][k % 9][matrix[k / 9][k % 9] - 1]++;
                        presence[2][presencePos][matrix[k / 9][k % 9] - 1]++;
                    }
//                    System.out.println(matrix[k / 9][k % 9]);
                    if (valid(presence, k)) {
                        if (k == 80)
                            solutionFound = true;
                        else {
                            do {
                                k++;
                            } while (canModify[k / 9][k % 9] != 0);
                        }
                    }
                } else {
                    presence[0][k / 9][matrix[k / 9][k % 9] - 1] = 0;
                    presence[1][k % 9][matrix[k / 9][k % 9] - 1] = 0;
                    presence[2][((k % 9) / 3) + ((k / 9) / 3)][matrix[k / 9][k % 9] - 1] = 0;
                    matrix[k / 9][k % 9] = 0;
                    do {
                        k--;
                        if(k < 0) break;
                        System.out.println(k);
                    } while (canModify[k / 9][k % 9] != 0);
                }
            }
            for (int i = 0; i < 81; i++) {
                if (i % 9 == 0)
                    System.out.println();
                System.out.print(matrix[i / 9][i % 9]);
            }
        }
        System.out.println("Program stopped");
    }
}
