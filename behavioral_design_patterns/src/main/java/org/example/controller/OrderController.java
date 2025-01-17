package org.example.controller;

import org.example.dto.OrderDTO;
import org.example.model.Order;
import org.example.service.OrderService;
import org.example.exception.OrderNotFoundException;
import org.example.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found for id: " + id));
        return ResponseEntity.ok(orderMapper.toDTO(order));
    }

    // Create a new order
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        Order createdOrder = orderService.placeOrder(order);
        return ResponseEntity.ok(orderMapper.toDTO(createdOrder));
    }

    // Update existing order
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody @Valid OrderDTO orderDTO) {
        Order existingOrder = orderService.getOrderById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found for id: " + id));
        Order updatedOrder = orderMapper.toEntity(orderDTO);
        Order savedOrder = orderService.updateOrder(id, updatedOrder);
        return ResponseEntity.ok(orderMapper.toDTO(savedOrder));
    }

    // Delete order by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (!orderService.existsById(id)) {
            throw new OrderNotFoundException("Order not found for id: " + id);
        }
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
