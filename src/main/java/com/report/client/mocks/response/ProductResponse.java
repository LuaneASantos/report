package com.report.client.mocks.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Integer codigo;
    @JsonProperty("tipo_vinho")
    private String tipoVinho;
    private Double preco;
    private String safra;
    @JsonProperty("ano_compra")
    private Integer anoCompra;

}
