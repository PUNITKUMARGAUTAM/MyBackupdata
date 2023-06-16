package learningtask.learningnew.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
//@JsonIgnoreProperties (ignoreUnknown = true)
public class UpdatePasswordRequest {
    @JsonProperty ("UserEmail")
    private String userEmail;

    @JsonProperty ("NewPassword")
    private String newPassword;
}