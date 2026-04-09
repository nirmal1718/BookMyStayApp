import java.util.*;

class Reservation {
    int id;
    String customerName;
    String date;
    double amount;

    Reservation(int id, String customerName, String date, double amount) {
        this.id = id;
        this.customerName = customerName;
        this.date = date;
        this.amount = amount;
    }

    void display() {
        System.out.println("ID: " + id +
                ", Name: " + customerName +
                ", Date: " + date +
                ", Amount: " + amount);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        // Booking History (List maintains insertion order)
        List<Reservation> bookingHistory = new ArrayList<>();

        // Booking Report Service logic variables
        int totalBookings = 0;
        double totalRevenue = 0;

        // Step 1: Booking confirmed and added to history
        bookingHistory.add(new Reservation(1, "Nirmal", "2026-04-09", 1200));
        bookingHistory.add(new Reservation(2, "Arun", "2026-04-10", 1500));
        bookingHistory.add(new Reservation(3, "Priya", "2026-04-11", 2000));

        // Step 2: Display Booking History (Admin view)
        System.out.println("--- Booking History ---");
        for (Reservation r : bookingHistory) {
            r.display();
        }

        // Step 3: Generate Summary Report (Read-only)
        for (Reservation r : bookingHistory) {
            totalBookings++;
            totalRevenue += r.amount;
        }

        System.out.println("\n--- Booking Summary Report ---");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: " + totalRevenue);

        // Step 4: Detailed Report
        System.out.println("\n--- Detailed Report ---");
        for (Reservation r : bookingHistory) {
            r.display();
        }
    }
}