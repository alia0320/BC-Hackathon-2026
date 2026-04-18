package prod.degworks_and_bs_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(
        name = "schedules",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"emplid", "semester"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scheduleId;

    /**
     * The student this schedule belongs to
     */
    @Column(nullable = false)
    private Integer emplid;

    /**
     * The semester this schedule represents (e.g. "Fall 2026")
     */
    @Column(nullable = false, length = 15)
    private String semester;

    /**
     * Enrollments included in this schedule
     */
    @OneToMany
    @JoinColumn(name = "schedule_id")
    private List<StudentEnrollment> enrollments;
}
