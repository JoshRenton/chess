package board;

import pieces.Pawn;
import pieces.Piece;

public class Board {
    private static final int BOARD_SIZE = 8;
    private Piece[][] board;

    public Board() {
        initialiseBoard();
        setupBoard();
    }

    private void initialiseBoard() {
        board = new Piece[8][8];
    }

    private void setupBoard() {
        setupPawns();
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

    private void setupPawns() {
        int whiteRow = 1;
        int blackRow = 6;

        for (int column = 0; column < BOARD_SIZE; column++) {
            setPiece(new Pawn(true), whiteRow, column);
            setPiece(new Pawn(false), blackRow, column);
        }
    }
}
