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

    @Test
    public void equalsReturnsTrueGivenEqualCoordinate() {
        Coordinate c1 = new Coordinate(3, 6);
        Coordinate c2 = new Coordinate(3, 6);
        assertThat(c1.equals(c2)).isTrue();
    }

    @Test
    public void equalsReturnsFalseGivenInequalCoordinate() {
        Coordinate c1 = new Coordinate(3, 6);
        Coordinate c2 = new Coordinate(7, 6);
        assertThat(c1.equals(c2)).isFalse();
    }
}
