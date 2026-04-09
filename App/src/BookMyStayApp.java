import java.util.*;

// Custom Exception
class BookingException extends Exception {
    public BookingException(String message) {
        super(message);
    }
}

// Reservation Class
class Reservation {
    int id;
    String name;
    String roomType;
    String roomId;
    boolean isCancelled;

    Reservation(int id, String name, String roomType, String roomId) {
        this.id = id;
        this.name = name;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }

    void display() {
        System.out.println("ID: " + id +
                ", Name: " + name +
                ", RoomType: " + roomType +
                ", RoomID: " + roomId +
                ", Cancelled: " + isCancelled);
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        // Inventory (room type → count)
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 1);

        // Booking history
        List<Reservation> history = new ArrayList<>();

        // Stack for rollback (released room IDs)
        Stack<String> rollbackStack = new Stack<>();

        // Sample bookings
        bookRoom(1, "Nirmal", "Single", inventory, history);
        bookRoom(2, "Arun", "Double", inventory, history);

        // Display before cancellation
        System.out.println("\n--- Before Cancellation ---");
        displayAll(history);

        // Cancellation requests
        cancelBooking(1, history, inventory, rollbackStack); // valid
        cancelBooking(1, history, inventory, rollbackStack); // already cancelled
        cancelBooking(5, history, inventory, rollbackStack); // not exist

        // Display after cancellation
        System.out.println("\n--- After Cancellation ---");
        displayAll(history);

        // Show rollback stack
        System.out.println("\n--- Rollback Stack (LIFO) ---");
        while (!rollbackStack.isEmpty()) {
            System.out.println("Released RoomID: " + rollbackStack.pop());
        }
    }

    // Booking method
    static void bookRoom(int id, String name, String roomType,
                         Map<String, Integer> inventory,
                         List<Reservation> history) {

        if (!inventory.containsKey(roomType) || inventory.get(roomType) <= 0) {
            System.out.println("Booking failed for " + name);
            return;
        }

        // Generate simple room ID
        String roomId = roomType.substring(0,1).toUpperCase() + (inventory.get(roomType));

        // Update inventory
        inventory.put(roomType, inventory.get(roomType) - 1);

        Reservation r = new Reservation(id, name, roomType, roomId);
        history.add(r);

        System.out.println("Booking successful for " + name + " (RoomID: " + roomId + ")");
    }

    // Cancellation Service (with validation + rollback)
    static void cancelBooking(int id,
                              List<Reservation> history,
                              Map<String, Integer> inventory,
                              Stack<String> rollbackStack) {

        try {
            Reservation r = findReservation(id, history);

            // Validation: already cancelled
            if (r.isCancelled) {
                throw new BookingException("Booking already cancelled (ID: " + id + ")");
            }

            // Step 1: Push roomID to rollback stack (LIFO)
            rollbackStack.push(r.roomId);

            // Step 2: Restore inventory
            inventory.put(r.roomType, inventory.get(r.roomType) + 1);

            // Step 3: Update booking state
            r.isCancelled = true;

            System.out.println("Cancellation successful for ID: " + id);

        } catch (BookingException e) {
            System.out.println("Cancellation failed: " + e.getMessage());
        }
    }

    // Find reservation (validation: existence)
    static Reservation findReservation(int id, List<Reservation> history)
            throws BookingException {

        for (Reservation r : history) {
            if (r.id == id) {
                return r;
            }
        }

        throw new BookingException("Reservation not found (ID: " + id + ")");
    }

    // Display all bookings
    static void displayAll(List<Reservation> history) {
        for (Reservation r : history) {
            r.display();
        }
    }
}