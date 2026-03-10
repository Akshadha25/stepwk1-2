import java.util.*;

class Vehicle {
    String plate;
    int spot;
    long entryTime;

    Vehicle(String plate, int spot) {
        this.plate = plate;
        this.spot = spot;
        this.entryTime = System.currentTimeMillis();
    }
}

public class Problem8_ParkingSystem {
    private int capacity;
    private Vehicle[] slots;
    private int totalProbes = 0;
    private int parkedVehicles = 0;
    private Map<String, Vehicle> activeVehicles = new HashMap<>();

    public Problem8_ParkingSystem(int capacity) {
        this.capacity = capacity;
        this.slots = new Vehicle[capacity];
    }

    private int hash(String plate) {
        return Math.abs(plate.hashCode() % capacity);
    }

    public boolean parkVehicle(String plate) {
        int start = hash(plate);
        int probes = 0;
        for (int i = 0; i < capacity; i++) {
            int idx = (start + i) % capacity;
            probes++;
            if (slots[idx] == null) {
                Vehicle v = new Vehicle(plate, idx);
                slots[idx] = v;
                activeVehicles.put(plate, v);
                totalProbes += probes;
                parkedVehicles++;
                System.out.println("Parked " + plate + " at spot #" + idx + " (" + (probes-1) + " probes)");
                return true;
            }
        }
        System.out.println("Parking full for " + plate);
        return false;
    }

    public void exitVehicle(String plate) {
        Vehicle v = activeVehicles.get(plate);
        if (v != null) {
            long durationMillis = System.currentTimeMillis() - v.entryTime;
            double hours = durationMillis / 3600000.0;
            double fee = hours * 5; // $5 per hour
            slots[v.spot] = null;
            activeVehicles.remove(plate);
            parkedVehicles--;
            System.out.println("Vehicle " + plate + " exited. Fee: $" + String.format("%.2f", fee));
        }
    }

    public void printStats() {
        double avgProbes = parkedVehicles == 0 ? 0 : (double) totalProbes / parkedVehicles;
        double occupancy = (double) parkedVehicles / capacity * 100;
        System.out.println("Occupancy: " + String.format("%.2f", occupancy) + "%, Avg Probes: " + String.format("%.2f", avgProbes));
    }

    public static void main(String[] args) {
        Problem8_ParkingSystem parking = new Problem8_ParkingSystem(5);

        parking.parkVehicle("ABC-123");
        parking.parkVehicle("XYZ-999");
        parking.parkVehicle("LMN-456");
        parking.parkVehicle("AAA-111");
        parking.parkVehicle("BBB-222");
        parking.parkVehicle("CCC-333"); // should fail

        parking.exitVehicle("XYZ-999");
        parking.exitVehicle("AAA-111");

        parking.printStats();
    }
}