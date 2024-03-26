package com.report.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReportResponse {

    private Integer codigo;
    private String tipoVinho;
    private double preco;
    private String safra;
    private Integer anoCompra;

}
