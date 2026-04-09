import java.util.*;

// Booking Request
class BookingRequest {
    int id;
    String guestName;
    String roomType;

    BookingRequest(int id, String guestName, String roomType) {
        this.id = id;
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared System (Queue + Inventory)
class BookingSystem {

    Queue<BookingRequest> queue = new LinkedList<>();
    Map<String, Integer> inventory = new HashMap<>();

    BookingSystem() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }

    // Add request (Producer)
    synchronized void addRequest(BookingRequest req) {
        queue.add(req);
        System.out.println(req.guestName + " added booking request");
    }

    // Process request (Consumer - Critical Section)
    synchronized void processRequest() {
        if (queue.isEmpty()) return;

        BookingRequest req = queue.poll();

        if (!inventory.containsKey(req.roomType)) {
            System.out.println("Invalid room type for " + req.guestName);
            return;
        }

        int available = inventory.get(req.roomType);

        if (available > 0) {
            // Critical section: allocation + update
            inventory.put(req.roomType, available - 1);
            System.out.println("Booking SUCCESS for " + req.guestName +
                    " (" + req.roomType + ")");
        } else {
            System.out.println("Booking FAILED (No rooms) for " + req.guestName);
        }
    }
}

// Thread Class
class BookingProcessor extends Thread {

    BookingSystem system;

    BookingProcessor(BookingSystem system) {
        this.system = system;
    }

    public void run() {
        // Each thread tries to process requests
        for (int i = 0; i < 3; i++) {
            system.processRequest();
            try {
                Thread.sleep(100); // simulate delay
            } catch (Exception e) {}
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        // Simulate multiple guest requests (concurrent input)
        system.addRequest(new BookingRequest(1, "Nirmal", "Single"));
        system.addRequest(new BookingRequest(2, "Arun", "Single"));
        system.addRequest(new BookingRequest(3, "Priya", "Single")); // extra → fail
        system.addRequest(new BookingRequest(4, "Kiran", "Double"));
        system.addRequest(new BookingRequest(5, "Rahul", "Double")); // extra → fail

        // Multiple threads (concurrent processing)
        BookingProcessor t1 = new BookingProcessor(system);
        BookingProcessor t2 = new BookingProcessor(system);

        t1.start();
        t2.start();
    }
}