/*
  Tuyen Cao 1836324
  ***To run the program,
        Please run from an IDE such as IntelliJ, Eclipse
        choose java target byte code version 8
        pass filename as an argument when compiling file, eg. test1.txt
  ***Assumption about test1.txt
        Each vehicle log is on one line, includes:
          + Vehicle license plate (3 letters)
          + Is at entrance gate (code 1), or exit gate (code 2)
          + Is requesting Express parking spot (code 1), or Regular parking spot (code 2)
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ParkingLotManager {
    private final ParkingLot parkingLotA;
    private final ParkingLot parkingLotB;
    static int capacity, expressCapacity,regularCapacity, numofInGate, numofOutGate;
    static double hourlyRate;
    static int numofVeh = 0;
    public ParkingGroup1 group1 = new ParkingGroup1();
    public ParkingGroup2 group2 = new ParkingGroup2();
    //static ParkingGroup1 group1;

    static List<String> log = new ArrayList<>(); //arrayList to store log id # of vehicles
    static List<String> veh = new ArrayList<>();  //arrayList to store license plate
    static List<String> gate = new ArrayList<>();  //arrayList to store gate number
    static List<String> spaceNeeded = new ArrayList<>(); //arrayList to store log of vehicles

    ParkingGroup1 pGroup1 = new ParkingGroup1();
    ParkingGroup2 pGroup2 = new ParkingGroup2();

    List<Ticket> tickets = new ArrayList<>();

    public ParkingLotManager() {

        parkingLotA = new ParkingLot("Parking Lot A",3,7, group1);
        parkingLotB = new ParkingLot("Parking Lot B",5,20, group2);
    }

    public static void main(String[] args) throws InterruptedException {

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
            int tcount = 0;
            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();
                if (line != "") {
                    System.out.println(line);    //test input reading
                    String[] tokens = line.split(" ");
                    veh.add(tokens[0]);
                    gate.add(String.valueOf(tokens[1]));
                    spaceNeeded.add(String.valueOf(tokens[2]));
                    log.add(String.valueOf(tcount));
                    tcount++;

                }
            }

        } catch (FileNotFoundException e) {

        }


        new ParkingLotManager().logReader(numofVeh);

        /*test read file for vehicle log
        for (int i = 0; i < numofVeh; i++) {
            System.out.println("Log " + i + " Vehicle: " + veh.get(i) + " inOut: " + gate.get(i) + "     ticketNo " + log.get(i) );
        } */

    }


    private void logReader(int numberOfIterations) throws InterruptedException {

        for (int i = 0; i < numofVeh; i++) {

            if (Integer.parseInt(gate.get(i)) ==2) {  //vehicle at exit, gate=2
                Ticket ticket = findMyTicket(veh.get(i));
                ParkingLot parkingLot = findMyParkingLot(veh.get(i));
                parkingLot.unparkVehicle(ticket);
                System.out.println("Vehicle " +veh.get(i)+ " exited " + parkingLot.getLotName());
                System.out.println(parkingLot);
                tickets.remove(ticket);
            } else {
                try {
                    Vehicle newVehicle = new Vehicle(veh.get(i),i, Integer.valueOf(spaceNeeded.get(i)));
                    String plate = newVehicle.inquirePrice();
                    HashMap<ParkingLot, Double> quote = new HashMap<ParkingLot, Double>();

                    //getPrice from parkingLot that have available spots only
                    if (newVehicle.getSpacesNeeded()==1) { //check for avail express spots, then add to quote list

                        if (parkingLotA.numofAvailableExpress() > 0) {
                            double feeA = (parkingLotA.getParkingGroup()).replyFee(plate);
                            if (feeA != -1)
                                quote.put(parkingLotA, feeA);
                        }
                        if (parkingLotB.numofAvailableExpress() > 0) {
                            double feeB = (parkingLotB.getParkingGroup()).replyFee(plate);
                            if (feeB != -1)
                                quote.put(parkingLotB, feeB);
                        }
                    }
                    else { ////check for avail regular spots, then add to quote list
                        if (parkingLotA.numofAvailableRegular() > 0) {
                            double feeA = (parkingLotA.getParkingGroup()).replyFee(plate);
                            if (feeA != -1)
                                quote.put(parkingLotA, feeA);
                        }
                        if (parkingLotB.numofAvailableRegular() > 0) {
                            double feeB = (parkingLotB.getParkingGroup()).replyFee(plate);
                            if (feeB != -1)
                                quote.put(parkingLotB, feeB);
                        }
                    }
                    if (quote.size()>0) { //proceed let car choose a parkingLot that has available spots
                            ParkingLot acceptLot = newVehicle.acceptParkingLot(quote);
                           //acceptLot.parkVehicle(newVehicle, acceptLot);
                            Ticket ticket = acceptLot.parkVehicle(newVehicle, acceptLot);
                            tickets.add(ticket);
                            System.out.println("Vehicle " + newVehicle.getPlate() + " parked at " + acceptLot.getLotName()+" on a " + spotTypeConvertion(spaceNeeded.get(i)));
                            System.out.println(acceptLot);

                    }
                    else {
                        System.out.println("Vehicle not parked: " + newVehicle.getPlate());

                    }
                } catch (Exception e) {
                    System.out.println("Vehicle not parked: " + e.getMessage());
                }
            }

           } //end for loop
    } //end logReader


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

    public String spotTypeConvertion(String type) {
        if (type =="1")
            return "Express Parking Space";
        else return "Regular Parking Space";
    }


}