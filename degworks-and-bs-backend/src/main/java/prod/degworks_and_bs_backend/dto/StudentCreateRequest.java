package prod.degworks_and_bs_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentCreateRequest {

    private Integer emplid;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Email(regexp = ".+@stu-mail\\.brooklyn\\.cuny\\.edu")
    private String schoolEmail;

    @NotBlank
    @Size(min = 6)
    private String password;

    // getters and setters
}