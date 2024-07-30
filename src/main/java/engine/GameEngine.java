package engine;

import board.Board;
import pieces.Pawn;
import pieces.Piece;
import pieces.Piece.PieceName;
import utility.Coordinate;
import utility.Move;
import utility.Square;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static engine.MoveValidator.*;

public class GameEngine {
    private static Board board;
    // Keeps track of the previous board state so that moves can be undone
    private static Board previousBoardState;
    private static boolean isWhiteTurn = true;
    private static Coordinate startCoordinate;
    private static boolean pieceSelected = false;
    private static Coordinate blackKingPos;
    private static Coordinate whiteKingPos;

    public static void main(String[] args) {
        // TODO: Consider having these be set at board creation instead of hard-coded values
        whiteKingPos = new Coordinate(0, 4);
        blackKingPos = new Coordinate(7, 4);
        board = new Board();
        BoardVisualiser.initialise(board);
        BoardVisualiser.showWindow();
    }

    public static boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    private static void playerAction(Coordinate endingCoordinates) {
        Move move = new Move(startCoordinate, endingCoordinates);

        MoveStatus status = isValid(board, move);

        previousBoardState = new Board(board);

        boolean moveSuccessful = false;

        // Check move is valid before doing move
        if (status != MoveStatus.INVALID) {

            switch (status) {
                case VALID -> {
                    moveSuccessful = attemptMove(move, false);
                }
                case EN_PASSANT -> {
                    moveSuccessful = attemptMove(move, true);
                }
            }

            // Swap player turn only if move was successful
            if (moveSuccessful) {
                isWhiteTurn = !isWhiteTurn;
            }
        }
    }

    // Performs a move if it does not result in the turn player being in check
    // Returns whether the move is completed or not
    private static boolean attemptMove(Move move, boolean isEnPassant) {
        doMove(move, isEnPassant);
        // Check if turn player is in check after move
        if (isInCheck()) {
            // Undo move
            board = previousBoardState;
            return false;
        } else {
            visualiseMove(move, isEnPassant);
            return true;
        }
    }

    private static void doMove(final Move move, final boolean isEnPassant) {
        Piece movingPiece = board.getPiece(move.getStartCoordinate());

        // Check if move is en passant
        if (isEnPassant) {
            int row = move.getStartRow();
            int column = move.getEndColumn();

            board.removePiece(new Coordinate(row, column));
        }

        // Update piece positions on board
        board.removePiece(move.getStartCoordinate());
        board.setPiece(movingPiece, move.getEndCoordinate());

        if (movingPiece.getName() == PieceName.PAWN) {
            ((Pawn) movingPiece).setMoved();
        } else if (movingPiece.getName() == PieceName.KING) {
            // Update king position
            if (movingPiece.isWhite()) {
                whiteKingPos = move.getEndCoordinate();
            } else {
                blackKingPos = move.getEndCoordinate();
            }
        }
    }

    // Update the GUI to show the result of a move
    private static void visualiseMove(final Move move, final boolean isEnPassant) {
        Coordinate startCoordinate = move.getStartCoordinate();
        Coordinate endCoordinate = move.getEndCoordinate();

        BoardVisualiser.updateButtonIcon(board.getPiece(startCoordinate).getIcon(),
                startCoordinate);
        BoardVisualiser.updateButtonIcon(board.getPiece(endCoordinate).getIcon(),
                endCoordinate);

        if (isEnPassant) {
            int row = startCoordinate.getRow();
            int column = endCoordinate.getColumn();
            System.out.println(row);
            System.out.println(column);

            Coordinate enPassantCoordinate = new Coordinate(row, column);

            BoardVisualiser.updateButtonIcon(board.getPiece(enPassantCoordinate).getIcon(), enPassantCoordinate);
        }
    }

    public Coordinate getBlackKingPos() {
        return blackKingPos;
    }

    public Coordinate getWhiteKingPos() {
        return whiteKingPos;
    }

    private static boolean isInCheck() {
        Coordinate kingPos;

        if (isWhiteTurn) {
            kingPos = whiteKingPos;
        } else {
            kingPos = blackKingPos;
        }

        return isSquareThreatened(kingPos);
    }

    // Check if any opposing pieces threaten the input coordinate
    private static boolean isSquareThreatened(Coordinate coordinate) {
        int[][] steps = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        int[][] knightSteps = {{1, 2}, {2, 1}, {-1, 2}, {-2, 1}, {1, -2}, {2, -1}, {-1, -2}, {-2, -1}};

        for (int[] stepDir: steps) {
            if (!isDirectionSafe(coordinate, stepDir[0], stepDir[1])) {
                return true;
            }
        }

        // Check if any knights are attacking the specified square
        for (int[] knightStep: knightSteps) {
            int rowToCheck = coordinate.getRow() + knightStep[0];
            int columnToCheck = coordinate.getColumn() + knightStep[1];

            if (rowToCheck >= 0 && rowToCheck < board.getBoardSize() && columnToCheck >= 0 &&
                    columnToCheck < board.getBoardSize()) {
                Piece piece = board.getPiece(new Coordinate(rowToCheck, columnToCheck));
                if (piece.getName() == PieceName.KNIGHT && piece.isWhite() != isWhiteTurn()) {
                    return true;
                }
            }
        }

        return false;
    }

    // TODO: Maybe change to isDirectionThreatened to match other methods
    private static boolean isDirectionSafe(Coordinate initialCoordinate, final int rowStep, final int columnStep) {
        final int row = initialCoordinate.getRow();
        final int column = initialCoordinate.getColumn();

        int currentRow = row + rowStep;
        int currentColumn = column + columnStep;

        while (currentRow < board.getBoardSize() && currentRow >= 0 && currentColumn < board.getBoardSize() &&
                currentColumn >= 0) {
            Coordinate currentCoordinate = new Coordinate(currentRow, currentColumn);
            if (board.isOccupied(currentCoordinate)) {
                Move move = new Move(currentCoordinate, initialCoordinate);
                return MoveValidator.isValid(board, move) == MoveStatus.INVALID;
            }

            currentRow += rowStep;
            currentColumn += columnStep;
        }

        return true;
    }

    // Listens for interaction with a square
    private static class SquareListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Square square = (Square) e.getSource();
            Coordinate coordinate = square.getCoordinates();
            Piece currentSelectedPiece = board.getPiece(coordinate);
            if (pieceSelected) {
                Piece previousSelectedPiece = board.getPiece(startCoordinate);
                if (previousSelectedPiece.getColour() != currentSelectedPiece.getColour()) {
                    playerAction(coordinate);
                    pieceSelected = false;
                } else {
                    startCoordinate = coordinate;
                }

                // This prevents a click on a square with no piece from beginning a move, and a click on a piece that
                // is not of the turn player's colour
            } else if (currentSelectedPiece.isWhite() == isWhiteTurn()){
                startCoordinate = coordinate;
                pieceSelected = true;
            }
        }
    }

    // TODO: Maybe this should be returning a single instance instead of a new listener each time
    public static SquareListener getSquareListener() {
        return new SquareListener();
    }
}
