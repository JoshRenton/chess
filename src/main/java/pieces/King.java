package pieces;

public class King extends Piece {
    boolean hasMoved;
    public King(boolean isWhite) {
        super(isWhite);
        hasMoved = true;
    }

    public boolean canMove(final int startRow, final int startColumn, final int endRow, final int endColumn) {
        int rowDiff = startRow - endRow;
        int columnDiff = startColumn - endColumn;

        return Math.abs(rowDiff) <= 1 && Math.abs(columnDiff) <= 1;
    }

    public void setMoved() {
        hasMoved = true;
    }

    public char asChar() {
        return 'K';
    }
}
