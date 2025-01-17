package org.example.payment;

import lombok.Value;
import org.example.service.PaymentGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditCardPayment implements PaymentStrategy {

    @Autowired
    private PaymentGatewayService paymentGatewayService; // Assuming a service that processes card payments

    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;

    @Override
    public boolean pay(double amount) {
        // Call the payment gateway service to process payment
        boolean paymentSuccessful = paymentGatewayService.processCreditCardPayment(
                cardNumber, cardHolderName, expirationDate, cvv, amount);

        if (!paymentSuccessful) {
            // Payment failed, return false or handle the failure as needed
            System.out.println("Credit card payment failed for amount: " + amount);
            return false;
        }

        // Payment was successful
        System.out.println("Credit card payment succeeded for amount: " + amount);
        return true;
    }

    // Optional: Add setters for card details if needed
}
