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
import java.util.stream.Stream;

public class GameEngine {
    private static Board board;
    private static boolean isWhiteTurn = true;
    private static Coordinate startingCoordinates;
    private static boolean pieceSelected = false;
    private static Coordinate blackKingPos;
    private static Coordinate whiteKingPos;
    private static boolean inCheck;

    public static void main(String[] args) {
        inCheck = false;
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
        Move move = new Move(startingCoordinates, endingCoordinates);

        // Check move is valid before doing move
        if (MoveValidator.isValid(board, move)) {
            doMove(move);
            // Update board interface
            BoardVisualiser.updateButtonText(board.getPiece(startingCoordinates).asString(),
                    startingCoordinates);
            BoardVisualiser.updateButtonText(board.getPiece(endingCoordinates).asString(),
                    endingCoordinates);

            inCheck = isInCheck();

            if (inCheck) {
                System.exit(0);
            }

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
                whiteKingPos = move.getEndCoordinates();
            } else {
                blackKingPos = move.getEndCoordinates();
            }
        }
    }

    public Coordinate getBlackKingPos() {
        return blackKingPos;
    }

    public Coordinate getWhiteKingPos() {
        return whiteKingPos;
    }

    // Returns true if a player is in check
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
