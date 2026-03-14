import java.util.*;

// Reservation class
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// BookingRequestQueue
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.add(r);
    }

    public synchronized Reservation getRequest() {
        return queue.poll();
    }
}

// RoomInventory
class RoomInventory {

    Map<String,Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single",5);
        rooms.put("Double",3);
        rooms.put("Suite",2);
    }

    public boolean allocateRoom(String roomType) {

        int available = rooms.get(roomType);

        if(available > 0){
            rooms.put(roomType, available-1);
            return true;
        }

        return false;
    }

    public void showInventory(){

        System.out.println("\nRemaining Inventory:");
        for(String key : rooms.keySet()){
            System.out.println(key + ": " + rooms.get(key));
        }
    }
}

// RoomAllocationService
class RoomAllocationService {

    public void allocateRoom(Reservation r, RoomInventory inventory){

        boolean success = inventory.allocateRoom(r.roomType);

        if(success){
            System.out.println("Booking confirmed for Guest: "
                    + r.guestName + ", Room Type: " + r.roomType);
        }
        else{
            System.out.println("No rooms available for " + r.roomType);
        }
    }
}

// ConcurrentBookingProcessor
class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService){

        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {

        while(true){

            Reservation reservation;

            synchronized(bookingQueue){
                reservation = bookingQueue.getRequest();
            }

            if(reservation == null)
                break;

            synchronized(inventory){
                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}

// Main Class
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Concurrent Booking Simulation\n");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Add booking requests
        bookingQueue.addRequest(new Reservation("Abhi","Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi","Double"));
        bookingQueue.addRequest(new Reservation("Kural","Suite"));
        bookingQueue.addRequest(new Reservation("Subha","Single"));

        // Create threads
        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService));

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService));

        // Start threads
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        inventory.showInventory();
    }
}