import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ParkingLotManager {
    private static ParkingLot parkingLotB;
    private static ParkingLot parkingLotA;
   // private final ParkingLot parkingLotA, parkingLotB; //parkingLotC;
    //String[] vehicleTypes = {"MotoBike", "Car", "Bus"};
    static int capacity, expressCapacity,regularCapacity, numofInGate, numofOutGate;
    static double hourlyRate;
    static int numofVeh = 0;
    static ParkingGroup1 group1;
    static ParkingGroup2 group2;
    //static ParkingGroup1 group1;

    static List<String> log = new ArrayList<>(); //arrayList to store log id # of vehicles
    static List<String> veh = new ArrayList<>();  //arrayList to store license plate
    static List<String> gate = new ArrayList<>();  //arrayList to store gate number
    static List<String> spaceNeeded = new ArrayList<>(); //arrayList to store log of vehicles

    ParkingGroup1 pGroup1 = new ParkingGroup1();
    ParkingGroup2 pGroup2 = new ParkingGroup2();

    List<Ticket> tickets = new ArrayList<>();

    public ParkingLotManager() {

        ParkingLotA parkingLotA = new ParkingLotA((int)Math.random()*3,(int)Math.random()*7, group1);
        ParkingLotB parkingLotB = new ParkingLotB((int)Math.random()*5,(int)Math.random()*20, group2);

       // parkingLotC = new ParkingLotC((int)Math.random()*5,(int)Math.random()*10, group1);

    }

    public static void main(String[] args) {

        String fileName = "";
        //System.out.println("Please enter file name with extension");
        //Scanner sc = new Scanner(System.in);
        if (args[0] != null) {
            try {
                fileName = args[0];
                Path path = Paths.get(fileName);
                numofVeh = (int) ( Files.lines(path).count() );

                System.out.println("Number of logs: " + numofVeh);
            } catch (NumberFormatException | IOException e) {
                System.err.println("File not found! Please try again!");
                System.exit(1);
            }
        }
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
               /* capacity = scanner.nextInt();
                numofInGate = scanner.nextInt();
                numofOutGate = scanner.nextInt();
                hourlyRate = scanner.nextDouble();
                System.out.println(capacity + "   " + numofInGate + "  " + numofOutGate + "  " + hourlyRate);
                //new ParkingLotManager(capacity,numofInGate,numofOutGate);
                scanner.nextLine(); */
                int tcount = 0;
                //while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    System.out.println(line);    //test input reading
                    String[] tokens = line.split(" ");
                    veh.add(tokens[0]);
                    gate.add(String.valueOf(tokens[1]));
                    spaceNeeded.add(String.valueOf(tokens[2]));
                    log.add(String.valueOf(tcount));
                    tcount++;
                //}

            }
            //System.out.println(capacity + "   " + numofInGate + "  " + numofOutGate + "  " + hourlyRate);
            //scanner.close();

        } catch (FileNotFoundException e) {
            //throw new DbException(e.getMessage(), e);
        }


        new ParkingLotManager().logReader(numofVeh);
        //test read file for vehicle log
        for (int i = 0; i < numofVeh; i++) {
            System.out.println("Log " + i + " Vehicle: " + veh.get(i) + " inOut: " + gate.get(i) + "     ticketNo " + log.get(i) );
        }

    }


    private void logReader(int numberOfIterations) {


        for (int i = 0; i < numofVeh; i++) {

            if (Integer.parseInt(gate.get(i)) ==2) {  //vehicle at exit, gate=2

                findMyParkingLot(veh.get(i)).unparkVehicle(findMyTicket(veh.get(i))); //find vehicle ticket and lot, then unpark
            } else {
                try {
                    Vehicle newVehicle = new Vehicle(veh.get(i),i, Integer.valueOf(spaceNeeded.get(i)));
                    String plate = newVehicle.inquirePrice();
                    HashMap<ParkingLot, Double> quote = new HashMap<ParkingLot, Double>() ;
                    quote.put(parkingLotA, (parkingLotA.getParkingGroup()).replyFee(plate));
                    quote.put(parkingLotB, (parkingLotB.getParkingGroup()).replyFee(plate));
                   // quote.put(parkingLotC, (parkingLotC.getParkingGroup()).replyFee(plate));
                    ParkingLot acceptLot = newVehicle.acceptParkingLot(quote);
                    tickets.add(acceptLot.parkVehicle(newVehicle,acceptLot));
                    System.out.println();

                    //parkNewVehicle(veh.get(i), i);
                } catch (Exception e) {
                    System.out.println("Vehicle not parked: " + e.getMessage());
                }
            }
            System.out.println();
        }
    }

   /* private void parkNewVehicle(Vehicle vehicle, ParkingLot parkingLot) {
        //String vehicleType = vehicleTypes[new Random().nextInt(vehicleTypes.length)];
        //int spacesNeeded = 1 + Arrays.asList(vehicleTypes).indexOf(vehicleType);
        //double costFactor = 10 + ( 2 * spacesNeeded );
        tickets.add(parkingLot.parkVehicle(vehicle,parkingLot));
    } */


    public ParkingLot findMyParkingLot(String plate) {
        Ticket ticket = findMyTicket(plate);
        return ticket.getParkingLot();
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