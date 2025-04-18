package org.example.expert.domain.user.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordRequest {

    @NotBlank
    private String oldPassword;
    @NotBlank
    @Size(min = 8, message = "비밀번호는 최소 8자 이상")
    @Pattern(regexp = ".*\\d.*")
    @Pattern(regexp = ".*[A-Z].*")
    private String newPassword;
}
