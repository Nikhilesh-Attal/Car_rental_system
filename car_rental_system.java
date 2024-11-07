import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Car Class
class Car {
    private String car_Id;
    private String brand;
    private String model;
    private double price_per_day;
    private boolean is_available;

    // Constructor
    public Car(String car_Id, String brand, String model, double price_per_day, boolean is_available) {
        this.car_Id = car_Id;
        this.brand = brand;
        this.model = model;
        this.price_per_day = price_per_day;
        this.is_available = is_available;
    }

    // Getter for Car ID
    public String getCarID() {
        return car_Id;
    }

    // Getter for Brand
    public String getBrand() {
        return brand;
    }

    // Getter for Model
    public String getModel() {
        return model;
    }

    // Calculate rental price based on the number of days
    public double calculatePrice(int rentalDays) {
        return price_per_day * rentalDays;
    }

    // Check if the car is available
    public boolean isAvailable() {
        return is_available;
    }

    // Mark the car as rented (unavailable)
    public void rentCar() {
        is_available = false;
    }

    // Mark the car as returned (available)
    public void returnCar() {
        is_available = true;
    }
}

// Customer Class
class Customer {
    private String customer_id;
    private String name;

    public Customer(String customer_id, String name) {
        this.customer_id = customer_id;
        this.name = name;
    }

    public String getCustomerId() {
        return customer_id;
    }

    public String getName() {
        return name;
    }
}

// Rental Class
class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

// CarRentalSystem Class
class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rentCar();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car) {
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            car.returnCar();
            System.out.println("Car is returned successfully.");
        } else {
            System.out.println("Car was not rented.");
        }
    }

    public void menu() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("\n\tWelcome To Pravesh Car Rentwala");
            System.out.println("Choose any option");
            System.out.println("0. Exit");
            System.out.println("1. Rent a car");
            System.out.println("2. Return car");

            int choice = scan.nextInt();
            scan.nextLine(); // Consume newline

            switch (choice) {
                case 0:
                    System.exit(0);
                    break;

                case 1: // Rent a car
                    System.out.println("\nEnter your name: ");
                    String customerName = scan.nextLine();

                    System.out.println("Available cars:");
                    for (Car car : cars) {
                        if (car.isAvailable()) {
                            System.out.println(car.getCarID() + " - " + car.getBrand() + " - " + car.getModel());
                        }
                    }

                    System.out.println("Enter the car ID you want to rent: ");
                    String carID = scan.nextLine();

                    System.out.println("Enter the number of days for rental: ");
                    int rentalDays = scan.nextInt();
                    scan.nextLine(); // Consume newline

                    // Creating a new customer
                    Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                    addCustomer(newCustomer);

                    // Find the selected car
                    Car selectedCar = null;
                    for (Car car : cars) {
                        if (car.getCarID().equals(carID) && car.isAvailable()) {
                            selectedCar = car;
                            break;
                        }
                    }

                    if (selectedCar != null) {
                        double totalPrice = selectedCar.calculatePrice(rentalDays);
                        System.out.printf("\nRental Information:\nCustomer ID: %s\nCustomer Name: %s\nCar: %s %s\nRental Days: %d\nTotal Price: ₹%.2f\n",
                                newCustomer.getCustomerId(), newCustomer.getName(), selectedCar.getBrand(), selectedCar.getModel(), rentalDays, totalPrice);

                        System.out.println("\nConfirm rental (Y/N): ");
                        String confirm = scan.nextLine();

                        if (confirm.equalsIgnoreCase("Y")) {
                            rentCar(selectedCar, newCustomer, rentalDays);
                            System.out.println("Car rented successfully.");
                        } else {
                            System.out.println("Rental canceled.");
                        }
                    } else {
                        System.out.println("Invalid car selection or car is not available.");
                    }
                    break;

                case 2: // Return car
                    System.out.println("\nEnter the car ID you want to return: ");
                    String returnCarID = scan.nextLine();

                    Car carToReturn = null;
                    for (Car car : cars) {
                        if (car.getCarID().equals(returnCarID) && !car.isAvailable()) {
                            carToReturn = car;
                            break;
                        }
                    }

                    if (carToReturn != null) {
                        Customer customer = null;
                        for (Rental rental : rentals) {
                            if (rental.getCar() == carToReturn) {
                                customer = rental.getCustomer();
                                break;
                            }
                        }

                        if (customer != null) {
                            returnCar(carToReturn);
                            System.out.println("Car returned successfully by " + customer.getName());
                        } else {
                            System.out.println("Car was not rented.");
                        }
                    } else {
                        System.out.println("Invalid car ID or car was not rented.");
                    }
                    break;

                default:
                    System.out.println("Please enter a valid option.");
                    break;
            }
        }
    }
}

// Main Class
public class car_rental_system {
    public static void main(String[] args) {
        CarRentalSystem crs = new CarRentalSystem();

        // Add cars to the system
        crs.addCar(new Car("101", "Toyota", "Corolla", 3000, true));
        crs.addCar(new Car("102", "Honda", "Civic", 3500, true));
        crs.addCar(new Car("103", "Ford", "Mustang", 7000, true));
        crs.addCar(new Car("104", "Chevrolet", "Camaro", 6500, true));
        crs.addCar(new Car("105", "BMW", "X5", 10000, true));
        crs.addCar(new Car("106", "Audi", "A4", 8000, true));
        crs.addCar(new Car("107", "Tesla", "Model S", 12000, true));

        crs.menu();
    }
}