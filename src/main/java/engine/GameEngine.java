package engine;

import board.Board;
import pieces.Pawn;
import pieces.Piece;
import utility.Move;
import utility.Square;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Scanner;

public class GameEngine {
    private static Board board;
    private static boolean isWhiteTurn = true;
    private static int[] startingCoordinates;
    private static boolean pieceSelected = false;
    private static int[] blackKingPos;
    private static int[] whiteKingPos;

    public static void main(String[] args) {
        board = new Board();
        BoardVisualiser.initialise(board);
        BoardVisualiser.showWindow();
    }

    public static boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    private static void attemptMove(int[] endingCoordinates) {
        Move move = new Move(startingCoordinates[0], startingCoordinates[1], endingCoordinates[0],
                endingCoordinates[1]);
        if (MoveValidator.isValid(board, move)) {
            doMove(move);
            // Update board interface
            BoardVisualiser.updateButtonText(board.getPiece(startingCoordinates[0], startingCoordinates[1]).asString(),
                    startingCoordinates);
            BoardVisualiser.updateButtonText(board.getPiece(endingCoordinates[0], endingCoordinates[1]).asString(),
                    endingCoordinates);
            // Swap player turn
            isWhiteTurn = !isWhiteTurn;
        }
    }

    private static void doMove(Move move) {
        Piece movingPiece = board.getPiece(move.getStartRow(), move.getStartColumn());
        board.removePiece(move.getStartRow(), move.getStartColumn());
        board.setPiece(movingPiece, move.getEndRow(), move.getEndColumn());

        if (movingPiece.asString().equals("P")) {
            ((Pawn) movingPiece).setMoved();
        } else if (movingPiece.asString().equals("K")) {
            // Update king position
            if (movingPiece.isWhite()) {
                setWhiteKingPos(move.getEndRow(), move.getEndColumn());
            } else {
                setBlackKingPos(move.getEndRow(), move.getEndColumn());
            }
        }
    }

    private static void setBlackKingPos(int row, int column) {
        blackKingPos = new int[]{row, column};
    }

    private static void setWhiteKingPos(int row, int column) {
        whiteKingPos = new int[]{row, column};
    }

    public int[] getBlackKingPos() {
        return blackKingPos;
    }

    public int[] getWhiteKingPos() {
        return whiteKingPos;
    }

    private static class SquareListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Square square = (Square) e.getSource();
            int[] coordinates = square.getCoordinates();
            if (pieceSelected) {
                attemptMove(coordinates);
                pieceSelected = false;
                // This prevents a click on a square with no piece from beginning a move, and a click on a piece that
                // is not of the turn player's colour
            } else if (board.isOccupied(coordinates[0], coordinates[1]) && board.getPiece(coordinates[0],
                    coordinates[1]).isWhite() == isWhiteTurn()){
                startingCoordinates = coordinates;
                pieceSelected = true;
            }
        }
    }

    // TODO: Maybe this should be returning a single instance instead of a new listener each time
    public static SquareListener getSquareListener() {
        return new SquareListener();
    }
}
