public class FeeCalculator {
    public double getFee(long parkingDuration, double hourlyRate) {
        return parkingDuration * hourlyRate;
    }
}
