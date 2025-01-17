package org.example.service;

import org.example.model.Order;
import org.example.model.OrderStatus;
import org.example.payment.PaymentStrategy;
import org.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private PaymentStrategy paymentStrategy;

    @Autowired
    private OrderRepository orderRepository;

    // Get an order by its ID
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // Place a new order and save it to the repository
    public Order placeOrder(Order order) {
        order.setStatus(String.valueOf(OrderStatus.PLACED));
        orderRepository.save(order);
        return order;
    }

    // Set the payment strategy for processing payment
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    // Process payment for an order
    public boolean processOrderPayment(double amount) {
        if (paymentStrategy == null) {
            throw new RuntimeException("Payment strategy not defined");
        }
        return paymentStrategy.pay(amount);
    }

    // Update an existing order
    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setCustomerName(updatedOrder.getCustomerName());
                    order.setTotalAmount(updatedOrder.getTotalAmount());
                    order.setStatus(updatedOrder.getStatus());
                    order.setPaymentDetails(updatedOrder.getPaymentDetails());
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // Delete an order by its ID
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(id);
    }
}
