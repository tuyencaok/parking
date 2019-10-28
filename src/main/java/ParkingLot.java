import java.util.*;

public abstract class ParkingLot {
    private final Collection<ParkingSpace> availableExpress = new HashSet<>();
    private final Collection<ParkingSpace> availableRegular = new HashSet<>();

    private final Collection<ParkingSpace> allExpressSpaces = new HashSet<>();
    private final Collection<ParkingSpace> allRegularSpaces = new HashSet<>();

    private final Map<Vehicle, ParkingSpace> usedExpress = new HashMap<>();
    private final Map<Vehicle, ParkingSpace> usedRegular = new HashMap<>();

    int expressCapacity; // spot type 1
    int regularCapacity; // spot type 0
    int parkingGroup; //1,2 or 3
    int feeAlgorithm;
    //int numofInGate;
    //int numofOutGate;
    private double income = 0.0;
    private static final FeeCalculator CALCULATOR = new FeeCalculator();

    //ParkingLot Constructor
    private static ParkingLot pl = null;
    //constructor
    public ParkingLot(int expressCapacity,int regularCapacity, int parkingGroup) {
        this.expressCapacity = expressCapacity;
        this.regularCapacity = regularCapacity;
        //this.numofInGate = numofInGate ;
        //this.numofOutGate = numofOutGate ;

        //create all parking spaces and initially assign all parking spaces as available
        Random random = new Random();
        for (int i = 0; i < expressCapacity; i++) {
            allExpressSpaces.add(new ParkingSpace(1 + random.nextInt(3)));
        }
        for (int i = 0; i < regularCapacity; i++) {
            allRegularSpaces.add(new ParkingSpace(1 + random.nextInt(3)));
        }
        // all slots initially free
        availableExpress.addAll(allExpressSpaces);
        availableRegular.addAll(allRegularSpaces);

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
        if (vehicle.getSpacesNeeded()==1) {
        ParkingSpace targetSlot = availableExpress.stream().filter(p -> p.accepts(vehicle)).findFirst()
                .orElseThrow(() -> new RuntimeException("No free slot for " + vehicle));
            targetSlot.addVehicle(vehicle);
            targetSlot.addVehicle(vehicle);
            if (!targetSlot.isFree()) {
                availableExpress.remove(targetSlot);
            }
            usedExpress.put(vehicle, targetSlot)
        }
        else {
            ParkingSpace targetSlot = availableRegular.stream().filter(p -> p.accepts(vehicle)).findFirst()
                    .orElseThrow(() -> new RuntimeException("No free slot for " + vehicle));

            targetSlot.addVehicle(vehicle);
            if (!targetSlot.isFree()) {
                availableRegular.remove(targetSlot);
            }
            usedRegular.put(vehicle, targetSlot);
        }



        return new Ticket(vehicle);
    }

    public void unparkVehicle(Ticket ticket) {

        ParkingSpace targetSlot = usedExpress.remove(ticket.getVehicle());
        targetSlot.removeVehicle(ticket.getVehicle());
        availableExpress.add(targetSlot); // set keeps uniqueness
        income += ticket.calculateCost(CALCULATOR);
    }

    @Override
    public String toString() {
        return String.format("ParkingLot [income=%.2f, availableExpress=%d,availableRegular=%d, parkingVehicles=%d]", income, availableExpress.size(),
                usedExpress.size());
    }
}

class ParkingLot1 extends ParkingLot {

    public ParkingLot1(int expressCapacity,int regularCapacity, int parkingGroup) {
        super(expressCapacity, regularCapacity, parkingGroup);
    }
}

class ParkingLot2 extends ParkingLot {

    public ParkingLot2(int expressCapacity,int regularCapacity, int parkingGroup) {
        super(expressCapacity, regularCapacity, parkingGroup);
    }
}

class ParkingLot3 extends ParkingLot {

    public ParkingLot3(int expressCapacity,int regularCapacity, int parkingGroup) {
        super(expressCapacity, regularCapacity, parkingGroup);
    }
}
