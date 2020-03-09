package MatrixOperations;

public class Main {

    private Main() {

        Matrix a = new Matrix(3, 4);
        Matrix b = new Matrix(3, 3);

        a.fillRow(0, EquationConverter.convert("3x+4y+7z=-2"));
        a.fillRow(1, EquationConverter.convert("2x+5y+4z=-7"));
        a.fillRow(2, EquationConverter.convert("1x+9y+1z=-8"));

        b.fillRow(0, EquationConverter.convert("4x+5y=-19"));
        b.fillRow(1, EquationConverter.convert("7x+1y=-1"));
        b.fillRow(2, EquationConverter.convert("6x+3y=-9"));

        System.out.println(a);
        System.out.println(b);

        System.out.println(MatrixOperations.trace(a));
        System.out.println(MatrixOperations.determinant(a));
        System.out.println(MatrixOperations.adjoint(a));
        System.out.println(MatrixOperations.inverse(a));
        System.out.println(MatrixOperations.reducedRowEchelonForm(a));
        System.out.println(MatrixOperations.multiply(a, b));

    }

    public static void main(String[] args) {
        new Main();
    }

}







