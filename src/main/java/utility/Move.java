package utility;

public class Move {
    private final Coordinate startCoordinates;
    private final Coordinate endCoordinates;

    public Move(final Coordinate startCoordinates, final Coordinate endCoordinates) {
        this.startCoordinates = startCoordinates;
        this.endCoordinates = endCoordinates;
    }

    public Coordinate getStartCoordinates() {
        return startCoordinates;
    }

    public Coordinate getEndCoordinates() {
        return endCoordinates;
    }

    public int getStartRow() {
        return startCoordinates.getRow();
    }

    public int getStartColumn() {
        return startCoordinates.getColumn();
    }

    public int getEndRow() {
        return endCoordinates.getRow();
    }

    public int getEndColumn() {
        return endCoordinates.getColumn();
    }
}

