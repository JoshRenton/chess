package pieces;

import board.Square;

public class Pawn extends Piece{
    private boolean hasMoved;

    public Pawn(boolean isWhite) {
        super(isWhite);
        this.hasMoved = false;
    }

    public boolean canMove(Square startSquare, Square endSquare) {
        int rowDiff = endSquare.getRow() - startSquare.getRow();
        int columnDiff = endSquare.getColumn() - startSquare.getColumn();

        if (columnDiff == 0 && rowDiff == 1) { // Pawn moving forward one square.
            return true;
        } else if (columnDiff == 0 && rowDiff == 2 && !hasMoved) { // First pawn move can be two squares.
            return true;
        } else if (!endSquare.isOccupied()) {
            return false;
        } else if (Math.abs(columnDiff) == 1 && rowDiff == 1
                && endSquare.getPiece().isWhite() != this.isWhite()) { // Pawn can only move diagonally if capturing.
            return true;
        }

        return false;
    }

    public void setMoved() {
        hasMoved = true;
    }
}
