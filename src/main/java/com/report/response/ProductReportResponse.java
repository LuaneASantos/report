package com.report.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReportResponse {

    private String tipo_vinho;
    private double preco;
    private String safra;
    private int ano_compra;

}
