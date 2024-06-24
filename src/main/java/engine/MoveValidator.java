package engine;

import pieces.Piece;
import board.Board;
import utility.Coordinate;
import utility.Move;

import static engine.GameEngine.*;

// TODO: Add validation for king moves.
// TODO: Add validation for en passant.

public final class MoveValidator {

    private MoveValidator() {}

    // TODO: Reverse logic so moves are denied by default
    public static boolean isValid(Board board, Move move) {
        Piece piece = board.getPiece(move.getStartCoordinates());

        Coordinate startCoordinate = new Coordinate(move.getStartRow(), move.getStartColumn());
        Coordinate endCoordinate = new Coordinate(move.getEndRow(), move.getEndColumn());

        Piece endPiece = board.getPiece(move.getEndCoordinates());

        if (isCorrectTurn(piece.isWhite())) {
            // Deal with pawn special rules
            if (piece.asString().equals("P")) {
                return (piece.canMove(move) && isValidPawnMove(board, startCoordinate, endCoordinate)) ||
                        (isOppositeColourPiece(piece, endPiece) && isPawnCapture(startCoordinate, endCoordinate));
            } else if (piece.canMove(move)) {
                if (endPiece.asString().equals(" ") || isOppositeColourPiece(piece, endPiece)) {
                    if (piece.asString().equals("N")) {
                        return true;
                    } else {
                        return pathIsUnobstructed(board, startCoordinate, endCoordinate);
                    }
                }
            }
        }

        return false;
    }

    private static boolean isValidPawnMove(Board board, Coordinate startCoordinate, Coordinate endCoordinate) {
        return pathIsUnobstructed(board, startCoordinate, endCoordinate) &&
                board.getPiece(endCoordinate).asString().equals(" ");
    }

    private static boolean isPawnCapture(Coordinate startCoordinate, Coordinate endCoordinate) {
        if (isWhiteTurn() && ((endCoordinate.getRow() - startCoordinate.getRow()) == 1) &&
                (Math.abs(endCoordinate.getColumn() - startCoordinate.getColumn()) == 1)) {
            return true;
        } else return !isWhiteTurn() && ((endCoordinate.getRow() - startCoordinate.getRow()) == -1) &&
                (Math.abs(endCoordinate.getColumn() - startCoordinate.getColumn()) == 1);
    }

    private static boolean isEnPassant(Board board, int startRow, int endRow, int startColumn, int endColumn) {
        // TODO: Currently using GameEngine.isWhiteTurn to avoid passing it as a parameter, but if the order of
        // TODO: validation checks changes in the future, this could break so change at some point
        if (isWhiteTurn() && startRow == 4 && Math.abs(endColumn - startColumn) == 1 &&
                board.getPiece(new Coordinate(startRow, endColumn)).asString().equals("P")) {
            return true;
        } else return !isWhiteTurn() && startRow == 3 && Math.abs(endColumn - startColumn) == 1 &&
                board.getPiece(new Coordinate(startRow, endColumn)).asString().equals("P");
    }

    private static boolean isCorrectTurn(Boolean isWhitePiece) {
        // Check that the moving piece is of the turn players colour
        return isWhitePiece == isWhiteTurn();
    }

    // Returns true if path is unobstructed
    private static boolean pathIsUnobstructed(Board board, Coordinate startCoordinate, Coordinate endCoordinate) {
        int rowDirection = endCoordinate.getRow() - startCoordinate.getRow();
        int columnDirection = endCoordinate.getColumn() - startCoordinate.getColumn();

        rowDirection = normalizeDirection(rowDirection);
        columnDirection = normalizeDirection(columnDirection);

        int row = startCoordinate.getRow() + rowDirection;
        int column = startCoordinate.getColumn() + columnDirection;

        while (row != endCoordinate.getRow() || column != endCoordinate.getColumn()) {
            if (board.isOccupied(new Coordinate(row, column))) {
                return false;
            }
            row += rowDirection;
            column += columnDirection;
        }

        return true;
    }

    private static int normalizeDirection(int direction) {
        if (direction < 0) {
            direction = -1;
        } else if (direction > 0){
            direction = 1;
        }

        return direction;
    }

    private static boolean isOppositeColourPiece(Piece piece, Piece endPiece) {
        return !endPiece.asString().equals(" ") && piece.isWhite() != endPiece.isWhite();
    }
}
