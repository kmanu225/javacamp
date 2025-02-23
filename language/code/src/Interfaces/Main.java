package Interfaces;

// Interface with abstract methods
interface Animal {
    void makeSound(); // Method signature (no body)
}

// Implementing the interface in a class
class Cat implements Animal {
    @Override
    public void makeSound() {
        System.out.println("Cat meows");
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        Animal myCat = new Cat(); // Create a Cat object
        myCat.makeSound(); // Calls implemented method (Output: Cat meows)
    }
}
