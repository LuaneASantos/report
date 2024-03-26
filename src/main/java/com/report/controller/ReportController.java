package com.report.controller;

import com.report.client.mocks.response.ProductResponse;
import com.report.response.CustomerShoppingReportResponse;
import com.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/compras")
    @Operation(summary = "Retorna uma lista de compras ordenadas de forma crescente por valor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerShoppingReportResponse[].class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content) })
    public ResponseEntity<List<CustomerShoppingReportResponse>> getCustomerShoppingReport() {
        return ResponseEntity.ok(reportService.getCustomerShoppingReport());
    }

    @GetMapping("/maior-compra/{ano}")
    @Operation(summary = "Retorna a maior compra do ano.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerShoppingReportResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content) })
    public ResponseEntity<Optional<CustomerShoppingReportResponse>> getBiggestPurchaseYearReport(@PathVariable Integer ano) {
        return ResponseEntity.ok(reportService.getBiggestPurchaseYearReport(ano));
    }

    @GetMapping("/clientes-fieis")
    @Operation(summary = "Retorna o Top 3 clientes mais fieis.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerShoppingReportResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content) })
    public ResponseEntity<List<CustomerShoppingReportResponse>> getLoyalCustomersReport() {
        return ResponseEntity.ok(reportService.getLoyalCustomersReport());
    }

    @GetMapping("/recomendacao/cliente/tipo/{cpf}")
    @Operation(summary = "Retorna uma recomendação de vinho baseado nos tipos de vinho que o cliente mais compra.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content) })
    public ResponseEntity<ProductResponse> getCustomerRecommendationReport(@PathVariable String cpf) {
        return ResponseEntity.ok(reportService.getCustomerRecommendationReport(cpf));
    }

}
