package polymorphism.atRuntime;

// Parent Class
class Animal {
    void makeSound() {
        System.out.println("Animal makes a sound");
    }
}

// Child Class overriding makeSound() method
class Dog extends Animal {
    @Override
    void makeSound() {
        System.out.println("Dog barks");
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {
        Animal myAnimal = new Dog(); // Parent reference, Child object
        myAnimal.makeSound(); // Calls Dog's overridden method (Output: Dog barks)
    }
}