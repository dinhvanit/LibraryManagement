package uet.librarymanagementsystem.entity.documents;

public enum MaterialType {
    //khoi tao cac loai material cua tung document, khoi tao ca id dua vao material cho document o day
    BOOK("01"),
    JOURNAL("02"),
    NEWSPAPER("03"),
    THESIS("04");

    //OTHER;

    private final String code;

    MaterialType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
