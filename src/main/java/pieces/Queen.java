package pieces;

public class Queen extends Piece {
    public Queen(boolean isWhite) {
        super(isWhite);
    }

    public boolean canMove(final int startRow, final int startColumn, final int endRow, final int endColumn) {
        int rowDiff = startRow - endRow;
        int columnDiff = startColumn - endColumn;

        return Math.abs(rowDiff) == Math.abs(columnDiff) || (startRow == endRow ^ startColumn == endColumn);
    }
}
