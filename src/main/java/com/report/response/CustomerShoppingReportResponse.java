package com.report.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerShoppingReportResponse {

    private String nome;
    private String cpf;
    private ProductReportResponse produto;
    private Long quantidade = 0L;
    private Double valorTotal = 0D;

}
