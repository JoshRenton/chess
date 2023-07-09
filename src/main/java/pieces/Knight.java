package pieces;

public class Knight extends Piece {
    public Knight(boolean isWhite) {
        super(isWhite);
    }

    public boolean canMove(final int startRow, final int startColumn, final int endRow, final int endColumn) {
        int absRowDiff = Math.abs(startRow - endRow);
        int absColumnDiff = Math.abs(startColumn - endColumn);

        return Math.abs(absRowDiff) == 1 && Math.abs(absColumnDiff) == 2
                || Math.abs(absRowDiff) == 2 && Math.abs(absColumnDiff) == 1;
    }
}
