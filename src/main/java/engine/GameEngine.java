package engine;

import board.Board;
import pieces.Pawn;
import pieces.Piece;
import utility.Move;

import java.util.Scanner;

public class GameEngine {
    private static Board board;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        board = new Board();
        gameLoop();
    }

    private static void gameLoop() {
        boolean gameOver = false;
        boolean isWhiteTurn = true;
        while (!gameOver) {
            printBoard();
            String moveInput = scanner.nextLine();
            Move move = InputParser.parse(moveInput, isWhiteTurn);
            if (move != null) {
                if (MoveValidator.isValid(board, move)) {
                    doMove(move);
                    isWhiteTurn = !isWhiteTurn;
                }
            }
        }
    }

    private static void doMove(Move move) {
        Piece movingPiece = board.getPiece(move.getStartRow(), move.getStartColumn());
        board.removePiece(move.getStartRow(), move.getStartColumn());
        board.setPiece(movingPiece, move.getEndRow(), move.getEndColumn());
        if (movingPiece instanceof Pawn) {
            ((Pawn) movingPiece).setMoved();
        }
    }

    private static void printBoard() {
        int boardSize = board.getBoardSize();
        for (int row = boardSize - 1; row >= 0; row--) {
            System.out.print(row + 1);
            System.out.print(" | ");
            for (int column = 0; column < boardSize; column++) {
                Piece piece = board.getPiece(row, column);

                if (piece != null) {
                    System.out.print(piece.asChar());
                } else {
                    System.out.print('.');
                }

                if (column != boardSize - 1) {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }

        System.out.print("    ");
        for (int column = 0; column < boardSize; column++) {
            System.out.print('_');
            System.out.print(' ');
        }
        System.out.println();

        // Prints column letters along the bottom of the board
        System.out.print("    ");
        for (char column = 'a'; column <= 'h'; column++) {
            System.out.print(column);
            System.out.print(' ');
        }

        System.out.println();
    }
}
