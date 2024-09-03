package utility;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CoordinateTest {
    @Test
    public void isValidReturnsTrueGivenValidCoordinate() {
        Coordinate testCoordinate = new Coordinate(4, 5);
        assertThat(testCoordinate.isValid()).isTrue();
    }

    @Test
    public void isValidReturnsFalseGivenInvalidCoordinate() {
        Coordinate testCoordinate = new Coordinate(2, 9);
        assertThat(testCoordinate.isValid()).isFalse();
    }
}
