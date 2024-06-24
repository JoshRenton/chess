package pieces;

import org.junit.jupiter.api.Test;
import utility.Coordinate;
import utility.Move;

import static org.assertj.core.api.Assertions.assertThat;

public class KnightTest {
    private Knight knight;
    @Test
    public void canMoveSucceedsGivenCorrectCoordinates() {
        knight = new Knight(true);
        Coordinate startCoordinate = new Coordinate(1, 2);
        Coordinate endCoordinate = new Coordinate(3, 3);
        Move move = new Move(startCoordinate, endCoordinate);
        boolean canMove = knight.canMove(move);
        assertThat(canMove).isTrue();
    }
}
