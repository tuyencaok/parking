public class Ticket {
    private final long startTime = System.currentTimeMillis();
    private final Vehicle vehicle;

    public Ticket(Vehicle vehicle) {
        super();
        this.vehicle = vehicle;
    }
    public long calculateParkingDuration(){
        return System.currentTimeMillis() - startTime;
    }
    public double calculateCost(FeeCalculator calculator){
        return calculator.getFee(calculateParkingDuration(), vehicle.getCostFactor());
    }

    public Vehicle getVehicle(){
        return vehicle;
    }

}
