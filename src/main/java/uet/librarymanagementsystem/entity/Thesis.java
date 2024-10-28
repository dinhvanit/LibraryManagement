package uet.librarymanagementsystem.entity;

public class Thesis extends Document {
    private String university;
    private String supervisor;
    private String researchArea;

    /**
     * Constructor 1.
     *
     * @param id           ma tai lieu
     * @param title        ten tai lieu
     * @param author       tac gia
     * @param quantity     so luong
     * @param university   truong dai hoc
     * @param supervisor   nguoi huong dan
     * @param researchArea de tai nghien cuu
     */
    public Thesis(String id, String title, String author, String material, String category, int quantity, String university, String supervisor, String researchArea) {
        super(id, title, author, category, material, quantity);
        this.university = university;
        this.supervisor = supervisor;
        this.researchArea = researchArea;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getResearchArea() {
        return researchArea;
    }

    public void setResearchArea(String researchArea) {
        this.researchArea = researchArea;
    }

    /**
     * In ra ID, title, author, university, supervisor, research area, quantity, available.
     */
    @Override
    public void printInfo() {
        System.out.println("ID: " + getId());
        System.out.println("Title: " + getTitle());
        System.out.println("Author: " + getAuthor());
        System.out.println("University: " + university);
        System.out.println("Supervisor: " + supervisor);
        System.out.println("Research Area: " + researchArea);
        System.out.println("Quantity: " + getQuantity());
    }
}

