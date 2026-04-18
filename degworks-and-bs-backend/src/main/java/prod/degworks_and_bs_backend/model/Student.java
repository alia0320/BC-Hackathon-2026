package prod.degworks_and_bs_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    private Integer emplid;

    @Column(nullable = false)
    private double GPA = 0;

    @Column(nullable = false)
    private int credits;

    @Size(max = 100, message = "50 Max Character Limit")
    @Column (nullable = false, length = 50)
    private String name;

    @NotBlank
    @Email(regexp = ".+@stu-mail\\.brooklyn\\.cuny\\.edu")
    @JsonIgnore
    private String schoolEmail;

    @JsonIgnore
    private String password;



}
