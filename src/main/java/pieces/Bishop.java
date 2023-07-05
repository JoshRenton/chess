package pieces;

import board.Square;

public class Bishop extends Piece {
    public Bishop(boolean isWhite) {
        super(isWhite);
    }

    /**
     * @param startSquare the square the bishop is currently occupying
     * @param endSquare the square that the bishop is trying to move to
     * @return true if the bishop can move to that square, ignoring other pieces
     */
    public boolean canMove(Square startSquare, Square endSquare) {
        int rowDiff = startSquare.getRow() - endSquare.getRow();
        int columnDiff = startSquare.getColumn() - endSquare.getColumn();

        return Math.abs(rowDiff) == Math.abs(columnDiff);
    }
}
