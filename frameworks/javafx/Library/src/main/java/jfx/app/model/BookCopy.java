package jfx.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookCopy extends Book {

    private IntegerProperty copyId = new SimpleIntegerProperty();
    private StringProperty editorName = new SimpleStringProperty();

    private User borrower = new User();

    public BookCopy() {

    }

    public int getCopyId() {
        return copyId.get();
    }

    public IntegerProperty copyIdProperty() {
        return copyId;
    }

    public void setCopyId(int copyId) {
        this.copyId.set(copyId);
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public String getEditorName() {
        return editorName.get();
    }

    public StringProperty editorNameProperty() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName.set(editorName);
    }

}
