import org.example.model.Order;
import org.example.model.PaymentDetails;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    // Validates the payment for a given order
    public boolean validatePayment(Order order) {
        if (order == null || order.getPaymentDetails() == null) {
            return false; // Invalid order or missing payment details
        }

        PaymentDetails paymentDetails = order.getPaymentDetails();
        
        // Check if the card number is present
        if (paymentDetails.getCardNumber() == null) {
            return false; // Card number is missing, validation failed
        }

        // Log the method used for payment validation
        logPaymentMethod(paymentDetails);

        // Additional validation logic can be added here (e.g., checking the payment status)

        return true; // If all validations pass
    }

    // Helper method to log payment method for better clarity
    private void logPaymentMethod(PaymentDetails paymentDetails) {
        System.out.println("Payment validated with method: " + paymentDetails.getPaymentMethod());
    }
}
