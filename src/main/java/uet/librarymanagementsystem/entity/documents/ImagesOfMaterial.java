package uet.librarymanagementsystem.entity.documents;

public enum ImagesOfMaterial {
    BOOK("/uet/librarymanagementsystem/image/book_image.png"),
    JOURNAL("/uet/librarymanagementsystem/image/journal_image.png"),
    NEWSPAPER("/uet/librarymanagementsystem/image/newspaper_image.png"),
    THESIS("/uet/librarymanagementsystem/image/thesis_image.png");

    private final String path;

    ImagesOfMaterial(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
