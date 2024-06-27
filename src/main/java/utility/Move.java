package utility;

public class Move {
    private final Coordinate startCoordinate;
    private final Coordinate endCoordinate;

    public Move(final Coordinate startCoordinate, final Coordinate endCoordinate) {
        this.startCoordinate = startCoordinate;
        this.endCoordinate = endCoordinate;
    }

    public Coordinate getStartCoordinate() {
        return startCoordinate;
    }

    public Coordinate getEndCoordinate() {
        return endCoordinate;
    }

    public int getStartRow() {
        return startCoordinate.getRow();
    }

    public int getStartColumn() {
        return startCoordinate.getColumn();
    }

    public int getEndRow() {
        return endCoordinate.getRow();
    }

    public int getEndColumn() {
        return endCoordinate.getColumn();
    }
}

