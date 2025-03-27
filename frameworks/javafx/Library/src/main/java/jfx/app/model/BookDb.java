package jfx.app.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookDb {

    // Add a new book
    public static boolean checkAuthorExistence(String AuthorName, Integer AuthorBirthDate)
            throws SQLException, ClassNotFoundException {
        ResultSet rs = DbUtils.dbExecuteQuery(" SELECT COUNT(1) FROM Author WHERE AuthorName = '" + AuthorName
                + "' AND BirthDate = " + AuthorBirthDate + "");
        if (rs.next()) {
            return rs.getInt(1) == 1;
        }
        return false;
    }

    public static void AddAuthor(String Name, Integer BirthYear) throws SQLException, ClassNotFoundException {
        DbUtils.dbExecuteUpdate("""
                INSERT INTO Author (AuthorName, Birthdate)
                VALUES ('""" + Name + "'," + BirthYear + ");");
    }

    public static int getAuthorId(String Name, Integer BirthYear) throws SQLException, ClassNotFoundException {
        ResultSet rs = DbUtils
                .dbExecuteQuery("SELECT Id FROM Author WHERE AuthorName = '" + Name + "' AND BirthDate = " + BirthYear);
        int id = 0;
        if (rs.next()) {
            id = rs.getInt("Id");
        }
        return id;
    }

    public static boolean checkBookExistence(String title, String firstEdition)
            throws SQLException, ClassNotFoundException {
        ResultSet rs1 = DbUtils.dbExecuteQuery("""
                SELECT COUNT(1) FROM Book
                HERE Title ='""" + title + "' AND FirstYearEdition = " + "'" + firstEdition + "'");

        if (rs1.next() && !Objects.equals(title, "") && !Objects.equals(firstEdition, "")) {
            return (rs1.getInt(1) == 1);
        }
        return false;
    }

    public static void AddBook(String title, String firstEdition, String description)
            throws SQLException, ClassNotFoundException {
        DbUtils.dbExecuteUpdate("""
                INSERT INTO Book (Title, FirstYearEdition, Description)
                VALUES ('""" + title + "'," + firstEdition + ",'" + description + "');");
    }

    public static boolean checkEditorExistence(String EditorISBN) throws SQLException, ClassNotFoundException {
        ResultSet rs = DbUtils.dbExecuteQuery(" SELECT COUNT(1) FROM Editor WHERE ISBN = '" + EditorISBN + "'");
        if (rs.next()) {
            return rs.getInt(1) == 1;
        }
        return false;
    }

    public static void AddEditor(String EditorName, String EditorISBN) throws SQLException, ClassNotFoundException {
        DbUtils.dbExecuteUpdate("""
                INSERT INTO Editor (EditorName, ISBN)
                VALUES ('""" + EditorName + "','" + EditorISBN + "');");
    }

    public static boolean checkHasWrittenExistence(String BookTitle, Integer BookFirstYearEdition, int AuthorId)
            throws SQLException, ClassNotFoundException {
        ResultSet rs = DbUtils.dbExecuteQuery("""
                SELECT COUNT(1) FROM HasWritten
                WHERE BookTitle ='""" + BookTitle + "' AND BookFirstYearEdition = " + BookFirstYearEdition
                + " AND AuthorId = " + AuthorId + "");
        if (rs.next()) {
            return rs.getInt(1) == 1;
        }
        return false;
    }

    public static void updateHasWritten(String BookTitle, Integer BookFirstYearEdition, int AuthorId)
            throws SQLException, ClassNotFoundException {
        DbUtils.dbExecuteUpdate("""
                INSERT INTO HasWritten (BookTitle, BookFirstYearEdition, AuthorId)
                VALUES ('""" + BookTitle + "'," + BookFirstYearEdition + "," + AuthorId + ")");
    }

    public static void AddBookCopy(String CopyId, String BookTitle, Integer BookFirstYearEdition, String EditorISBN)
            throws SQLException, ClassNotFoundException {
        DbUtils.dbExecuteUpdate("""
                INSERT INTO BookCopy (CopyId, BookTitle, BookFirstYearEdition, EditorISBN)
                VALUES ('""" + CopyId + "','" + BookTitle + "'," + BookFirstYearEdition + ",'" + EditorISBN + "');");
    }

    // Search book
    public static BookCopy searchBook(String bookTitle, String bookAuthor, String bookEditor)
            throws SQLException, ClassNotFoundException {
        if (!Objects.equals(bookTitle, "")) {
            ResultSet rs = DbUtils.dbExecuteQuery(
                    """
                            SELECT BookCopy.BookTitle, AuthorName, EditorName, Description, CopyId FROM BookCopy
                            JOIN Book ON Book.Title = BookCopy.BookTitle AND Book.FirstYearEdition = BookCopy.BookFirstYearEdition
                            JOIN HasWritten ON Book.Title = HasWritten.BookTitle AND Book.FirstYearEdition = HasWritten.BookFirstYearEdition
                            JOIN Author ON Author.Id = HasWritten.AuthorId
                            JOIN Editor ON Editor.ISBN = BookCopy.EditorISBN
                            WHERE BookCopy.BookTitle ='"""
                            + bookTitle + "'" + "ORDER BY BookTitle DESC ");
            return Observe0(rs);
        }

        if (!Objects.equals(bookAuthor, "")) {
            ResultSet rs = DbUtils.dbExecuteQuery(
                    """
                            SELECT BookCopy.BookTitle, AuthorName, EditorName, Description, CopyId FROM BookCopy
                            JOIN Book ON Book.Title = BookCopy.BookTitle AND Book.FirstYearEdition = BookCopy.BookFirstYearEdition
                            JOIN HasWritten ON Book.Title = HasWritten.BookTitle AND Book.FirstYearEdition = HasWritten.BookFirstYearEdition
                            JOIN Author ON Author.Id = HasWritten.AuthorId
                            JOIN Editor ON Editor.ISBN = BookCopy.EditorISBN
                            WHERE Author.AuthorName ='"""
                            + bookAuthor + "'" + "ORDER BY BookTitle DESC ");
            return Observe0(rs);
        }

        if (!Objects.equals(bookEditor, "")) {
            ResultSet rs = DbUtils.dbExecuteQuery(
                    """
                            SELECT BookCopy.BookTitle, AuthorName, EditorName, Description, CopyId FROM BookCopy
                            JOIN Book ON Book.Title = BookCopy.BookTitle AND Book.FirstYearEdition = BookCopy.BookFirstYearEdition
                            JOIN HasWritten ON Book.Title = HasWritten.BookTitle AND Book.FirstYearEdition = HasWritten.BookFirstYearEdition
                            JOIN Author ON Author.Id = HasWritten.AuthorId
                            JOIN Editor ON Editor.ISBN = BookCopy.EditorISBN
                            WHERE Editor.EditorName ='"""
                            + bookEditor + "'" + "ORDER BY BookTitle DESC ");
            return Observe0(rs);
        }
        return null;
    }

    private static BookCopy Observe0(ResultSet rs) throws SQLException {
        BookCopy bookCopy = new BookCopy();
        while (rs.next()) {
            bookCopy.setBookTitle(rs.getString("BookTitle"));
            bookCopy.setAuthorName(rs.getString("AuthorName"));
            bookCopy.setEditorName(rs.getString("EditorName"));
            bookCopy.setDescription(rs.getString("Description"));
            bookCopy.setCopyId(rs.getInt("CopyId"));

        }
        return bookCopy;
    }

    public static boolean checkBookExistence(String bookTitle, String bookAuthor, String bookEditor)
            throws SQLException, ClassNotFoundException {
        ResultSet rs1 = DbUtils.dbExecuteQuery(bookTitle + """
                SELECT COUNT(1) FROM BookCopy
                WHERE BookCopy.BookTitle ='""" + "'");

        ResultSet rs2 = DbUtils.dbExecuteQuery(
                """
                        SELECT COUNT(1) FROM BookCopy
                        JOIN Book ON Book.Title = BookCopy.BookTitle AND Book.FirstYearEdition = BookCopy.BookFirstYearEdition
                        JOIN HasWritten ON Book.Title = HasWritten.BookTitle AND Book.FirstYearEdition = HasWritten.BookFirstYearEdition
                        JOIN Author ON Author.Id = HasWritten.AuthorId
                        WHERE Author.AuthorName ='"""
                        + bookAuthor + "'");

        ResultSet rs3 = DbUtils.dbExecuteQuery("""
                SELECT COUNT(1) FROM BookCopy
                JOIN Book ON Book.Title = BookCopy.BookTitle AND Book.FirstYearEdition = BookCopy.BookFirstYearEdition
                JOIN Editor ON Editor.ISBN = BookCopy.EditorISBN
                WHERE Editor.EditorName ='""" + bookEditor + "'");

        if (rs1.next() && !Objects.equals(bookTitle, "")) {
            return (rs1.getInt(1) == 1);
        }

        if (rs2.next() && !Objects.equals(bookAuthor, "")) {

            return (rs2.getInt(1) == 1);
        }

        if (rs3.next() && !Objects.equals(bookEditor, "")) {
            return (rs3.getInt(1) == 1);
        }
        return false;
    }

    // Search book
    public static BookCopy searchBook1(String bookTitle, String bookAuthor, String bookEditor)
            throws SQLException, ClassNotFoundException {
        if (!Objects.equals(bookTitle, "")) {
            ResultSet rs = DbUtils.dbExecuteQuery(
                    """
                            SELECT BookCopy.BookTitle, AuthorName, EditorName, Description FROM BookCopy
                            JOIN Book ON Book.Title = BookCopy.BookTitle AND Book.FirstYearEdition = BookCopy.BookFirstYearEdition
                            JOIN HasWritten ON Book.Title = HasWritten.BookTitle AND Book.FirstYearEdition = HasWritten.BookFirstYearEdition
                            JOIN Author ON Author.Id = HasWritten.AuthorId
                            JOIN Editor ON Editor.ISBN = BookCopy.EditorISBN
                            WHERE BookCopy.BookTitle ='"""
                            + bookTitle + "'" + "ORDER BY BookTitle DESC ");
            return Observe1(rs);
        }

        if (!Objects.equals(bookAuthor, "")) {
            ResultSet rs = DbUtils.dbExecuteQuery(
                    """
                            SELECT BookCopy.BookTitle, AuthorName, EditorName, Description FROM BookCopy
                            JOIN Book ON Book.Title = BookCopy.BookTitle AND Book.FirstYearEdition = BookCopy.BookFirstYearEdition
                            JOIN HasWritten ON Book.Title = HasWritten.BookTitle AND Book.FirstYearEdition = HasWritten.BookFirstYearEdition
                            JOIN Author ON Author.Id = HasWritten.AuthorId
                            JOIN Editor ON Editor.ISBN = BookCopy.EditorISBN
                            WHERE Author.AuthorName ='"""
                            + bookAuthor + "'" + "ORDER BY BookTitle DESC ");
            return Observe1(rs);
        }

        if (!Objects.equals(bookEditor, "")) {
            ResultSet rs = DbUtils.dbExecuteQuery(
                    """
                            SELECT BookCopy.BookTitle, AuthorName, EditorName, Description FROM BookCopy
                            JOIN Book ON Book.Title = BookCopy.BookTitle AND Book.FirstYearEdition = BookCopy.BookFirstYearEdition
                            JOIN HasWritten ON Book.Title = HasWritten.BookTitle AND Book.FirstYearEdition = HasWritten.BookFirstYearEdition
                            JOIN Author ON Author.Id = HasWritten.AuthorId
                            JOIN Editor ON Editor.ISBN = BookCopy.EditorISBN
                            WHERE Editor.EditorName ='"""
                            + bookEditor + "'" + "ORDER BY BookTitle DESC ");
            return Observe1(rs);
        }
        return null;
    }

    private static BookCopy Observe1(ResultSet rs) throws SQLException {
        BookCopy bookCopy = new BookCopy();
        while (rs.next()) {
            bookCopy.setBookTitle(rs.getString("BookTitle"));
            bookCopy.setAuthorName(rs.getString("AuthorName"));
            bookCopy.setEditorName(rs.getString("EditorName"));
            bookCopy.setDescription(rs.getString("Description"));

        }
        return bookCopy;
    }

    // Check available book
    public static ObservableList<BookCopy> searchAvailableBooks() throws SQLException, ClassNotFoundException {
        ResultSet rs = DbUtils.dbExecuteQuery(
                """
                        SELECT BookCopy.BookTitle, AuthorName, EditorName, Description, CopyId FROM BookCopy
                        JOIN Book ON Book.Title = BookCopy.BookTitle AND Book.FirstYearEdition = BookCopy.BookFirstYearEdition
                        JOIN HasWritten ON Book.Title = HasWritten.BookTitle AND Book.FirstYearEdition = HasWritten.BookFirstYearEdition
                        JOIN Author ON Author.Id = HasWritten.AuthorId
                        JOIN Editor ON Editor.ISBN = BookCopy.EditorISBN
                        ORDER BY BookTitle DESC
                        """);
        return getAvailableBooks(rs);
    }

    private static ObservableList<BookCopy> getAvailableBooks(ResultSet rs) throws SQLException {
        ObservableList<BookCopy> bookCopies;
        bookCopies = FXCollections.observableArrayList();

        while (rs.next()) {
            BookCopy bookCopy = new BookCopy();
            bookCopy.setBookTitle(rs.getString("BookTitle"));
            bookCopy.setAuthorName(rs.getString("AuthorName"));
            bookCopy.setEditorName(rs.getString("EditorName"));
            bookCopy.setDescription(rs.getString("Description"));
            bookCopy.setCopyId(rs.getInt("CopyId"));
            bookCopies.add(bookCopy);
        }
        return bookCopies;
    }

    public static ObservableList<BookCopy> searchAvailableBooks2() throws SQLException, ClassNotFoundException {
        ResultSet rs = DbUtils.dbExecuteQuery(
                """
                        SELECT DISTINCT BookCopy.BookTitle, AuthorName, EditorName, Description FROM BookCopy
                        JOIN Book ON Book.Title = BookCopy.BookTitle AND Book.FirstYearEdition = BookCopy.BookFirstYearEdition
                        JOIN HasWritten ON Book.Title = HasWritten.BookTitle AND Book.FirstYearEdition = HasWritten.BookFirstYearEdition
                        JOIN Author ON Author.Id = HasWritten.AuthorId
                        JOIN Editor ON Editor.ISBN = BookCopy.EditorISBN
                        ORDER BY BookTitle DESC
                        """);
        return getAvailableBooks2(rs);
    }

    private static ObservableList<BookCopy> getAvailableBooks2(ResultSet rs) throws SQLException {
        ObservableList<BookCopy> bookCopies;
        bookCopies = FXCollections.observableArrayList();

        while (rs.next()) {
            BookCopy bookCopy = new BookCopy();
            bookCopy.setBookTitle(rs.getString("BookTitle"));
            bookCopy.setAuthorName(rs.getString("AuthorName"));
            bookCopy.setEditorName(rs.getString("EditorName"));
            bookCopy.setDescription(rs.getString("Description"));
            bookCopies.add(bookCopy);
        }
        return bookCopies;
    }

    // Borrowed books
    public static ObservableList<HasBorrowed> searchBorrowedBooks() throws SQLException, ClassNotFoundException {
        ResultSet rs = DbUtils.dbExecuteQuery("""
                SELECT * FROM User
                JOIN HasBorrowed ON User.Login = HasBorrowed.BorrowerLogin
                JOIN BookCopy ON BookCopy.CopyID = HasBorrowed.BookCopyId
                WHERE HasBorrowed.GiveBackDate IS NULL""");
        return Observe2(rs);
    }

    public static ObservableList<HasBorrowed> searchBorrowedBooksByMe(String myLogin)
            throws SQLException, ClassNotFoundException {
        ResultSet rs = DbUtils.dbExecuteQuery("""
                SELECT * FROM User
                JOIN HasBorrowed ON User.Login = HasBorrowed.BorrowerLogin
                JOIN BookCopy ON BookCopy.CopyID = HasBorrowed.BookCopyId
                WHERE HasBorrowed.BorrowerLogin = '""" + myLogin + "'");
        return Observe2(rs);
    }

    private static ObservableList<HasBorrowed> Observe2(ResultSet rs) throws SQLException {
        ObservableList<HasBorrowed> hasBorroweds;
        hasBorroweds = FXCollections.observableArrayList();

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
        ResultSet rs = DbUtils.dbExecuteQuery(" SELECT COUNT(CopyId) FROM BookCopy WHERE CopyId = '" + CopyId + "'");
        if (rs.next()) {
            int nb = rs.getInt(1);
            return !(nb == 0);
        }
        return true;
    }

    public static boolean checkForLend(Integer CopyId) throws SQLException, ClassNotFoundException {
        ResultSet rs = DbUtils.dbExecuteQuery(" SELECT COUNT(BookCopyId) FROM HasBorrowed WHERE BookCopyId = '" + CopyId
                + "' AND GiveBackDate IS NULL");
        if (rs.next()) {
            int nb = rs.getInt(1);
            // System.out.println(nb);
            return (nb == 0);
        }
        return true;
    }

    public static void updateBorrowedBooks(Integer CopyId, String borrowerLogin)
            throws SQLException, ClassNotFoundException {
        String category = null;
        ResultSet rs2 = DbUtils.dbExecuteQuery(" SELECT Category FROM User WHERE Login = '" + borrowerLogin + "'");

        if (rs2.next()) {
            category = rs2.getString(1);
        }

        Integer maxBooks = new UserCategory().getMaxBooks(category);
        Integer maxDays = new UserCategory().getMaxDays(category);

        int EverBorrowed = 0;

        ResultSet rs = DbUtils.dbExecuteQuery(
                " SELECT COUNT(BorrowerLogin) FROM HasBorrowed WHERE BorrowerLogin = '" + borrowerLogin + "'");

        if (rs.next()) {
            EverBorrowed = rs.getInt(1);
        }

        if (maxBooks > EverBorrowed) {
            LocalDate borrowingDate = LocalDate.now();
            LocalDate LimitDate = borrowingDate.plusDays(maxDays);

            DbUtils.dbExecuteUpdate("""
                    INSERT INTO HasBorrowed (BookCopyId, BorrowerLogin, BorrowingDate, LimitDate)
                    VALUES ('""" + CopyId + "','" + borrowerLogin + "','" + borrowingDate + "','" + LimitDate + "')");
        }
    }

    public static void updateReturnDate(Integer CopyId, String borrowerLogin, LocalDate GiveBackDate)
            throws SQLException, ClassNotFoundException {
        DbUtils.dbExecuteUpdate("UPDATE HasBorrowed SET GiveBackDate = '" + GiveBackDate + "' WHERE BookCopyId = "
                + CopyId + " AND BorrowerLogin = '" + borrowerLogin + "'");
    }

}
