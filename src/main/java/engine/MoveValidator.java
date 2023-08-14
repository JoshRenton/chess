package engine;

import pieces.Piece;
import board.Board;
import pieces.Rook;
import utility.Move;

public class MoveValidator {

    public MoveValidator() {

    }

    public static boolean isValid(Board board, Move move) {
        try {
            Piece piece = board.getPiece(move.getStartRow(), move.getStartColumn());
            if (piece.isWhite() != move.isWhiteTurn()) {
                return false;
            }
            boolean canMove = piece.canMove(move);
            return canMove;
        } catch (NullPointerException e) {
            return false;
        }
    }
}
