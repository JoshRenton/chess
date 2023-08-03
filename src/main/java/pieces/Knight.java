package pieces;

import utility.Move;

public class Knight extends Piece {
    public Knight(boolean isWhite) {
        super(isWhite);
    }

    public boolean canMove(Move move) {
        int absRowDiff = Math.abs(move.getStartRow() - move.getEndRow());
        int absColumnDiff = Math.abs(move.getStartColumn() - move.getEndColumn());

        return Math.abs(absRowDiff) == 1 && Math.abs(absColumnDiff) == 2
                || Math.abs(absRowDiff) == 2 && Math.abs(absColumnDiff) == 1;
    }

    public char asChar() {
        return 'N';
    }
}
