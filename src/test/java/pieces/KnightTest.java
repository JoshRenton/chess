package pieces;

import org.junit.jupiter.api.Test;
import utility.Move;

import static org.assertj.core.api.Assertions.assertThat;

public class KnightTest {
    private Knight knight;
    @Test
    public void canMoveSucceedsGivenCorrectCoordinates() {
        knight = new Knight(true);
        Move move = new Move(true, 1, 2, 3, 3);
        boolean canMove = knight.canMove(move);
        assertThat(canMove).isTrue();
    }
}
