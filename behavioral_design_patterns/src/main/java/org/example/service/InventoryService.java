package org.example.service;

import org.example.model.Inventory;
import org.example.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    // Check if there is enough stock available for a given product and quantity
    public boolean isInStock(Long productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        Inventory inventory = inventoryRepository.findByProductId(productId);

        if (inventory == null) {
            throw new RuntimeException("Inventory not found for product ID: " + productId);
        }

        return inventory.getAvailableQuantity() >= quantity;
    }
}
