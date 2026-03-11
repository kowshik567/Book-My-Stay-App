/**
 * =====================================================
 * MAIN CLASS - UseCase2RoomInitialization
 * =====================================================
 *
 * Use Case 2: Basic Room Types & Static Availability
 *
 * Description:
 * This class demonstrates room initialization
 * using domain models before introducing
 * centralized inventory management.
 *
 * Availability is represented using
 * simple variables to highlight limitations.
 *
 * @version 2.1
 */

// ABSTRACT CLASS
abstract class Room {

    // Number of beds available in the room
    protected int numberOfBeds;

    // Total size of the room in square feet
    protected int squareFeet;

    // Price charged per night
    protected double pricePerNight;

    // Constructor used by child classes
    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    // Display room details
    public void displayRoomDetails() {
        System.out.println("Number of Beds: " + numberOfBeds);
        System.out.println("Room Size: " + squareFeet + " sq ft");
        System.out.println("Price Per Night: ₹" + pricePerNight);
    }
}

// SINGLE ROOM CLASS
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 255, 1580.5);
    }
}

// DOUBLE ROOM CLASS
class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

// SUITE ROOM CLASS
class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

// MAIN CLASS
public class Bookmystayapp {

    /**
     * Application entry point
     */
    public static void main(String[] args) {

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        System.out.println("Single Room Details:");
        single.displayRoomDetails();

        System.out.println();

        System.out.println("Double Room Details:");
        doubleRoom.displayRoomDetails();

        System.out.println();

        System.out.println("Suite Room Details:");
        suite.displayRoomDetails();
    }
}