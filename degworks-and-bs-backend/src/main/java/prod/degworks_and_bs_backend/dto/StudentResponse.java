package prod.degworks_and_bs_backend.dto;

public class StudentResponse {

    private Integer emplid;
    private String name;
    private double GPA;
    private int credits;

    public StudentResponse(Integer emplid, String name, double GPA, int credits) {
        this.emplid = emplid;
        this.name = name;
        this.GPA = GPA;
        this.credits = credits;
    }

    // getters only (no setters)
}