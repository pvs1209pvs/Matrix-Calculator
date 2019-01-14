package MatrixOperations;

import java.util.ArrayList;

public class MatrixEquationConvertor {

    private String[] userInput;
    private String[] linearSystem;
    private double[][] matrix;
    private ArrayList<Character> equ;
    private ArrayList<Character> ops;


    private ArrayList<Double> equDigit;


    public MatrixEquationConvertor(int numEqus) {

        equ = new ArrayList<Character>();
        ops = new ArrayList<Character>();
        equDigit = new ArrayList<Double>();
        userInput = new String[numEqus];
        linearSystem = new String[userInput.length];
        matrix = null;

    } // constructor


    /* uses all the method provided to form the Matrix */
    void matrixMaker() {
        equSetter();
        equAdder();
        numberExtractor();
        signAssigner();
        numbersToMatrix();
    }


    /* lets user input the equations of their choice in the form af a String */
    void userInputAdder(int index, String equation) {
        userInput[index] = equation;
    }

    /* converts the given equations into a matrix */
    private void numbersToMatrix() {
        int index = 0;
        for (int i = 0; i < linearSystem.length; i++) {
            for (int j = 0; j < equDigit.size() / linearSystem.length; j++) {
                matrix[i][j] = equDigit.get(index);
                index++;
            }
        }
    }

    /* assigns sign to the numbers */
    private void signAssigner() {
        matrix = new double[linearSystem.length][equDigit.size() / linearSystem.length];
        for (int i = 0; i < ops.size(); i++) {
            if (ops.get(i) == '-') {
                equDigit.set(i, -1 * equDigit.get(i));
            }
            else if (ops.get(i) == '-') {
                equDigit.set(i, 1 * equDigit.get(i));
            }
        }
    }

    /* adds the equation to ArrayList named equ from the Array named linearSystem */
    private void equAdder() {

        for (int i = 0; i < linearSystem.length; i++) {
            for (int j = 0; j < linearSystem[i].length(); j++) {
                if (!(linearSystem[i].charAt(j) == '=')) {
                    equ.add(linearSystem[i].charAt(j));
                }
            }
        }

        opsAdder();
    }

    /* adds positive and negative signs to the ArrayList named ops */
    private void opsAdder() {

        for (char x : equ) {
            if (x == '+' || x == '-') {
                ops.add(x);
            }
        }

    }

    /* extracts the number from the equation and adds them to the ArrayList named equDigit */
    private void numberExtractor() {

        String digit = "";

        for (char x : equ) {
            if (x == '+' || x == '-' || x == '/') {
                if (digit.length() > 0) {
                    digit = digit.substring(0, digit.length() - 1);
                    equDigit.add(Double.parseDouble(digit));
                }
                digit = "";
            }
            else {
                digit += x;
            }
        }

    }

    /* takes the equation entered by user in the required form */
    private void equSetter() {
        String equs = "";

        for (int i = 0; i < userInput.length; i++) {
            for (int j = 0; j < userInput[i].length(); j++) {

                equs += userInput[i].charAt(j);
            }
            equs += "*";
            equs += "/";
            linearSystem[i] = equs;
            equs = "";
        }


    }

    /* returns the matrix formed by this class */
    double[][] getMatrix() {
        return matrix;
    }


}
