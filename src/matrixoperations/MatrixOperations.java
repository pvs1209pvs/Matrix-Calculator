package matrixoperations;

import java.text.DecimalFormat;
import java.util.Arrays;

public class MatrixOperations {

    private static DecimalFormat df = new DecimalFormat(".##");

    /* Finds all the unknown of a matrix by using Carmers's Rule. */
    public static void carmersRule(Matrix mat) {

        if (mat.getRows() == mat.getCols()) {
            throw new UnequalNumberOfRowsAndUnknowns();
        }

        double[] answers = new double[mat.getRows()];

        Matrix unknowns = new Matrix(mat.getRows(), mat.getCols());
        Matrix result = new Matrix(mat.getRows(), mat.getCols());

        for (int i = 0; i < mat.getRows(); i++) {
            for (int j = 0; j < mat.getCols() - 1; j++) {
                unknowns.setAt(i, j, mat.getFrom(i, j));
            }
        }

        for (int n = 0; n < mat.getRows(); n++) {
            for (int i = 0; i < mat.getRows(); i++) {
                for (int j = 0; j < mat.getRows(); j++) {
                    if (j == n) {
                        result.setAt(i, n, mat.getFrom(i, mat.getRows()));
                    }
                    else {
                        result.setAt(i, j, unknowns.getFrom(i, j));
                    }
                }
            }

            answers[n] = Double.parseDouble(df.format(determinant(result) / determinant(unknowns)));
            for (int i = 0; i < mat.getRows(); i++) {
                Arrays.fill(result.getRow(i), 0);
            }

        }

    }

    /* Computes the inverse of the matrix by dividing every
    single element of it's adjoint by it's determinant. */
    public static Matrix inverse(Matrix mat) {

        if (mat.getRows() != mat.getCols()) {
            throw new NotASquareMatrixException();
        }

        Matrix result = new Matrix(mat.getRows(), mat.getCols());
        result.deepCopy(mat);

        result = adjoint(result);


        for (int i = 0; i < result.getRows(); i++) {
            for (int j = 0; j < result.getCols(); j++) {
                result.setAt(i, j, result.getFrom(i, j) / determinant(mat));
            }
        }

        return result;

    }

    /* Returns the adjoint of the matrix in the form of 2D array. transpose method is also used in this method. */
    public static Matrix adjoint(Matrix mat) {

        if (mat.getRows() != mat.getCols()) {
            throw new NotASquareMatrixException();
        }

        double[] minor = new double[(int) Math.pow(mat.getRows() - 1, 2)];
        double[] values = new double[(int) Math.pow(mat.getRows(), 2)];
        Matrix dummy = new Matrix(mat.getRows() - 1, mat.getCols() - 1);
        Matrix result = new Matrix(mat.getRows(), mat.getRows());

        int index = 0;
        int pos = 0;

        for (int a = 0; a < mat.getRows(); a++) {
            for (int b = 0; b < mat.getRows(); b++) {

                for (int i = 0; i < mat.getRows(); i++) {
                    for (int j = 0; j < mat.getRows(); j++) {
                        if (!(j == b | i == a)) {
                            minor[index] = mat.getFrom(i, j);
                            index++;
                        }
                    }
                }

                index = 0;
                for (int i = 0; i < dummy.getRows(); i++) {
                    for (int j = 0; j < dummy.getCols(); j++) {
                        dummy.setAt(i, j, minor[index]);
                        index++;
                    }
                }

                values[pos] = determinant(dummy);

                index = 0;
                pos++;
            }
        }

        pos = 0;

        for (int i = 0; i < mat.getRows(); i++) {
            for (int j = 0; j < mat.getRows(); j++) {
                result.setAt(i, j, Math.pow(-1, i + j) * values[pos]);
                pos++;
            }
        }

        return mat;

    }


    static public double determinant(Matrix mat) {
        double answer = 0;
        return determinant(mat, 0, answer);

    }

    /* Returns the determinant of the given matrix as a single double value also switches
    rows to get the correct determinant. */
    static double determinant(Matrix mat, int swaps, double answer) {

        if (mat.getRows() != mat.getCols()) {
            throw new NotASquareMatrixException();
        }

        double coef = 0;
        Matrix result = new Matrix(mat.getRows(), mat.getCols());
        Matrix extra = new Matrix(mat.getRows(), mat.getCols());

        extra.deepCopy(mat);
        result.deepCopy(mat);

        for (int a = 0; a < result.getRows(); a++) {
            for (int i = 0; i < result.getRows(); i++) {
                coef = -(result.getFrom(i, a) / result.getFrom(a, a));
                for (int j = 0; j < result.getCols(); j++) {
                    if (i != a & !(i < a)) {
                        result.setAt(i, j, result.getFrom(i, j) + (result.getFrom(a, j) * coef));
                    }
                }
            }
        }

        double number = 1;
        for (int i = 0; i < result.getRows(); i++) {
            for (int j = 0; j < result.getCols(); j++) {
                if (i == j) {
                    number *= result.getFrom(i, j);
                }
            }
        }

        if (!Double.isNaN(number)) {
            answer = Double.parseDouble(df.format(number));
        }
        else {
            double[] swapper = new double[result.getRows()];
            for (int i = swaps; i <= swaps; i++) {
                for (int j = 0; j < result.getCols(); j++) {
                    swapper[j] = extra.getFrom(i, j);
                }
            }
            for (int i = swaps; i <= swaps; i++) {
                for (int j = 0; j < result.getCols(); j++) {
                    extra.setAt(i, j, extra.getFrom(i + 1, j));
                }
            }
            for (int i = swaps + 1; i <= swaps + 1; i++) {
                for (int j = 0; j < result.getCols(); j++) {
                    extra.setAt(i, j, swapper[j]);
                }
            }
            swaps++;

            determinant(extra, swaps, answer);

        }

        return answer;
    }

    /* Uses reducedRowEchelonFormSteps to convert the given matrix to Reduced Row Echelon Form
    by calling the reducedRowEchelonFormSteps method equal to number of rows in the given matrix. */
    public static Matrix reducedRowEchelonForm(Matrix mat) {

        Matrix result = new Matrix(mat.getRows(), mat.getCols());
        result.deepCopy(mat);

        for (int i = 0; i < result.getRows(); i++) {
            reducedRowEchelonFormSteps(i, i, result);
        }

        return result;

    }

    /* Converts the pivot element of one single column of the given matrix to one and
    converts element above and below it to zero. */
    private static void reducedRowEchelonFormSteps(int a, int b, Matrix mat) {

        // STEP 1: convert the element at pivot equal to 1
        double coef = mat.getFrom(a, b);

        for (int i = 0; i < mat.getRow(a).length; i++) {
            mat.setAt(a, i, mat.getFrom(a, i) / coef);
        }

        // STEP 2: convert the number above and below the pivot equal to 0
        for (int i = 0; i < mat.getRows(); i++) {
            if (i != a) {
                if (mat.getFrom(i, b) > 0) {
                    coef = -1 * (mat.getFrom(a, b) * mat.getFrom(i, b));
                }
                else {
                    coef = -1 * (mat.getFrom(a, b) * mat.getFrom(i, b));
                }

                for (int j = 0; j < mat.getCols(); j++) {
                    mat.setAt(i, j, mat.getFrom(i, j) + (mat.getFrom(a, j) * coef));
                }
            }
        }

    }

    /* Adds two given matrix. If column length of the first matrix is not equal to the
    row length of second matrix then UnequalColumnRow() is thrown */
    // add another array for the 2nd Linear System to properly throw the exception.
    public static Matrix multiply(Matrix a, Matrix b) {

        if (a.getCols() != b.getRows()) {
            throw new UnequalColumnRow();
        }

        Matrix result = new Matrix(a.getRows(), b.getCols());

        double number = 0;
        for (int i = 0; i < result.getRows(); i++) {
            for (int j = 0; j < b.getCols(); j++) {
                for (int k = 0; k < result.getRows(); k++) {
                    number += a.getFrom(i, k) * b.getFrom(k, j);
                }
                result.setAt(i, j, number);
                number = 0;
            }
        }

        return result;

    }

    public static void booleanMultiply(boolean[][] a, boolean[][] b) {

        boolean[][] result = new boolean[a.length][b[0].length];
        boolean[] numbers = new boolean[result.length];
        boolean computedValue = false;

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < result.length; k++) {
                    numbers[k] = a[i][k] & b[k][j];
                }
                for (boolean x : numbers) {
                    if (x == true) {
                        computedValue = true;
                    }
                }
                result[i][j] = computedValue;
                computedValue = false;
            }
        }

        for (boolean[] x : result) {
            for (boolean y : x) {
                System.out.print(y);
            }
            System.out.println();
        }
    }

    /* Adds two given matrix. */
    public static Matrix add(Matrix matOne, Matrix matTwo) {

        if (!(matOne.getRows() == matTwo.getRows() && matOne.getCols() == matTwo.getCols())) {
            throw new UnequalColumnRow();
        }

        Matrix result = new Matrix(matOne.getRows(), matOne.getCols());

        for (int i = 0; i < matOne.getRows(); i++) {
            for (int j = 0; j < matTwo.getCols(); j++) {
                result.setAt(i, j, matOne.getFrom(i, j) + matTwo.getFrom(i, j));
            }
        }

        return result;

    }

    /* Finds the transpose of the give matrix*/
    public static Matrix transpose(Matrix mat) {

        Matrix result = new Matrix(mat.getCols(), mat.getRows());

        for (int i = 0; i < mat.getRows(); i++) {
            for (int j = 0; j < mat.getCols(); j++) {
                result.setAt(j, i, mat.getFrom(i, j));
            }
        }

        return result;

    }

    /* Outputs sum of the diagonal of the given matrix. */
    public static double trace(Matrix mat) {

        double number = 0;

        for (int i = 0; i < mat.getRows(); i++) {
            for (int j = 0; j < mat.getCols(); j++) {
                if (i == j) {
                    number += mat.getFrom(i, j);
                }
            }
        }

        return number;

    }


} // matrixoperations