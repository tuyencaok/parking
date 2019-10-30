/*public enum VehicleType {
    MOTORBIKE, CAR, BUS
}*/


import java.util.HashMap;
import java.util.Map;

public class Vehicle {
    private final int id;
    private final int spaceNeeded;
   // private final ParkingLot parkingLot;
    private String licensePlate;
    public Vehicle(String licensePlate, int id, int spaceNeeded) {
        super();
        this.licensePlate = licensePlate;
        this.id = id;
        this.spaceNeeded = spaceNeeded; //spotType 1=express  0=regular
        //this.parkingLot = parkingLot;
    }
    public int getSpacesNeeded() {
        return spaceNeeded;
    }//type of parking Spot
    //public ParkingLot getParkingLot() {
       // return  parkingLot;
    //}
    public String getPlate() {
        String plate = new String(licensePlate);
        return plate;
    }
    public String inquirePrice() {
        return this.licensePlate;
    }
    public ParkingLot acceptParkingLot(HashMap<ParkingLot, Double> quote) {

        ParkingLot[] lotList = new ParkingLot[quote.size()];
        Object[] priceList = new Object[quote.size()];

        int index =0;
        double lowestPrice = (double) quote.values().toArray()[0];
        ParkingLot parkingLot = (ParkingLot)quote.keySet().toArray()[0];
        for (Map.Entry<ParkingLot,Double>quoteEntry :quote.entrySet()){
            lotList[index] = quoteEntry.getKey();
            priceList[index]= quoteEntry.getValue();

                if ((double) priceList[index]<lowestPrice) {
                    lowestPrice = (double) priceList[index];
                    parkingLot = lotList[index]; }
        }
        return parkingLot;
    }

    @Override
    public String toString() {
        return String.format("Vehicle [licensePlate=%s, id=%d, is looking for parking Space type=%d]", licensePlate, id,
                spaceNeeded);
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