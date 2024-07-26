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

        previousBoardState = board;

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

    private static void doMove(Move move, boolean isEnPassant) {
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

    private static void visualiseMove(Move move, boolean isEnPassant) {
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

    // Returns true if a player is in check
    // TODO: A piece moving will never result in a discovered check by a pawn or knight
    // TODO: Therefore, can check the moving piece and also for rooks, bishops and queens (king cannot directly check)
    private static boolean isInCheck() {
        Coordinate kingPos;
        Piece[][] pieces = board.getBoard();

        if (isWhiteTurn) {
            kingPos = whiteKingPos;
        } else {
            kingPos = blackKingPos;
        }

        for (int row = 0; row < pieces.length; row++) {
            for (int column = 0; column < pieces[row].length; column++) {
                Coordinate start = new Coordinate(row, column);
                Piece piece = board.getPiece(start);
                // Check piece is of opposite colour and not empty
                if (piece.getName() != PieceName.EMPTY && (piece.isWhite() != isWhiteTurn)) {
                    Move move = new Move(start, kingPos);
                    if (isValid(board, move) == MoveStatus.VALID) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // Listens for interaction with a square
    private static class SquareListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Square square = (Square) e.getSource();
            Coordinate coordinate = square.getCoordinates();
            if (pieceSelected) {
                playerAction(coordinate);
                pieceSelected = false;

                // This prevents a click on a square with no piece from beginning a move, and a click on a piece that
                // is not of the turn player's colour
            } else if (board.isOccupied(coordinate) && board.getPiece(coordinate).isWhite() == isWhiteTurn()){
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
