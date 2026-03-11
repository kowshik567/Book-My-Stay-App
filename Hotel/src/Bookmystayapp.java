import java.util.HashMap;
import java.util.Map;

/**
 * =========================================================================
 * MAIN CLASS - UseCase4RoomSearch
 * =========================================================================
 * Use Case 4: Room Search & Availability Check
 * Description: Demonstrates how guests can view available rooms without
 * modifying inventory data.
 * @version 4.0
 */
public class Bookmystayapp {

    public static void main(String[] args) {
        // 1. Initialize Room Definitions (Beds, Size, Price)
        Room single = new Room(1, 250, 1500.0);
        Room doubleRm = new Room(2, 400, 2500.0);
        Room suite = new Room(3, 750, 5000.0);

        // 2. Initialize Inventory and set availability counts
        RoomInventory inventory = new RoomInventory();
        inventory.setAvailability("Single", 5);
        inventory.setAvailability("Double", 3);
        inventory.setAvailability("Suite", 2);

        // 3. Perform Search via the Service
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(inventory, single, doubleRm, suite);
    }
}

/**
 * =========================================================================
 * CLASS - RoomSearchService
 * =========================================================================
 */
class RoomSearchService {

    /**
     * Displays available rooms along with their details and pricing.
     */
    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        System.out.println("Room Search\n");
        Map<String, Integer> availability = inventory.getRoomAvailability();

        // Check and display Single Room
        if (availability.getOrDefault("Single", 0) > 0) {
            displayRoom("Single Room", singleRoom, availability.get("Single"));
        }

        // Check and display Double Room
        if (availability.getOrDefault("Double", 0) > 0) {
            displayRoom("Double Room", doubleRoom, availability.get("Double"));
        }

        // Check and display Suite Room
        if (availability.getOrDefault("Suite", 0) > 0) {
            displayRoom("Suite Room", suiteRoom, availability.get("Suite"));
        }
    }

    private void displayRoom(String label, Room room, int count) {
        System.out.println(label + ":");
        System.out.println("Beds: " + room.getBeds());
        System.out.println("Size: " + room.getSize() + " sqft");
        System.out.println("Price per night: " + room.getPrice());
        System.out.println("Available: " + count + "\n");
    }
}

/**
 * Data Model for individual Room details.
 */
class Room {
    private int beds;
    private int size;
    private double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public int getBeds() { return beds; }
    public int getSize() { return size; }
    public double getPrice() { return price; }
}

/**
 * Data Model for the centralized inventory system.
 */
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public void setAvailability(String type, int count) {
        availability.put(type, count);
    }

    public Map<String, Integer> getRoomAvailability() {
        return availability;
    }
}