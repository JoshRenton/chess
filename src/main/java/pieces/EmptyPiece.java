package pieces;

import utility.Move;

public class EmptyPiece extends Piece {
    public EmptyPiece() {
        // The colour is irrelevant
        super(true);
    }

    public boolean canMove(Move move) {
        // Empty piece can never move
        return false;
    }

    public char asChar() {
        return '.';
    }
}
