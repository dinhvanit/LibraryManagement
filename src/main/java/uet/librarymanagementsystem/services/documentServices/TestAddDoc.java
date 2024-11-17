package uet.librarymanagementsystem.services.documentServices;

import uet.librarymanagementsystem.DatabaseOperation.AuthorTable;
import uet.librarymanagementsystem.DatabaseOperation.DocumentDO;
import uet.librarymanagementsystem.DatabaseOperation.UserDO;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;

public class TestAddDoc {
    public static void main(String[] args) {
        AddDocumentService addoc = new AddDocumentService();
        addoc.addDocument("Anarchism And Ecology", "Graham Purchase", "BOOK", "NON_FICTION", "0000001");
        addoc.addDocument("The Canal House", "Mark Lee", "BOOK", "FICTION", "1234567890");
        addoc.addDocument("bao buoi sang", "Dinh Van", "NEWSPAPER", "TECHNOLOGY", "null");

    }
}
