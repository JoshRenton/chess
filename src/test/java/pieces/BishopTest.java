package pieces;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import utility.Coordinate;
import utility.Move;

public class BishopTest {
    private Bishop bishop;
    @Test
    public void canMoveSucceedsGivenCorrectCoordinates() {
        bishop = new Bishop(true);
        Coordinate startCoordinate = new Coordinate(2, 2);
        Coordinate endCoordinate = new Coordinate(1, 3);
        Move move = new Move(startCoordinate, endCoordinate);
        boolean canMove = bishop.canMove(move);
        assertThat(canMove).isTrue();
    }

    @Test
    // TODO: Currently only tests a single incorrect coordinate
    public void canMoveFailsGivenIncorrectCoordinates() {
        bishop = new Bishop(true);
        Coordinate startCoordinate = new Coordinate(2, 2);
        Coordinate endCoordinate = new Coordinate(5, 3);
        Move move = new Move(startCoordinate, endCoordinate);
        boolean canMove = bishop.canMove(move);
        assertThat(canMove).isFalse();
    }
}
