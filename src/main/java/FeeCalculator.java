public class FeeCalculator {
    public double getFee(long parkingDuration, double hourlyRate, double discountRate) {
        return parkingDuration * hourlyRate*(1-discountRate);
    }
}
