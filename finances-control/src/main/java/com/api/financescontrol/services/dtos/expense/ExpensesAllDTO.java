package com.api.financescontrol.services.dtos.expense;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesAllDTO {
    private UUID userId;
    private UUID id;

    @NotBlank
    @Size(max = 4)
    private Integer year;

    @NotBlank
    @Size(max = 2)
    private Integer month;

    @NotBlank
    @Size(max = 36)
    private String nameExpense;

    @Size(max = 255)
    private String descriptionExpense;

    private Integer value;
    private Boolean isFixed;
    private Integer timeAccount;
    private Boolean paidOut;
    private String typeOfExpense;
    private Integer amaunt;
}
