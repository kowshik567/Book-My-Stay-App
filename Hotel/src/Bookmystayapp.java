import java.util.*;

/**
 * =========================================================================
 * CLASS - RoomAllocationService
 * =========================================================================
 * Use Case 6: Reservation Confirmation & Room Allocation
 * Description: Responsible for confirming booking requests and assigning rooms.
 * It ensures unique room IDs and immediate inventory updates.
 * @version 6.0
 */
class RoomAllocationService {

    /** Stores all allocated room IDs to prevent duplicate assignments. */
    private Set<String> allocatedRoomIds;

    /** Stores assigned room IDs by room type (Key: Room type, Value: Set of IDs). */
    private Map<String, Set<String>> assignedRoomsByType;

    /** Initializes allocation tracking structures. */
    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    /**
     * Confirms a booking request by assigning a unique room ID and updating inventory.
     * @param reservation booking request
     * @param inventory centralized room inventory
     */
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String type = reservation.getRoomType();
        int currentStock = inventory.getRoomAvailability().getOrDefault(type, 0);

        if (currentStock > 0) {
            // Generate ID and update internal tracking
            String roomId = generateRoomId(type);
            allocatedRoomIds.add(roomId);

            assignedRoomsByType.putIfAbsent(type, new HashSet<>());
            assignedRoomsByType.get(type).add(roomId);

            // Update global inventory stock
            inventory.updateAvailability(type, currentStock - 1);

            System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() +
                    ", Room ID: " + roomId);
        } else {
            System.out.println("Booking failed for Guest: " + reservation.getGuestName() +
                    ". No " + type + " rooms available.");
        }
    }

    /** Generates a unique room ID for the given room type. */
    private String generateRoomId(String roomType) {
        int nextNumber = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size() + 1;
        return roomType + "-" + nextNumber;
    }
}

/**
 * =========================================================================
 * MAIN CLASS - UseCase6RoomAllocation
 * =========================================================================
 * Description: Demonstrates how booking requests are confirmed and rooms
 * are allocated safely in FIFO order.
 * @version 6.0
 */
public class Bookmystayapp {

    public static void main(String[] args) {
        System.out.println("Room Allocation Processing");

        // 1. Setup Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.updateAvailability("Single", 5);
        inventory.updateAvailability("Suite", 2);

        // 2. Setup Booking Queue (FIFO)
        BookingRequestQueue queue = new BookingRequestQueue();
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Suite"));

        // 3. Initialize Allocation Service
        RoomAllocationService allocationService = new RoomAllocationService();

        // 4. Process Queue
        while (queue.hasPendingRequests()) {
            Reservation request = queue.getNextRequest();
            allocationService.allocateRoom(request, inventory);
        }
    }
}

// --- Supporting Classes from Previous Use Cases ---

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

class BookingRequestQueue {
    private Queue<Reservation> requestQueue = new LinkedList<>();
    public void addRequest(Reservation r) { requestQueue.offer(r); }
    public Reservation getNextRequest() { return requestQueue.poll(); }
    public boolean hasPendingRequests() { return !requestQueue.isEmpty(); }
}

class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();
    public void updateAvailability(String type, int count) { availability.put(type, count); }
    public Map<String, Integer> getRoomAvailability() { return availability; }
}