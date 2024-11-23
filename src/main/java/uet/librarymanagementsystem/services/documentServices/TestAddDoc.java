package uet.librarymanagementsystem.services.documentServices;

import uet.librarymanagementsystem.DatabaseOperation.AuthorTable;
import uet.librarymanagementsystem.DatabaseOperation.DocumentDO;
import uet.librarymanagementsystem.DatabaseOperation.UserDO;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;

public class TestAddDoc {
    public static void main(String[] args) {
        AddDocumentService addoc = new AddDocumentService();
        addoc.addDocument("Islands", "Dan Sleigh", "BOOK", "HISTORY", "015101115X");
        addoc.addDocument("Bad Blood: Secrets and Lies in a Silicon Valley Startup", "John Carreyrou", "BOOK", "NON_FICTION", "9780525431992");
        addoc.addDocument("Educated", "Tara Westover", "BOOK", "NON_FICTION", "9780399590504");
        addoc.addDocument("The Wright Brothers", "David McCullough", "BOOK", "NON_FICTION", "9781476728759");
        addoc.addDocument("The Immortal Life of Henrietta Lacks", "Rebecca Skloot", "BOOK", "NON_FICTION", "9781400052189");
        addoc.addDocument("Into the Wild", "Jon Krakauer", "BOOK", "NON_FICTION", "9780385486805");

        addoc.addDocument("Long Walk to Freedom", "Nelson Mandela", "BOOK", "BIOGRAPHY", "9780316548182");
        addoc.addDocument("I Am Malala", "Malala Yousafzai", "BOOK", "BIOGRAPHY", "9780316322423");
        addoc.addDocument("Becoming", "Michelle Obama", "BOOK", "BIOGRAPHY", "9781524763138");
        addoc.addDocument("Steve Jobs", "Walter Isaacson", "BOOK", "BIOGRAPHY", "9781451648539");
        addoc.addDocument("Alexander Hamilton", "Ron Chernow", "BOOK", "BIOGRAPHY", "9780143034759");

        addoc.addDocument("Team of Rivals", "Doris Kearns Goodwin", "BOOK", "HISTORY", "9780743270755");
    }
}
