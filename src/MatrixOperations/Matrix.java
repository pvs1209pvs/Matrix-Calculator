package MatrixOperations;

import BasicIO.BasicForm;

import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Matrix {

    private ArrayList<Double> numbers;
    private double answer;
    private DecimalFormat df;

    public Matrix () {

        numbers = new ArrayList<Double>();
        df = new DecimalFormat("#.##");
        answer = 0;


    } // constructor

    /* Finds all the unknown of a matrix by using Carmers's Rule. */
    public void  carmersRule (double[][] mat, MatrixUI matrixUI, int button) {

        if (mat.length == mat[0].length) {
            throw new UnequalNumberOfRowsAndUnknowns();
        }

        double[][] unknows = new double[mat.length][mat.length];
        double[][] result = new double[mat.length][mat.length];
        double[] answers = new double[mat.length];

        for (int i=0; i<mat.length; i++) {
            for (int j = 0; j < mat[i].length - 1; j++) {
                unknows[i][j] = mat[i][j];
            }
        }

        for (int n=0; n<mat.length; n++) {
            for (int i=0; i<mat.length; i++) {
                for (int j=0; j<mat.length; j++) {
                    if (j==n) {
                        result[i][n] = mat[i][mat.length];
                    }
                    else {
                        result[i][j] = unknows[i][j];
                    }
                }
            }
            answers[n] = Double.parseDouble(df.format(determinant(result,0, matrixUI, button)/determinant(unknows,0, matrixUI, button)));
            for (int i=0; i<mat.length; i++) Arrays.fill(result[i], 0);
        }

        if (button==6) {
            matrixUI.mainUI.writeString("answer","Value of unknowns are "); matrixUI.mainUI.newLine("answer");
            for (double x : answers) {
                matrixUI.mainUI.writeDouble("answer",x);
                matrixUI.mainUI.newLine("answer");
            }
        }


    }

    /* Computes the inverse of the matrix by dividing every
    single element of it's adjoint by it's determinant. */
    public void inverse (double[][] mat, MatrixUI matrixUI, int button) {

        if (mat.length != mat[0].length) {
            throw new NotASquareMatrixException();
        }

        double[][] result = new double[mat.length][mat[0].length];

        for (int i=0; i<result.length; i++) {
            for (int j=0; j<result.length; j++) {
                result[i][j] = mat[i][j];
            }
        }

        result = adjoint(result, matrixUI, button);


        System.out.println("inverse");
        for (int i=0; i<result.length; i++) {
            for (int j=0; j<result[i].length; j++) {
                result[i][j] =(result[i][j]/determinant(mat,0, matrixUI, button));

            }
        }

        if (button==5) {
            for (double[] x : result) {
                for (double y : x) {
                    matrixUI.mainUI.writeString("answer", y + "\t");
                    System.out.println(y);
                }
                System.out.println();
                matrixUI.mainUI.newLine("answer");
            }
        }

    }

    /* Returns the adjoint of the matrix in the form of 2D array. transpose method is also used in this method. */
    public double[][] adjoint (double[][] mat, MatrixUI matrixUI, int button) {

        if (mat.length != mat[0].length) {
            throw new NotASquareMatrixException();
        }

        double[] minor = new double[(int) Math.pow(mat.length-1, 2)];
        double[] values = new double[(int)Math.pow(mat.length,2)];
        double[][] dummy = new double[mat.length-1][mat.length-1];
        double[][] result = new double[mat.length][mat.length];

        int index = 0;
        int pos = 0;

        for (int a=0; a<mat.length; a++) {
            for (int b=0; b<mat.length; b++) {

                for (int i=0; i<mat.length; i++) {
                    for (int j=0; j<mat.length; j++) {
                        if (!(j == b | i == a)) {
                            minor[index] = mat[i][j];
                            index++;
                        }
                    }
                }

                index = 0;
                for (int i=0; i<dummy.length; i++) {
                    for (int j=0; j<dummy[i].length; j++) {
                        dummy[i][j] = minor[index];
                        index++;
                    }
                }

                values[pos] =  determinant(dummy, 0, matrixUI, button);

                index = 0;
                pos++;
            }
        }

        pos = 0;

        for (int i=0; i<mat.length; i++) {
            for (int j=0; j<mat.length; j++) {
                result[j][i] = Math.pow(-1, i+j)*values[pos];
                pos++;
            }
        }

        if (button==4) {
            for (double[] x : result) {
                for (double y : x) {
                    matrixUI.mainUI.writeString("answer", y + "\t");
                }
                matrixUI.mainUI.newLine("answer");
            }
        }

        return result;
    }

    /* Returns the determinant of the given matrix as a single double value also switches
    rows to get the correct determinant. */
    public double determinant (double[][] mat, int swaps, MatrixUI matrixUI, int button) {

        if (mat.length != mat[0].length) {
            throw new NotASquareMatrixException();
        }

        double coef = 0;
        double[][] result =  new double[mat.length][mat[0].length];
        double[][] extra = new double[mat.length][mat[0].length];

        for (int i=0; i<mat.length; i++) {
            for (int j=0; j<mat[i].length; j++) {
                extra[i][j] = mat[i][j];
            }
        }
        for (int i=0; i<mat.length; i++) {
            for (int j=0; j<mat[i].length; j++) {
                result[i][j] = mat[i][j];
            }
        }


        for (int a=0; a<result.length; a++) {
            for (int i = 0; i<result.length; i++) {
                coef = -(result[i][a]/result[a][a]);
                    for (int j = 0; j < result[i].length; j++) {
                        if (i != a & !(i < a)) {
                        result[i][j] = result[i][j]+(result[a][j]*coef);
                    }
                }
            }
        }

        double number=1;
        for (int i=0; i<result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                if (i == j) {
                    number *= result[i][j];
                }
            }
        }

        if (!Double.isNaN(number)) {
             answer = Double.parseDouble(df.format(number));
        }
        else {
            double[] swapper = new double[result.length];
            for (int i=swaps; i<=swaps; i++) {
                for (int j=0; j<result[i].length; j++) {
                    swapper[j] = extra[i][j];
                }
            }
            for (int i=swaps; i<=swaps; i++) {
                for (int j=0; j<result[i].length; j++) {
                    extra[i][j]=extra[i+1][j];
                }
            }
            for (int i=swaps+1; i<=swaps+1; i++) {
                for (int j=0; j<result[i].length; j++) {
                    extra[i][j]=swapper[j];
                }
            }
            swaps++;
            determinant(extra, swaps, matrixUI, button);

        }

        if (button==3) {
            matrixUI.mainUI.writeDouble("answer", answer);
        }

        return answer;
    }

    /* Uses reducedRowEchelonFormSteps to convert the given matrix to Reduced Row Echelon Form
    by calling the reducedRowEchelonFormSteps method equal to number of rows in the given matrix. */
    public void reducedRowEchelonForm (double[][] mat, MatrixUI matrixUI) {

        double[][] result = new double[mat.length][mat[0].length];
        for (int i=0; i<result.length; i++) {
            for (int j=0; j<result[0].length; j++) {
                result[i][j] = mat[i][j];
            }
        }

        for (int i=0; i<result.length; i++) {
            reducedRowEchelonFormSteps(i, i, result);
        }

        for (double[] x : result) {
            for (double y : x) {
                matrixUI.mainUI.writeString("answer", y + "\t");
            }
            matrixUI.mainUI.newLine("answer");
        }

    }

    /* Converts the pivot element of one single column of the given matrix to one and
    converts element above and below it to zero. */
    public void reducedRowEchelonFormSteps (int a, int b, double[][] mat) {

        if (mat.length != mat[0].length) {
            throw new NotASquareMatrixException();
        }

        // STEP 1: convert the element at pivot equal to 1
        double coef = mat[a][b];

        for (int i=0; i<mat[a].length; i++) {
            mat[a][i] = mat[a][i]/coef;
        }

        // STEP 2: convert the number above and below the pivot equal to 0
        for (int i=0; i<mat.length; i++) {
            if ( i!=a ) {
                if ( mat[i][b] > 0 ) {
                    coef = -1*(mat[a][b]*mat[i][b]);
                }
                else {
                    coef = -1*(mat[a][b]*mat[i][b]);
                }

                for (int j=0; j<mat[i].length; j++) {
                    mat[i][j] = mat[i][j]+(mat[a][j]*coef);

                }
            }
        }

    }

    /* Adds two given matrix. If column length of the first matrix is not equal to the
    row length of second matrix then UnequalColumnRow() is thrown */
    // add another array for the 2nd Linear System to properly throw the exception.
    public void multiply (double[][] a, double[][] b, MatrixUI matrixUI) {

        if (a[0].length != b.length) {
            throw new UnequalColumnRow();
        }

        double[][] result = new double[a.length][b[0].length];

        double number = 0;
        for (int i=0; i<result.length ; i++) {
            for (int j=0; j<b[0].length; j++) {
                for (int k=0; k<result.length; k++) {
                    number += a[i][k]*b[k][j];
                }
                result[i][j] = number;
                number = 0;
            }
        }

        for (double[] x : result) {
            for (double y : x) {
                matrixUI.mainUI.writeString("answer", y + "\t");
            }
            matrixUI.mainUI.newLine("answer");
        }

    }

    public void booleanMultiply (boolean[][] a, boolean[][] b) {

        boolean[][] result = new boolean[a.length][b[0].length];
        boolean[] numbers = new boolean[result.length];
        boolean computedValue = false;

        for (int i=0; i<result.length ; i++) {
            for (int j=0; j<b[0].length; j++) {
                for (int k=0; k<result.length; k++) {
                    numbers[k] = a[i][k] & b[k][j];
                }
                for (boolean x : numbers) {
                    if (x==true) {
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
    public void add (double[][] matOne, double[][] matTwo, MatrixUI matrixUI) {

        if (!(matOne.length==matTwo.length && matOne[0].length==matTwo[0].length)) {
            throw new UnequalColumnRow();
        }

       double[][] result = new double[matOne.length][matOne[0].length];

        for (int i=0; i<matOne.length; i++) {
            for (int j=0; j<matTwo[i].length; j++) {
                result[i][j] = matOne[i][j]+matTwo[i][j];
            }
        }

        for (double[] x : result) {
            for (double y : x) {
                matrixUI.mainUI.writeString("answer", y +"\t");
            }
            matrixUI.mainUI.newLine("answer");
        }
    }

    /* Finds the transpose of the give matrix*/
    public void transpose (double [][] mat, MatrixUI matrixUI, int button) {

        double[][] result = new double[mat[0].length][mat.length];

        for (int i=0; i<mat.length; i++) {
            for (int j=0; j<mat[i].length; j++) {
                result[j][i] = mat[i][j];
            }
        }

       if (button==1) {
           for (double[] x : result) {
               for (double y : x) {
                   matrixUI.mainUI.writeString("answer", y + "\t");
               }
               matrixUI.mainUI.newLine("answer");
           }
       }

    }

    /* Outputs sum of the diagonal of the given matrix. */
    public void trace (double[][] mat, MatrixUI matrixUI) {

        double number=0;

        for (int i=0; i<mat.length; i++) {
            for (int j=0; j<mat[i].length; j++) {
                if (i==j) {
                    number += mat[i][j];
                }
            }
        }

        matrixUI.mainUI.writeDouble("answer", number);

    }

    /* Prints the desired matrix on to the console with a custom message. */
    public static void matrixPrinter (String msg, double[][] mat) {

        System.out.println(msg);

        for ( double[] x : mat) {
            for ( double y : x) {
                if ( y == -0) {
                    y = 0;
                }
                System.out.print("  "+ y + "  ");
            }
            System.out.println();
        }
        System.out.println("");

    }



} // Matrix