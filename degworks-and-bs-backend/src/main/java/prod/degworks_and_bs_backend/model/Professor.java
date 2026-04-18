package prod.degworks_and_bs_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "professors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profId;

    @NotBlank(message = "Name cannot be blank!")
    @Size(min = 2, max = 50, message = "Name must be between 2 to 50 characters!")
    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private double rating; // from professor ratings

    @NotBlank(message = "Department cannot be blank!")
    @Size(min = 3, max = 50, message = "Department name must be between 3 to 50 characters!")
    @Column(nullable = false, length = 50)
    private String department;

    @Column(nullable = false)
    private boolean active = true; // true = visible, false = hidden


}
