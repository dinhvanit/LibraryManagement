package uet.librarymanagementsystem.entity.documents;

public enum ImagesOfLibrary {
    BOOK("/uet/librarymanagementsystem/image/book_image.png"),
    JOURNAL("/uet/librarymanagementsystem/image/journal_image.png"),
    NEWSPAPER("/uet/librarymanagementsystem/image/newspaper_image.png"),
    THESIS("/uet/librarymanagementsystem/image/thesis_image.png"),
    QRCODEVIEW("/uet/librarymanagementsystem/image/imageQR/transactionQRCode.png"),
    QRCODEWIRTE("src/main/resources/uet/librarymanagementsystem/image/imageQR/transactionQRCode.png");

    private final String path;

    ImagesOfLibrary(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
