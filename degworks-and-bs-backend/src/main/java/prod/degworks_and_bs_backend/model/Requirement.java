package prod.degworks_and_bs_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "requirements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Requirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;   // e.g. "CS_MAJOR_CORE"

    @Column(nullable = false, length = 100)
    private String name;   // e.g. "Computer Science Major Core"

    @Column(nullable = false)
    private int requiredCredits;

    @Column(nullable = false)
    private boolean active = true;
}