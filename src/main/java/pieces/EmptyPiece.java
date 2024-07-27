package pieces;

import utility.Move;

public class EmptyPiece extends Piece {
    public EmptyPiece() {
        super(Colour.NONE);
    }

    public boolean canMove(Move move) {
        // Try as you might, empty piece shan't be moved
        return false;
    }

    public PieceName getName() {
        return PieceName.EMPTY;
    }

    @Override
    public String getIconName() {
        return "empty.png";
    }
}
