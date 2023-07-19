import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pieces.Bishop;

public class BishopTest {
    private Bishop bishop;
    @Test
    public void canMoveSucceedsGivenCorrectCoordinates() {
        bishop = new Bishop(true);
        boolean canMove = bishop.canMove(0,0,4, 4);
        assertThat(canMove).isTrue();
    }

    @Test
    public void canMoveFailsGivenIncorrectCoordinates() {
        bishop = new Bishop(true);
        boolean canMove = bishop.canMove(2,2,5, 3);
        assertThat(canMove).isFalse();
    }
}
