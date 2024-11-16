package uet.librarymanagementsystem.entity.documents;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MaterialTypeTest {
    @Test
    public void testGetCategoriesForMaterial() {
        // test có tồn tại 1 số category của bôk không
        ObservableList<String> bookCategories = MaterialType.getCategoriesForMaterial(MaterialType.BOOK);
        assertTrue(bookCategories.contains("FICTION"));
        assertTrue(bookCategories.contains("SCIENCE"));

        // test có tồn tại 1 số category của journal không
        ObservableList<String> journalCategories = MaterialType.getCategoriesForMaterial(MaterialType.JOURNAL);
        assertTrue(journalCategories.contains("REVIEW"));
        assertTrue(journalCategories.contains("CASE_STUDY"));

        // test có tồn tại 1 số category của weather không
        ObservableList<String> newspaperCategories = MaterialType.getCategoriesForMaterial(MaterialType.NEWSPAPER);
        assertTrue(newspaperCategories.contains("WEATHER"));

        // test có tồn tại 1 số category của thesis không
        ObservableList<String> thesisCategories = MaterialType.getCategoriesForMaterial(MaterialType.THESIS);
        assertTrue(thesisCategories.contains("ENVIRONMENT"));
    }
}