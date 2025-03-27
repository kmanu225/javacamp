package jfx.app.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfx.app.model.Item;
import jfx.app.model.HasBorrowed;
import jfx.app.model.UserCategory;
import static jfx.app.database.DbUtils.getConn;

public class BookDb {

    public static boolean checkAuthorExistence(String AuthorName, String AuthorBirthDate)
            throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(1) FROM Author WHERE AuthorName = ? AND BirthDate = ?";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, AuthorName);
            stmt.setInt(2, Integer.parseInt(AuthorBirthDate)); // Assuming AuthorBirthDate is an integer, like a year

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 1;
            }
        }
        return false;
    }

    public static void AddAuthor(String Name, String BirthYear) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Author (AuthorName, Birthdate) VALUES (?, ?)";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, Name);
            stmt.setInt(2, Integer.parseInt(BirthYear)); // Assuming BirthYear is an integer

            stmt.executeUpdate();
        }
    }

    public static int getAuthorId(String Name, String BirthYear) throws SQLException, ClassNotFoundException {
        String query = "SELECT Id FROM Author WHERE AuthorName = ? AND BirthDate = ?";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, Name);
            stmt.setInt(2, Integer.parseInt(BirthYear)); // Assuming BirthYear is an integer

            ResultSet rs = stmt.executeQuery();
            int id = 0;
            if (rs.next()) {
                id = rs.getInt("Id");
            }
            return id;
        }
    }

    public static boolean checkBookExistence(String title, String firstEdition)
            throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(1) FROM Book WHERE Title = ? AND FirstYearEdition = ?";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, title);
            stmt.setString(2, firstEdition);

            ResultSet rs = stmt.executeQuery();
            if (rs.next() && !title.isEmpty() && !firstEdition.isEmpty()) {
                return rs.getInt(1) == 1;
            }
        }
        return false;
    }

    public static void AddBook(String title, String firstEdition, String description)
            throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Book (Title, FirstYearEdition, Description) VALUES (?, ?, ?)";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, title);
            stmt.setString(2, firstEdition);
            stmt.setString(3, description);

            stmt.executeUpdate();
        }
    }

    public static boolean checkEditorExistence(String EditorISBN) throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(1) FROM Editor WHERE ISBN = ?";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, EditorISBN);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 1;
            }
        }
        return false;
    }

    public static void AddEditor(String EditorName, String EditorISBN) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Editor (EditorName, ISBN) VALUES (?, ?)";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, EditorName);
            stmt.setString(2, EditorISBN);

            stmt.executeUpdate();
        }
    }

    public static boolean checkHasWrittenExistence(String BookTitle, String BookFirstYearEdition, int AuthorId)
            throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(1) FROM HasWritten WHERE BookTitle = ? AND BookFirstYearEdition = ? AND AuthorId = ?";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, BookTitle);
            stmt.setString(2, BookFirstYearEdition);
            stmt.setInt(3, AuthorId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 1;
            }
        }
        return false;
    }

    public static void updateHasWritten(String BookTitle, String BookFirstYearEdition, int AuthorId)
            throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO HasWritten (BookTitle, BookFirstYearEdition, AuthorId) VALUES (?, ?, ?)";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, BookTitle);
            stmt.setString(2, BookFirstYearEdition);
            stmt.setInt(3, AuthorId);

            stmt.executeUpdate();
        }
    }

    public static void AddBookCopy(Integer CopyId, String BookTitle, String BookFirstYearEdition, String EditorISBN)
            throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Item (CopyId, BookTitle, BookFirstYearEdition, EditorISBN) VALUES (?, ?, ?, ?)";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, CopyId);
            stmt.setString(2, BookTitle);
            stmt.setString(3, BookFirstYearEdition);
            stmt.setString(4, EditorISBN);

            stmt.executeUpdate();
        }
    }

    public static Item searchBook(String bookTitle, String bookAuthor, String bookEditor)
            throws SQLException, ClassNotFoundException {

        String sql = """
                SELECT Item.BookTitle, AuthorName, EditorName, Description, CopyId
                FROM Item
                JOIN Book ON Book.Title = Item.BookTitle AND Book.FirstYearEdition = Item.BookFirstYearEdition
                JOIN HasWritten ON Book.Title = HasWritten.BookTitle AND Book.FirstYearEdition = HasWritten.BookFirstYearEdition
                JOIN Author ON Author.Id = HasWritten.AuthorId
                JOIN Editor ON Editor.ISBN = Item.EditorISBN
                WHERE 1=1
                """;

        // Dynamically add conditions based on provided parameters
        if (!Objects.equals(bookTitle, "")) {
            sql += " AND Item.BookTitle = ?";
        }
        if (!Objects.equals(bookAuthor, "")) {
            sql += " AND Author.AuthorName = ?";
        }
        if (!Objects.equals(bookEditor, "")) {
            sql += " AND Editor.EditorName = ?";
        }
        sql += " ORDER BY BookTitle DESC";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            int parameterIndex = 1;

            if (!Objects.equals(bookTitle, "")) {
                stmt.setString(parameterIndex++, bookTitle);
            }
            if (!Objects.equals(bookAuthor, "")) {
                stmt.setString(parameterIndex++, bookAuthor);
            }
            if (!Objects.equals(bookEditor, "")) {
                stmt.setString(parameterIndex++, bookEditor);
            }

            ResultSet rs = stmt.executeQuery();
            return Observe0(rs);
        }
    }

    private static Item Observe0(ResultSet rs) throws SQLException {
        Item item = new Item();
        while (rs.next()) {
            item.setBookTitle(rs.getString("BookTitle"));
            item.setAuthorName(rs.getString("AuthorName"));
            item.setEditorName(rs.getString("EditorName"));
            item.setDescription(rs.getString("Description"));
            item.setCopyId(rs.getInt("CopyId"));
        }
        return item;
    }

    public static boolean checkBookExistence(String bookTitle, String bookAuthor, String bookEditor)
            throws SQLException, ClassNotFoundException {

        String query1 = "SELECT COUNT(1) FROM Item WHERE Item.BookTitle = ?";
        String query2 = """
                SELECT COUNT(1) FROM Item
                JOIN Book ON Book.Title = Item.BookTitle AND Book.FirstYearEdition = Item.BookFirstYearEdition
                JOIN HasWritten ON Book.Title = HasWritten.BookTitle AND Book.FirstYearEdition = HasWritten.BookFirstYearEdition
                JOIN Author ON Author.Id = HasWritten.AuthorId
                WHERE Author.AuthorName = ?""";
        String query3 = """
                SELECT COUNT(1) FROM Item
                JOIN Book ON Book.Title = Item.BookTitle AND Book.FirstYearEdition = Item.BookFirstYearEdition
                JOIN Editor ON Editor.ISBN = Item.EditorISBN
                WHERE Editor.EditorName = ?""";

        try (Connection con = getConn()) {

            // Query 1: Check if bookTitle exists
            if (!Objects.equals(bookTitle, "")) {
                try (PreparedStatement stmt1 = con.prepareStatement(query1)) {
                    stmt1.setString(1, bookTitle);
                    ResultSet rs1 = stmt1.executeQuery();
                    if (rs1.next()) {
                        return rs1.getInt(1) == 1;
                    }
                }
            }

            // Query 2: Check if bookAuthor exists
            if (!Objects.equals(bookAuthor, "")) {
                try (PreparedStatement stmt2 = con.prepareStatement(query2)) {
                    stmt2.setString(1, bookAuthor);
                    ResultSet rs2 = stmt2.executeQuery();
                    if (rs2.next()) {
                        return rs2.getInt(1) == 1;
                    }
                }
            }

            // Query 3: Check if bookEditor exists
            if (!Objects.equals(bookEditor, "")) {
                try (PreparedStatement stmt3 = con.prepareStatement(query3)) {
                    stmt3.setString(1, bookEditor);
                    ResultSet rs3 = stmt3.executeQuery();
                    if (rs3.next()) {
                        return rs3.getInt(1) == 1;
                    }
                }
            }

            return false;
        }
    }

    public static Item searchBook1(String bookTitle, String bookAuthor, String bookEditor)
            throws SQLException, ClassNotFoundException {

        String query = """
                SELECT Item.BookTitle, AuthorName, EditorName, Description
                FROM Item
                JOIN Book ON Book.Title = Item.BookTitle AND Book.FirstYearEdition = Item.BookFirstYearEdition
                JOIN HasWritten ON Book.Title = HasWritten.BookTitle AND Book.FirstYearEdition = HasWritten.BookFirstYearEdition
                JOIN Author ON Author.Id = HasWritten.AuthorId
                JOIN Editor ON Editor.ISBN = Item.EditorISBN
                WHERE 1=1
                """;

        if (!Objects.equals(bookTitle, "")) {
            query += " AND Item.BookTitle = ?";
        }
        if (!Objects.equals(bookAuthor, "")) {
            query += " AND Author.AuthorName = ?";
        }
        if (!Objects.equals(bookEditor, "")) {
            query += " AND Editor.EditorName = ?";
        }
        query += " ORDER BY BookTitle DESC";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            int parameterIndex = 1;

            if (!Objects.equals(bookTitle, "")) {
                stmt.setString(parameterIndex++, bookTitle);
            }
            if (!Objects.equals(bookAuthor, "")) {
                stmt.setString(parameterIndex++, bookAuthor);
            }
            if (!Objects.equals(bookEditor, "")) {
                stmt.setString(parameterIndex++, bookEditor);
            }

            ResultSet rs = stmt.executeQuery();
            return Observe1(rs);
        }
    }

    private static Item Observe1(ResultSet rs) throws SQLException {
        Item item = new Item();
        while (rs.next()) {
            item.setBookTitle(rs.getString("BookTitle"));
            item.setAuthorName(rs.getString("AuthorName"));
            item.setEditorName(rs.getString("EditorName"));
            item.setDescription(rs.getString("Description"));
        }
        return item;
    }

    public static ObservableList<Item> searchAvailableBooks() throws SQLException, ClassNotFoundException {
        String query = """
                SELECT Item.BookTitle, AuthorName, EditorName, Description, CopyId
                FROM Item
                JOIN Book ON Book.Title = Item.BookTitle AND Book.FirstYearEdition = Item.BookFirstYearEdition
                JOIN HasWritten ON Book.Title = HasWritten.BookTitle AND Book.FirstYearEdition = HasWritten.BookFirstYearEdition
                JOIN Author ON Author.Id = HasWritten.AuthorId
                JOIN Editor ON Editor.ISBN = Item.EditorISBN
                ORDER BY BookTitle DESC
                """;

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            return getAvailableBooks(rs);
        }
    }

    private static ObservableList<Item> getAvailableBooks(ResultSet rs) throws SQLException {
        ObservableList<Item> bookCopies = FXCollections.observableArrayList();

        while (rs.next()) {
            Item item = new Item();
            item.setBookTitle(rs.getString("BookTitle"));
            item.setAuthorName(rs.getString("AuthorName"));
            item.setEditorName(rs.getString("EditorName"));
            item.setDescription(rs.getString("Description"));
            item.setCopyId(rs.getInt("CopyId"));
            bookCopies.add(item);
        }
        return bookCopies;
    }

    public static ObservableList<Item> searchAvailableBooks2() throws SQLException, ClassNotFoundException {
        String query = """
                SELECT DISTINCT Item.BookTitle, AuthorName, EditorName, Description
                FROM Item
                JOIN Book ON Book.Title = Item.BookTitle AND Book.FirstYearEdition = Item.BookFirstYearEdition
                JOIN HasWritten ON Book.Title = HasWritten.BookTitle AND Book.FirstYearEdition = HasWritten.BookFirstYearEdition
                JOIN Author ON Author.Id = HasWritten.AuthorId
                JOIN Editor ON Editor.ISBN = Item.EditorISBN
                ORDER BY BookTitle DESC
                """;

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            return getAvailableBooks2(rs);
        }
    }

    private static ObservableList<Item> getAvailableBooks2(ResultSet rs) throws SQLException {
        ObservableList<Item> bookCopies = FXCollections.observableArrayList();

        while (rs.next()) {
            Item item = new Item();
            item.setBookTitle(rs.getString("BookTitle"));
            item.setAuthorName(rs.getString("AuthorName"));
            item.setEditorName(rs.getString("EditorName"));
            item.setDescription(rs.getString("Description"));
            bookCopies.add(item);
        }
        return bookCopies;
    }

    public static ObservableList<HasBorrowed> searchBorrowedBooks() throws SQLException, ClassNotFoundException {
        String query = """
                SELECT * FROM User
                JOIN HasBorrowed ON User.Login = HasBorrowed.BorrowerLogin
                JOIN Item ON Item.CopyID = HasBorrowed.BookCopyId
                WHERE HasBorrowed.GiveBackDate IS NULL
                """;

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            return Observe2(rs);
        }
    }

    public static ObservableList<HasBorrowed> searchBorrowedBooksByMe(String myLogin)
            throws SQLException, ClassNotFoundException {
        String query = """
                SELECT * FROM User
                JOIN HasBorrowed ON User.Login = HasBorrowed.BorrowerLogin
                JOIN Item ON Item.CopyID = HasBorrowed.BookCopyId
                WHERE HasBorrowed.BorrowerLogin = ?
                """;

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, myLogin);

            try (ResultSet rs = stmt.executeQuery()) {
                return Observe2(rs);
            }
        }
    }

    private static ObservableList<HasBorrowed> Observe2(ResultSet rs) throws SQLException {
        ObservableList<HasBorrowed> hasBorroweds = FXCollections.observableArrayList();

        while (rs.next()) {
            HasBorrowed hasBorrowed = new HasBorrowed();
            hasBorrowed.setBookCopyId(rs.getInt("BookCopyId"));
            hasBorrowed.setBookTitle(rs.getString("BookTitle"));
            hasBorrowed.setUserEmail(rs.getString("EmailAddress"));
            hasBorrowed.setLastName(rs.getString("LastName"));
            hasBorrowed.setFirstName(rs.getString("FirstName"));
            hasBorrowed.setLimitDate(String.valueOf(rs.getDate("LimitDate")));
            hasBorrowed.setGiveBackDate(String.valueOf(rs.getDate("GiveBackDate")));

            hasBorroweds.add(hasBorrowed);
        }
        return hasBorroweds;
    }

    public static boolean checkBookCopyExistence(Integer CopyId) throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(CopyId) FROM Item WHERE CopyId = ?";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, CopyId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) != 0;
                }
            }
        }
        return false;
    }

    public static boolean checkForLend(Integer CopyId) throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(BookCopyId) FROM HasBorrowed WHERE BookCopyId = ? AND GiveBackDate IS NULL";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, CopyId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) == 0;
            }
        }
    }

    public static void updateBorrowedBooks(Integer CopyId, String borrowerLogin)
            throws SQLException, ClassNotFoundException {
        String category = null;
        String queryCategory = "SELECT Category FROM User WHERE Login = ?";
        try (Connection con = getConn();
                PreparedStatement stmtCategory = con.prepareStatement(queryCategory)) {

            stmtCategory.setString(1, borrowerLogin);

            try (ResultSet rs2 = stmtCategory.executeQuery()) {
                if (rs2.next()) {
                    category = rs2.getString(1);
                }
            }
        }

        Integer maxBooks = new UserCategory().getMaxBooks(category);
        Integer maxDays = new UserCategory().getMaxDays(category);

        int everBorrowed = 0;
        String queryCountBorrowed = "SELECT COUNT(BorrowerLogin) FROM HasBorrowed WHERE BorrowerLogin = ?";
        try (Connection con = getConn();
                PreparedStatement stmtCount = con.prepareStatement(queryCountBorrowed)) {

            stmtCount.setString(1, borrowerLogin);

            try (ResultSet rs = stmtCount.executeQuery()) {
                if (rs.next()) {
                    everBorrowed = rs.getInt(1);
                }
            }
        }

        if (maxBooks > everBorrowed) {
            LocalDate borrowingDate = LocalDate.now();
            LocalDate limitDate = borrowingDate.plusDays(maxDays);

            String queryInsert = "INSERT INTO HasBorrowed (BookCopyId, BorrowerLogin, BorrowingDate, LimitDate) VALUES (?, ?, ?, ?)";
            try (Connection con = getConn();
                    PreparedStatement stmtInsert = con.prepareStatement(queryInsert)) {

                stmtInsert.setInt(1, CopyId);
                stmtInsert.setString(2, borrowerLogin);
                stmtInsert.setDate(3, Date.valueOf(borrowingDate));
                stmtInsert.setDate(4, Date.valueOf(limitDate));

                stmtInsert.executeUpdate();
            }
        }
    }

    public static void updateReturnDate(Integer CopyId, String borrowerLogin, LocalDate GiveBackDate)
            throws SQLException, ClassNotFoundException {
        String query = "UPDATE HasBorrowed SET GiveBackDate = ? WHERE BookCopyId = ? AND BorrowerLogin = ?";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setDate(1, Date.valueOf(GiveBackDate));
            stmt.setInt(2, CopyId);
            stmt.setString(3, borrowerLogin);

            stmt.executeUpdate();
        }
    }

}
