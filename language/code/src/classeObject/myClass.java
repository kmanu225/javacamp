package classeObject;

class myClass {   // Main class

    int x = 2;        // Modiable attribute
    final int y = 3;  // Unmodiable attribute

    static void myMethod() { // Method
        System.out.println("I am in a method.");
    }

    public static void main(String[] args) {
        // Creating an object of the Car class
        myClass myObj1 = new myClass();      // Object 1
        myObj1.x = 3;
        System.out.println("x = " + myObj1.x);

        myClass myObj2 = new myClass();      // Object 2
        System.out.println("x = " + myObj2.y);

        myMethod();                     // Call the method
    }
}
