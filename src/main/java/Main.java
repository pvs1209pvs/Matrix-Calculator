import com.company.matrix.Matrix;
import com.company.matrix.MatrixOperations;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import com.company.matrixexceptions.NotASquareMatrixException;
import com.company.matrixexceptions.SquareMatrixException;
import com.company.matrixexceptions.UnequalColumnRowException;

import static com.company.matrix.MatrixOperations.readEquations;


public class Main extends Application {

    private final TextArea matrixEquationOne = new TextArea(
            """
                    2x+3y=4
                    5x+4y=7
                    9x+4y=8""");

    private final TextArea matrixEquationTwo = new TextArea(
            """
                    2x+6y=4
                    9x+3y=2
                    8x+7y=1""");

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

        ops.getChildren().forEach(button -> runOperations((Button) button, matrixSolution));

        ops.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.gridLinesVisibleProperty().setValue(true);
        gridPane.add(matrixArea, 0, 0);
        gridPane.add(ops, 0, 1);

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void runOperations(Button button, TextArea solution) {

        switch (button.getText()) {

            case "Adjoint" -> button.setOnAction(event -> {
                try {
                    Matrix matOne = readEquations(matrixEquationOne.getText());
                    solution.setText(String.valueOf(MatrixOperations.adjoint(matOne)));
                } catch (NotASquareMatrixException notASquareMatrixException) {
                    solution.setText("First matrix must be square.");
                }

            });

            case "Determinant" -> button.setOnAction((event -> {
                try {
                    Matrix matOne = readEquations(matrixEquationOne.getText());
                    solution.setText(String.valueOf(MatrixOperations.determinant(matOne)));
                } catch (NotASquareMatrixException exception) {
                    matrixSolution.setText("First matrix must be square.");
                }
            }));

            case "Trace" -> button.setOnAction((event -> {
                try {
                    Matrix matOne = readEquations(matrixEquationOne.getText());
                    solution.setText(String.valueOf(MatrixOperations.trace(matOne)));
                } catch (NotASquareMatrixException exception) {
                    matrixSolution.setText("First matrix must be square.");
                }
            }));

            case "RREF" -> button.setOnAction((event -> {
                try {
                    Matrix matOne = readEquations(matrixEquationOne.getText());
                    solution.setText(MatrixOperations.reducedRowEchelonForm(matOne).toString());
                } catch (SquareMatrixException exception) {
                    matrixSolution.setText("First matrix must not be square.");
                }
            }));

            case "Transpose" -> button.setOnAction((event -> {
                Matrix matOne = readEquations(matrixEquationOne.getText());
                solution.setText(MatrixOperations.transpose(matOne).toString());
            }));

            case "Inverse" -> button.setOnAction((event -> {
                try{
                    Matrix matOne = readEquations(matrixEquationOne.getText());
                    solution.setText(MatrixOperations.inverse(matOne).toString());
                }catch(NotASquareMatrixException notASquareMatrixException){
                    solution.setText("First matrix must be square.");
                }
            }));

            case "Addition" -> button.setOnAction((event -> {
                try {
                    Matrix matOne = readEquations(matrixEquationOne.getText());
                    Matrix matTwo = readEquations(matrixEquationTwo.getText());
                    solution.setText(MatrixOperations.add(matOne, matTwo).toString());
                } catch (UnequalColumnRowException exception) {
                    matrixSolution.setText("First and second matrix must have equal number of rows and cols.");
                }
            }));

            case "Multiplication" -> button.setOnAction((event -> {
                try {
                    Matrix matOne = readEquations(matrixEquationOne.getText());
                    Matrix matTwo = readEquations(matrixEquationTwo.getText());
                    solution.setText(MatrixOperations.multiply(matOne, matTwo).toString());
                } catch (UnequalColumnRowException exception) {
                    matrixSolution.setText("First and second matrix must have equal number of rows and cols.");
                }
            }));

        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
