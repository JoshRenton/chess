package board;

import pieces.Piece;

public class Board {
    private Piece[][] board;

    public Board() {
        initialiseBoard();
    }

    private void initialiseBoard() {
        board = new Piece[8][8];
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void setPiece(Piece piece, int row, int column) {
        board[row][column] = piece;
    }

    public Piece getPiece(int row, int column) {
        return board[row][column] != null ? board[row][column] : null;
    }

    public boolean isOccupied(int row, int column) {
        return board[row][column] != null;
    }
}
