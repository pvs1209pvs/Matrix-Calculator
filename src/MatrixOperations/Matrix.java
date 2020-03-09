package MatrixOperations;

import java.util.List;

class Matrix {

    private double[][] matrix;
    private int rows;
    private int cols;

    Matrix(int rows, int cols) {
        matrix = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    void fillRow(int i, List<Double> numbers){
        for (int j = 0; j < rows; j++) {
            matrix[i][j] = numbers.get(j);
        }
    }

    double[] getRow(int i) {
        return matrix[i];
    }

    void setAt(int i, int j, double v) {
        matrix[i][j] = v;
    }

    double getFrom(int i, int j) {
        return matrix[i][j];
    }

     int getRows() {
        return rows;
    }

     int getCols() {
        return cols;
    }

    double[][] getMatrix() {
        return matrix;
    }

    void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

     void deepCopy(Matrix from) {
        for (int i = 0; i < from.getRows(); i++) {
            for (int j = 0; j < from.getCols(); j++) {
                setAt(i, j, from.getFrom(i, j));
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
