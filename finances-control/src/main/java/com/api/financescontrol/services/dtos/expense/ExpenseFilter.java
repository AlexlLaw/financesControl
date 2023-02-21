package com.api.financescontrol.services.dtos.expense;

import com.api.financescontrol.enums.TypeofExpense;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseFilter {
    private int month;
    private int year;

    @Nullable
    @JsonProperty(required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean paidOut;

    @Nullable
    @JsonProperty(required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TypeofExpense typeofExpense;
}
