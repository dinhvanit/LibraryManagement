package uet.librarymanagementsystem.entity.documents;

import org.junit.jupiter.api.Test;
import uet.librarymanagementsystem.entity.documents.materials.Book;
import uet.librarymanagementsystem.entity.documents.materials.Journal;
import uet.librarymanagementsystem.entity.documents.materials.Newspaper;
import uet.librarymanagementsystem.entity.documents.materials.Thesis;

import static org.junit.jupiter.api.Assertions.*;

class DocumentFactoryTest {
    @Test
    public void testCreateBook() {
        // Giả sử thể loại "TEXTBOOK" là hợp lệ trong enum BookCategory
        Document book = DocumentFactory.createDocument("1", "The Human Equation: Building Profits by Putting People First", "Jeffrey Pfeffer", "BOOK", "HISTORY", "875848419");

        assertNotNull(book);
        assertTrue(book instanceof Book);
        Book createdBook = (Book) book;
        assertEquals("The Human Equation: Building Profits by Putting People First", createdBook.getTitle());
        assertEquals("Jeffrey Pfeffer", createdBook.getAuthor());
        assertEquals("HISTORY", createdBook.getCategory());
        assertEquals("875848419", createdBook.getIsbn());
    }

    @Test
    public void testCreateJournal() {
        Document journal = DocumentFactory.createDocument("2", "Programming", "Dinh Van", "JOURNAL", "RESEARCH", null);

        assertNotNull(journal);
        assertTrue(journal instanceof Journal);
        Journal createdJournal = (Journal) journal;
        assertEquals("Programming", createdJournal.getTitle());
        assertEquals("Dinh Van", createdJournal.getAuthor());
        assertEquals("RESEARCH", createdJournal.getCategory());
    }

    @Test
    public void testCreateNewspaper() {
        Document newspaper = DocumentFactory.createDocument("3", "Thoi tiet mien Bac", "Dinh Van", "NEWSPAPER", "WEATHER", null);

        assertNotNull(newspaper);
        assertTrue(newspaper instanceof Newspaper);
        Newspaper createdNewspaper = (Newspaper) newspaper;
        assertEquals("Thoi tiet mien Bac", createdNewspaper.getTitle());
        assertEquals("Dinh Van", createdNewspaper.getAuthor());
        assertEquals("WEATHER", createdNewspaper.getCategory());
    }

    @Test
    public void testCreateThesis() {
        Document thesis = DocumentFactory.createDocument("4", "OOP project", "Dinh Van", "THESIS", "COMPUTER_SCIENCE", null);

        assertNotNull(thesis);
        assertTrue(thesis instanceof Thesis);
        Thesis createdThesis = (Thesis) thesis;
        assertEquals("OOP project", createdThesis.getTitle());
        assertEquals("Dinh Van", createdThesis.getAuthor());
        assertEquals("COMPUTER_SCIENCE", createdThesis.getCategory());
    }

    @Test
    public void testInvalidMaterialType() {
        assertThrows(IllegalArgumentException.class, () -> {
            DocumentFactory.createDocument("5", "Invalid Book", "Unknown Author", "INVALID_MATERIAL", "TEXTBOOK", "12345");
        });
    }

    @Test
    public void testInvalidCategoryForBook() {
        assertThrows(IllegalArgumentException.class, () -> {
            DocumentFactory.createDocument("6", "Invalid Book Category", "Dinh Van", "BOOK", "INVALID_CATEGORY", "978-1234567890");
        });
    }
}