package MatrixOperations;

import BasicIO.BasicForm;

import java.util.ArrayList;

public class MatrixUI {

    public BasicForm mainUI;


    public MatrixUI () {

    } // constructor


    public void setUpMainUI () {
        mainUI = new BasicForm("Trace", "Transpose", "RREF", "Determinant", "Adjoint", "Inverse", "Carmer's Rule", "Addition", "Multiply", "READ EQUATION", "CHANGE",   "QUIT");
        mainUI.addTextArea("equOne", "1st LS", 5, 44, 10, 10);
        mainUI.addTextArea("equTwo","2nd LS", 5, 44, 510, 10);
        mainUI.addTextArea("answer", "Answer", 5, 88, 10,150);
    }

    // equationReader
    public void equationReader (String sec, ArrayList<String> arrayList) {

        String equation = mainUI.readString(sec);
        int index = 0;

        arrayList.clear();

        while ( !mainUI.isEOF() ) {
            arrayList.add(index, equation);
            equation = mainUI.readString(sec);
            index++;
        }

        equation = "";

    }

    public void mainUIClose () {
        mainUI.close();
    }
}
