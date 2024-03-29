public class Vehicle {
    private final int id;
    private final int spaceNeeded;
    private final double hourlyRate;
    private String licensePlate;
    public Vehicle(String licensePlate, int id, int spaceNeeded, double hourlyRate) {
        super();
        this.licensePlate = licensePlate;
        this.id = id;
        this.spaceNeeded = spaceNeeded;
        this.hourlyRate = hourlyRate;
    }
    public int getSpacesNeeded() {
        return spaceNeeded;
    }
    public double getCostFactor() {
        return hourlyRate;
    }
    public String getPlate() {
        return licensePlate;
    }

    @Override
    public String toString() {
        return String.format("Vehicle [licensePlate=%s, id=%d, spacesNeeded=%d, costFactor=%.2f]", licensePlate, id,
                spaceNeeded, hourlyRate);
    }
}