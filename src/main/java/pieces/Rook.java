package pieces;

public class Rook extends Piece {
    boolean hasMoved;

    public Rook(boolean isWhite) {
        super(isWhite);
        hasMoved = false;
    }

    public boolean canMove(final int startRow, final int startColumn, final int endRow, final int endColumn) {
        return startRow == endRow ^ startColumn == endColumn;
    }

    public void setMoved() {
        hasMoved = true;
    }

    public char asChar() {
        return 'R';
    }
}
