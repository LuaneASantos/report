package com.report.client.mocks.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    private String nome;
    private String cpf;
    private List<ShoppingResponse> compras;

}
