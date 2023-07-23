package engine;

import board.Board;
import pieces.Piece;

public class GameEngine {
    private static Board board;
    private static final int BOARD_SIZE = 8;

    public GameEngine() {

    }

    public static void main(String[] args) {
        board = new Board();
        printBoard();
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
    }
}
