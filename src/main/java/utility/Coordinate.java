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

    // Check if this coordinate is within the game board
    public boolean isValid() {
        return coordinate[0] < 8 && coordinate[0] >= 0 && coordinate[1] < 8 && coordinate[1] >= 0;
    }

    @Override
    public String toString() {
        return String.format("[%d, %d]", this.getRow(), this.getColumn());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj.getClass() != this.getClass()) {
            return false;
        } else {
            Coordinate otherCoordinate = (Coordinate) obj;
            return this.getRow() == otherCoordinate.getRow() && this.getColumn() == otherCoordinate.getColumn();
        }
    }
}
