package io.github.hjle.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String userId;

    @NotBlank
    private String password;

    @NotBlank
    private String name;
}