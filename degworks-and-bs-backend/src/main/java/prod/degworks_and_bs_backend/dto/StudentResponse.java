package prod.degworks_and_bs_backend.dto;

public class StudentResponse {

    private Integer emplid;
    private String name;
    private double gpa;   // ✅ lowercase
    private int credits;

    public StudentResponse(Integer emplid, String name, double gpa, int credits) {
        this.emplid = emplid;
        this.name = name;
        this.gpa = gpa;
        this.credits = credits;
    }

    public Integer getEmplid() { return emplid; }
    public String getName() { return name; }
    public double getGpa() { return gpa; }   // ✅ standard getter
    public int getCredits() { return credits; }
}