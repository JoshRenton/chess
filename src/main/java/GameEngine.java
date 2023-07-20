import board.Board;
import pieces.Pawn;
import pieces.Piece;

import java.util.Optional;

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
    }
}
