package utility;

public class Move {
    private final int startRow;
    private final int startColumn;
    private final int endRow;
    private final int endColumn;
    private final boolean isWhiteTurn;
    public Move(final boolean isWhiteTurn, final int startRow, final int startColumn, final int endRow, final int endColumn) {
        this.isWhiteTurn = isWhiteTurn;
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.endRow = endRow;
        this.endColumn = endColumn;
    }

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getEndColumn() {
        return endColumn;
    }
}

