package com.api.financescontrol.dtos.expense;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseAllDTO {
    private List<ExpensesAllDTO> expenses;
    private Integer amount;
}
