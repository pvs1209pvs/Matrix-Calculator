package matrixoperations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MatrixOperationsTest {

    private final Matrix squareMatOne = MatrixOperations.readEquations(
            "2x+3y=4\n" + "5x+4y=7\n" + "9x+4y=8\n");

    private final Matrix squareMatTwo = MatrixOperations.readEquations(
            "5x+9y=5\n" + "8x+2y=6\n" + "3x+7y=8\n");

    final Matrix nonSquareMat = MatrixOperations.readEquations(
            "2x+3y+8z=4\n" + "5x+4y+6z=7\n" + "9x+4y+1z=8\n");


    @org.junit.jupiter.api.Test
    void inverse() {

        Matrix result = new Matrix(3, 3);
        result.fillRow(0, Arrays.asList(0.31, -0.62, 0.38));
        result.fillRow(1, Arrays.asList(1.77, -1.54, 0.46));
        result.fillRow(2, Arrays.asList(-1.23, 1.46, -0.54));

        assertEquals(result, MatrixOperations.inverse(squareMatOne));

    }

    @org.junit.jupiter.api.Test
    void adjoint() {
        Matrix result = new Matrix(3, 3);
        result.fillRow(0, Arrays.asList(4.0, -8.0, 5.0));
        result.fillRow(1, Arrays.asList(23.0, -20.0, 6.0));
        result.fillRow(2, Arrays.asList(-16.0, 19.0, -7.0));

        assertEquals(result, MatrixOperations.adjoint(squareMatOne));
    }

    @org.junit.jupiter.api.Test
    void determinant() {
        assertEquals(13.0, MatrixOperations.determinant(squareMatOne));
    }

    @org.junit.jupiter.api.Test
    void reducedRowEchelonForm() {

        assertArrayEquals(
                new double[]{-0.52, 3.33, -0.62},
                MatrixOperations.reducedRowEchelonForm(nonSquareMat).getCol(nonSquareMat.COL_LEN - 1));

    }

    @org.junit.jupiter.api.Test
    void transpose() {

        Matrix result = new Matrix(3, 3);
        result.fillRow(0, Arrays.asList(2.0, 5.0, 9.0));
        result.fillRow(1, Arrays.asList(3.0, 4.0, 4.0));
        result.fillRow(2, Arrays.asList(4.0, 7.0, 8.0));

        assertEquals(result, MatrixOperations.transpose(squareMatOne));

    }

    @org.junit.jupiter.api.Test
    void trace() {
        assertEquals(14, MatrixOperations.trace(squareMatOne));
    }

    @org.junit.jupiter.api.Test
    void multiply() {

        Matrix result = new Matrix(3, 3);
        result.fillRow(0, Arrays.asList(46.0, 52.0, 60.0));
        result.fillRow(1, Arrays.asList(78.0, 102.0, 105.0));
        result.fillRow(2, Arrays.asList(101.0, 145.0, 133.0));

        assertEquals(result, MatrixOperations.multiply(squareMatOne, squareMatTwo));

    }

    @org.junit.jupiter.api.Test
    void add() {

        Matrix result = new Matrix(3, 3);
        result.fillRow(0, Arrays.asList(7.0, 12.0, 9.0));
        result.fillRow(1, Arrays.asList(13.0, 6.0, 13.0));
        result.fillRow(2, Arrays.asList(12.0, 11.0, 16.0));

        assertEquals(result, MatrixOperations.add(squareMatOne, squareMatTwo));

    }
}
