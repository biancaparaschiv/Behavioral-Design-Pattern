package org.example.command;

import org.example.model.Order;
import org.example.handler.InventoryCheckHandler;
import org.example.handler.PaymentValidationHandler;
import org.example.service.OrderService;
import org.example.exception.OrderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlaceOrderCommand implements OrderCommand {

    private static final Logger logger = LoggerFactory.getLogger(PlaceOrderCommand.class);

    private final OrderService orderService;
    private final InventoryCheckHandler inventoryHandler;
    private final PaymentValidationHandler paymentHandler;

    private Long orderId;

    @Autowired
    public PlaceOrderCommand(OrderService orderService, InventoryCheckHandler inventoryHandler, PaymentValidationHandler paymentHandler) {
        this.orderService = orderService;
        this.inventoryHandler = inventoryHandler;
        this.paymentHandler = paymentHandler;
    }

    // Setter method to set the order ID for which the command will be executed
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public void execute() {
        // Retrieve the order by ID with exception handling
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        logger.info("Executing PlaceOrderCommand for Order ID: {}", orderId);

        try {
            // Step 1: Check inventory
            inventoryHandler.handle(order);
            logger.info("Inventory check passed for Order ID: {}", orderId);

            // Step 2: Validate payment
            paymentHandler.handle(order);
            logger.info("Payment validation passed for Order ID: {}", orderId);

            // Step 3: Finalize order placement
            orderService.placeOrder(order);
            logger.info("Order placed successfully for Order ID: {}", orderId);

        } catch (Exception e) {
            logger.error("Error during order placement for Order ID: {}", orderId, e);
            throw e;  // Rethrow the exception after logging it
        }
    }
}
