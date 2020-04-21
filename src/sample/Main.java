package sample;

import MatrixOperations.EquationConverter;
import MatrixOperations.Matrix;
import MatrixOperations.MatrixOperations;
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

        TextArea matrixEquation = new TextArea("Enter equations here");
        TextArea matrixSolution = new TextArea("Solution appears here");

        HBox matrixArea = new HBox(matrixEquation, matrixSolution);

        Button rref = new Button("RREF");
        Button trace = new Button("Trace");
        Button transpose = new Button("Transpose");
        Button inverse = new Button("Inverse");
        Button read = new Button("Read Equation(s)");
        read.setId("readEq");


        read.setOnAction(actionEvent -> {

            List<String> equations = new ArrayList<>(Arrays.asList(matrixEquation.getText().split("\n")));

            List<List<Double>> numbers = new ArrayList<>();

            for (String e : equations) {
                numbers.add((EquationConverter.convert(e)));
            }

            Matrix matrix = new Matrix(numbers.size(), numbers.get(0).size());

            for (int i = 0; i < numbers.size(); i++) {
                matrix.fillRow(i, numbers.get(i));
            }

            runOperations(rref, matrix, matrixSolution);
            runOperations(trace, matrix, matrixSolution);
            runOperations(transpose, matrix, matrixSolution);
            runOperations(inverse, matrix, matrixSolution);

        });

        HBox ops = new HBox(rref, trace, transpose, inverse, read);
        ops.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.gridLinesVisibleProperty().setValue(true);
        gridPane.add(matrixArea, 0, 0);
        gridPane.add(ops, 0, 1);


        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void runOperations(Button button, Matrix mat, TextArea solution) {

        switch (button.getText()) {
            case "RREF":
                button.setOnAction((event -> {
                    solution.setText(MatrixOperations.reducedRowEchelonForm(mat).toString());

                }));
                break;
            case "Inverse":
                button.setOnAction((event -> {
                    solution.setText(MatrixOperations.inverse(mat).toString());
                }));
                break;
            case "Trace":
                button.setOnAction((event -> {
                    solution.setText(String.valueOf(MatrixOperations.trace(mat)));
                }));
                break;
            case "Transpose":
                button.setOnAction((event -> {
                    solution.setText(MatrixOperations.transpose(mat).toString());
                }));
                break;
        }
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