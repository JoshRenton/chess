package engine;

import board.Board;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pieces.King;
import pieces.Piece;
import pieces.Piece.PieceName;
import pieces.Queen;
import pieces.Rook;
import utility.Coordinate;
import utility.Move;
import utility.Square;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import static engine.MoveValidator.*;

public class GameEngine {
    private static Board board;
    // Keeps track of the previous board state so that moves can be undone
    private static Board previousBoardState;
    private static boolean isWhiteTurn = true;
    private static Coordinate startCoordinate;
    private static boolean pieceSelected = false;
    private static final Logger logger = LogManager.getLogger(GameEngine.class);

    // TODO: Promotion
    // TODO: Stalemate

    public static void main(String[] args) {
        board = new Board();
        board.setupClassic();

        BoardVisualiser.initialise(board);
        BoardVisualiser.showWindow();
    }

    public static boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    private static void playerAction(Coordinate endingCoordinates) {
        Move move = new Move(startCoordinate, endingCoordinates);

        MoveStatus status = isValid(board, move);

        previousBoardState = new Board(board);

        boolean moveSuccessful = false;

        // Check move is valid before doing move
        if (status != MoveStatus.INVALID) {
            moveSuccessful = attemptMove(move, status);

            // Check if player is in checkmate BEFORE changing turn
            if (isCheckmate()) {
                // TODO: Purely for testing
                System.exit(0);
            }

            // Swap player turn only if move was successful
            if (moveSuccessful) {
                isWhiteTurn = !isWhiteTurn;
            }
        }
    }

    // Performs a move if it does not result in the turn player being in check
    // Returns whether the move is completed or not
    private static boolean attemptMove(final Move move, final MoveStatus status) {
        ArrayList<Coordinate> updateCoordinates = doMove(board, move, status);
        // Check if turn player is in check after move
        if (getCheckStatus(true).isThreatened()) {
            // Undo move
            board = previousBoardState;
            return false;
        } else {
            visualiseMove(updateCoordinates);
            if (status == MoveStatus.PROMOTION) {
                promote(move.getEndCoordinate());
            }
            return true;
        }
    }

    // Handles promoting a pawn
    private static void promote(final Coordinate promotionCoordinate) {
        Piece promotingPawn = board.getPiece(promotionCoordinate);
        board.setPiece(new Queen(promotingPawn.getColour()), promotionCoordinate);
        visualiseMove(promotionCoordinate);
    }

    // Update the internal board state to reflect the input move
    private static ArrayList<Coordinate> doMove(Board board, final Move move, final MoveStatus status) {
        Piece movingPiece = board.getPiece(move.getStartCoordinate());
        ArrayList<Coordinate> updatedCoordinates = new ArrayList<>();

        // Check if move is en passant
        if (status == MoveStatus.EN_PASSANT) {
            // Remove the en passanted pawn
            int row = move.getStartRow();
            int column = move.getEndColumn();

            Coordinate removeCoordinate = new Coordinate(row, column);

            // TODO: The action of removing a piece and adding the coordinate should be bundled together in a method
            // TODO: Same for setting a piece
            board.removePiece(removeCoordinate);
            updatedCoordinates.add(removeCoordinate);
        } else if (status == MoveStatus.CASTLE) {
            // Move the castling rook
            int columnDiff = move.getEndColumn() - move.getStartColumn();
            Coordinate rookCoordinate;
            int columnModifier;
            if (columnDiff == 2) {
                rookCoordinate = new Coordinate(move.getStartRow(), board.getBoardSize() - 1);
                columnModifier = -1;
            } else {
                rookCoordinate = new Coordinate(move.getStartRow(), 0);
                columnModifier = 1;
            }
            Piece rook = board.getPiece(rookCoordinate);
            board.removePiece(rookCoordinate);
            updatedCoordinates.add(rookCoordinate);

            Coordinate updatedRookCoordinate = new Coordinate(move.getStartRow(),
                    move.getEndColumn() + columnModifier);
            board.setPiece(rook, updatedRookCoordinate);
            updatedCoordinates.add(updatedRookCoordinate);
        }

        // Update piece positions on board
        board.removePiece(move.getStartCoordinate());
        updatedCoordinates.add(move.getStartCoordinate());
        board.setPiece(movingPiece, move.getEndCoordinate());
        updatedCoordinates.add(move.getEndCoordinate());

        // Keep track of whether kings, rooks and pawns have moved
        movingPiece.setHasMoved();

        return updatedCoordinates;
    }

    // Update the GUI to show the result of a move
    private static void visualiseMove(ArrayList<Coordinate> updateCoordinates) {
        for (Coordinate coordinate: updateCoordinates) {
            BoardVisualiser.updateButtonIcon(board.getPiece(coordinate).getIcon(), coordinate);
        }
    }

    // Overloads method above for simple use of single coordinates
    private static void visualiseMove(Coordinate updateCoordinate) {
        ArrayList<Coordinate> updateCoordinates = new ArrayList<>();
        updateCoordinates.add(updateCoordinate);
        visualiseMove(updateCoordinates);
    }

    /*
        For a player to be in checkmate, the following conditions must all be met:

        1. The king must be in check
        2. The king has no legal moves - every square surrounding the king is either threatened or occupied by
           a piece of the same colour
        3. There is no piece that can capture the checking piece
        4. There is no piece that can move between the checking piece and the king to block the check
     */
    private static boolean isCheckmate() {
        // Get threat status on opposing king
        ThreatStatus opposingKingThreatStatus = getCheckStatus(false);

        // If it is a double check, only need to see if king has a legal move
        if (opposingKingThreatStatus.isDoubleCheck()) {
            return !opposingKingHasLegalMove();
        }

        // 1
        if (opposingKingThreatStatus.isThreatened()) {
            // 2
            if (!opposingKingHasLegalMove()) {
                // 3
                Coordinate checkingPiecePos = opposingKingThreatStatus.getThreateningCoordinates()[0];
                if (!isSquareThreatened(board, checkingPiecePos, !isWhiteTurn).isThreatened()) {
                    // 4 - Knights and pawns skip this step
                    // TODO: Code below is almost identical to MoveValidator.pathIsUnobstructed so consider merging
                    // TODO: Maybe don't call getOpposingKingPos over and over again
                    int rowDirection = checkingPiecePos.getRow() - getOpposingKingPos().getRow();
                    int columnDirection = checkingPiecePos.getColumn() - getOpposingKingPos().getColumn();

                    rowDirection = normalizeDirection(rowDirection);
                    columnDirection = normalizeDirection(columnDirection);

                    int row = getOpposingKingPos().getRow() + rowDirection;
                    int column = getOpposingKingPos().getColumn() + columnDirection;

                    while (row != checkingPiecePos.getRow() || column != checkingPiecePos.getColumn()) {
                        ThreatStatus threatStatus = isSquareThreatened(board, new Coordinate(row, column), !isWhiteTurn);
                        if (threatStatus.isThreatened() &&
                                !threatStatus.getThreateningCoordinates()[0].equals(getOpposingKingPos())) {
                            return false;
                        }
                        row += rowDirection;
                        column += columnDirection;
                    }

                    return true;
                }
            }
        }

        return false;
    }

    private static boolean opposingKingHasLegalMove() {
        int[][] steps = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        Coordinate opposingKingPos;

        opposingKingPos = getOpposingKingPos();

        for (int[] step: steps) {
            int possibleRow = opposingKingPos.getRow() + step[0];
            int possibleColumn = opposingKingPos.getColumn() + step[1];

            Coordinate possibleKingCoordinate = new Coordinate(possibleRow, possibleColumn);
            if (possibleKingCoordinate.isValid()) {
                Move possibleMove = new Move(opposingKingPos, possibleKingCoordinate);
                MoveStatus status = MoveValidator.isValid(board, possibleMove);
                if (status == MoveStatus.VALID) {
                    Board simBoard = simulateMove(possibleMove, status);
                    if (!isSquareThreatened(simBoard, possibleKingCoordinate, isWhiteTurn()).isThreatened()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // Simulates a move and returns the resulting board state without altering the original board
    private static Board simulateMove(final Move move, final MoveStatus moveStatus) {
        Board simBoard = new Board(board);
        doMove(simBoard, move, moveStatus);
        return simBoard;
    }

    // TODO: King can put itself in check by a pawn (BUG)
    protected static ThreatStatus getCheckStatus(boolean turnPlayer) {
        Coordinate kingPos;
        boolean whiteThreatening;

        whiteThreatening = isWhiteTurn() ^ turnPlayer;
        if (whiteThreatening) {
            kingPos = board.getBlackKingPos();
        } else {
            kingPos = board.getWhiteKingPos();
        }

        return isSquareThreatened(board, kingPos, whiteThreatening);
    }

    // Check if any pieces of the specified colour threaten the input coordinate
    protected static ThreatStatus isSquareThreatened(final Board board, final Coordinate coordinate,
                                                     final boolean whiteThreatening) {
        int[][] steps = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        int[][] knightSteps = {{1, 2}, {2, 1}, {-1, 2}, {-2, 1}, {1, -2}, {2, -1}, {-1, -2}, {-2, -1}};

        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();

        for (int[] stepDir: steps) {
            ThreatStatus status = isDirectionThreatened(board, coordinate, stepDir[0], stepDir[1], whiteThreatening);
            if (status.isThreatened()) {
                coordinates.addAll(Arrays.asList(status.getThreateningCoordinates()));
            }
        }

        // Check if any knights are attacking the specified square
        for (int[] knightStep: knightSteps) {
            int rowToCheck = coordinate.getRow() + knightStep[0];
            int columnToCheck = coordinate.getColumn() + knightStep[1];

            if (rowToCheck >= 0 && rowToCheck < board.getBoardSize() && columnToCheck >= 0 &&
                    columnToCheck < board.getBoardSize()) {
                Coordinate potentialKnightCoordinate = new Coordinate(rowToCheck, columnToCheck);
                Piece piece = board.getPiece(potentialKnightCoordinate);
                if (piece.getName() == PieceName.KNIGHT && piece.isWhite() == whiteThreatening) {
                    coordinates.add(potentialKnightCoordinate);
                }
            }
        }

        if (coordinates.size() != 0) {
            Coordinate[] threateningCoordinates = new Coordinate[]{};
            return new ThreatStatus(true, coordinates.toArray(threateningCoordinates));
        }

        logger.debug("Square {} threatened by coordinates: {}", coordinate, coordinates);

        return new ThreatStatus(false);
    }

    private static ThreatStatus isDirectionThreatened(final Board board, final Coordinate initialCoordinate, final int rowStep,
                                                 final int columnStep, final boolean whiteThreatening) {
        final int row = initialCoordinate.getRow();
        final int column = initialCoordinate.getColumn();

        int currentRow = row + rowStep;
        int currentColumn = column + columnStep;

        while (currentRow < board.getBoardSize() && currentRow >= 0 && currentColumn < board.getBoardSize() &&
                currentColumn >= 0) {
            Coordinate currentCoordinate = new Coordinate(currentRow, currentColumn);
            if (board.isOccupied(currentCoordinate)) {
                Piece piece = board.getPiece(currentCoordinate);
                if (piece.isWhite() == whiteThreatening) {
                    Move move = new Move(currentCoordinate, initialCoordinate);
                    if (MoveValidator.isValid(board, move) == MoveStatus.VALID) {
                        return new ThreatStatus(true, currentCoordinate);
                    }
                } else {
                    return new ThreatStatus(false);
                }
            }

            currentRow += rowStep;
            currentColumn += columnStep;
        }

        return new ThreatStatus(false);
    }

    public static int normalizeDirection(int direction) {
        if (direction < 0) {
            direction = -1;
        } else if (direction > 0){
            direction = 1;
        }

        return direction;
    }

    public static Coordinate getOpposingKingPos() {
        if (isWhiteTurn) {
            return board.getBlackKingPos();
        } else {
            return board.getWhiteKingPos();
        }
    }

    // Listens for interaction with a square
    private static class SquareListener implements ActionListener {
        private final Logger logger = LogManager.getLogger(this.getClass());
        @Override
        public void actionPerformed(final ActionEvent e) {
            Square square = (Square) e.getSource();
            Coordinate coordinate = square.getCoordinates();
            Piece currentSelectedPiece = board.getPiece(coordinate);
            if (pieceSelected) {
                Piece previousSelectedPiece = board.getPiece(startCoordinate);
                if (previousSelectedPiece.getColour() != currentSelectedPiece.getColour()) {
                    playerAction(coordinate);
                    pieceSelected = false;
                } else {
                    startCoordinate = coordinate;
                    logger.debug("Currently selected {} at {}",
                            board.getPiece(coordinate).getName(), coordinate);
                }

                // This prevents a click on a square with no piece from beginning a move, and a click on a piece that
                // is not of the turn player's colour
            } else if (currentSelectedPiece.isWhite() == isWhiteTurn() &&
                    currentSelectedPiece.getName() != PieceName.EMPTY) {
                startCoordinate = coordinate;
                pieceSelected = true;
                logger.debug("Currently selected {} at {}", board.getPiece(coordinate).getName(), coordinate);
            }
        }
    }

    // TODO: Maybe this should be returning a single instance instead of a new listener each time
    public static SquareListener getSquareListener() {
        return new SquareListener();
    }
}
