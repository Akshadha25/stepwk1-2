// Problem 8: Parking System
import java.util.HashMap;
import java.util.Map;

public class Problem8_ParkingSystem {
    private Map<Integer, Integer> parkingSlots;

    public Problem8_ParkingSystem(int small, int medium, int large) {
        parkingSlots = new HashMap<>();
        parkingSlots.put(1, small);
        parkingSlots.put(2, medium);
        parkingSlots.put(3, large);
    }

    // Park a car: 1=small, 2=medium, 3=large
    public boolean parkCar(int carType) {
        int available = parkingSlots.getOrDefault(carType, 0);
        if (available > 0) {
            parkingSlots.put(carType, available - 1);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Problem8_ParkingSystem parking = new Problem8_ParkingSystem(2, 3, 1);

        System.out.println("Park small car: " + parking.parkCar(1)); // true
        System.out.println("Park medium car: " + parking.parkCar(2)); // true
        System.out.println("Park large car: " + parking.parkCar(3)); // true
        System.out.println("Park another large car: " + parking.parkCar(3)); // false
    }
}