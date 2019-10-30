import java.util.ArrayList;
import java.util.List;

public class ParkingSpace {
    interface CapacityCalculator {
        boolean hasAvailableSpace();
    }

    private static final int FREE = 0;
    private static final int FULL = 2;
    private static int idCounter = 0;
    private final int id = ++idCounter;
    private final int spaceType;   //1=express  0=regular
    private final List<Vehicle> parkingVehicles = new ArrayList<>();

    //ParkingSpace constructor

    public ParkingSpace(int spaceType) {

        this.spaceType = spaceType;
    }

    public boolean accepts(Vehicle vehicle) {
        return isSpaceAvail(() -> (spaceType >= parkingVehicles.get(0).getSpacesNeeded() + vehicle.getSpacesNeeded()));
    }

    public void addVehicle(Vehicle vehicle) {
        parkingVehicles.add(vehicle);
        System.out.println(String.format("%s parked on slot %d", vehicle, id));
    }
    public void removeVehicle(Vehicle vehicle) {
        parkingVehicles.remove(vehicle);
        System.out.println(String.format("%s removed from slot %d", vehicle, id));
    }

    public boolean isFree() {
        return isSpaceAvail(() -> (spaceType > parkingVehicles.get(0).getSpacesNeeded()));
    }

    private boolean isSpaceAvail(CapacityCalculator b) {
        switch (parkingVehicles.size()) {
            case FREE:
                return true;
            case FULL:
                return false;
            default:
                return b.hasAvailableSpace();
        }
    }
    /*
    public Ticket findMyTicket(String plate) {
        Vehicle vehicle=null;
        for (int i = 0; i < parkingVehicles.size(); i++) {
            if (plate == parkingVehicles.get(i).getPlate())
                vehicle = parkingVehicles.get(i);
        }

        return new Ticket(vehicle);
    } */
    @Override
    public String toString() {
        return "Slot [id: " + id + ", availableSpots=" + spaceType + ", numofParkingVehicles=" + parkingVehicles + "]";
    }
}

