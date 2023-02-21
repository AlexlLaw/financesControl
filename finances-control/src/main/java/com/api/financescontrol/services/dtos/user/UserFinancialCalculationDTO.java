package com.api.financescontrol.services.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Objeto contendo calculo financeiro")
public class UserFinancialCalculationDTO {
    private double essentials;
    private double bullshit;
    private double financialReserve;
}
