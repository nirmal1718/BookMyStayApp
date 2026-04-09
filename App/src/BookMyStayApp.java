import java.io.*;
import java.util.*;

// Reservation class (Serializable)
class Reservation implements Serializable {
    int id;
    String name;
    String roomType;

    Reservation(int id, String name, String roomType) {
        this.id = id;
        this.name = name;
        this.roomType = roomType;
    }

    void display() {
        System.out.println("ID: " + id +
                ", Name: " + name +
                ", RoomType: " + roomType);
    }
}

// Wrapper class for system state
class SystemState implements Serializable {
    List<Reservation> history;
    Map<String, Integer> inventory;

    SystemState(List<Reservation> history, Map<String, Integer> inventory) {
        this.history = history;
        this.inventory = inventory;
    }
}

public class BookMyStayApp {

    static final String FILE_NAME = "system_state.dat";

    public static void main(String[] args) {

        List<Reservation> history = new ArrayList<>();
        Map<String, Integer> inventory = new HashMap<>();

        // Step 1: Restore state (on startup)
        SystemState state = loadState();

        if (state != null) {
            history = state.history;
            inventory = state.inventory;
            System.out.println("System state restored from file.");
        } else {
            // Default initial state
            inventory.put("Single", 2);
            inventory.put("Double", 1);
            System.out.println("No previous data found. Starting fresh.");
        }

        // Step 2: Simulate booking
        book(1, "Nirmal", "Single", history, inventory);
        book(2, "Arun", "Double", history, inventory);

        // Step 3: Display current state
        System.out.println("\n--- Current Bookings ---");
        for (Reservation r : history) {
            r.display();
        }

        System.out.println("\n--- Inventory ---");
        for (String key : inventory.keySet()) {
            System.out.println(key + ": " + inventory.get(key));
        }

        // Step 4: Save state (before shutdown)
        saveState(new SystemState(history, inventory));
        System.out.println("\nSystem state saved successfully.");
    }

    // Booking logic
    static void book(int id, String name, String type,
                     List<Reservation> history,
                     Map<String, Integer> inventory) {

        if (!inventory.containsKey(type) || inventory.get(type) <= 0) {
            System.out.println("Booking failed for " + name);
            return;
        }

        inventory.put(type, inventory.get(type) - 1);
        history.add(new Reservation(id, name, type));

        System.out.println("Booking successful for " + name);
    }

    // Save (Serialization)
    static void saveState(SystemState state) {
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            oos.writeObject(state);
            oos.close();
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load (Deserialization)
    static SystemState loadState() {
        try {
            ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream(FILE_NAME));
            SystemState state = (SystemState) ois.readObject();
            ois.close();
            return state;
        } catch (FileNotFoundException e) {
            // File not found → first run
            return null;
        } catch (Exception e) {
            // Corrupted file or error
            System.out.println("Error loading state. Starting fresh.");
            return null;
        }
    }
}