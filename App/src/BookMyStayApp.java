import java.util.HashMap;
import java.util.Map;

/* =========================
   DOMAIN MODEL (Room)
   ========================= */

abstract class Room {
    protected String type;
    protected int beds;
    protected double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public abstract void displayDetails();
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1000);
    }

    public void displayDetails() {
        System.out.println("Type: " + type + ", Beds: " + beds + ", Price: ₹" + price);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2000);
    }

    public void displayDetails() {
        System.out.println("Type: " + type + ", Beds: " + beds + ", Price: ₹" + price);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000);
    }

    public void displayDetails() {
        System.out.println("Type: " + type + ", Beds: " + beds + ", Price: ₹" + price);
    }
}

/* =========================
   INVENTORY (STATE HOLDER)
   ========================= */

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // intentionally unavailable
        inventory.put("Suite Room", 2);
    }

    // Read-only access
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Expose read-only view (no modification here)
    public Map<String, Integer> getAllAvailability() {
        return inventory;
    }
}

/* =========================
   SEARCH SERVICE (READ-ONLY)
   ========================= */

class SearchService {

    public void searchAvailableRooms(RoomInventory inventory, Room[] rooms) {

        System.out.println("=== Available Rooms ===");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getType());

            // Validation: show only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println("----------------------");
            }
        }
    }
}

/* =========================
   MAIN APPLICATION
   ========================= */

public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room objects (domain model)
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Guest triggers search
        SearchService searchService = new SearchService();
        searchService.searchAvailableRooms(inventory, rooms);

        System.out.println("Search completed (no state modified).");
    }
}