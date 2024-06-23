package board;

import pieces.*;
import utility.Coordinate;

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

    public int getBoardSize() {
        return BOARD_SIZE;
    }

    public void setPiece(Piece piece, Coordinate coordinates) {
        int row = coordinates.getRow();
        int column = coordinates.getColumn();
        board[row][column] = piece;
    }

    public void removePiece(Coordinate coordinates) {
        setPiece(null, coordinates);
    }

    public Piece getPiece(Coordinate coordinates) {
        return board[coordinates.getRow()][coordinates.getColumn()] != null ?
                board[coordinates.getRow()][coordinates.getColumn()] : new EmptyPiece();
    }

    public boolean isOccupied(Coordinate coordinates) {
        return board[coordinates.getRow()][coordinates.getColumn()] != null;
    }

    private void setupPawns() {
        int whiteRow = 1;
        int blackRow = 6;

        for (int column = 0; column < BOARD_SIZE; column++) {
            setPiece(new Pawn(true), new Coordinate(whiteRow, column));
            setPiece(new Pawn(false), new Coordinate(blackRow, column));
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

            setPiece(new Rook(isWhite), new Coordinate(row, 0));
            setPiece(new Rook(isWhite), new Coordinate(row, BOARD_SIZE - 1));

            setPiece(new Knight(isWhite), new Coordinate(row, 1));
            setPiece(new Knight(isWhite), new Coordinate(row, BOARD_SIZE - 2));

            setPiece(new Bishop(isWhite), new Coordinate(row, 2));
            setPiece(new Bishop(isWhite), new Coordinate(row, BOARD_SIZE - 3));

            setPiece(new Queen(isWhite), new Coordinate(row, 3));

            setPiece(new King(isWhite), new Coordinate(row, 4));
        });
    }
}
