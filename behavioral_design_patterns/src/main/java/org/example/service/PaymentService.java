package org.example.service;

import org.example.model.Order;
import org.example.model.PaymentDetails;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public boolean validatePayment(Order order) {
        PaymentDetails paymentDetails = order.getPaymentDetails();

        // Perform validation based on payment details
        if (paymentDetails == null || paymentDetails.getCardNumber() == null) {
            return false; // Validation failed
        }

        System.out.println("Validating payment with method: " + paymentDetails.getPaymentMethod());
        // Additional validation logic here

        return true;
    }
}

