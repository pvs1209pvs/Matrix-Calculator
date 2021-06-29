package matrixoperations;

import java.util.List;


public class Matrix {

    private final double[][] matrix;
    final int ROW_LEN;
    final int COL_LEN;

    public Matrix(int rows, int cols) {
        this.ROW_LEN = rows;
        this.COL_LEN = cols;
        matrix = new double[rows][cols];
    }

    public void fillRow(int i, List<Double> numbers) {
        for (int j = 0; j < COL_LEN; j++) {
            matrix[i][j] = numbers.get(j);
        }
    }

    double[] getRow(int i) {
        return matrix[i];
    }

    void set(int i, int j, double v) {
        matrix[i][j] = v;
    }

    double get(int i, int j) {
        return matrix[i][j];
    }

    void deepCopy(Matrix from) {

        for (int i = 0; i < from.ROW_LEN; i++) {
            for (int j = 0; j < from.COL_LEN; j++) {
                set(i, j, from.get(i, j));
            }
        }
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        for (double[] doubles : matrix) {
            for (double aDouble : doubles) {
                stringBuilder.append(aDouble).append("    ");
            }
            stringBuilder.append('\n');
        }

        return stringBuilder.toString();

    }

}
