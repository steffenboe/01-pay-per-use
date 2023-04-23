package com.steffenboe.todo;

import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.steffenboe.EthereumPaymentGateway;

import jakarta.annotation.PostConstruct;

@Component
public class PaymentGatewayRegistrator {
    
    private EthereumPaymentGateway paymentGateway;
    @Value("${todo.ethereumAddress}")
    private String publicAppAdress;
    private UUID appId;

    public PaymentGatewayRegistrator(EthereumPaymentGateway paymentGateway){
        this.paymentGateway = paymentGateway;
    }

    @PostConstruct
    void registerApp() {
        appId = paymentGateway.registerApp("todoApp", publicAppAdress);
        try {
            paymentGateway.registerUser(appId, "steffenboe");
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    public void charge(String userId, double amount) throws Exception {
        paymentGateway.charge(appId, userId, amount);
    }

    public String getUserAddress(String userId) {
        return paymentGateway.getAddress(appId, userId);
    }

    public BigDecimal getCurrentFunding(String userId) {
        return paymentGateway.available(appId, userId);
    }

}
