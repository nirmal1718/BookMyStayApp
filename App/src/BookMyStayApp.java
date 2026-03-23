/**
 * Hotel Booking System - Room Modeling
 * Demonstrates abstraction, inheritance, polymorphism, and simple availability handling.
 *
 * @author Nirmal
 * @version 1.0
 */

// Abstract class
abstract class Room {
    protected String type;
    protected int beds;
    protected double price;

    // Constructor
    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    // Abstract method
    public abstract void displayDetails();
}

// Single Room class
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 1000);
    }

    @Override
    public void displayDetails() {
        System.out.println("Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

// Double Room class
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 2000);
    }

    @Override
    public void displayDetails() {
        System.out.println("Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

// Suite Room class
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 5000);
    }

    @Override
    public void displayDetails() {
        System.out.println("Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

// Main Application
public class BookMyStayApp {

    public static void main(String[] args) {

        // Creating room objects (Polymorphism)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Availability (simple variables)
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("=== Hotel Room Details ===\n");

        // Display Single Room
        single.displayDetails();
        System.out.println("Available: " + singleAvailable);
        System.out.println("-------------------------");

        // Display Double Room
        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleAvailable);
        System.out.println("-------------------------");

        // Display Suite Room
        suite.displayDetails();
        System.out.println("Available: " + suiteAvailable);
        System.out.println("-------------------------");

        System.out.println("Application terminated.");
    }
}