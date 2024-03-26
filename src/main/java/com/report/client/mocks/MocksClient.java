package com.report.client.mocks;

import com.report.client.mocks.response.CustomerResponse;
import com.report.client.mocks.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "MocksClient", url = "https://run.mocky.io/v3/")
public interface MocksClient {

    @GetMapping("/45e7bcac-6f2c-40f1-8662-eb3a62aa005c")
    List<ProductResponse> getProducts();

    @GetMapping("/d1992fa3-e01d-43ea-97d6-e8ef302c886d")
    List<CustomerResponse> getCustomerShopping();

}
