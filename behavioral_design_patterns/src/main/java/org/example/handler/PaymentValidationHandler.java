package org.example.handler;

import org.example.model.Order;
import org.example.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentValidationHandler extends OrderValidationHandler {

    @Autowired
    private PaymentService paymentService; // This service handles payment processing

    @Override
    public void validate(Order order) {
        // Validate payment details for the order
        boolean paymentValid = paymentService.validatePayment(order);

        if (!paymentValid) {
            // If payment validation fails, halt processing with an exception
            throw new IllegalStateException("Payment validation failed for order: " + order.getId());
        }

        // Log successful payment validation
        System.out.println("Payment validation passed for order: " + order.getId());

        // Proceed to the next handler in the chain if validation succeeds
        if (nextHandler != null) {
            nextHandler.validate(order);
        }
    }

    @Override
    public void handle(Order order) {
        // Call the validate method to perform inventory checks
        validate(order);

        // Proceed to the next handler in the chain if it exists
        if (nextHandler != null) {
            nextHandler.handle(order);
        }
    }
}
