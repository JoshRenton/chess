package pieces;

import utility.Move;

public class Rook extends Piece {
    boolean hasMoved;

    public Rook(Colour colour) {
        super(colour);
        hasMoved = false;
    }

    public boolean canMove(Move move) {
        return move.getStartRow() == move.getEndRow() ^ move.getStartColumn() == move.getEndColumn();
    }

    public void setMoved() {
        hasMoved = true;
    }

    public PieceName getName() {
        return PieceName.ROOK;
    }
}
