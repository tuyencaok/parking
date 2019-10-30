public class Ticket {
    private final long startTime = System.currentTimeMillis();
    private final Vehicle vehicle;
    private final ParkingLot parkingLot;

    public Ticket(Vehicle vehicle, ParkingLot parkingLot) {
        super();
        this.vehicle = vehicle;
        this.parkingLot=parkingLot;
    }
    public long calculateParkingDuration(){
        return System.currentTimeMillis() - startTime;
    }


    public double calculateCost(FeeCalculator calculator){
        return calculator.getFee(calculateParkingDuration(), (parkingLot.getParkingGroup()).getHourlyRate(),(parkingLot.getParkingGroup()).getDiscount(vehicle.getPlate()));
    }

    public Vehicle getVehicle(){
        return vehicle;
    }
    public ParkingLot getParkingLot(){
        return parkingLot;
    }

}
