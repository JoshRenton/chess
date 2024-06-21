package engine;

import pieces.EmptyPiece;
import pieces.Piece;
import board.Board;
import utility.Move;

// TODO: Add validation for king moves.
// TODO: Add validation for pawn captures.

public final class MoveValidator {

    private MoveValidator() {}

    public static boolean isValid(Board board, Move move) {
        Piece piece = board.getPiece(move.getStartRow(), move.getStartColumn());
        
        int startRow = move.getStartRow();
        int endRow = move.getEndRow();
        int startColumn = move.getStartColumn();
        int endColumn = move.getEndColumn();

        int rowDirection = endRow - startRow;
        int columnDirection = endColumn - startColumn;

        rowDirection = normalizeDirection(rowDirection);
        columnDirection = normalizeDirection(columnDirection);

        if (isIncorrectTurn(piece.isWhite())) return false;

        // Check that there is no piece in between the start and end square
        // Ignore this check for knights
        if (!piece.asString().equals("N")) {
            if (pathIsObstructed(board, startRow, endRow, startColumn, endColumn, rowDirection, columnDirection)) {
                return false;
            }
        }

        // Check that if there is a piece at the end square, it is of the opposite colour
        // Also check that it is not the empty piece
        Piece endPiece = board.getPiece(endRow, endColumn);
        if (endPiece.isWhite() == piece.isWhite() && !endPiece.asString().equals(" ")) {
            return false;
        }

        // Check for pawn capture
        if (piece.asString().equals("P") && !endPiece.asString().equals(" ")) {
            if (isPawnCapture(piece.isWhite(), startRow, endRow, startColumn, endColumn)) {
                return true;
            }
        }

        return piece.canMove(move);
    }

    private static boolean isPawnCapture(boolean isWhite, int startRow, int endRow, int startColumn, int endColumn) {
        if (isWhite && (endRow - startRow) == 1 && Math.abs(endColumn - startColumn) == 1) {
            return true;
        } else return !isWhite && (endRow - startRow) == -1 && Math.abs(endColumn - startColumn) == 1;
    }

    private static boolean isIncorrectTurn(Boolean isWhitePiece) {
        // Check that the moving piece is of the turn players colour
        return isWhitePiece != GameEngine.isWhiteTurn();
    }

    private static boolean pathIsObstructed(Board board, int startRow, int endRow, int startColumn, int endColumn,
                                               int rowDirection, int columnDirection) {
        int row = startRow + rowDirection;
        int column = startColumn + columnDirection;

        while (row != endRow || column != endColumn) {
            if (board.isOccupied(row, column)) {
                return true;
            }
            row += rowDirection;
            column += columnDirection;
        }

        return false;
    }

    private static int normalizeDirection(int direction) {
        if (direction < 0) {
            direction = -1;
        } else if (direction > 0){
            direction = 1;
        }

        return direction;
    }
}
