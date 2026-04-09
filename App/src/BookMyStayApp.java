import java.util.*;

// Custom Exception for Invalid Booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation Class
class Reservation {
    int id;
    String customerName;
    String roomType;

    Reservation(int id, String customerName, String roomType) {
        this.id = id;
        this.customerName = customerName;
        this.roomType = roomType;
    }

    void display() {
        System.out.println("ID: " + id +
                ", Name: " + customerName +
                ", Room Type: " + roomType);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        // Room inventory (system state)
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 1);

        // Booking history
        List<Reservation> history = new ArrayList<>();

        // Test bookings (simulate Guest input)
        tryBooking(1, "Nirmal", "Single", inventory, history);
        tryBooking(2, "Arun", "Triple", inventory, history); // Invalid type
        tryBooking(3, "Priya", "Double", inventory, history);
        tryBooking(4, "Kiran", "Double", inventory, history); // No availability

        // Display valid bookings
        System.out.println("\n--- Valid Booking History ---");
        for (Reservation r : history) {
            r.display();
        }
    }

    // Booking process with validation
    static void tryBooking(int id, String name, String roomType,
                           Map<String, Integer> inventory,
                           List<Reservation> history) {

        try {
            validateBooking(roomType, inventory);

            // If validation passes → update state
            inventory.put(roomType, inventory.get(roomType) - 1);

            Reservation r = new Reservation(id, name, roomType);
            history.add(r);

            System.out.println("Booking successful for " + name);

        } catch (InvalidBookingException e) {
            // Graceful failure handling
            System.out.println("Booking failed for " + name + ": " + e.getMessage());
        }
    }

    // Validator (Fail-Fast Design)
    static void validateBooking(String roomType, Map<String, Integer> inventory)
            throws InvalidBookingException {

        // Check 1: Valid room type
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        // Check 2: Availability
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }
    }
}