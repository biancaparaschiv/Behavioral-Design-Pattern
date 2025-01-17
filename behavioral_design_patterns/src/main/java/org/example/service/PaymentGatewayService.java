package org.example.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentGatewayService {

    // Process the payment through the credit card
    public boolean processCreditCardPayment(String cardNumber, String cardHolderName, String expirationDate, String cvv, double amount) {
        // Validate input data
        if (isInputInvalid(cardNumber, cardHolderName, expirationDate, cvv, amount)) {
            return false; // Invalid input, cannot process payment
        }

        // Simulate payment processing (replace with actual API integration in real applications)
        boolean paymentSuccess = simulatePaymentProcessing();
        
        return paymentSuccess;
    }

    // Helper method to validate input data
    private boolean isInputInvalid(String cardNumber, String cardHolderName, String expirationDate, String cvv, double amount) {
        return cardNumber == null || cardNumber.isEmpty() ||
               cardHolderName == null || cardHolderName.isEmpty() ||
               expirationDate == null || expirationDate.isEmpty() ||
               cvv == null || cvv.isEmpty() ||
               amount <= 0;
    }

    // Simulate the payment processing result (80% success rate)
    private boolean simulatePaymentProcessing() {
        return Math.random() > 0.2; // Simulate a success rate of 80%
    }
}
