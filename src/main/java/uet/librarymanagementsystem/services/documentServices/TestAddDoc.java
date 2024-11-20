package uet.librarymanagementsystem.services.documentServices;

import uet.librarymanagementsystem.DatabaseOperation.AuthorTable;
import uet.librarymanagementsystem.DatabaseOperation.DocumentDO;
import uet.librarymanagementsystem.DatabaseOperation.UserDO;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;

public class TestAddDoc {
    public static void main(String[] args) {
        AddDocumentService addoc = new AddDocumentService();
        addoc.addDocument("Fashion Sourcebook 1920s", "Charlotte Fiell", "BOOK", "ART", "1906863482");
        addoc.addDocument("Hungary 56", "Andy Anderson", "BOOK", "HISTORY", "948984147");
        addoc.addDocument("All-American Anarchist", "Carlotta R. Anderson", "BOOK", "HISTORY", "814327079");
        addoc.addDocument("The Human Equation", "Jeffrey Pfeffer", "BOOK", "NON_FICTION", "875848419");
        addoc.addDocument("Hawaii: An Uncommon History", "Edward Joesting", "BOOK", "HISTORY", "393009076");
        addoc.addDocument("Genuine Happiness", "B. Alan Wallace", "BOOK", "PHILOSOPHY", "047146984X");
        addoc.addDocument("Anthropological Studies of Religion", "Brian Morris", "BOOK", "RELIGION", "052133991X");
        addoc.addDocument("Anarchism And Ecology", "Graham Purchase", "BOOK", "NON_FICTION", "1551640260");
        addoc.addDocument("Happiness: Lessons from a New Science", "Richard Layard", "BOOK", "SELF_HELP", "143037013");
        addoc.addDocument("Dressed to Rule", "Philip Mansel", "BOOK", "HISTORY", "300106971");
        addoc.addDocument("After Long Silence", "Helen Fremont", "BOOK", "BIOGRAPHY", "385333706");
        addoc.addDocument("The Principles of Psychology: Volume 1", "William James", "BOOK", "SCIENCE", "486203816");
        addoc.addDocument("Islands", "Dan Sleigh", "BOOK", "HISTORY", "015101115X");
        addoc.addDocument("Character Strengths and Virtues", "Christopher Peterson", "BOOK", "SELF_HELP", "195167015");
        addoc.addDocument("Parnassus on Wheels", "Christopher Morley", "BOOK", "FICTION", "1414270658");
        addoc.addDocument("The Accidental President of Brazil", "Fernando Henrique Cardoso", "BOOK", "BIOGRAPHY", "1586483242");
        addoc.addDocument("The Shining Path", "Gustavo Gorriti", "BOOK", "NON_FICTION", "807846767");
        addoc.addDocument("Irrational Exuberance", "Robert J. Shiller", "BOOK", "NON_FICTION", "767923634");
        addoc.addDocument("Jane Austen and the War of Ideas", "Marilyn Butler", "BOOK", "NON_FICTION", "198129688");
        addoc.addDocument("The Canal House", "Mark Lee", "BOOK", "FICTION", "162095545");
        addoc.addDocument("Toward Rational Exuberance", "B. Mark Smith", "BOOK", "NON_FICTION", "374528500");

    }
}
