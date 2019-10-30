public  class Spot {
    private boolean isOccupied;
    private int spotNumber;

    Spot(int spotNumber) {
        isOccupied = false;
        this.spotNumber = spotNumber;
    }

    boolean isOccupied() {
        return isOccupied;
    }

    int getSlotNumber() {
        return spotNumber;
    }

    void addVeh() {
        isOccupied = true;
    }

    void removeVeh() {
        isOccupied = false;
    }

    @Override
    public boolean equals(Object o) {
        return (((Spot) o).spotNumber == this.spotNumber);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.spotNumber;
        return hash;
    }

}
/*
class MotoSpot extends Spot {

    MotoSpot(int spotNumber){
        super(spotNumber);
    }
}
class CarSpot extends Spot {

    CarSpot(int spotNumber){
        super(spotNumber);
    }
}
class BusSpot extends Spot {

    BusSpot(int spotNumber){
        super(spotNumber);
    }
}*/