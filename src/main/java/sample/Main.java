package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import matrixoperations.*;

import static matrixoperations.MatrixOperations.readEquations;

public class Main extends Application {

    private final TextArea matrixEquationOne = new TextArea(
            "2x+3y=4\n" +
                    "5x+4y=7\n" +
                    "9x+4y=8");

    private final TextArea matrixEquationTwo = new TextArea(
            "2x+6y=4\n" +
                    "9x+3y=2\n" +
                    "8x+7y=1");

    private final TextArea matrixSolution = new TextArea("Solution appears here");

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setHeight(200);
        primaryStage.setWidth(800);

        HBox matrixArea = new HBox(matrixEquationOne, matrixEquationTwo, matrixSolution);

        HBox ops = new HBox(
                new Button("Inverse"),
                new Button("RREF"),
                new Button("Transpose"),
                new Button("Adjoint"),
                new Button("Trace"),
                new Button("Determinant"),
                new Button("Addition"),
                new Button("Multiplication"));

        ops.getChildren().forEach(button -> runOperations((Button) button, readEquations(matrixEquationOne.getText()), readEquations(matrixEquationTwo.getText()), matrixSolution));

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

            case "Adjoint":
                button.setOnAction(event -> {
                    solution.setText(String.valueOf(MatrixOperations.adjoint(matOne)));
                });
                break;

            case "Determinant":
                button.setOnAction((event -> {
                    try {
                        solution.setText(String.valueOf(MatrixOperations.determinant(matOne)));
                    } catch (NotASquareMatrixException exception) {
                        readEquations(matrixEquationOne.getText());
                        matrixSolution.setText("First matrix must be square.");
                    }
                }));
                break;

            case "Trace":
                button.setOnAction((event -> {
                    try {
                        solution.setText(String.valueOf(MatrixOperations.trace(matOne)));
                    } catch (NotASquareMatrixException exception) {
                        readEquations(matrixEquationOne.getText());
                        matrixSolution.setText("First matrix must be square.");
                    }
                }));
                break;

            case "RREF":
                button.setOnAction((event -> {
                    try {
                        solution.setText(MatrixOperations.reducedRowEchelonForm(matOne).toString());
                    } catch (SquareMatrixException exception) {
                        readEquations(matrixEquationOne.getText());
                        matrixSolution.setText("First matrix must not be square.");
                    }
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
                    try {
                        solution.setText(MatrixOperations.add(matOne, matTwo).toString());
                    } catch (UnequalColumnRow exception) {
                        readEquations(matrixEquationOne.getText());
                        matrixSolution.setText("First and second matrix must have equal number of rows and cols.");
                    }
                }));
                break;

            case "Multiplication":
                button.setOnAction((event -> {
                    try {
                        solution.setText(MatrixOperations.multiply(matOne, matTwo).toString());
                    } catch (UnequalColumnRow exception) {
                        readEquations(matrixEquationOne.getText());
                        matrixSolution.setText("First and second matrix must have equal number of rows and cols.");
                    }
                }));
                break;

        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
