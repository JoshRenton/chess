package engine;

import pieces.Piece;
import board.Board;
import utility.Coordinate;
import utility.Move;

import static engine.GameEngine.*;
import static pieces.Piece.*;

public final class MoveValidator {

    private MoveValidator() {}

    // Checks if a move is valid and returns either a VALID, INVALID, or EN_PASSANT status
    public static MoveStatus isValid(final Board board, final Move move) {
        Piece piece = board.getPiece(move.getStartCoordinate());

        Coordinate startCoordinate = new Coordinate(move.getStartRow(), move.getStartColumn());
        Coordinate endCoordinate = new Coordinate(move.getEndRow(), move.getEndColumn());

        Piece endPiece = board.getPiece(move.getEndCoordinate());

        // Deal with pawn special rules
        if (piece.getName() == PieceName.PAWN) {
            if (piece.canMove(move)) {
                if (isValidPawnMove(board, startCoordinate, endCoordinate)) {
                    return MoveStatus.VALID;
                } else {
                    return MoveStatus.INVALID;
                }
            } else {
                if (isPawnCapture(startCoordinate, endCoordinate) && endPiece.getName() != PieceName.EMPTY) {
                    return MoveStatus.VALID;
                } else if (isEnPassant(board, startCoordinate, endCoordinate)) {
                    return MoveStatus.EN_PASSANT;
                }
            }
        } else if (piece.canMove(move)) {
            if (endPiece.getName() == PieceName.EMPTY || isOppositeColourPiece(piece, endPiece)) {
                // Knights can ignore obstructions
                if (piece.getName() == PieceName.KNIGHT) {
                    return MoveStatus.VALID;
                } else if (pathIsUnobstructed(board, startCoordinate, endCoordinate)) {
                    return MoveStatus.VALID;
                }
            }
        }

        return MoveStatus.INVALID;
    }

    // Returns whether the attempted move is a valid pawn move (not including captures)
    private static boolean isValidPawnMove(final Board board, final Coordinate startCoordinate,
                                           final Coordinate endCoordinate) {
        return pathIsUnobstructed(board, startCoordinate, endCoordinate) &&
                board.getPiece(endCoordinate).getName() == PieceName.EMPTY;
    }

    // Returns whether the attempted move is a valid pawn capture
    private static boolean isPawnCapture(final Coordinate startCoordinate, final Coordinate endCoordinate) {
        if (isWhiteTurn() && ((endCoordinate.getRow() - startCoordinate.getRow()) == 1) &&
                (Math.abs(endCoordinate.getColumn() - startCoordinate.getColumn()) == 1)) {
            return true;
        } else return !isWhiteTurn() && ((endCoordinate.getRow() - startCoordinate.getRow()) == -1) &&
                (Math.abs(endCoordinate.getColumn() - startCoordinate.getColumn()) == 1);
    }

    // Returns whether the attempted move is en passant
    private static boolean isEnPassant(final Board board, final Coordinate startCoordinate,
                                       final Coordinate endCoordinate) {
        // TODO: Consider not using GameEngine.isWhiteTurn
        int startRow = startCoordinate.getRow();
        int startColumn = startCoordinate.getColumn();
        int endColumn = endCoordinate.getColumn();

        if (isWhiteTurn() && startRow == 4 && Math.abs(endColumn - startColumn) == 1 &&
                board.getPiece(new Coordinate(startRow, endColumn)).getName() == PieceName.PAWN) {
            return true;
        } else return !isWhiteTurn() && startRow == 3 && Math.abs(endColumn - startColumn) == 1 &&
                board.getPiece(new Coordinate(startRow, endColumn)).getName() == PieceName.PAWN;
    }

    // Returns true if path between start coordinate and end coordinate is unobstructed
    private static boolean pathIsUnobstructed(final Board board, final Coordinate startCoordinate,
                                              final Coordinate endCoordinate) {
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

    private static boolean isOppositeColourPiece(final Piece piece, final Piece endPiece) {
        return endPiece.getName() != PieceName.EMPTY && piece.isWhite() != endPiece.isWhite();
    }

    /*
        A move status of VALID indicates that the attempted move is a legal move.
        A move status of INVALID indicates that the attempted move is not a legal move.
        A move status of EN_PASSANT indicates that the attempted move is an en_passant, which differs in functionality
        from other moves.
     */
    public enum MoveStatus {
        VALID,
        INVALID,
        EN_PASSANT
    }
}
