package com.api.financescontrol.dtos.expense;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class ExpensesAllDTO {
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
    private UUID userId;
}
