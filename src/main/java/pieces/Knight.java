package pieces;

import utility.Move;

public class Knight extends Piece {
    public Knight(boolean isWhite) {
        super(isWhite);
    }

    public boolean canMove(Move move) {
        int absRowDiff = Math.abs(move.getStartRow() - move.getEndRow());
        int absColumnDiff = Math.abs(move.getStartColumn() - move.getEndColumn());

        return absRowDiff == 1 && absColumnDiff == 2
                || absRowDiff == 2 && absColumnDiff == 1;
    }

    public String asString() {
        return "N";
    }
}
