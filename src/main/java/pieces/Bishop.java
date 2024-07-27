package pieces;

import utility.Move;

public class Bishop extends Piece {
    public Bishop(Colour colour) {
        super(colour);
    }

    public boolean canMove(Move move) {
        int rowDiff = move.getStartRow() - move.getEndRow();
        int columnDiff = move.getStartColumn() - move.getEndColumn();

        return Math.abs(rowDiff) == Math.abs(columnDiff);
    }

    public PieceName getName() {
        return PieceName.BISHOP;
    }
}
