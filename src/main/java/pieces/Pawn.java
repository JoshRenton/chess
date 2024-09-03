package pieces;

import utility.Move;

public class Pawn extends Piece{

    public Pawn(Colour colour) {
        super(colour);
    }

    public boolean canMove(Move move) {
        int rowDiff = move.getEndRow() - move.getStartRow();
        int columnDiff = move.getEndColumn() - move.getStartColumn();

        // Pawn moving forward one square
        if (columnDiff == 0 && rowDiff == 1 && isWhite()) {
            return true;
            // First pawn move can be two squares
        } else if (columnDiff == 0 && rowDiff == 2 && isWhite() && super.hasNotMoved()) {
            return true;
        } else if (columnDiff == 0 && rowDiff == -1 && !isWhite()) {
            return true;
        } else
            return columnDiff == 0 && rowDiff == -2 && !isWhite() && super.hasNotMoved();
    }

    public PieceName getName() {
        return PieceName.PAWN;
    }
}
