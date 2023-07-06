package pieces;

import board.Square;

public class Knight extends Piece {
    public Knight(boolean isWhite) {
        super(isWhite);
    }

    public boolean canMove(Square startSquare, Square endSquare) {
        int absRowDiff = Math.abs(startSquare.getRow() - endSquare.getRow());
        int absColumnDiff = Math.abs(startSquare.getColumn() - endSquare.getColumn());

        return Math.abs(absRowDiff) == 1 && Math.abs(absColumnDiff) == 2
                || Math.abs(absRowDiff) == 2 && Math.abs(absColumnDiff) == 1;
    }
}
