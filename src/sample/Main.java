package sample;

import matrixoperations.EquationConverter;
import matrixoperations.Matrix;
import matrixoperations.MatrixOperations;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setHeight(200);
        primaryStage.setWidth(800);

        TextArea matrixEquationOne = new TextArea("2x+3y=4\n" +
                "5x+4y=7\n" +
                "9x+4y=8");
        TextArea matrixEquationTwo = new TextArea("2x+6y=4\n" +
                "9x+3y=2\n" +
                "8x+7y=1");
        TextArea matrixSolution = new TextArea("Solution appears here");

        HBox matrixArea = new HBox(matrixEquationOne, matrixEquationTwo, matrixSolution);

        Button rref = new Button("RREF");
        Button trace = new Button("Trace");
        Button transpose = new Button("Transpose");
        Button inverse = new Button("Inverse");
        Button addition = new Button("Addition");
        Button multiplication = new Button("Multiplication");
        Button determinant = new Button("Determinant");
        Button read = new Button("Read Equation(s)");

        read.setOnAction(actionEvent -> {

            Matrix matrixOne = buildMatrix(matrixEquationOne);
            Matrix matrixTwo = buildMatrix(matrixEquationTwo);

            runOperations(determinant, matrixOne, matrixTwo, matrixSolution);
            runOperations(trace, matrixOne, matrixTwo, matrixSolution);
            runOperations(rref, matrixOne, matrixTwo, matrixSolution);
            runOperations(transpose, matrixOne, matrixTwo, matrixSolution);
            runOperations(inverse, matrixOne, matrixTwo, matrixSolution);
            runOperations(addition, matrixOne, matrixTwo, matrixSolution);
            runOperations(multiplication, matrixOne, matrixTwo, matrixSolution);

        });

        HBox ops = new HBox(determinant, trace, rref, transpose, inverse, addition, multiplication, read);
        ops.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.gridLinesVisibleProperty().setValue(true);
        gridPane.add(matrixArea, 0, 0);
        gridPane.add(ops, 0, 1);

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void runOperations(Button button, Matrix matOne, Matrix matTwo, TextArea solution) {

        switch (button.getText()) {
            case "Determinant":
                button.setOnAction((event -> {
                    solution.setText(String.valueOf(MatrixOperations.determinant(matOne)));
                }));
                break;
            case "Trace":
                button.setOnAction((event -> {
                    solution.setText(String.valueOf(MatrixOperations.trace(matOne)));
                }));
                break;
            case "RREF":
                button.setOnAction((event -> {
                    solution.setText(MatrixOperations.reducedRowEchelonForm(matOne).toString());
                }));
                break;
            case "Transpose":
                button.setOnAction((event -> {
                    solution.setText(MatrixOperations.transpose(matOne).toString());
                }));
                break;
            case "Inverse":
                button.setOnAction((event -> {
                    solution.setText(MatrixOperations.inverse(matOne).toString());
                }));
                break;
            case "Addition":
                button.setOnAction((event -> {
                    solution.setText(MatrixOperations.add(matOne, matTwo).toString());
                }));
                break;
            case "Multiplication":
                button.setOnAction((event -> {
                    solution.setText(MatrixOperations.multiply(matOne, matTwo).toString());
                }));
                break;
        }
    }


    private Matrix buildMatrix(TextArea textArea) {

        String text = textArea.getText();

        if (text.isEmpty()) return null;

        List<String> equationsOne = new ArrayList<>(Arrays.asList(text.split("\n")));

        List<List<Double>> numbers = new ArrayList<>();

        for (String e : equationsOne) {
            numbers.add((EquationConverter.convert(e)));
        }

        Matrix matrix = new Matrix(numbers.size(), numbers.get(0).size());

        for (int i = 0; i < numbers.size(); i++) {
            matrix.fillRow(i, numbers.get(i));
        }

        return matrix;

    }

    public static void main(String[] args) {
        launch(args);
    }
}

/*
2x+3y=4
5x+4y=7
9x+4y=8
*/