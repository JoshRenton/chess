package pieces;

import board.Square;

public class King extends Piece {
    boolean hasMoved;
    public King(boolean isWhite) {
        super(isWhite);
        hasMoved = true;
    }

    public boolean canMove(Square startSquare, Square endSquare) {
        int rowDiff = startSquare.getRow() - endSquare.getRow();
        int columnDiff = startSquare.getColumn() - endSquare.getColumn();

        return Math.abs(rowDiff) <= 1 && Math.abs(columnDiff) <= 1;
    }

    public void setMoved() {
        hasMoved = true;
    }
}
