package pieces;

import utility.Move;

public class Pawn extends Piece{
    private boolean hasMoved;

    public Pawn(Colour colour) {
        super(colour);
        this.hasMoved = false;
    }

    public boolean canMove(Move move) {
        int rowDiff = move.getEndRow() - move.getStartRow();
        int columnDiff = move.getEndColumn() - move.getStartColumn();

        if (columnDiff == 0 && rowDiff == 1 && isWhite()) { // Pawn moving forward one square.
            return true;
        } else if (columnDiff == 0 && rowDiff == 2 && isWhite() && !hasMoved) { // First pawn move can be two squares.
            return true;
        } else if (columnDiff == 0 && rowDiff == -1 && !isWhite()) {
            return true;
        } else
            return columnDiff == 0 && rowDiff == -2 && !isWhite() && !hasMoved;
    }

    public void setMoved() {
        hasMoved = true;
    }

    public PieceName getName() {
        return PieceName.PAWN;
    }
}
