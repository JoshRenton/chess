package pieces;

import utility.Move;

public class King extends Piece {
    public King(Colour colour) {
        super(colour);
    }

    public boolean canMove(Move move) {
        int rowDiff = move.getStartRow() - move.getEndRow();
        int columnDiff = move.getStartColumn() - move.getEndColumn();

        return Math.abs(rowDiff) <= 1 && Math.abs(columnDiff) <= 1;
    }

    public PieceName getName() {
        return PieceName.KING;
    }
}
