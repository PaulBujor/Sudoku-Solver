import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Solver {
    public static int[][] load(String filename) throws FileNotFoundException {
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

    public static void main(String[] args) {
        
    }
}
