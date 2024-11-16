package uet.librarymanagementsystem.entity.documents;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uet.librarymanagementsystem.entity.documents.materials.Book;
import uet.librarymanagementsystem.entity.documents.materials.Journal;
import uet.librarymanagementsystem.entity.documents.materials.Newspaper;
import uet.librarymanagementsystem.entity.documents.materials.Thesis;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum MaterialType {
    //khoi tao cac loai material cua tung document, khoi tao ca id dua vao material cho document o day
    BOOK("01"),
    JOURNAL("02"),
    NEWSPAPER("03"),
    THESIS("04");

    //OTHER;

    private final String code;

    MaterialType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ObservableList<String> getCategoriesForMaterial(MaterialType type) {
        ObservableList<String> categories = FXCollections.observableArrayList();
        switch (type) {
            case BOOK -> categories.addAll(Arrays.asList(Book.BookCategory.values()).stream().map(Enum::name).collect(Collectors.toList()));
            case JOURNAL -> categories.addAll(Arrays.asList(Journal.JournalCategory.values()).stream().map(Enum::name).collect(Collectors.toList()));
            case THESIS -> categories.addAll(Arrays.asList(Thesis.ThesisCategory.values()).stream().map(Enum::name).collect(Collectors.toList()));
            case NEWSPAPER -> categories.addAll(Arrays.asList(Newspaper.NewspaperCategory.values()).stream().map(Enum::name).collect(Collectors.toList()));
        }
        return categories;
    }
}
