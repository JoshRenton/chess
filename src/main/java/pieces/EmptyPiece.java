package pieces;

import utility.Move;

public class EmptyPiece extends Piece {
    public EmptyPiece() {
        // The colour is irrelevant
        super(true);
    }

    public boolean canMove(Move move) {
        // Try as you might, empty piece shan't be moved
        return false;
    }

    public char asChar() {
        return ' ';
    }
}
