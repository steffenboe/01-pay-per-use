package com.steffenboe.todo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.steffenboe.EthereumPaymentGateway;

@Configuration
public class PaymentGatewayConfig {
    
    @Bean
    EthereumPaymentGateway ethereumPaymentGateway() {
        return new EthereumPaymentGateway();
    }
}
