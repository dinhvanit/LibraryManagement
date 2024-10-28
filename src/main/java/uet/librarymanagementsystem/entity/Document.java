package uet.librarymanagementsystem.entity;

public class Document {
    private String id;
    private String title;
    private String author;
    private String material;
    private String category;
    private int quantity;

    public Document(String id, String title, String author, String material, String category, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.material = material;
        this.category = category;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * In ra id, title, author, available.
     */
    public void printInfo() {
        System.out.println("ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Category: " + category);
        System.out.println("Material: " + material);
        System.out.println("Quantity: " + quantity);
    }

    /*
    public static Document getDocumentById(String id) {
        // Logic to retrieve the document from a static list or a database
        List<Document> documents = getAllDocuments(); // Get all documents

        for (Document doc : documents) {
            if (doc.getId().equals(id)) {
                return doc;
            }
        }
        return null;
    }

    */
}
