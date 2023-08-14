import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pieces.Bishop;
import utility.Move;

public class BishopTest {
    private Bishop bishop;
    @Test
    public void canMoveSucceedsGivenCorrectCoordinates() {
        bishop = new Bishop(true);
        Move move = new Move(true, 2, 2, 1, 3);
        boolean canMove = bishop.canMove(move);
        assertThat(canMove).isTrue();
    }

    @Test
    public void canMoveFailsGivenIncorrectCoordinates() {
        bishop = new Bishop(true);
        Move move = new Move(true, 2, 2, 5, 3);
        boolean canMove = bishop.canMove(move);
        assertThat(canMove).isFalse();
    }
}
