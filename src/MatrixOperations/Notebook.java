package MatrixOperations;

import java.util.ArrayList;

public class Notebook {

    private ArrayList<String> equsOne;
    private ArrayList<String> equsTwo;

    private MatrixUI ui;
    private MatrixEquationConvertor mecO;
    private MatrixEquationConvertor mecT;
    private double[][] matrixO;
    private double[][] matrixT;

    public Notebook () {

        Matrix ops = new Matrix();


        equsOne = new ArrayList<String>();
        equsTwo = new ArrayList<String>();

        double[][] matrixOne=null;
        double[][] matrixTwo=null;

        MatrixUI matrixUI;
        Matrix matOps;
        MatrixEquationConvertor mecOne;
        MatrixEquationConvertor mecTwo;

        matOps = new Matrix();
        matrixUI = new MatrixUI();
        matrixUI.setUpMainUI();

        if (matrixUI.mainUI.accept()==9) {

            matrixUI.equationReader("equOne", equsOne);
            mecOne = new MatrixEquationConvertor(equsOne.size());
            for (int i = 0; i < equsOne.size(); i++) {
                mecOne.userInputAdder(i, equsOne.get(i));
            }
            mecOne.matrixMaker();
            matrixOne = mecOne.getMatrix();

            matrixUI.equationReader("equTwo", equsTwo);
            mecTwo = new MatrixEquationConvertor(equsTwo.size());
            for (int i = 0; i < equsTwo.size(); i++) {
                mecTwo.userInputAdder(i, equsTwo.get(i));

            }
            mecTwo.matrixMaker();
            matrixTwo = mecTwo.getMatrix();
        }

        for ( ; ; ) {

            int button = matrixUI.mainUI.accept();

            matrixUI.mainUI.clear("answer");
            switch (button) {
                case 0:
                    matOps.trace(matrixOne, matrixUI);
                    break;
                case 1:
                    matOps.transpose(matrixOne, matrixUI, button);
                    break;
                case 2:
                    matOps.reducedRowEchelonForm(matrixOne, matrixUI);
                    break;
                case 3:
                    try {
                        matOps.determinant(matrixOne, 0, matrixUI, button);
                    }
                    catch (NotASquareMatrixException nasme) {
                        matrixUI.mainUI.writeString("answer", "NOT A SQUARE MATRIX.");
                    }
                    break;
                case 4:
                    try {
                        matOps.adjoint(matrixOne, matrixUI, button);
                    }
                    catch (NotASquareMatrixException nasme) {
                        matrixUI.mainUI.writeString("answer", "NOT A SQUARE MATRIX.");
                    }
                    break;
                case 5:
                    try {
                        matOps.inverse(matrixOne, matrixUI, button);
                    }
                    catch (NotASquareMatrixException nasme) {
                        matrixUI.mainUI.writeString("answer", "NOT A SQUARE MATRIX.");
                    }
                    break;
                case 6:
                    matOps.carmersRule(matrixOne, matrixUI, button);
                    break;
                case 7:
                    try {
                        matOps.add(matrixOne, matrixTwo, matrixUI);
                    }
                    catch (UnequalColumnRow ucr) {
                        matrixUI.mainUI.writeString("answer", "Column length of the first matrix is equal to the row length of the second matrix.");
                    }
                    break;
                case 8:
                    try {
                        matOps.multiply(matrixOne, matrixTwo, matrixUI);
                    }
                    catch (UnequalColumnRow ucr) {
                        matrixUI.mainUI.writeString("answer", "Column length of the first matrix is equal to the row length of the second matrix.");
                    }
                    break;
                case 10:
                    matrixUI.mainUIClose();

                    matrixUI = new MatrixUI();
                    matrixUI.setUpMainUI();

                    matrixOne=null;
                    matrixTwo=null;

                    if (matrixUI.mainUI.accept()==9) {
                        matrixUI.equationReader("equOne", equsOne);
                        mecOne = new MatrixEquationConvertor(equsOne.size());
                        for (int i = 0; i < equsOne.size(); i++) {
                            mecOne.userInputAdder(i, equsOne.get(i));
                        }
                        mecOne.matrixMaker();
                        matrixOne = mecOne.getMatrix();

                        matrixUI.equationReader("equTwo", equsTwo);
                        mecTwo = new MatrixEquationConvertor(equsTwo.size());
                        for (int i = 0; i < equsTwo.size(); i++) {
                            mecTwo.userInputAdder(i, equsTwo.get(i));
                        }
                        mecTwo.matrixMaker();
                        matrixTwo = mecTwo.getMatrix();
                    }

                   // equationsPreparer(matrixUI, mecOne, mecTwo, matrixOne, matrixTwo);
                    break;
                case 11:
                    matrixUI.mainUI.close();
                    break;
            }

        }

    } // constructor


    /**
     * @param UI
     * @param mecO
     * @param mecT
     * @param matrixO
     * @param matrixT
     */
    private void equationsPreparer (MatrixUI UI, MatrixEquationConvertor mecO, MatrixEquationConvertor mecT, double[][] matrixO, double[][] matrixT) {

        UI.mainUI.accept();
        UI.equationReader("equOne", equsOne);
        mecO= new MatrixEquationConvertor(equsOne.size());
        for (int i=0; i<equsOne.size(); i++) {
            mecO.userInputAdder(i, equsOne.get(i));
        }
        mecO.matrixMaker();
        matrixO= mecO.getMatrix();

        UI.mainUI.accept();
        UI.equationReader("equTwo", equsTwo);
        mecT= new MatrixEquationConvertor(equsTwo.size());
        for (int i=0; i<equsTwo.size(); i++) {
            mecT.userInputAdder(i, equsTwo.get(i));
        }
        mecT.matrixMaker();
        matrixT= mecT.getMatrix();
    }

    public static void main (String[] args) {
        new Notebook();
    } // main
}







