package constructor;
// Defining a class named Car

class Car {

    // Attributes (variables)
    String brand;
    String model;
    int year;

    // Constructor to initialize object attributes
    public Car() { // Default constructo
        this.brand = "Toyota";
        this.model = "Rav4";
        this.year = 1994;
    }

    public Car(String brand, String model, int year) { // Constructor with parameters
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    // Method to display car details
    public void displayInfo() {
        System.out.println("Car: " + brand + " " + model + " (" + year + ")");
    }
}

// Main class to run the program
public class Main {

    public static void main(String[] args) {
        // Creating an object of the Car class
        Car myCar1 = new Car();
        Car myCar2 = new Car("Toyota", "Camry", 2022);

        // Calling the displayInfo method
        myCar1.displayInfo();
        myCar2.displayInfo();
    }
}
