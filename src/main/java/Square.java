import Pieces.Piece;
public class Square {
    private boolean isOccupied;
    private final Coordinates coordinates;
    private Piece piece;

    public Square(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }
}
