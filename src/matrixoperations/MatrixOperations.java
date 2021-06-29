package matrixoperations;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class MatrixOperations {

    private static final DecimalFormat twoDecimalPlacesFormat = new DecimalFormat(".##");

    /**
     * Calculates the inverse of the matrix.
     *
     * @param mat Matrix to calculate inverse of.
     * @return Inverse value of matrix.
     */
    public static Matrix inverse(@NotNull Matrix mat) {

        if (mat.ROW_LEN != mat.COL_LEN) {
            throw new NotASquareMatrixException();
        }

        Matrix result = adjoint(mat);

        for (int i = 0; i < result.ROW_LEN; i++) {
            for (int j = 0; j < result.COL_LEN; j++) {
                result.set(i, j, Double.parseDouble(twoDecimalPlacesFormat.format(result.get(i, j) / determinant(mat))));
            }
        }

        return result;

    }

    /**
     * Calculates the adjoint of the matrix.
     *
     * @param mat Matrix to calculate adjoint of.
     * @return Adjoint value of matrix.
     */
    public static Matrix adjoint(@NotNull Matrix mat) {

        if (mat.ROW_LEN != mat.COL_LEN) {
            throw new NotASquareMatrixException();
        }

        double[] minor = new double[(int) Math.pow(mat.ROW_LEN - 1, 2)];
        double[] values = new double[(int) Math.pow(mat.ROW_LEN, 2)];
        Matrix temp = new Matrix(mat.ROW_LEN - 1, mat.COL_LEN - 1);
        Matrix result = new Matrix(mat.ROW_LEN, mat.ROW_LEN);

        int index = 0;
        int pos = 0;

        for (int a = 0; a < mat.ROW_LEN; a++) {
            for (int b = 0; b < mat.ROW_LEN; b++) {

                for (int i = 0; i < mat.ROW_LEN; i++) {
                    for (int j = 0; j < mat.ROW_LEN; j++) {
                        if (!(j == b | i == a)) {
                            minor[index] = mat.get(i, j);
                            index++;
                        }
                    }
                }

                index = 0;
                for (int i = 0; i < temp.ROW_LEN; i++) {
                    for (int j = 0; j < temp.COL_LEN; j++) {
                        temp.set(i, j, minor[index]);
                        index++;
                    }
                }

                values[pos] = determinant(temp);

                index = 0;
                pos++;
            }
        }

        pos = 0;

        for (int i = 0; i < mat.ROW_LEN; i++) {
            for (int j = 0; j < mat.ROW_LEN; j++) {
                result.set(j, i, Math.pow(-1, i + j) * values[pos]);
                pos++;
            }
        }

        return result;

    }

    /**
     * Calculates the determinant of the matrix.
     *
     * @param mat Matrix whose determinant needs to calculated.
     * @return Determinant value of the matrix.
     */
    static public double determinant(@NotNull Matrix mat) {

        if (mat.ROW_LEN != mat.COL_LEN) {
            throw new NotASquareMatrixException();
        }

        return determinant(mat, 0, 0);
    }

    /**
     * Calculates valid determinant of the matrix for each row swap.
     *
     * @param mat    Matrix whose determinant needs to calculated.
     * @param swaps  Number of swaps to perform.
     * @param answer Valid determinant so far.
     * @return Valid determinant.
     */
    static double determinant(@NotNull Matrix mat, int swaps, double answer) {

        double coef = 0;
        Matrix result = new Matrix(mat.ROW_LEN, mat.COL_LEN);
        Matrix extra = new Matrix(mat.ROW_LEN, mat.COL_LEN);

        extra.deepCopy(mat);
        result.deepCopy(mat);

        for (int a = 0; a < result.ROW_LEN; a++) {
            for (int i = 0; i < result.ROW_LEN; i++) {
                coef = -(result.get(i, a) / result.get(a, a));
                for (int j = 0; j < result.COL_LEN; j++) {
                    if (i != a & !(i < a)) {
                        result.set(i, j, result.get(i, j) + (result.get(a, j) * coef));
                    }
                }
            }
        }

        double number = 1;
        for (int i = 0; i < result.ROW_LEN; i++) {
            for (int j = 0; j < result.COL_LEN; j++) {
                if (i == j) {
                    number *= result.get(i, j);
                }
            }
        }

        if (!Double.isNaN(number)) {
            answer = Double.parseDouble(twoDecimalPlacesFormat.format(number));
        } else {
            double[] swapper = new double[result.ROW_LEN];
            for (int i = swaps; i <= swaps; i++) {
                for (int j = 0; j < result.COL_LEN; j++) {
                    swapper[j] = extra.get(i, j);
                }
            }
            for (int i = swaps; i <= swaps; i++) {
                for (int j = 0; j < result.COL_LEN; j++) {
                    extra.set(i, j, extra.get(i + 1, j));
                }
            }
            for (int i = swaps + 1; i <= swaps + 1; i++) {
                for (int j = 0; j < result.COL_LEN; j++) {
                    extra.set(i, j, swapper[j]);
                }
            }
            swaps++;

            determinant(extra, swaps, answer);

        }

        return answer;
    }

    /**
     * Calculates the Reduced Row Echelon form of the matrix.
     *
     * @param mat Matrix whose RREF needs to be calculated.
     * @return RREF value of matrix.
     */
    public static Matrix reducedRowEchelonForm(@NotNull Matrix mat) {

        if (mat.ROW_LEN == mat.COL_LEN) {
            throw new SquareMatrixException();
        }

        Matrix result = new Matrix(mat.ROW_LEN, mat.COL_LEN);
        result.deepCopy(mat);

        for (int i = 0; i < result.ROW_LEN; i++) {
            reducedRowEchelonForm(result, i);
        }

        for (int i = 0; i < mat.ROW_LEN; i++) {
            result.set(i, mat.COL_LEN - 1, Double.parseDouble(twoDecimalPlacesFormat.format(result.get(i, result.COL_LEN - 1))));
        }

        return result;

    }

    /**
     * Calculates the Reduced Row Echelon form for a single row of the matrix.
     *
     * @param mat          Matrix whose RREF needs to be calculated.
     * @param diagonalTrav Diagonal traversal number.
     */
    private static void reducedRowEchelonForm(@NotNull Matrix mat, int diagonalTrav) {

        // STEP 1: convert the element at pivot equal to 1
        double coef = mat.get(diagonalTrav, diagonalTrav);

        for (int i = 0; i < mat.getRow(diagonalTrav).length; i++) {
            mat.set(diagonalTrav, i, mat.get(diagonalTrav, i) / coef);
        }

        // STEP 2: convert the number above and below the pivot equal to 0
        for (int i = 0; i < mat.ROW_LEN; i++) {
            if (i != diagonalTrav) {
                coef = -1 * (mat.get(diagonalTrav, diagonalTrav) * mat.get(i, diagonalTrav));
                for (int j = 0; j < mat.COL_LEN; j++) {
                    mat.set(i, j, mat.get(i, j) + (mat.get(diagonalTrav, j) * coef));
                }
            }
        }

    }


    /**
     * Calculates the transpose of the matrix.
     *
     * @param mat Matrix whose transpose needs to be calculated.
     * @return Transpose value of the matrix.
     */
    public static Matrix transpose(@NotNull Matrix mat) {

        Matrix result = new Matrix(mat.COL_LEN, mat.ROW_LEN);

        for (int i = 0; i < mat.ROW_LEN; i++) {
            for (int j = 0; j < mat.COL_LEN; j++) {
                result.set(j, i, mat.get(i, j));
            }
        }

        return result;

    }

    /**
     * Calculates the trace of the matrix.
     *
     * @param mat Matrix whose trace needs to be calculated.
     * @return
     */
    public static double trace(@NotNull Matrix mat) {

        if (mat.ROW_LEN != mat.COL_LEN) {
            throw new NotASquareMatrixException();
        }

        double number = 0;

        for (int i = 0; i < mat.ROW_LEN; i++) {
            for (int j = 0; j < mat.COL_LEN; j++) {
                if (i == j) {
                    number += mat.get(i, j);
                }
            }
        }

        return number;

    }

    /**
     * Calculates the multiplication of the two matrices.
     *
     * @param matOne First matrix.
     * @param matTwo Second matrix.
     * @return Multiplication of the two matrices.
     */
    public static Matrix multiply(@NotNull Matrix matOne, @NotNull Matrix matTwo) {

        if (matOne.COL_LEN != matTwo.ROW_LEN) {
            throw new UnequalColumnRow();
        }

        Matrix result = new Matrix(matOne.ROW_LEN, matTwo.COL_LEN);

        double number = 0;
        for (int i = 0; i < result.ROW_LEN; i++) {
            for (int j = 0; j < matTwo.COL_LEN; j++) {
                for (int k = 0; k < result.ROW_LEN; k++) {
                    number += matOne.get(i, k) * matTwo.get(k, j);
                }
                result.set(i, j, number);
                number = 0;
            }
        }

        return result;

    }


    /**
     * Calculates the addition of the two matrices.
     *
     * @param matOne First matrix.
     * @param matTwo Second matrix.
     * @return Addition of the two matrices.
     */
    public static Matrix add(@NotNull Matrix matOne, @NotNull Matrix matTwo) {

        if (!(matOne.ROW_LEN == matTwo.ROW_LEN && matOne.COL_LEN == matTwo.COL_LEN)) {
            throw new UnequalColumnRow();
        }

        Matrix result = new Matrix(matOne.ROW_LEN, matOne.COL_LEN);

        for (int i = 0; i < matOne.ROW_LEN; i++) {
            for (int j = 0; j < matTwo.COL_LEN; j++) {
                result.set(i, j, matOne.get(i, j) + matTwo.get(i, j));
            }
        }

        return result;

    }

}