package board;

import pieces.*;

import java.util.stream.Stream;

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
        setupBackRow();
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void setPiece(Piece piece, int row, int column) {
        board[row][column] = piece;
    }

    public void removePiece(int row, int column) {
        setPiece(null, row, column);
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

    private void setupBackRow() {
        Stream.of(true, false).forEach(isWhite -> {
            int row;

            if (isWhite) {
                row = 0;
            } else {
                row = BOARD_SIZE - 1;
            }

            setPiece(new Rook(isWhite), row, 0);
            setPiece(new Rook(isWhite), row, BOARD_SIZE - 1);

            setPiece(new Knight(isWhite), row, 1);
            setPiece(new Knight(isWhite), row, BOARD_SIZE - 2);

            setPiece(new Bishop(isWhite), row, 2);
            setPiece(new Bishop(isWhite), row, BOARD_SIZE - 3);

            setPiece(new Queen(isWhite), row, 3);

            setPiece(new King(isWhite), row, 4);
        });
    }
}
