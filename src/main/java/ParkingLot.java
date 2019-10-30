import java.util.*;

public class ParkingLot {
  /*  private final Collection<ParkingSpace> availableExpress = new HashSet<>();
    private final Collection<ParkingSpace> availableRegular = new HashSet<>();

    private final Collection<ParkingSpace> allExpressSpaces = new HashSet<>();
    private final Collection<ParkingSpace> allRegularSpaces = new HashSet<>();

    private final Map<Vehicle, ParkingSpace> usedExpress = new HashMap<>();
    private final Map<Vehicle, ParkingSpace> usedRegular = new HashMap<>(); */

    private final ArrayList<Spot> availableExpress = new ArrayList<>();
    private final ArrayList<Spot> availableRegular = new ArrayList<>();

    private final ArrayList<Spot> allExpressSpaces = new ArrayList<>();
    private final ArrayList<Spot> allRegularSpaces = new ArrayList<>();

    private final Map<Vehicle, Spot> usedExpress = new HashMap<>();
    private final Map<Vehicle, Spot> usedRegular = new HashMap<>();

    int expressCapacity; // spot type 1
    int regularCapacity; // spot type 0
    ParkingGroup parkingGroup; //1 or 2
    //int feeAlgorithm;
    //int numofInGate;
    //int numofOutGate;
    private double income = 0.0;
    private static final FeeCalculator CALCULATOR = new FeeCalculator();

    //ParkingLot Constructor
    private static ParkingLot pl = null;
    private Object Exception;

    //constructor
    public ParkingLot(int expressCapacity,int regularCapacity, ParkingGroup parkingGroup) {
        this.expressCapacity = expressCapacity;
        this.regularCapacity = regularCapacity;
        this.parkingGroup=parkingGroup;
        //this.numofInGate = numofInGate ;
        //this.numofOutGate = numofOutGate ;

        //create all parking spaces and initially assign all parking spaces as available
        //Random random = new Random();
        for (int i = 0; i < expressCapacity; i++) {
            allExpressSpaces.add(new Spot(i));
        }
        for (int i = 0; i < regularCapacity; i++) {
            allRegularSpaces.add(new Spot(i));
        }
        // all slots initially free
        availableExpress.addAll(allExpressSpaces);
        availableRegular.addAll(allRegularSpaces);

    }

    public ParkingGroup getParkingGroup() {
        return parkingGroup;
    }
    //create only one parking lot
    /*
    public static ParkingLot getParkingLot() {
        if (pl == null) {
            pl = new ParkingLot();
        }
        return pl;
    } */

    /*
    public Ticket parkVehicle(Vehicle vehicle, ParkingLot parkingLot) {
        if (vehicle.getSpacesNeeded()==1) {
        ParkingSpace targetSlot = availableExpress.stream().filter(p -> p.accepts(vehicle)).findFirst()
                .orElseThrow(() -> new RuntimeException("No free slot for " + vehicle));
            targetSlot.addVehicle(vehicle);
            targetSlot.addVehicle(vehicle);
            if (!targetSlot.isFree()) {
                availableExpress.remove(targetSlot);
            }
            usedExpress.put(vehicle, targetSlot);
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



        return new Ticket(vehicle,parkingLot);
    } */
    public Ticket parkVehicle(Vehicle vehicle, ParkingLot parkingLot) {

        if (vehicle.getSpacesNeeded()==1) { //express parking
            if (availableExpress.size() > 0) {
                Spot parkingSpot = availableExpress.get(0);
                availableExpress.remove(parkingSpot);
                usedExpress.put(vehicle, parkingSpot);
                parkingSpot.addVeh();
            }
            else {
                new RuntimeException("No free slot for " + vehicle);
            }
        }


        else { //regular parking
            if (availableRegular.size() > 0) {
                Spot parkingSpot = availableRegular.get(0);
                availableRegular.remove(parkingSpot);
                usedRegular.put(vehicle, parkingSpot);
                parkingSpot.addVeh();
            }
            else {
                new RuntimeException("No free slot for " + vehicle);
            }
        }
        return new Ticket(vehicle,parkingLot);
    }

    public void unparkVehicle(Ticket ticket) {

       /* ParkingSpace targetSlot = usedExpress.remove(ticket.getVehicle());
        targetSlot.removeVehicle(ticket.getVehicle());
        availableExpress.add(targetSlot); // set keeps uniqueness
        income += ticket.calculateCost(CALCULATOR); */

        Vehicle vehicle = ticket.getVehicle();
        if (vehicle.getSpacesNeeded()==1) {

            Spot releasedSpot = usedExpress.get(vehicle);
            releasedSpot.removeVeh();
            usedExpress.remove(vehicle);
            availableExpress.add(releasedSpot);

        }
        else { //regular parking
            Spot releasedSpot = usedRegular.get(vehicle);
            releasedSpot.removeVeh();
            usedRegular.remove(vehicle);
            availableRegular.add(releasedSpot);
        }
    }

   /* @Override
    public String toString() {
        return String.format("ParkingLot [income=%.2f, availableExpress=%d,availableRegular=%d, parkingVehicles=%d]", income, availableExpress.size(),
                usedExpress.size());
    }*/
}

class ParkingLotA extends ParkingLot {

    public ParkingLotA(int expressCapacity, int regularCapacity, ParkingGroup parkingGroup) {
        super(expressCapacity, regularCapacity, parkingGroup);

        expressCapacity = (int)Math.random()*3;
        regularCapacity = (int)Math.random()*7;
        parkingGroup = new ParkingGroup1();
    }
    public ParkingGroup getParkingGroup() {
        return parkingGroup;
    }
}

class ParkingLotB extends ParkingLot {

    public ParkingLotB(int expressCapacity, int regularCapacity, ParkingGroup parkingGroup) {
        super(expressCapacity, regularCapacity, parkingGroup);

        expressCapacity = (int)Math.random()*5;
        regularCapacity = (int)Math.random()*20;
        parkingGroup = new ParkingGroup2();
    }
    public ParkingGroup getParkingGroup() {
        return parkingGroup;
    }
}
