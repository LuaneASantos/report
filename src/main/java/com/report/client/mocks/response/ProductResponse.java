package com.report.client.mocks.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Integer codigo;
    private String tipo_vinho;
    private Double preco;
    private String safra;
    private Integer ano_compra;

}
