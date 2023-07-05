package pieces;

import board.Square;

public class Rook extends Piece {

    public Rook(boolean isWhite) {
        super(isWhite);
    }

    public boolean canMove(Square startSquare, Square endSquare) {
        return startSquare.getRow() == endSquare.getRow()
                ^ startSquare.getColumn() == endSquare.getColumn();
    }
}
