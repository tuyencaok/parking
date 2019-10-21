import java.util.*;

public class ParkingLot {
    private final Collection<ParkingSpace> availableSpaces = new HashSet<>();
    private final Collection<ParkingSpace> allParkingSpaces = new HashSet<>();
    private final Map<Vehicle, ParkingSpace> usedSpaces = new HashMap<>();
    int capacity;
    int numofInGate;
    int numofOutGate;
    private double income = 0.0;
    private static final FeeCalculator CALCULATOR = new FeeCalculator();

    //ParkingLot Constructor
    private static ParkingLot pl = null;
    //constructor
    public ParkingLot(int capacity, int numofInGate, int numofOutGate) {
        this.capacity = capacity;
         this.numofInGate = numofInGate ;
        this.numofOutGate = numofOutGate ;

        //create all parking spaces and initially assign all parking spaces as available
        Random random = new Random();
        for (int i = 0; i < capacity; i++) {
        allParkingSpaces.add(new ParkingSpace(1 + random.nextInt(3)));
        }
        // all slots initially free
        availableSpaces.addAll(allParkingSpaces);


    }
    //create only one parking lot
    /*
    public static ParkingLot getParkingLot() {
        if (pl == null) {
            pl = new ParkingLot();
        }
        return pl;
    } */

    public Ticket parkVehicle(Vehicle vehicle) {
        ParkingSpace targetSlot = availableSpaces.stream().filter(p -> p.accepts(vehicle)).findFirst()
                .orElseThrow(() -> new RuntimeException("No free slot for " + vehicle));
        targetSlot.addVehicle(vehicle);
        if (!targetSlot.isFree()) {
            availableSpaces.remove(targetSlot);
        }
        usedSpaces.put(vehicle, targetSlot);
        return new Ticket(vehicle);
    }

    public void unparkVehicle(Ticket ticket) {
        ParkingSpace targetSlot = usedSpaces.remove(ticket.getVehicle());
        targetSlot.removeVehicle(ticket.getVehicle());
        availableSpaces.add(targetSlot); // set keeps uniqueness
        income += ticket.calculateCost(CALCULATOR);
    }

    @Override
    public String toString() {
        return String.format("ParkingLot [income=%.2f, availableSpaces=%d, parkingVehicles=%d]", income, availableSpaces.size(),
                usedSpaces.size());
    }
}
