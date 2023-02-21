package com.api.financescontrol.services.dtos.expense;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ExpenseCreateDTO {

    @NotBlank
    @Size(max = 36)
    private String nameExpense;

    @Size(max = 255)
    private String descriptionExpense;
    private Integer year;
    private Integer month;
    private Integer value;
    private Boolean isFixed;
    private Integer timeAccount;
    private Boolean paidOut;
    private UUID userId;
    private String typeOfExpense;
    private String paymentVoucher;

    public ExpenseCreateDTO() {

    }
}
