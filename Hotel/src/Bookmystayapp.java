import java.util.LinkedList;
import java.util.Queue;

/**
 * =========================================================================
 * CLASS - Reservation
 * =========================================================================
 * Use Case 5: Booking Request (FIFO)
 * Description: This class represents a booking request made by a guest.
 * At this stage, a reservation only captures intent, not confirmation.
 * @version 5.0
 */
class Reservation {
    /** Name of the guest making the booking. */
    private String guestName;
    /** Requested room type. */
    private String roomType;

    /**
     * Creates a new booking request.
     * @param guestName name of the guest
     * @param roomType requested room type
     */
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    /** @return guest name */
    public String getGuestName() { return guestName; }

    /** @return requested room type */
    public String getRoomType() { return roomType; }
}

/**
 * =========================================================================
 * CLASS - BookingRequestQueue
 * =========================================================================
 * Description: This class manages booking requests using a queue to
 * ensure fair allocation. Requests are processed strictly in FIFO order.
 * @version 5.0
 */
class BookingRequestQueue {
    /** Queue that stores booking requests. */
    private Queue<Reservation> requestQueue;

    /** Initializes an empty booking queue. */
    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    /** Adds a booking request to the queue. */
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    /** Retrieves and removes the next booking request from the queue. */
    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    /** Checks whether there are pending booking requests. */
    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}

/**
 * =========================================================================
 * MAIN CLASS - UseCase5BookingRequestQueue
 * =========================================================================
 * Description: Demonstrates how booking requests are accepted and queued
 * in a fair and predictable First-Come-First-Served order.
 * @version 5.0
 */
public class Bookmystayapp {

    /**
     * Application entry point.
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        // Display application header
        System.out.println("Booking Request Queue");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create booking requests
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        // Add requests to the queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queued booking requests in FIFO order
        while (bookingQueue.hasPendingRequests()) {
            Reservation next = bookingQueue.getNextRequest();
            System.out.println("Processing booking for Guest: " + next.getGuestName() +
                    ", Room Type: " + next.getRoomType());
        }
    }
}