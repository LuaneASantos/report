package com.report.controller;

import com.report.client.mocks.MocksClient;
import com.report.client.mocks.response.ProductResponse;
import com.report.response.CustomerShoppingReportResponse;
import com.report.response.ProductReportResponse;
import com.report.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ReportController.class)
public class ReportControllerTest {

    @Mock
    private MocksClient mocksClient;

    @MockBean
    private ReportService reportService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetCustomerShoppingReport() throws Exception {

        List<CustomerShoppingReportResponse> customerShoppingReportResponses = Arrays.asList(
                new CustomerShoppingReportResponse("John", "123",
                        new ProductReportResponse(1,"Tinto", 20.0, "Safra 1", 2020), 1L, 25D)
        );

        when(reportService.getCustomerShoppingReport()).thenReturn(customerShoppingReportResponses);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/compras")).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testGetBiggestPurchaseYearReport() throws Exception {

        Optional<CustomerShoppingReportResponse> customerShoppingReportResponse = Optional.of(new CustomerShoppingReportResponse("John", "123",
                new ProductReportResponse(1, "Tinto", 20.0, "Safra 1", 2020), 1L, 25D));

        when(reportService.getBiggestPurchaseYearReport(2020)).thenReturn(customerShoppingReportResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/maior-compra/{ano}", 2020)).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testGetLoyalCustomersReport() throws Exception {

        List<CustomerShoppingReportResponse> customerShoppingReportResponses = Arrays.asList(
                new CustomerShoppingReportResponse("John", "123",
                        new ProductReportResponse(1, "Tinto", 20.0, "Safra 1", 2020), 1L, 25D)
        );

        when(reportService.getLoyalCustomersReport()).thenReturn(customerShoppingReportResponses);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/clientes-fieis")).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testGetCustomerRecommendationReport() throws Exception {

        ProductResponse productReportResponse = new ProductResponse(1, "Tinto", 20.0, "Safra 1", 2020);

        when(reportService.getCustomerRecommendationReport("123")).thenReturn(productReportResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/recomendacao/cliente/tipo/{cpf}", "123")).andExpect(MockMvcResultMatchers.status().isOk());

    }

}
