package com.report.service;

import com.report.client.mocks.MocksClient;
import com.report.client.mocks.response.CustomerResponse;
import com.report.client.mocks.response.ProductResponse;
import com.report.client.mocks.response.ShoppingResponse;
import com.report.response.CustomerShoppingReportResponse;
import com.report.response.ProductReportResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ReportService {

    private final MocksClient mocksClient;

    private static final String PRODUCT_NOT_FOUND_MESSAGE  = "Produto não encontrado para o código: ";

    public List<ProductResponse> getProducts() {
        return mocksClient.getProducts();
    }

    public List<CustomerResponse> getCustomerShopping() {
        return mocksClient.getCustomerShopping();
    }

    public List<CustomerShoppingReportResponse> getCustomerShoppingReport() {

        List<ProductResponse> products = getProducts();

        List<CustomerResponse> customerResponses = getCustomerShopping();

        List<CustomerShoppingReportResponse> customerShoppingReportResponses = new ArrayList<>();

        customerResponses.stream().forEach(customerResponse -> {

                    customerResponse.getCompras().stream().forEach(shoppingResponse -> {

                        CustomerShoppingReportResponse customerShopping = new CustomerShoppingReportResponse();
                        customerShopping.setCpf(customerResponse.getCpf());
                        customerShopping.setNome(customerResponse.getNome());

                        ProductReportResponse productReportResponse = new ProductReportResponse();

                        ProductResponse product = products.stream()
                                .filter(p -> p.getCodigo().equals(shoppingResponse.getCodigo()))
                                .findFirst()
                                .orElseThrow(() -> new NoSuchElementException(PRODUCT_NOT_FOUND_MESSAGE + shoppingResponse.getCodigo()));

                        productReportResponse.setSafra(product.getSafra());
                        productReportResponse.setAno_compra(product.getAno_compra());
                        productReportResponse.setTipo_vinho(product.getTipo_vinho());
                        productReportResponse.setPreco(product.getPreco());

                        customerShopping.setProduto(productReportResponse);

                        customerShopping.setQuantidade(shoppingResponse.getQuantidade());
                        customerShopping.setValorTotal(shoppingResponse.getQuantidade() * product.getPreco());

                        customerShoppingReportResponses.add(customerShopping);

                    });
                }
        );

        // Ordena a lista pelo valorTotal de forma crescente
        return customerShoppingReportResponses.stream()
                .sorted(Comparator.comparingDouble(CustomerShoppingReportResponse::getValorTotal))
                .toList();
    }

    public Optional<CustomerShoppingReportResponse> getBiggestPurchaseYearReport(Integer yearPurchase) {

        // Filtra os produtos pelo ano de compra
        List<ProductResponse> products = this.getProducts().stream().filter(p -> p.getAno_compra().equals(yearPurchase)).toList();

        // Obtem apenas os códigos desses produtos
        List<Integer> codeProducts = products.stream()
                .map(ProductResponse::getCodigo)
                .toList();

        List<CustomerResponse> customerResponses = this.getCustomerShopping();

        List<CustomerShoppingReportResponse> customerShoppingReportResponses = new ArrayList<>();

        customerResponses.stream().forEach(customerResponse -> {

                    // Filtra as compras pelos códigos de produtos que foram comprados no ano informado
                    customerResponse.getCompras().stream().filter(c -> codeProducts.contains(c.getCodigo())).forEach(shoppingResponse -> {

                        CustomerShoppingReportResponse customerShopping = new CustomerShoppingReportResponse();
                        customerShopping.setCpf(customerResponse.getCpf());
                        customerShopping.setNome(customerResponse.getNome());

                        ProductReportResponse productReportResponse = new ProductReportResponse();

                        ProductResponse product = products.stream().filter(p -> p.getCodigo().equals(shoppingResponse.getCodigo())).findFirst()
                                .orElseThrow(() -> new NoSuchElementException(PRODUCT_NOT_FOUND_MESSAGE + shoppingResponse.getCodigo()));

                        productReportResponse.setSafra(product.getSafra());
                        productReportResponse.setAno_compra(product.getAno_compra());
                        productReportResponse.setTipo_vinho(product.getTipo_vinho());
                        productReportResponse.setPreco(product.getPreco());

                        customerShopping.setProduto(productReportResponse);

                        customerShopping.setQuantidade(shoppingResponse.getQuantidade());
                        customerShopping.setValorTotal(shoppingResponse.getQuantidade() * product.getPreco());

                        customerShoppingReportResponses.add(customerShopping);

                    });
                }
        );

        // Ordena a lista pelo valorTotal de forma decrescente
        return customerShoppingReportResponses.stream()
                .sorted(Comparator.comparingDouble(CustomerShoppingReportResponse::getValorTotal).reversed())
                .toList().stream().findFirst();
    }

    public List<CustomerShoppingReportResponse> getLoyalCustomersReport() {

        List<ProductResponse> products = this.getProducts();

        List<CustomerResponse> customerResponses = this.getCustomerShopping();

        List<CustomerShoppingReportResponse> customerShoppingReportResponses = new ArrayList<>();

        customerResponses.stream().forEach(customerResponse -> {

                    CustomerShoppingReportResponse customerShopping = new CustomerShoppingReportResponse();

                    customerShopping.setCpf(customerResponse.getCpf());
                    customerShopping.setNome(customerResponse.getNome());

                    customerResponse.getCompras().stream().forEach(shoppingResponse -> {

                        ProductResponse product = products.stream().filter(p -> p.getCodigo().equals(shoppingResponse.getCodigo())).findFirst()
                                .orElseThrow(() -> new NoSuchElementException(PRODUCT_NOT_FOUND_MESSAGE + shoppingResponse.getCodigo()));

                        customerShopping.setQuantidade(customerShopping.getQuantidade() + shoppingResponse.getQuantidade());
                        customerShopping.setValorTotal(customerShopping.getValorTotal() + shoppingResponse.getQuantidade() * product.getPreco());


                    });

                    customerShoppingReportResponses.add(customerShopping);
                }
        );

        // Ordena a lista pelo valorTotal de forma decrescente e busca os 3 primeiros da lista
        return customerShoppingReportResponses.stream()
                .sorted(Comparator.comparingDouble(CustomerShoppingReportResponse::getQuantidade).thenComparing(CustomerShoppingReportResponse::getValorTotal).reversed())
                .limit(3)
                .toList();
    }

    public ProductResponse getCustomerRecommendationReport(String cpf) {

        List<ProductResponse> products = this.getProducts();

        Optional<CustomerResponse> customerResponse = this.getCustomerShopping().stream().filter(c -> c.getCpf().equals(cpf)).findFirst();

        if (customerResponse.isEmpty()) {
            throw new IllegalArgumentException("Cliente não encontrado para o CPF fornecido: " + cpf);
        }

        // Lista com os 3 tipos de vinhos mais consumidos pelo cliente
        List<Integer> mostConsumedWines = customerResponse.get().getCompras().stream()
                .sorted(Comparator.comparingDouble(ShoppingResponse::getQuantidade).reversed())
                .limit(3)
                .map(ShoppingResponse::getCodigo)
                .toList();

        // Busca o tipo de vinho pelo código da compra
        List<String> wineTypes = products.stream().filter(p -> mostConsumedWines.contains(p.getCodigo())).map(ProductResponse::getTipo_vinho).toList();

        // Filtra os vinhos dos tipos que o cliente mais consome
        List<ProductResponse> recommendedProducts = products.stream().filter(p -> wineTypes.contains(p.getTipo_vinho())).toList();

        // Retorna um vinho aleatório dos tipos mais consumidos
        return recommendedProducts
                .stream()
                .skip(new Random().nextInt(recommendedProducts.size()))
                .findFirst()
                .orElse(null);

    }

}
