package uet.librarymanagementsystem.services.documentServices;

import uet.librarymanagementsystem.DatabaseOperation.AuthorTable;
import uet.librarymanagementsystem.DatabaseOperation.DocumentDO;
import uet.librarymanagementsystem.DatabaseOperation.UserDO;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;

public class TestAddDoc {
    public static void main(String[] args) {
        AddDocumentService addoc = new AddDocumentService();
        addoc.addDocument("Anarchism And Ecology", "Graham Purchase", "BOOK", "NON_FICTION");
        addoc.addDocument("The Canal House", "Mark Lee", "BOOK", "FICTION");

    }
}
