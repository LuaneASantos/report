package com.report.service;

import com.report.client.mocks.MocksClient;
import com.report.client.mocks.response.CustomerResponse;
import com.report.client.mocks.response.ProductResponse;
import com.report.client.mocks.response.ShoppingResponse;
import com.report.response.CustomerShoppingReportResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private MocksClient mocksClient;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetCustomerShoppingReport() {

        List<ProductResponse> products = Arrays.asList(
                new ProductResponse(1, "Safra 1", 20.0, "Tinto", 2020),
                new ProductResponse(2, "Safra 2", 25.0, "Branco", 2021)
        );
        List<CustomerResponse> customerResponses = Arrays.asList(
                new CustomerResponse("John", "123", Arrays.asList(
                        new ShoppingResponse(1, 1L),
                        new ShoppingResponse(2, 4L)
                )),
                new CustomerResponse("Alice", "456", Arrays.asList(
                        new ShoppingResponse(1, 6L),
                        new ShoppingResponse(2, 5L)
                ))
        );

        when(reportService.getProducts()).thenReturn(products);
        when(reportService.getCustomerShopping()).thenReturn(customerResponses);

        List<CustomerShoppingReportResponse> reportResponses = reportService.getCustomerShoppingReport();

        assertEquals(4, reportResponses.size());
        assertEquals("John", reportResponses.get(1).getNome());
        assertEquals("Alice", reportResponses.get(3).getNome());
        assertEquals(reportResponses.get(3).getQuantidade() * reportResponses.get(3).getProduto().getPreco(), reportResponses.get(3).getValorTotal());
    }

    @Test
    void testGetBiggestPurchaseYearReport() {

        List<ProductResponse> products = Arrays.asList(
                new ProductResponse(1, "Tinto", 20.0, "2019", 2020),
                new ProductResponse(2, "Branco", 25.0, "2020", 2021),
                new ProductResponse(3, "Rosé", 26.0, "2018", 2020)
        );
        List<CustomerResponse> customerResponses = Arrays.asList(
                new CustomerResponse("John", "123", Arrays.asList(
                        new ShoppingResponse(1, 10L),
                        new ShoppingResponse(2, 4L),
                        new ShoppingResponse(3, 5L)
                )),
                new CustomerResponse("Alice", "456", Arrays.asList(
                        new ShoppingResponse(1, 6L),
                        new ShoppingResponse(2, 5L),
                        new ShoppingResponse(3, 9L)
                ))
        );

        when(reportService.getProducts()).thenReturn(products);
        when(reportService.getCustomerShopping()).thenReturn(customerResponses);

        CustomerShoppingReportResponse reportResponses = reportService.getBiggestPurchaseYearReport(2020).get();

        assertEquals("Alice", reportResponses.getNome());
        assertEquals("Rosé", reportResponses.getProduto().getTipo_vinho());
        assertEquals(9 * 26.0, reportResponses.getValorTotal());
    }

    @Test
    void testGetCustomerRecommendationReport() {

        List<ProductResponse> products = Arrays.asList(
                new ProductResponse(1, "Tinto", 20.0, "2019", 2020),
                new ProductResponse(2, "Branco", 25.0, "2020", 2021),
                new ProductResponse(3, "Rosé", 26.0, "2018", 2020)
        );
        List<CustomerResponse> customerResponses = Arrays.asList(
                new CustomerResponse("John", "123", Arrays.asList(
                        new ShoppingResponse(1, 10L),
                        new ShoppingResponse(2, 4L)
                )),
                new CustomerResponse("Alice", "456", Arrays.asList(
                        new ShoppingResponse(1, 6L),
                        new ShoppingResponse(2, 5L),
                        new ShoppingResponse(3, 9L)
                ))
        );

        when(reportService.getProducts()).thenReturn(products);
        when(reportService.getCustomerShopping()).thenReturn(customerResponses);

        ProductResponse productResponse = reportService.getCustomerRecommendationReport("123");

        assertNotEquals("Rosé", productResponse.getTipo_vinho());
    }

    @Test
    void testGetLoyalCustomersReport() {

        List<ProductResponse> products = Arrays.asList(
                new ProductResponse(1, "Tinto", 20.0, "2019", 2020),
                new ProductResponse(2, "Branco", 25.0, "2020", 2021),
                new ProductResponse(3, "Rosé", 26.0, "2018", 2020)
        );
        List<CustomerResponse> customerResponses = Arrays.asList(
                new CustomerResponse("John", "123", Arrays.asList(
                        new ShoppingResponse(1, 10L),
                        new ShoppingResponse(2, 4L)
                )),
                new CustomerResponse("Alice", "456", Arrays.asList(
                        new ShoppingResponse(1, 6L),
                        new ShoppingResponse(2, 5L),
                        new ShoppingResponse(3, 9L)
                )),
                new CustomerResponse("Lucas", "147", Arrays.asList(
                        new ShoppingResponse(1, 6L),
                        new ShoppingResponse(3, 15L)
                )),
                new CustomerResponse("Davi", "258", Arrays.asList(
                        new ShoppingResponse(2, 1L),
                        new ShoppingResponse(3, 1L)
                ))
        );

        when(reportService.getProducts()).thenReturn(products);
        when(reportService.getCustomerShopping()).thenReturn(customerResponses);

        List<CustomerShoppingReportResponse> customerShoppingReportResponses = reportService.getLoyalCustomersReport();

        assertEquals(3, customerShoppingReportResponses.size());
        assertEquals("Lucas", customerShoppingReportResponses.get(0).getNome());
        assertEquals("123", customerShoppingReportResponses.get(2).getCpf());
    }

}
