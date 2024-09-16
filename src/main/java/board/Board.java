package board;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pieces.*;
import utility.Coordinate;

import java.util.stream.Stream;

import static pieces.Piece.Colour;

public class Board {
    private static final int BOARD_SIZE = 8;
    private static final Logger logger = LogManager.getLogger(Board.class);
    private Piece[][] board;
    private Coordinate whiteKingPos;
    private Coordinate blackKingPos;

    public Board() {
        initialiseBoard();
    }

    // Creates a new board using the board state of the input board
    public Board(final Board boardToCopy) {
        initialiseBoard();

        for (int row = 0; row < boardToCopy.getBoardSize(); row++) {
            for (int column = 0; column < boardToCopy.getBoardSize(); column++) {
                Coordinate coordinate = new Coordinate(row, column);
                this.board[row][column] = boardToCopy.getBoard()[row][column];
            }
        }
    }

    public Coordinate getBlackKingPos() {
        return blackKingPos;
    }

    public Coordinate getWhiteKingPos() {
        return whiteKingPos;
    }

    private void initialiseBoard() {
        board = new Piece[8][8];
    }

    public void setupClassic() {
        setupPawns();
        setupBackRow();
    }

    public Piece[][] getBoard() {
        return board;
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }

    public void setPiece(final Piece piece, final Coordinate coordinate) {
        int row = coordinate.getRow();
        int column = coordinate.getColumn();
        board[row][column] = piece;
        if (piece == null) {
            logger.debug("Removed piece at coordinates {}", coordinate);
        } else {
            logger.debug("Set {} at coordinates {}", piece.getName(), coordinate);

            // Update king position
            if (piece.getName() == Piece.PieceName.KING) {
                if (piece.getColour() == Colour.WHITE) {
                    whiteKingPos = coordinate;
                } else {
                    blackKingPos = coordinate;
                }
            }
        }
    }

    public void removePiece(final Coordinate coordinates) {
        setPiece(null, coordinates);
    }

    public Piece getPiece(final Coordinate coordinates) {
        return board[coordinates.getRow()][coordinates.getColumn()] != null ?
                board[coordinates.getRow()][coordinates.getColumn()] : new EmptyPiece();
    }

    public boolean isOccupied(final Coordinate coordinates) {
        return board[coordinates.getRow()][coordinates.getColumn()] != null;
    }

    private void setupPawns() {
        int whiteRow = 1;
        int blackRow = 6;

        for (int column = 0; column < BOARD_SIZE; column++) {
            setPiece(new Pawn(Colour.WHITE), new Coordinate(whiteRow, column));
            setPiece(new Pawn(Colour.BLACK), new Coordinate(blackRow, column));
        }
    }

    private void setupBackRow() {
        Stream.of(Colour.WHITE, Colour.BLACK).forEach(colour -> {
            int row;

            if (colour == Colour.WHITE) {
                row = 0;
            } else {
                row = BOARD_SIZE - 1;
            }

            setPiece(new Rook(colour), new Coordinate(row, 0));
            setPiece(new Rook(colour), new Coordinate(row, BOARD_SIZE - 1));

            setPiece(new Knight(colour), new Coordinate(row, 1));
            setPiece(new Knight(colour), new Coordinate(row, BOARD_SIZE - 2));

            setPiece(new Bishop(colour), new Coordinate(row, 2));
            setPiece(new Bishop(colour), new Coordinate(row, BOARD_SIZE - 3));

            setPiece(new Queen(colour), new Coordinate(row, 3));

            setPiece(new King(colour), new Coordinate(row, 4));
        });
    }
}
