package board;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import pieces.Piece;

public class BoardTest {
    private Board board;

    // TODO: Rewrite this test to separate out the assertions
    @Test
    public void checkBoardIsSetupCorrectlyWhenInstantiated() {
        board = new Board();
        int boardSize = board.getBoardSize();

        char[] backRow = {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'};

        for (int row = 0; row < boardSize - 1; row++) {
            for (int column = 0; column < boardSize - 1; column++) {
                Piece piece = board.getPiece(row, column);
                if (row == 0 || row == boardSize - 1) {
                    assertThat(piece.asString()).isEqualTo(backRow[column]);
                }
                else if (row == 1 || row == boardSize - 2) {
                    assertThat(piece.asString()).isEqualTo('P');
                }
                else {
                    assertThat(piece.asString()).isEqualTo('.');
                }
            }
        }
    }
}
