public interface ParkingGroup {
    double  getHourlyRate();
    double  getDiscount(String licensePlate);
    double getPolicies(String licensePlate);
    double replyFee(String licensePlate);

}

class ParkingGroup1 implements ParkingGroup {

    @Override
    public double getHourlyRate() {
        return 1.0;
    }

    @Override
    public double getDiscount(String licensePlate) {
        if (licensePlate.charAt(0) == 'Z') {  //give discount for handicap vehicle, which license plate starts with Z
            return 0.1;
        }
        else return 0;
    }

    @Override
    public double getPolicies(String licensePlate) {
        if (licensePlate.charAt(0) == 'C') {
            return 1;  //refuse vehicle with license plate start with C
        }
        else return 0;
    }

    @Override
    public double replyFee(String licensePlate) {
        if (licensePlate.charAt(0) == 'C') {  //inform there is no space available for this type of vehicle
            return -1;
        }
        else if (licensePlate.charAt(0) == 'Z')
            return 1.0 - (1-.1);  //inform discounted price for handicap vehicle

            else return 1.0;
    }
}

class ParkingGroup2 implements ParkingGroup {

    @Override
    public double getHourlyRate() {
        return 1.1;
    }

    @Override
    public double getDiscount(String licensePlate) {
        if (licensePlate.charAt(0) == 'Z') {
            return 0.15;
        }
        else return 0;
    }

    @Override
    public double getPolicies(String licensePlate) {
         return 0; //no restriction on vehicle
    }

    @Override
    public double replyFee(String licensePlate) {
        if (licensePlate.charAt(0) == 'C') {  //no restriction at parking group 2 for commercial vehicle (C..)
            return 1.1;
        }
        else if (licensePlate.charAt(0) == 'Z')
            return 1.1*(1-0.15);  //inform discounted price for handicap vehicle (Z..)

        else return 1.1;
    }
}

