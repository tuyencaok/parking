import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ParkingLotManager {
    private final ParkingLot parkingLot;
    String[] vehicleTypes = {"MotorCycle", "Car", "Bus"};
    static int capacity, numofInGate, numofOutGate;
    static double hourlyRate;
    static int numofVeh = 0;

    static List<String> ticket = new ArrayList<>(); //arrayList to store log of vehicles
    static List<String> veh = new ArrayList<>();  //arrayList to store license plate
    static List<String> gate = new ArrayList<>();  //arrayList to store gate number
    List<Ticket> tickets = new ArrayList<>();

    public ParkingLotManager(int capacity, int numofInGate, int numofOutGate) {

        parkingLot = new ParkingLot(capacity, numofInGate, numofOutGate);

    }

    public static void main(String[] args) {

        String fileName = "";
        System.out.println("Please enter file name with extension");
        Scanner sc = new Scanner(System.in);
        if (sc != null) {
            try {
                fileName = sc.nextLine().trim();
                Path path = Paths.get(fileName);
                numofVeh = (int) ( Files.lines(path).count() - 2 );

                System.out.println("Number of lines: " + numofVeh);
            } catch (NumberFormatException | IOException e) {
                System.err.println("File not found! Please try again!");
                System.exit(1);
            }
        }
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                capacity = scanner.nextInt();
                numofInGate = scanner.nextInt();
                numofOutGate = scanner.nextInt();
                hourlyRate = scanner.nextDouble();
                System.out.println(capacity + "   " + numofInGate + "  " + numofOutGate + "  " + hourlyRate);
                //new ParkingLotManager(capacity,numofInGate,numofOutGate);
                scanner.nextLine();
                int tcount = 0;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    System.out.println(line);    //test input reading
                    String[] tokens = line.split(" ");
                    veh.add(tokens[0]);
                    gate.add(String.valueOf(tokens[1]));
                    ticket.add(String.valueOf(tcount));
                    tcount++;
                }

            }
            System.out.println(capacity + "   " + numofInGate + "  " + numofOutGate + "  " + hourlyRate);
            scanner.close();

        } catch (FileNotFoundException e) {
            //throw new DbException(e.getMessage(), e);
        }
        new ParkingLotManager(capacity, numofInGate, numofOutGate).logReader(numofVeh);
        //test read file for vehicle log
        for (int i = 0; i < numofVeh; i++) {
            System.out.println("Log " + i + " Vehicle: " + veh.get(i) + " inOut: " + gate.get(i) + "ticketNo " + ticket.get(i));
        }

    }


    private void logReader(int numberOfIterations) {


        for (int i = 0; i < numofVeh; i++) {

            if (Integer.valueOf(gate.get(i)) > numofInGate) {  //vehicle at exit gate

                parkingLot.unparkVehicle(findMyTicket(veh.get(i)));
            } else {
                try {
                    parkNewVehicle(veh.get(i), i);
                } catch (Exception e) {
                    System.out.println("Vehicle not parked: " + e.getMessage());
                }
            }
            System.out.println(parkingLot);
        }
    }

    private void parkNewVehicle(String plate, int i) {
        String vehicleType = vehicleTypes[new Random().nextInt(vehicleTypes.length)];
        int spacesNeeded = 1 + Arrays.asList(vehicleTypes).indexOf(vehicleType);
        double costFactor = 10 + ( 2 * spacesNeeded );
        tickets.add(parkingLot.parkVehicle(new Vehicle(plate, i, spacesNeeded, costFactor)));
    }

    public Ticket findMyTicket(String plate) {
        Ticket ticket=null;
        Vehicle vehicle;
        int i = 0;
        do {

            Vehicle temp = tickets.get(i).getVehicle();
            String plateLookup = new String(temp.getPlate());
            String tempPlate = plateLookup;
            if (plate.equals(tempPlate)) {
                 ticket = tickets.get(i);

            }

            i++;

        } while (i<tickets.size());
        return ticket;
    }
}