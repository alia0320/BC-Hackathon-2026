package prod.degworks_and_bs_backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    private Integer emplid;

    @Column(nullable = false)
    private double gpa = 0;

    @Column(nullable = false)
    private int credits = 0;

    @Size(max = 100, message = "50 Max Character Limit")
    @Column (nullable = false, length = 50)
    private String name;

    @NotBlank
    @Email(regexp = ".+@stu-mail\\.brooklyn\\.cuny\\.edu")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String schoolEmail;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;



}
