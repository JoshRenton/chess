package pieces;

import board.Square;

public class Rook extends Piece {
    boolean hasMoved;

    public Rook(boolean isWhite) {
        super(isWhite);
        hasMoved = false;
    }

    public boolean canMove(Square startSquare, Square endSquare) {
        return startSquare.getRow() == endSquare.getRow()
                ^ startSquare.getColumn() == endSquare.getColumn();
    }

    public void setMoved() {
        hasMoved = true;
    }
}
