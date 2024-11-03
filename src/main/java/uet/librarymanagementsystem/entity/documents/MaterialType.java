package uet.librarymanagementsystem.entity.documents;

public enum MaterialType {
    //khoi tao cac loai material cua tung document, khoi tao ca id dua vao material cho document o day
    book("B"),
    journal("J"),
    thesis("T"),
    newspaper("N");

    private final String code;

    MaterialType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
