import board.Board;
import pieces.Pawn;

public class GameEngine {
    private Board board;
    private int BOARD_SIZE = 8;

    public GameEngine() {

    }

    public static void main(String[] args) {
        System.out.println("Successfully run GameEngine.");
    }

    private void setupBoard() {
        board = new Board();
        setupPawns();
    }

    private void printBoard() {
        for (int row = BOARD_SIZE - 1; row >= 0; row--) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                System.out.println(board.getPiece(row, column).asChar());
            }
            System.out.println();
        }
    }

    private void setupPawns() {
        int whiteRow = 1;
        int blackRow = 6;

        for (int column = 0; column < BOARD_SIZE; column++) {
            board.setPiece(new Pawn(true), whiteRow, column);
            board.setPiece(new Pawn(false), blackRow, column);
        }
    }
}
