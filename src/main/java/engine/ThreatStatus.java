package engine;

import utility.Coordinate;

public class ThreatStatus {
    private final boolean isThreatened;
    private final Coordinate[] threateningCoordinates;

    public ThreatStatus(boolean isThreatened, Coordinate[] threateningCoordinates) {
        this.isThreatened = isThreatened;
        this.threateningCoordinates = threateningCoordinates;
    }

    public ThreatStatus(boolean isThreatened) {
        this.isThreatened = isThreatened;
        this.threateningCoordinates = new Coordinate[]{};
    }

    public ThreatStatus(boolean isThreatened, Coordinate threateningCoordinate) {
        this.isThreatened = isThreatened;
        this.threateningCoordinates = new Coordinate[]{threateningCoordinate};
    }

    public boolean isDoubleCheck() {
        return threateningCoordinates.length == 2;
    }

    public Coordinate[] getThreateningCoordinates() {
        return threateningCoordinates;
    }

    public boolean isThreatened() {
        return isThreatened;
    }
}
