package engine;

import board.Board;
import pieces.Pawn;
import pieces.Piece;
import utility.Coordinate;
import utility.Move;
import utility.Square;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameEngine {
    private static Board board;
    private static boolean isWhiteTurn = true;
    private static Coordinate startCoordinate;
    private static boolean pieceSelected = false;
    private static Coordinate blackKingPos;
    private static Coordinate whiteKingPos;
    private static boolean inCheck;

    public static void main(String[] args) {
        inCheck = false;
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

        // Check move is valid before doing move
        if (MoveValidator.isValid(board, move)) {
            doMove(move);
            // Update board interface
            visualiseMove(move);

            inCheck = isInCheck();

            // Swap player turn
            isWhiteTurn = !isWhiteTurn;
        }
    }

    private static void doMove(Move move) {
        Piece movingPiece = board.getPiece(move.getStartCoordinate());
        board.removePiece(move.getStartCoordinate());
        board.setPiece(movingPiece, move.getEndCoordinate());

        if (movingPiece.asString().equals("P")) {
            ((Pawn) movingPiece).setMoved();
        } else if (movingPiece.asString().equals("K")) {
            // Update king position
            if (movingPiece.isWhite()) {
                whiteKingPos = move.getEndCoordinate();
            } else {
                blackKingPos = move.getEndCoordinate();
            }
        }
    }

    private static void visualiseMove(Move move) {
        Coordinate startCoordinate = move.getStartCoordinate();
        Coordinate endCoordinate = move.getEndCoordinate();

        BoardVisualiser.updateButtonText(board.getPiece(startCoordinate).asString(),
                startCoordinate);
        BoardVisualiser.updateButtonText(board.getPiece(endCoordinate).asString(),
                endCoordinate);
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

        if (!isWhiteTurn) {
            kingPos = whiteKingPos;
        } else {
            kingPos = blackKingPos;
        }

        for (int row = 0; row < pieces.length; row++) {
            for (int column = 0; column < pieces[row].length; column++) {
                Coordinate start = new Coordinate(row, column);
                Piece piece = board.getPiece(start);
                // Check piece is of opposite colour and not empty
                if (!piece.asString().equals(" ") && piece.isWhite() == isWhiteTurn) {
                    Move move = new Move(start, kingPos);
                    if (MoveValidator.isValid(board, move)) {
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
