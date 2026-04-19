package prod.degworks_and_bs_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enrollments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer enrollmentId;

    @Column(nullable = false)
    private Integer emplid;

    @Size(max = 10)
    @Column(nullable = false, length = 10)
    private String courseCode;

    @Column(nullable = false)
    private Integer credits;

    @NotBlank(message = "Grade cannot be blank!")
    @Size(max = 2, message = "Must be a letter grade! i.e. \"A+\" or \"C\"")
    @Column(nullable = true)
    private String grade;

    @Size(max = 15,  message = "Character is 15")
    @Column(nullable = false, length = 15)
    private String semester; // timeframe when student took the course

    @NotBlank(message = "Professor cannot be blank!")
    @Size(min = 5, max = 50, message = "Character limit is between 5 and 50")
    @Column(nullable = false, length = 50)
    private String professor;

    @Column(nullable = true)
    private boolean completed; // another way of figuring whether a student has completed their enrollment

    @PrePersist
    @PreUpdate
    public void normalize() {
        if (courseCode != null) {
            courseCode = courseCode.toUpperCase();
        }

        if (semester != null) {
            semester = semester.trim();
        }
    }
}
