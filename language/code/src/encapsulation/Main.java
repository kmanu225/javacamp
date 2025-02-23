package encapsulation;

// Encapsulated class
class Person {

    // Private variable (cannot be accessed directly)
    private String name;

    // Getter method to access the private variable
    public String getName() {
        return name;
    }

    // Setter method to update the private variable
    public void setName(String newName) {
        this.name = newName;
    }
}

// Main class
public class Main {

    public static void main(String[] args) {
        // Creating an object of the Person class
        Person person = new Person();

        // Using setter to set a value
        person.setName("Alice");

        // Using getter to retrieve the value
        System.out.println("Person's Name: " + person.getName());
    }
}
