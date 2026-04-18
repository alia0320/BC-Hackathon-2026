package prod.degworks_and_bs_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class AddEnrollmentsRequest {

    @NotEmpty
    private List<Integer> enrollmentIds;

}