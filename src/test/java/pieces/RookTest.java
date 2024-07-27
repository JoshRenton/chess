package pieces;

import org.junit.jupiter.api.Test;
import utility.Coordinate;
import utility.Move;

import static org.assertj.core.api.Assertions.assertThat;
import static pieces.Piece.*;

public class RookTest {
    private Rook rook;

    @Test
    public void canMoveSucceedsGivenCorrectCoordinates() {
        rook = new Rook(Colour.WHITE);
        Coordinate startCoordinate = new Coordinate(0, 0);
        Coordinate endCoordinate = new Coordinate(0, 4);
        Move move = new Move(startCoordinate, endCoordinate);
        boolean canMove = rook.canMove(move);
        assertThat(canMove).isTrue();
    }
}
