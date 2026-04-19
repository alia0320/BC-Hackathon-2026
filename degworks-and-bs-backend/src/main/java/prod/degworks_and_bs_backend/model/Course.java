package prod.degworks_and_bs_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "courses")
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @Size(min = 4, max = 10, message = "Code must be between 4-10 characters")
    private String code;

    @NotBlank(message = "Title cannot be blank!")
    @Size(min = 10, max = 100, message = "Title must be between 10-100 characters")
    @Column(nullable = false, length = 100)
    private String title;

    @ElementCollection
    private List<String> prerequisites;

    @Column(nullable = false)
    private int credits;

    @ManyToMany
    @JoinTable(
            name = "course_requirements",
            joinColumns = @JoinColumn(name = "course_code"),
            inverseJoinColumns = @JoinColumn(name = "requirement_id")
    )
    private List<Requirement> satisfies;

    @PrePersist
    @PreUpdate
    public void normalize() {
        this.code = this.code.toUpperCase();
    }
}
