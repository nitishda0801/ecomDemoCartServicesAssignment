package com.ecom.ecomDemo.service;

import com.ecom.ecomDemo.dto.OrderSummary;
import com.ecom.ecomDemo.model.CartItem;
import com.ecom.ecomDemo.model.Product;
import com.ecom.ecomDemo.repositories.CartRepository;
import com.ecom.ecomDemo.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EcommerceService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public EcommerceService(ProductRepository productRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public CartItem addToCart(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock available");
        }

        CartItem cartItem = cartRepository.findByProductId(productId)
                .orElse(new CartItem());

        if (cartItem.getId() == null) {
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        return cartRepository.save(cartItem);
    }

    public List<CartItem> getCartItems() {
        return cartRepository.findAll();
    }

    public OrderSummary checkout() {
        List<CartItem> items = cartRepository.findAll();
        if (items.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double total = items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        return new OrderSummary(items, total);
    }

    @Transactional
    public String processPayment() {
        List<CartItem> items = cartRepository.findAll();
        if (items.isEmpty()) {
            throw new RuntimeException("No active order to pay for");
        }

        // Deduct Stock
        for (CartItem item : items) {
            Product product = item.getProduct();
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Stock deficit for product: " + product.getName());
            }
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }

        // Clear Cart after successful mock payment
        cartRepository.deleteAll();

        return "Payment of Successful. Order Placed!";
    }
}
