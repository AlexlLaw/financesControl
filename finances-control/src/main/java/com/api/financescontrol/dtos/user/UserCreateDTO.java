package com.api.financescontrol.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserCreateDTO {

    @NotBlank
    @Size(max = 11)
    private String cpf;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 255)
    private String lastName;

    private Boolean active;

    private Boolean firstAcess;

    @NotBlank
    private String restrictions;

    @NotBlank
    @Size(max = 16)
    private String password;

    private double wage;

    public UserCreateDTO() {

    }
}
