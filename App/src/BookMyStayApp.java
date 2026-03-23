import java.util.*;

/* =========================
   ADD-ON SERVICE (MODEL)
   ========================= */
class Service {
    private String name;
    private double price;

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void display() {
        System.out.println(name + " → ₹" + price);
    }
}

/* =========================
   ADD-ON SERVICE MANAGER
   ========================= */
class AddOnServiceManager {

    // Map: Reservation ID → List of Services
    private Map<String, List<Service>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, Service service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service '" + service.getName() +
                "' to Reservation ID: " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {

        System.out.println("\nServices for Reservation ID: " + reservationId);

        List<Service> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services added.");
            return;
        }

        for (Service s : services) {
            s.display();
        }
    }

    // Calculate total add-on cost
    public double calculateTotalCost(String reservationId) {

        double total = 0;

        List<Service> services = serviceMap.get(reservationId);

        if (services != null) {
            for (Service s : services) {
                total += s.getPrice();
            }
        }

        return total;
    }
}

/* =========================
   MAIN APPLICATION
   ========================= */
public class BookMyStayApp {

    public static void main(String[] args) {

        // Assume these reservation IDs already exist (from booking system)
        String res1 = "SI1";
        String res2 = "SU2";

        // Create services
        Service breakfast = new Service("Breakfast", 300);
        Service wifi = new Service("WiFi", 200);
        Service pickup = new Service("Airport Pickup", 800);

        // Initialize manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Guest selects services
        manager.addService(res1, breakfast);
        manager.addService(res1, wifi);
        manager.addService(res2, pickup);

        // Display services
        manager.displayServices(res1);
        manager.displayServices(res2);

        // Calculate total cost
        System.out.println("\nTotal Add-On Cost for " + res1 + " = ₹" +
                manager.calculateTotalCost(res1));

        System.out.println("Total Add-On Cost for " + res2 + " = ₹" +
                manager.calculateTotalCost(res2));

        System.out.println("\nCore booking & inventory remain unchanged.");
    }
}