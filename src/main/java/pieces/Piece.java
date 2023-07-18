package pieces;

public abstract class Piece {
    private final boolean isWhite;

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public abstract boolean canMove(final int startRow, final int startColumn, final int endRow, final int endColumn);
}
