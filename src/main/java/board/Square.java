package board;

import pieces.Piece;

public class Square {
    private boolean isOccupied;
    private final int row;
    private final int column;
    private Piece piece;

    public Square(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        if (!isOccupied) {
            isOccupied = true;
        }
    }

    public Piece getPiece() {
        return piece;
    }

    public void removePiece() {
        if (isOccupied) {
            piece = null;
            isOccupied = false;
        }
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
