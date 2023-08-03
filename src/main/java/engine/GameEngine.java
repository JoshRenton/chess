package engine;

import board.Board;
import pieces.Piece;
import utility.Move;

import java.util.Scanner;

public class GameEngine {
    private static final int BOARD_SIZE = 8;
    private static Board board;
    private static Scanner scanner;

    public GameEngine() {

    }

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        board = new Board();
        gameLoop();
    }

    private static void gameLoop() {
        boolean gameOver = false;
        while (!gameOver) {
            printBoard();
            String moveInput = scanner.nextLine();
            Move move = InputParser.parse(moveInput);
            if (move != null) {
                if (MoveValidator.isValid(board, move)) {
                    doMove(move);
                }
            }
        }
    }

    private static void doMove(Move move) {
        Piece movingPiece = board.getPiece(move.getStartRow(), move.getStartColumn());
        board.removePiece(move.getStartRow(), move.getStartColumn());
        board.setPiece(movingPiece, move.getEndRow(), move.getEndColumn());
    }

    private static void printBoard() {
        for (int row = BOARD_SIZE - 1; row >= 0; row--) {
            System.out.print(row + 1);
            System.out.print(" | ");
            for (int column = 0; column < BOARD_SIZE; column++) {
                Piece piece = board.getPiece(row, column);

                if (piece != null) {
                    System.out.print(piece.asChar());
                } else {
                    System.out.print('.');
                }

                if (column != BOARD_SIZE - 1) {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }

        System.out.print("    ");
        for (int column = 0; column < BOARD_SIZE; column++) {
            System.out.print('_');
            System.out.print(' ');
        }
        System.out.println();

        // Prints column letters along the bottom of the board.
        System.out.print("    ");
        for (char column = 'a'; column <= 'h'; column++) {
            System.out.print(column);
            System.out.print(' ');
        }

        System.out.println();
    }
}
