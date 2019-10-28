/*public enum VehicleType {
    MOTORBIKE, CAR, BUS
}*/


public class Vehicle {
    private final int id;
    private final int spaceNeeded;
    private final double hourlyRate;
    private String licensePlate;
    public Vehicle(String licensePlate, int id, int spaceNeeded, double hourlyRate) {
        super();
        this.licensePlate = licensePlate;
        this.id = id;
        this.spaceNeeded = spaceNeeded; //spotType 1=express  0=regular
        this.hourlyRate = hourlyRate;
    }
    public int getSpacesNeeded() {
        return spaceNeeded;
    }
    public double getCostFactor() {
        return hourlyRate;
    }
    public String getPlate() {
        String plate = new String(licensePlate);
        return plate;
    }

    @Override
    public String toString() {
        return String.format("Vehicle [licensePlate=%s, id=%d, spacesNeeded=%d, costFactor=%.2f]", licensePlate, id,
                spaceNeeded, hourlyRate);
    }
}
/*
public class Car extends Vehicle {
    public Car() {
        super(VehicleType.MOTORBIKE);
    }
}

public class Van extends Vehicle {
    public Van() {
        super(VehicleType.CAR);
    }
}

public class Truck extends Vehicle {
    public Truck() {
        super(VehicleType.BUS);
    }
} */