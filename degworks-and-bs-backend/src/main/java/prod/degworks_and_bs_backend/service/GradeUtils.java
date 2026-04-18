package prod.degworks_and_bs_backend.service;

public class GradeUtils {
    public static double convertToPoints(String grade) {
        return switch (grade.toUpperCase()) {
            case "A+", "A" -> 4.0;
            case "A-" -> 3.7;
            case "B+" -> 3.3;
            case "B" -> 3.0;
            case "B-" -> 2.7;
            case "C+" -> 2.3;
            case "C" -> 2.0;
            case "C-" -> 1.7;
            case "D" -> 1.0;
            case "F" -> 0.0;
            default -> throw new IllegalArgumentException("Invalid grade: " + grade);
        };
    }
}
