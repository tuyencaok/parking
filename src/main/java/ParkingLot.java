import java.util.*;

public class ParkingLot {
  /*  private final Collection<ParkingSpace> availableExpress = new HashSet<>();
    private final Collection<ParkingSpace> availableRegular = new HashSet<>();

    private final Collection<ParkingSpace> allExpressSpaces = new HashSet<>();
    private final Collection<ParkingSpace> allRegularSpaces = new HashSet<>();

    private final Map<Vehicle, ParkingSpace> usedExpress = new HashMap<>();
    private final Map<Vehicle, ParkingSpace> usedRegular = new HashMap<>(); */

    private final List<Spot> availableExpress = new ArrayList<>();
    private final List<Spot> availableRegular = new ArrayList<>();

    private final List<Spot> allExpressSpaces = new ArrayList<>();
    private final List<Spot> allRegularSpaces = new ArrayList<>();

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
    private String lotName;
    //constructor
    public ParkingLot(String lotName ,int expressCapacity,int regularCapacity, ParkingGroup parkingGroup) {
        this.lotName = lotName;
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
    public String getLotName() {
        return lotName;
    }
    public int numofAvailableExpress(){
        return availableExpress.size();
    }
    public int numofAvailableRegular(){
        return availableRegular.size();
    }

    public Ticket parkVehicle(Vehicle vehicle, ParkingLot parkingLot) {

        if (vehicle.getSpacesNeeded()==1) { //express parking
            if (availableExpress.size() > 0) {
                Spot parkingSpot = availableExpress.get(0);
                usedExpress.put(vehicle, parkingSpot);
                parkingSpot.addVeh();
                availableExpress.remove(0);

            }
            else {
                new RuntimeException("No free slot for " + vehicle);
            }
        }


        else { //regular parking
            if (availableRegular.size() > 0) {
                Spot parkingSpot = availableRegular.get(0);
                usedRegular.put(vehicle, parkingSpot);
                parkingSpot.addVeh();
                availableRegular.remove(0);

            }
            else {
                new RuntimeException("No free slot for " + vehicle);
            }
        }
        return new Ticket(vehicle,parkingLot);
    }

    public void unparkVehicle(Ticket ticket) {
        Vehicle vehicle = ticket.getVehicle();
        if (vehicle.getSpacesNeeded()==1) {

            Spot releasedSpot = usedExpress.get(vehicle);
            releasedSpot.removeVeh();

            availableExpress.add(releasedSpot);
            usedExpress.remove(vehicle);

        }
        else { //regular parking
            Spot releasedSpot = usedRegular.get(vehicle);
            releasedSpot.removeVeh();

            availableRegular.add(releasedSpot);
            usedRegular.remove(vehicle);
        }
        income += ticket.calculateCost(CALCULATOR);
    }

   @Override
    public String toString() {
        return String.format("%s [income=%.2f, parkedExpress=%d, availableExpress=%d,parkedRegular=%d, availableRegular=%d]",getLotName(), income,usedExpress.size(),availableExpress.size(),usedRegular.size(),
                availableRegular.size());
    }
}
/*
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
} */
