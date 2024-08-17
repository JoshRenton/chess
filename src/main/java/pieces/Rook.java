package pieces;

import utility.Move;

public class Rook extends Piece {

    public Rook(Colour colour) {
        super(colour);
    }

    public boolean canMove(Move move) {
        return move.getStartRow() == move.getEndRow() ^ move.getStartColumn() == move.getEndColumn();
    }

    public PieceName getName() {
        return PieceName.ROOK;
    }
}
