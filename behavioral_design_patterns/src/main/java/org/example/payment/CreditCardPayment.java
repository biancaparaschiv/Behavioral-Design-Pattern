package org.example.payment;

import org.example.service.PaymentGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditCardPayment implements PaymentStrategy {

    private final PaymentGatewayService paymentGatewayService; // Assuming a service that processes card payments
    private final String cardNumber;
    private final String cardHolderName;
    private final String expirationDate;
    private final String cvv;

    // Constructor for setting card details and injecting dependencies
    @Autowired
    public CreditCardPayment(PaymentGatewayService paymentGatewayService, 
                             String cardNumber, String cardHolderName, 
                             String expirationDate, String cvv) {
        this.paymentGatewayService = paymentGatewayService;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    @Override
    public boolean pay(double amount) {
        try {
            // Call the payment gateway service to process payment
            boolean paymentSuccessful = paymentGatewayService.processCreditCardPayment(
                    cardNumber, cardHolderName, expirationDate, cvv, amount);

            if (!paymentSuccessful) {
                // Payment failed, log the failure and return false
                System.err.println("Credit card payment failed for amount: " + amount);
                return false;
            }

            // Payment was successful
            System.out.println("Credit card payment succeeded for amount: " + amount);
            return true;

        } catch (Exception e) {
            // Log unexpected errors during payment processing
            System.err.println("Error processing payment: " + e.getMessage());
            return false;
        }
    }
}
