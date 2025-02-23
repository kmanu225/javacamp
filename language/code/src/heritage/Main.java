package heritage;

// Superclass (Parent Class)
class Animal {

    protected String name; // Only accesssible by subclasses

    void makeSound() {
        System.out.println("Animal makes a sound");
    }
}

// Subclass (Child Class) that inherits from Animal
class Dog extends Animal {

    void bark() {
        System.out.println(name + " barks");
    }
}

// Main class to test inheritance
public class Main {

    public static void main(String[] args) {
        Dog myDog = new Dog();
        myDog.name = "Buddy";  // Inherited from Animal class
        myDog.makeSound();      // Inherited method
        myDog.bark();           // Own method
    }
}
