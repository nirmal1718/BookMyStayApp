import java.util.*;

/* =========================
   RESERVATION (Request)
   ========================= */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/* =========================
   INVENTORY SERVICE
   ========================= */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decreaseAvailability(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void displayInventory() {
        System.out.println("\n=== Inventory ===");
        for (String key : inventory.keySet()) {
            System.out.println(key + " → " + inventory.get(key));
        }
    }
}

/* =========================
   BOOKING REQUEST QUEUE
   ========================= */
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // FIFO removal
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

/* =========================
   BOOKING SERVICE (CORE)
   ========================= */
class BookingService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private HashMap<String, Set<String>> roomAllocations = new HashMap<>();
    private int roomCounter = 1;

    public void processBookings(BookingRequestQueue queue, RoomInventory inventory) {

        while (!queue.isEmpty()) {

            Reservation request = queue.getNextRequest();
            String type = request.getRoomType();

            System.out.println("\nProcessing request for: " + request.getGuestName());

            // Check availability
            if (inventory.getAvailability(type) > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(type);

                // Ensure uniqueness (Set prevents duplicates)
                if (!allocatedRoomIds.contains(roomId)) {

                    allocatedRoomIds.add(roomId);

                    // Map room type → allocated IDs
                    roomAllocations.putIfAbsent(type, new HashSet<>());
                    roomAllocations.get(type).add(roomId);

                    // Decrease inventory immediately
                    inventory.decreaseAvailability(type);

                    System.out.println("Booking Confirmed!");
                    System.out.println("Guest: " + request.getGuestName());
                    System.out.println("Room Type: " + type);
                    System.out.println("Room ID: " + roomId);

                } else {
                    System.out.println("Error: Duplicate Room ID!");
                }

            } else {
                System.out.println("Booking Failed! No rooms available for " + type);
            }
        }
    }

    // Unique Room ID Generator
    private String generateRoomId(String type) {
        return type.replace(" ", "").substring(0, 2).toUpperCase() + roomCounter++;
    }

    // Display all allocations
    public void displayAllocations() {
        System.out.println("\n=== Allocated Rooms ===");

        for (String type : roomAllocations.keySet()) {
            System.out.println(type + " → " + roomAllocations.get(type));
        }
    }
}

/* =========================
   MAIN APPLICATION
   ========================= */
public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        BookingService bookingService = new BookingService();

        // Add booking requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room")); // should fail
        queue.addRequest(new Reservation("David", "Suite Room"));

        // Process bookings (FIFO)
        bookingService.processBookings(queue, inventory);

        // Display results
        bookingService.displayAllocations();
        inventory.displayInventory();

        System.out.println("\nAll bookings processed safely.");
    }
}