package pieces;

import utility.Move;

public class King extends Piece {
    boolean hasMoved;
    public King(boolean isWhite) {
        super(isWhite);
        hasMoved = true;
    }

    public boolean canMove(Move move) {
        int rowDiff = move.getStartRow() - move.getEndRow();
        int columnDiff = move.getStartColumn() - move.getEndColumn();

        return Math.abs(rowDiff) <= 1 && Math.abs(columnDiff) <= 1;
    }

    public void setMoved() {
        hasMoved = true;
    }

    public PieceName getName() {
        return PieceName.KING;
    }
}
