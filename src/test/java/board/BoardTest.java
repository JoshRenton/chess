package board;

import org.junit.jupiter.api.Test;
import pieces.Piece;
import pieces.Piece.PieceName;
import utility.Coordinate;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {
    private Board board;

    // TODO: Rewrite this test to separate out the assertions
    @Test
    public void checkBoardIsSetupCorrectlyWhenInstantiated() {
        board = new Board();
        int boardSize = board.getBoardSize();

        // TODO: This is ridiculous, make it so that repeat names aren't needed
        PieceName[] backRow = {PieceName.ROOK, PieceName.KNIGHT, PieceName.BISHOP, PieceName.QUEEN, PieceName.KING,
                PieceName.BISHOP, PieceName.KNIGHT, PieceName.ROOK};

        for (int row = 0; row < boardSize - 1; row++) {
            for (int column = 0; column < boardSize - 1; column++) {
                Piece piece = board.getPiece(new Coordinate(row, column));
                if (row == 0 || row == boardSize - 1) {
                    assertThat(piece.getName()).isEqualTo(backRow[column]);
                }
                else if (row == 1 || row == boardSize - 2) {
                    assertThat(piece.getName()).isEqualTo(PieceName.PAWN);
                }
                else {
                    assertThat(piece.getName()).isEqualTo(PieceName.EMPTY);
                }
            }
        }
    }
}
