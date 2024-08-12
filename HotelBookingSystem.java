
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Booking {
    private static int counter = 1;
    private int bookingId;
    private String customerName;
    private HotelRoom room;
    private int duration;

    public Booking(String customerName, HotelRoom room, int duration) {
        this.bookingId = counter++;
        this.customerName = customerName;
        this.room = room;
        this.duration = duration;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public HotelRoom getRoom() {
        return room;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "Booking ID=" + bookingId +
                ", Customer Name='" + customerName + '\'' +
                ", Room Details=" + room +
                ", Duration (nights)=" + duration +
                '}';
    }
}

class HotelManagement {
    private ArrayList<HotelRoom> roomList;
    private HashMap<Integer, Booking> bookingMap;

    public HotelManagement() {
        roomList = new ArrayList<>();
        bookingMap = new HashMap<>();
        roomList.add(new HotelRoom(101, "Single", 100.0));
        roomList.add(new HotelRoom(102, "Double", 150.0));
        roomList.add(new HotelRoom(103, "Suite", 250.0));
    }

    public void displayBooking(int bookingId) {
        Booking booking = bookingMap.get(bookingId);
        if (booking != null) {
            System.out.println("Booking Details:");
            System.out.println(booking);
        } else {
            System.out.println("No booking found with ID " + bookingId);
        }
    }

    public void createBooking(String customerName, int number, int duration) {
        HotelRoom room = findAvailableRoom(number);
        if (room == null) {
            System.out.println("Sorry, Room " + number + " is not available.");
            return;
        }

        double totalAmount = room.getCost() * duration;
        if (PaymentService.processPayment(customerName, totalAmount)) {
            room.setAvailability(false);
            Booking booking = new Booking(customerName, room, duration);
            bookingMap.put(booking.getBookingId(), booking);
            System.out.println("Booking confirmed: " + booking);
        } else {
            System.out.println("Payment failed. Unable to complete the booking.");
        }
    }

    public void showAvailableRooms() {
        System.out.println("Available Rooms:");
        for (HotelRoom room : roomList) {
            if (room.isAvailable()) {
                System.out.println(room);
            }
        }
    }

    public HotelRoom findAvailableRoom(int number) {
        for (HotelRoom room : roomList) {
            if (room.getNumber() == number && room.isAvailable()) {
                return room;
            }
        }
        return null;
    }
}

class HotelRoom {
    private int number;
    private String type;
    private double cost;
    private boolean available;

    public HotelRoom(int number, String type, double cost) {
        this.number = number;
        this.type = type;
        this.cost = cost;
        this.available = true;
    }

    public int getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public double getCost() {
        return cost;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailability(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "HotelRoom{" +
                "Room Number=" + number +
                ", Type='" + type + '\'' +
                ", Cost=" + cost +
                ", Available=" + available +
                '}';
    }
}

class PaymentService {
    public static boolean processPayment(String customerName, double totalAmount) {
        System.out.println("Processing payment for " + customerName + " of $" + totalAmount);
        return true;
    }
}

public class HotelBookingSystem {
    public static void main(String[] args) {
        HotelManagement hotelManagement = new HotelManagement();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. View Available Rooms");
            System.out.println("2. Make a Booking");
            System.out.println("3. View Booking Details");
            System.out.println("4. Exit");
            System.out.print("Please select an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    hotelManagement.showAvailableRooms();
                    break;
                case 2:
                    System.out.print("Enter the customer name: ");
                    String customerName = scanner.next();
                    System.out.print("Enter the room number: ");
                    int number = scanner.nextInt();
                    System.out.print("Enter number of nights: ");
                    int duration = scanner.nextInt();
                    hotelManagement.createBooking(customerName, number, duration);
                    break;
                case 3:
                    System.out.print("Enter booking ID: ");
                    int bookingId = scanner.nextInt();
                    hotelManagement.displayBooking(bookingId);
                    break;
                case 4:
                    scanner.close();
                    System.out.println("Thank you for using the Hotel Booking System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
    }
}
