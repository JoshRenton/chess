package board;

public class Board {
    private static final Square[][] squares = initialiseSquares();

    public Board() {

    }

    private static Square[][] initialiseSquares() {
        Square[][] squares = new Square[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Square square = new Square(row, column);
                squares[row][column] = square;
            }
        }
        return squares;
    }

    public Square[][] getBoard() {
        return squares;
    }
}
