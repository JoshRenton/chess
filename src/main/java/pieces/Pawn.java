package pieces;

public class Pawn extends Piece{
    private boolean hasMoved;

    public Pawn(boolean isWhite) {
        super(isWhite);
        this.hasMoved = false;
    }

    public boolean canMove(final int startRow, final int startColumn, final int endRow, final int endColumn) {
        int rowDiff = endRow - startRow;
        int columnDiff = endColumn - startColumn;

        if (columnDiff == 0 && rowDiff == 1) { // Pawn moving forward one square.
            return true;
        } else if (columnDiff == 0 && rowDiff == 2 && !hasMoved) { // First pawn move can be two squares.
            return true;
        }

        // TODO: Capturing will have to be figured out externally for this.

        return false;
    }

    public void setMoved() {
        hasMoved = true;
    }
}
