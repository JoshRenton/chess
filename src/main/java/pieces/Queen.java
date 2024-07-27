package pieces;

import utility.Move;

public class Queen extends Piece {
    public Queen(Colour colour) {
        super(colour);
    }

    public boolean canMove(Move move) {
        int rowDiff = move.getStartRow() - move.getEndRow();
        int columnDiff = move.getStartColumn() - move.getEndColumn();

        return Math.abs(rowDiff) == Math.abs(columnDiff) || (move.getStartRow() == move.getEndRow()
                ^ move.getStartColumn() == move.getEndColumn());
    }

    public PieceName getName() {
        return PieceName.QUEEN;
    }
}
