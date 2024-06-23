package engine;

import board.Board;
import pieces.Pawn;
import pieces.Piece;
import utility.Coordinate;
import utility.Move;
import utility.Square;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Scanner;

public class GameEngine {
    private static Board board;
    private static boolean isWhiteTurn = true;
    private static Coordinate startingCoordinates;
    private static boolean pieceSelected = false;
    private static Coordinate blackKingPos;
    private static Coordinate whiteKingPos;

    public static void main(String[] args) {
        board = new Board();
        BoardVisualiser.initialise(board);
        BoardVisualiser.showWindow();
    }

    public static boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    private static void attemptMove(Coordinate endingCoordinates) {
        Move move = new Move(startingCoordinates, endingCoordinates);
        if (MoveValidator.isValid(board, move)) {
            doMove(move);
            // Update board interface
            BoardVisualiser.updateButtonText(board.getPiece(startingCoordinates).asString(),
                    startingCoordinates);
            BoardVisualiser.updateButtonText(board.getPiece(endingCoordinates).asString(),
                    endingCoordinates);
            // Swap player turn
            isWhiteTurn = !isWhiteTurn;
        }
    }

    private static void doMove(Move move) {
        Piece movingPiece = board.getPiece(move.getStartCoordinates());
        board.removePiece(move.getStartCoordinates());
        board.setPiece(movingPiece, move.getEndCoordinates());

        if (movingPiece.asString().equals("P")) {
            ((Pawn) movingPiece).setMoved();
        } else if (movingPiece.asString().equals("K")) {
            // Update king position
            if (movingPiece.isWhite()) {
                setWhiteKingPos(move.getEndCoordinates());
            } else {
                setBlackKingPos(move.getEndCoordinates());
            }
        }
    }

    private static void setBlackKingPos(Coordinate coordinates) {
        blackKingPos = coordinates;
    }

    private static void setWhiteKingPos(Coordinate coordinates) {
        whiteKingPos = coordinates;
    }

    public Coordinate getBlackKingPos() {
        return blackKingPos;
    }

    public Coordinate getWhiteKingPos() {
        return whiteKingPos;
    }

    private static class SquareListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Square square = (Square) e.getSource();
            Coordinate coordinate = square.getCoordinates();
            if (pieceSelected) {
                attemptMove(coordinate);
                pieceSelected = false;
                // This prevents a click on a square with no piece from beginning a move, and a click on a piece that
                // is not of the turn player's colour
            } else if (board.isOccupied(coordinate) && board.getPiece(coordinate).isWhite() == isWhiteTurn()){
                startingCoordinates = coordinate;
                pieceSelected = true;
            }
        }
    }

    // TODO: Maybe this should be returning a single instance instead of a new listener each time
    public static SquareListener getSquareListener() {
        return new SquareListener();
    }
}
