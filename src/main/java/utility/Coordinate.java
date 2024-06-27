package utility;

public class Coordinate {
    private final int[] coordinate;

    public Coordinate(int row, int column) {
        coordinate = new int[]{row, column};
    }

    public int getRow() {
        return coordinate[0];
    }

    public int getColumn() {
        return coordinate[1];
    }
}
