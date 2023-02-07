package com.api.financescontrol.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Objeto contendo dados dos usuario")
public class UsersAllDTO {
    private String cpf;
    private String name;
    private String lastName;
    private Boolean active;
    private String restrictions;
    private double wage;
}
