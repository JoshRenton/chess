package engine;

import pieces.Piece;
import board.Board;
import pieces.Rook;
import utility.Move;

public final class MoveValidator {

    private MoveValidator() {}

    public static boolean isValid(Board board, Move move) {
        try {
            Piece piece = board.getPiece(move.getStartRow(), move.getStartColumn());
            // Check that the moving piece is of the turn players colour
            if (piece.isWhite() != GameEngine.isWhiteTurn()) {
                return false;
            }
            return piece.canMove(move);
        } catch (NullPointerException e) {
            return false;
        }
    }
}
