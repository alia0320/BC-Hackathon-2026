package prod.degworks_and_bs_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer professorId;

    @NotBlank(message = "Professor name cannot be blank!")
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String professorName;

    @NotBlank(message = "Course code cannot be blank!")
    @Size(min = 4, max = 10)
    private String courseCode;

    @NotBlank(message = "Comment cannot be blank!")
    @Size(min = 20, max = 500, message = "Comment must be between 20-500 characters")
    private String comment;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private Integer helpful = 0;

    @Column(nullable = false)
    private Integer unhelpful = 0;

    @Column(nullable = false)
    private LocalDateTime reviewTime;

    @PrePersist
    public void onPrePersist() {
        reviewTime = LocalDateTime.now();
        if (courseCode != null) {
            courseCode = courseCode.toUpperCase();
        }
    }

    @PreUpdate
    public void onPreUpdate() {
        if (courseCode != null) {
            courseCode = courseCode.toUpperCase();
        }
    }
}