package pieces;

import utility.Move;

public class Bishop extends Piece {
    public Bishop(boolean isWhite) {
        super(isWhite);
    }

    public boolean canMove(Move move) {
        int rowDiff = move.getStartRow() - move.getEndRow();
        int columnDiff = move.getStartColumn() - move.getEndColumn();

        return Math.abs(rowDiff) == Math.abs(columnDiff);
    }

    public String asString() {
        return "B";
    }
}
