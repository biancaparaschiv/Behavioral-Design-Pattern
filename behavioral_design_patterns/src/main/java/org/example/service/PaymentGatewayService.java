package org.example.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentGatewayService {
    public boolean processCreditCardPayment(String cardNumber, String cardHolderName, String expirationDate, String cvv, double amount) {
        // Simulate processing payment with a credit card (in real applications, integrate with payment API)
        // Example logic: return true if payment is successful
        return Math.random() > 0.2; // Simulate a success rate of 80%
    }
}

