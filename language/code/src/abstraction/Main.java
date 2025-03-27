package abstraction;

// Abstract class
abstract class Animal {
    // Abstract method (no body)
    abstract void makeSound();

    // Concrete method (has body)
    void sleep() {
        System.out.println("Sleeping...");
    }
}

// Subclass that provides implementation
class Dog extends Animal {
    @Override
    void makeSound() {
        System.out.println("Dog barks");
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        Animal myDog = new Dog(); // Create a Dog object
        myDog.makeSound(); // Calls overridden method (Output: Dog barks)
        myDog.sleep();     // Calls inherited concrete method (Output: Sleeping...)
    }
}
