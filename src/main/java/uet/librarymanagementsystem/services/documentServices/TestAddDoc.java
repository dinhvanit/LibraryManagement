package uet.librarymanagementsystem.services.documentServices;

import uet.librarymanagementsystem.DatabaseOperation.AuthorTable;
import uet.librarymanagementsystem.DatabaseOperation.DocumentDO;
import uet.librarymanagementsystem.DatabaseOperation.UserDO;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;

public class TestAddDoc {
    public static void main(String[] args) {
        AddDocumentService addoc = new AddDocumentService();
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
        addoc.addDocument("The Fellowship of the Ring", "J.R.R. Tolkien", "BOOK", "FICTION", "9780261103573");
        addoc.addDocument("The Two Towers", "J.R.R. Tolkien", "BOOK", "FICTION", "9780261102361");
        addoc.addDocument("The Return of the King", "J.R.R. Tolkien", "BOOK", "FICTION", "9780261102385");
        addoc.addDocument("The Hobbit", "J.R.R. Tolkien", "BOOK", "FICTION", "9780547928227");
        addoc.addDocument("The Silmarillion", "J.R.R. Tolkien", "BOOK", "FICTION", "9780261102736");
        addoc.addDocument("Unfinished Tales", "J.R.R. Tolkien", "BOOK", "FICTION", "9780261103627");
        addoc.addDocument("The Children of Húrin", "J.R.R. Tolkien", "BOOK", "FICTION", "9780547016413");
        addoc.addDocument("Beren and Lúthien", "J.R.R. Tolkien", "BOOK", "FICTION", "9780544763136");
        addoc.addDocument("The History of Middle-earth (Volume 1)", "J.R.R. Tolkien", "BOOK", "FICTION", "9780261102026");
        addoc.addDocument("The History of Middle-earth (Volume 2)", "J.R.R. Tolkien", "BOOK", "FICTION", "9780261102033");
        addoc.addDocument("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "BOOK", "FICTION", "9780590353427");
        addoc.addDocument("Harry Potter and the Chamber of Secrets", "J.K. Rowling", "BOOK", "FICTION", "9780439064873");
        addoc.addDocument("Harry Potter and the Prisoner of Azkaban", "J.K. Rowling", "BOOK", "FICTION", "9780439136365");
        addoc.addDocument("Harry Potter and the Goblet of Fire", "J.K. Rowling", "BOOK", "FICTION", "9780439139601");
        addoc.addDocument("Harry Potter and the Order of the Phoenix", "J.K. Rowling", "BOOK", "FICTION", "9780439358071");
        addoc.addDocument("Harry Potter and the Half-Blood Prince", "J.K. Rowling", "BOOK", "FICTION", "9780439785969");
        addoc.addDocument("Harry Potter and the Deathly Hallows", "J.K. Rowling", "BOOK", "FICTION", "9780545010221");
        addoc.addDocument("1984", "George Orwell", "BOOK", "FICTION", "9780451524935");
        addoc.addDocument("Animal Farm", "George Orwell", "BOOK", "FICTION", "9780451526342");
        addoc.addDocument("Brave New World", "Aldous Huxley", "BOOK", "FICTION", "9780060850524");
        addoc.addDocument("Fahrenheit 451", "Ray Bradbury", "BOOK", "FICTION", "9781451673319");
        addoc.addDocument("A Brief History of Time", "Stephen Hawking", "BOOK", "SCIENCE", "9780553380163");
        addoc.addDocument("The Art of War", "Sun Tzu", "BOOK", "PHILOSOPHY", "9781599869773");
        addoc.addDocument("The Selfish Gene", "Richard Dawkins", "BOOK", "SCIENCE", "9780199291151");
        addoc.addDocument("Sapiens: A Brief History of Humankind", "Yuval Noah Harari", "BOOK", "HISTORY", "9780062316097");
        addoc.addDocument("Thinking, Fast and Slow", "Daniel Kahneman", "BOOK", "SELF_HELP", "9780374533557");
        addoc.addDocument("The Elegant Universe", "Brian Greene", "BOOK", "SCIENCE", "9780393338102");
        addoc.addDocument("The Story of Art", "E.H. Gombrich", "BOOK", "ART", "9780714847030");
        addoc.addDocument("Cosmos", "Carl Sagan", "BOOK", "SCIENCE", "9780345539434");
        addoc.addDocument("On the Origin of Species", "Charles Darwin", "BOOK", "SCIENCE", "9781509827695");
        addoc.addDocument("The Diary of a Young Girl", "Anne Frank", "BOOK", "BIOGRAPHY", "9780553296983");
        addoc.addDocument("Steve Jobs", "Walter Isaacson", "BOOK", "BIOGRAPHY", "9781451648539");
        addoc.addDocument("Becoming", "Michelle Obama", "BOOK", "BIOGRAPHY", "9781524763138");
        addoc.addDocument("The Immortal Life of Henrietta Lacks", "Rebecca Skloot", "BOOK", "NON_FICTION", "9781400052189");
        addoc.addDocument("Guns, Germs, and Steel", "Jared Diamond", "BOOK", "HISTORY", "9780393317558");
        addoc.addDocument("The Power of Now", "Eckhart Tolle", "BOOK", "SELF_HELP", "9781577314806");
        addoc.addDocument("Man's Search for Meaning", "Viktor E. Frankl", "BOOK", "SELF_HELP", "9780807014271");
        addoc.addDocument("The Innovators", "Walter Isaacson", "BOOK", "TECHNOLOGY", "9781476708706");
        addoc.addDocument("The War of Art", "Steven Pressfield", "BOOK", "ART", "9781936891023");
        addoc.addDocument("Musicophilia: Tales of Music and the Brain", "Oliver Sacks", "BOOK", "MUSIC", "9781400033539");

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
        addoc.addDocument("The Guns of August", "Barbara W. Tuchman", "BOOK", "HISTORY", "9780345476098");
        addoc.addDocument("A People's History of the United States", "Howard Zinn", "BOOK", "HISTORY", "9780062397342");
        addoc.addDocument("The Silk Roads: A New History of the World", "Peter Frankopan", "BOOK", "HISTORY", "9781101912379");
        addoc.addDocument("Guns, Germs, and Steel", "Jared Diamond", "BOOK", "HISTORY", "9780393317558");

        addoc.addDocument("Astrophysics for People in a Hurry", "Neil deGrasse Tyson", "BOOK", "SCIENCE", "9780393609394");
        addoc.addDocument("The Gene: An Intimate History", "Siddhartha Mukherjee", "BOOK", "SCIENCE", "9781476733524");
        addoc.addDocument("Why We Sleep", "Matthew Walker", "BOOK", "SCIENCE", "9781501144318");
        addoc.addDocument("Silent Spring", "Rachel Carson", "BOOK", "SCIENCE", "9780618249060");
        addoc.addDocument("The Immortal Life of Henrietta Lacks", "Rebecca Skloot", "BOOK", "SCIENCE", "9781400052189");

        addoc.addDocument("The Innovators", "Walter Isaacson", "BOOK", "TECHNOLOGY", "9781476708706");
        addoc.addDocument("Code: The Hidden Language of Computer Hardware and Software", "Charles Petzold", "BOOK", "TECHNOLOGY", "9780735611313");
        addoc.addDocument("The Singularity Is Near", "Ray Kurzweil", "BOOK", "TECHNOLOGY", "9780143037880");
        addoc.addDocument("Algorithms to Live By", "Brian Christian and Tom Griffiths", "BOOK", "TECHNOLOGY", "9781250118363");
        addoc.addDocument("Life 3.0: Being Human in the Age of Artificial Intelligence", "Max Tegmark", "BOOK", "TECHNOLOGY", "9781101970317");

        addoc.addDocument("Ways of Seeing", "John Berger", "BOOK", "ART", "9780140135152");
        addoc.addDocument("The Story of Art", "E.H. Gombrich", "BOOK", "ART", "9780714847030");
        addoc.addDocument("The War of Art", "Steven Pressfield", "BOOK", "ART", "9781936891023");
        addoc.addDocument("Art & Fear", "David Bayles and Ted Orland", "BOOK", "ART", "9780961454739");
        addoc.addDocument("The Artist's Way", "Julia Cameron", "BOOK", "ART", "9780143129256");

        addoc.addDocument("This Is Your Brain on Music", "Daniel J. Levitin", "BOOK", "MUSIC", "9780452288522");
        addoc.addDocument("The Rest Is Noise: Listening to the Twentieth Century", "Alex Ross", "BOOK", "MUSIC", "9780312427719");
        addoc.addDocument("Musicophilia: Tales of Music and the Brain", "Oliver Sacks", "BOOK", "MUSIC", "9781400033539");
        addoc.addDocument("The Music Lesson", "Victor L. Wooten", "BOOK", "MUSIC", "9780425220931");
        addoc.addDocument("The History of Jazz", "Ted Gioia", "BOOK", "MUSIC", "9780195399707");

        addoc.addDocument("The Republic", "Plato", "BOOK", "PHILOSOPHY", "9780141442433");
        addoc.addDocument("Meditations", "Marcus Aurelius", "BOOK", "PHILOSOPHY", "9780140449334");
        addoc.addDocument("Being and Time", "Martin Heidegger", "BOOK", "PHILOSOPHY", "9780060638508");
        addoc.addDocument("Critique of Pure Reason", "Immanuel Kant", "BOOK", "PHILOSOPHY", "9780521657297");
        addoc.addDocument("The Art of War", "Sun Tzu", "BOOK", "PHILOSOPHY", "9781599869773");

        addoc.addDocument("The Bible", "Multiple Authors", "BOOK", "RELIGION", "9780310918394");
        addoc.addDocument("The Qur'an", "Multiple Translators", "BOOK", "RELIGION", "9780199535958");
        addoc.addDocument("The Bhagavad Gita", "Eknath Easwaran", "BOOK", "RELIGION", "9781586380199");
        addoc.addDocument("Mere Christianity", "C.S. Lewis", "BOOK", "RELIGION", "9780060652920");
        addoc.addDocument("The Tao of Pooh", "Benjamin Hoff", "BOOK", "RELIGION", "9780140067477");

        addoc.addDocument("How to Win Friends and Influence People", "Dale Carnegie", "BOOK", "SELF_HELP", "9780671027032");
        addoc.addDocument("Atomic Habits", "James Clear", "BOOK", "SELF_HELP", "9780735211292");
        addoc.addDocument("The 7 Habits of Highly Effective People", "Stephen R. Covey", "BOOK", "SELF_HELP", "9780743269513");
        addoc.addDocument("Think and Grow Rich", "Napoleon Hill", "BOOK", "SELF_HELP", "9781937879501");
        addoc.addDocument("The Power of Now", "Eckhart Tolle", "BOOK", "SELF_HELP", "9781577314806");

        addoc.addDocument("Sapiens: A Brief History of Humankind", "Yuval Noah Harari", "BOOK", "NON_FICTION", "9780062316097");
        addoc.addDocument("Homo Deus: A Brief History of Tomorrow", "Yuval Noah Harari", "BOOK", "NON_FICTION", "9780062464316");
        addoc.addDocument("Factfulness", "Hans Rosling", "BOOK", "NON_FICTION", "9781250107817");
        addoc.addDocument("Quiet: The Power of Introverts in a World That Can't Stop Talking", "Susan Cain", "BOOK", "NON_FICTION", "9780307352156");
        addoc.addDocument("The Tipping Point", "Malcolm Gladwell", "BOOK", "NON_FICTION", "9780316346624");

        addoc.addDocument("The Diary of a Young Girl", "Anne Frank", "BOOK", "BIOGRAPHY", "9780553296983");
        addoc.addDocument("My Life on the Road", "Gloria Steinem", "BOOK", "BIOGRAPHY", "9780345408167");
        addoc.addDocument("The Glass Castle", "Jeannette Walls", "BOOK", "BIOGRAPHY", "9780743247542");
        addoc.addDocument("Open", "Andre Agassi", "BOOK", "BIOGRAPHY", "9780307388407");
        addoc.addDocument("Born a Crime", "Trevor Noah", "BOOK", "BIOGRAPHY", "9780399588198");

        addoc.addDocument("The History of the Ancient World", "Susan Wise Bauer", "BOOK", "HISTORY", "9780393059748");
        addoc.addDocument("The Rise and Fall of the Third Reich", "William L. Shirer", "BOOK", "HISTORY", "9781451651683");
        addoc.addDocument("The Wright Brothers", "David McCullough", "BOOK", "HISTORY", "9781476728759");
        addoc.addDocument("1491: New Revelations of the Americas Before Columbus", "Charles C. Mann", "BOOK", "HISTORY", "9781400032051");
        addoc.addDocument("SPQR: A History of Ancient Rome", "Mary Beard", "BOOK", "HISTORY", "9781631492228");

        addoc.addDocument("A Brief History of Time", "Stephen Hawking", "BOOK", "SCIENCE", "9780553380163");
        addoc.addDocument("The Elegant Universe", "Brian Greene", "BOOK", "SCIENCE", "9780393338102");
        addoc.addDocument("The Feynman Lectures on Physics", "Richard P. Feynman", "BOOK", "SCIENCE", "9780465023820");
        addoc.addDocument("Cosmos", "Carl Sagan", "BOOK", "SCIENCE", "9780345539434");
        addoc.addDocument("The Sixth Extinction", "Elizabeth Kolbert", "BOOK", "SCIENCE", "9781250062185");

        addoc.addDocument("Steve Jobs", "Walter Isaacson", "BOOK", "TECHNOLOGY", "9781451648546");
        addoc.addDocument("Hackers: Heroes of the Computer Revolution", "Steven Levy", "BOOK", "TECHNOLOGY", "9781449388393");
        addoc.addDocument("The Innovator's Dilemma", "Clayton Christensen", "BOOK", "TECHNOLOGY", "9781633691780");
        addoc.addDocument("The Everything Store", "Brad Stone", "BOOK", "TECHNOLOGY", "9780316219263");
        addoc.addDocument("Elon Musk: Tesla, SpaceX, and the Quest for a Fantastic Future", "Ashlee Vance", "BOOK", "TECHNOLOGY", "9780062301239");

        addoc.addDocument("The Art Spirit", "Robert Henri", "BOOK", "ART", "9780465002634");
        addoc.addDocument("The Elements of Drawing", "John Ruskin", "BOOK", "ART", "9780486227320");
        addoc.addDocument("Color and Light", "James Gurney", "BOOK", "ART", "9780740797712");
        addoc.addDocument("Drawing on the Right Side of the Brain", "Betty Edwards", "BOOK", "ART", "9781585429202");
        addoc.addDocument("Steal Like an Artist", "Austin Kleon", "BOOK", "ART", "9780761169253");

        addoc.addDocument("Just Kids", "Patti Smith", "BOOK", "MUSIC", "9780060936228");
        addoc.addDocument("Born to Run", "Bruce Springsteen", "BOOK", "MUSIC", "9781501141515");
        addoc.addDocument("Chronicles: Volume One", "Bob Dylan", "BOOK", "MUSIC", "9780743244583");
        addoc.addDocument("Your Brain on Music", "Daniel J. Levitin", "BOOK", "MUSIC", "9780452288522");
        addoc.addDocument("Me", "Elton John", "BOOK", "MUSIC", "9781250147608");

        addoc.addDocument("The Consolations of Philosophy", "Alain de Botton", "BOOK", "PHILOSOPHY", "9780679779179");
        addoc.addDocument("Sophie's World", "Jostein Gaarder", "BOOK", "PHILOSOPHY", "9780374530716");
        addoc.addDocument("Ethics", "Benedict de Spinoza", "BOOK", "PHILOSOPHY", "9780140435719");
        addoc.addDocument("The Prince", "Niccolò Machiavelli", "BOOK", "PHILOSOPHY", "9780199535699");
        addoc.addDocument("Beyond Good and Evil", "Friedrich Nietzsche", "BOOK", "PHILOSOPHY", "9780140449235");

        addoc.addDocument("Man's Search for Meaning", "Viktor E. Frankl", "BOOK", "RELIGION", "9780807014295");
        addoc.addDocument("The Book of Joy", "Dalai Lama and Desmond Tutu", "BOOK", "RELIGION", "9780399185045");
        addoc.addDocument("The Alchemist", "Paulo Coelho", "BOOK", "RELIGION", "9780062315007");
        addoc.addDocument("The Case for Christ", "Lee Strobel", "BOOK", "RELIGION", "9780310209307");
        addoc.addDocument("The Purpose Driven Life", "Rick Warren", "BOOK", "RELIGION", "9780310344848");

        addoc.addDocument("The Life-Changing Magic of Tidying Up", "Marie Kondo", "BOOK", "SELF_HELP", "9781607747307");
        addoc.addDocument("You Are a Badass", "Jen Sincero", "BOOK", "SELF_HELP", "9780762447695");
        addoc.addDocument("The Subtle Art of Not Giving a F*ck", "Mark Manson", "BOOK", "SELF_HELP", "9780062457714");
        addoc.addDocument("Mindset: The New Psychology of Success", "Carol S. Dweck", "BOOK", "SELF_HELP", "9780345472328");
        addoc.addDocument("Deep Work", "Cal Newport", "BOOK", "SELF_HELP", "9781455586691");

        addoc.addDocument("To Kill a Mockingbird", "Harper Lee", "BOOK", "FICTION", "9780061120084");
        addoc.addDocument("1984", "George Orwell", "BOOK", "FICTION", "9780451524935");
        addoc.addDocument("A Brief History of Time", "Stephen Hawking", "BOOK", "SCIENCE", "9780553380163");
        addoc.addDocument("The Elegant Universe", "Brian Greene", "BOOK", "SCIENCE", "9780393338102");
        addoc.addDocument("The Art of Computer Programming", "Donald E. Knuth", "BOOK", "TECHNOLOGY", "9780201896831");
        addoc.addDocument("Introduction to Algorithms", "Thomas H. Cormen", "BOOK", "TECHNOLOGY", "9780262033848");
        addoc.addDocument("Steve Jobs", "Walter Isaacson", "BOOK", "BIOGRAPHY", "9781451648539");
        addoc.addDocument("The Diary of a Young Girl", "Anne Frank", "BOOK", "BIOGRAPHY", "9780553296983");
        addoc.addDocument("Sapiens: A Brief History of Humankind", "Yuval Noah Harari", "BOOK", "HISTORY", "9780062316097");
        addoc.addDocument("Guns, Germs, and Steel", "Jared Diamond", "BOOK", "HISTORY", "9780393354324");
        addoc.addDocument("The God Delusion", "Richard Dawkins", "BOOK", "RELIGION", "9780618918249");
        addoc.addDocument("Mere Christianity", "C.S. Lewis", "BOOK", "RELIGION", "9780060652920");
        addoc.addDocument("The Power of Habit", "Charles Duhigg", "BOOK", "SELF_HELP", "9780812981605");
        addoc.addDocument("Atomic Habits", "James Clear", "BOOK", "SELF_HELP", "9780735211292");
        addoc.addDocument("The Body Keeps the Score", "Bessel van der Kolk", "BOOK", "HEALTH", "9780143127741");
        addoc.addDocument("How Not to Die", "Michael Greger", "BOOK", "HEALTH", "9781250066114");
        addoc.addDocument("Educated", "Tara Westover", "BOOK", "EDUCATION", "9780399590504");
        addoc.addDocument("Pedagogy of the Oppressed", "Paulo Freire", "BOOK", "EDUCATION", "9780826412768");
        addoc.addDocument("The Story of Art", "E.H. Gombrich", "BOOK", "ART", "9780714832470");
        addoc.addDocument("Ways of Seeing", "John Berger", "BOOK", "ART", "9780140135152");
        addoc.addDocument("Musicophilia", "Oliver Sacks", "BOOK", "MUSIC", "9781400033539");
        addoc.addDocument("This Is Your Brain on Music", "Daniel J. Levitin", "BOOK", "MUSIC", "9780452288521");
        addoc.addDocument("The Republic", "Plato", "BOOK", "PHILOSOPHY", "9780140455113");
        addoc.addDocument("Meditations", "Marcus Aurelius", "BOOK", "PHILOSOPHY", "9780486298238");
        addoc.addDocument("Born to Run", "Christopher McDougall", "BOOK", "SPORTS", "9780307279187");
        addoc.addDocument("Moneyball", "Michael Lewis", "BOOK", "SPORTS", "9780393324815");
        addoc.addDocument("Into the Wild", "Jon Krakauer", "BOOK", "TRAVEL", "9780385486804");
        addoc.addDocument("Eat, Pray, Love", "Elizabeth Gilbert", "BOOK", "TRAVEL", "9780143038412");
        addoc.addDocument("Mastering the Art of French Cooking", "Julia Child", "BOOK", "COOKING", "9780375413407");
        addoc.addDocument("Salt, Fat, Acid, Heat", "Samin Nosrat", "BOOK", "COOKING", "9781476753836");

        addoc.addDocument("Introduction to the Theory of Numbers", "G.H. Hardy, E.M. Wright", "BOOK", "MATHEMATICS", "9780199219865");
        addoc.addDocument("Calculus: Early Transcendentals", "James Stewart", "BOOK", "MATHEMATICS", "9781285741550");
        addoc.addDocument("Discrete Mathematics and Its Applications", "Kenneth H. Rosen", "BOOK", "MATHEMATICS", "9780073383095");
        addoc.addDocument("Linear Algebra and Its Applications", "David C. Lay", "BOOK", "MATHEMATICS", "9780321982384");
        addoc.addDocument("Abstract Algebra", "David S. Dummit, Richard M. Foote", "BOOK", "MATHEMATICS", "9780471433347");
        addoc.addDocument("A Mathematician's Apology", "G.H. Hardy", "BOOK", "MATHEMATICS", "9781107604636");
        addoc.addDocument("How to Solve It: A New Aspect of Mathematical Method", "George Pólya", "BOOK", "MATHEMATICS", "9780691164076");
        addoc.addDocument("Principles of Mathematical Analysis", "Walter Rudin", "BOOK", "MATHEMATICS", "9780070542358");
        addoc.addDocument("Mathematics: Its Content, Methods and Meaning", "A.D. Aleksandrov, A.N. Kolmogorov, M.A. Lavrent’ev", "BOOK", "MATHEMATICS", "9780486409160");
        addoc.addDocument("The Princeton Companion to Mathematics", "Timothy Gowers", "BOOK", "MATHEMATICS", "9780691118802");

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
