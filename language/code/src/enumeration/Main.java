package enumeration;

// Defining an Enum
enum Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;
}

// Enum with Constructor and Method
enum Color {
    RED("#FF0000"),
    GREEN("#00FF00"),
    BLUE("#0000FF");

    private String hexCode;

    // Constructor
    Color(String hexCode) {
        this.hexCode = hexCode;
    }

    // Getter Method
    public String getHexCode() {
        return hexCode;
    }
}


// Using the Enum
public class Main {
    public static void main(String[] args) {
        Day today = Day.WEDNESDAY;
        System.out.println("Today is: " + today);

        Color favoriteColor = Color.BLUE;
        System.out.println("Favorite color hex code: " + favoriteColor.getHexCode());
    }
}
