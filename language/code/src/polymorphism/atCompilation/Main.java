package polymorphism.atCompilation;

class MathOperations {
    // Method with two parameters
    int add(int a, int b) {
        return a + b;
    }

    // Overloaded method with three parameters
    int add(int a, int b, int c) {
        return a + b + c;
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {
        MathOperations math = new MathOperations();
        System.out.println(math.add(5, 10));        // Calls the two-parameter method
        System.out.println(math.add(5, 10, 15));    // Calls the three-parameter method
    }
}