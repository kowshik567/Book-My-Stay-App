import java.util.HashMap;
import java.util.Map;

/*
====================================================
CLASS : RoomInventory
Use Case 3 : Centralized Room Inventory Management
====================================================
*/

class RoomInventory {

    // Store available rooms for each room type
    private Map<String, Integer> roomAvailability;

    // Constructor initializes the inventory
    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    // Initialize room availability
    private void initializeInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    // Return room availability
    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

/*
====================================================
MAIN CLASS : UseCase3InventorySetup
====================================================
*/

public class Bookmystayapp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        System.out.println("Hotel Room Inventory Status\n");

        System.out.println("Single Room:");
        System.out.println("Beds: 1");
        System.out.println("Size: 250 sqft");
        System.out.println("Price per night: 1500.0");
        System.out.println("Available Rooms: " +
                inventory.getRoomAvailability().get("Single"));

        System.out.println();

        System.out.println("Double Room:");
        System.out.println("Beds: 2");
        System.out.println("Size: 400 sqft");
        System.out.println("Price per night: 2500.0");
        System.out.println("Available Rooms: " +
                inventory.getRoomAvailability().get("Double"));

        System.out.println();

        System.out.println("Suite Room:");
        System.out.println("Beds: 3");
        System.out.println("Size: 750 sqft");
        System.out.println("Price per night: 5000.0");
        System.out.println("Available Rooms: " +
                inventory.getRoomAvailability().get("Suite"));
    }
}