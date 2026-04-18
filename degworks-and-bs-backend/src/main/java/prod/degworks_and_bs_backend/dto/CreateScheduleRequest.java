package prod.degworks_and_bs_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateScheduleRequest {

    @NotNull
    private Integer emplid;

    @NotBlank
    private String semester;
}