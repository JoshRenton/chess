package pieces;

import board.Square;

public class Queen extends Piece {
    public Queen(boolean isWhite) {
        super(isWhite);
    }

    public boolean canMove(Square startSquare, Square endSquare) {
        int rowDiff = startSquare.getRow() - endSquare.getRow();
        int columnDiff = startSquare.getColumn() - endSquare.getColumn();

        return Math.abs(rowDiff) == Math.abs(columnDiff)
                || (startSquare.getRow() == endSquare.getRow()
                ^ startSquare.getColumn() == endSquare.getColumn());
    }
}
