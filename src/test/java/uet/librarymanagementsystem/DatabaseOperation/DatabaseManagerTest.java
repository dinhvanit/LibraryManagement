package uet.librarymanagementsystem.DatabaseOperation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseManagerTest {
    @Test
    void testConnection() {
        assertDoesNotThrow(DatabaseManager::connect, "Connection to SQLite should not throw exception.");
    }

    @Test
    void testPrintTableColumns() {
        assertDoesNotThrow(() -> DatabaseManager.printTableColumns("Document"),
                "Printing columns for a valid table should not throw an exception.");
    }

    @Test
    void testDropTableMaterial() {
        assertDoesNotThrow(DatabaseManager::dropTableMaterial,
                "Dropping Material table should not throw an exception if it exists.");
    }

    @Test
    void testDropTableCategory() {
        assertDoesNotThrow(DatabaseManager::dropTableCategory,
                "Dropping Category table should not throw an exception if it exists.");
    }
}