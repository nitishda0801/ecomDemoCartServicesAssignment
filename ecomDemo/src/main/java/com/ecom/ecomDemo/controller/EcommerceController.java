package com.ecom.ecomDemo.controller;

import com.ecom.ecomDemo.dto.OrderSummary;
import com.ecom.ecomDemo.model.CartItem;
import com.ecom.ecomDemo.model.Product;
import com.ecom.ecomDemo.service.EcommerceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // for front-end integration
public class EcommerceController {
    private final EcommerceService ecommerceService;

    public EcommerceController(EcommerceService ecommerceService) {
        this.ecommerceService = ecommerceService;
    }

    // 1. Product Listing
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(ecommerceService.getAllProducts());
    }


    // 2. Add to Cart
    @PostMapping("/cart/add")
    public ResponseEntity<CartItem> addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        return ResponseEntity.ok(ecommerceService.addToCart(productId, quantity));
    }

    @GetMapping("/cart")
    public ResponseEntity<List<CartItem>> getCart() {
        return ResponseEntity.ok(ecommerceService.getCartItems());
    }

    // 3. Checkout (Order Summary)
    @GetMapping("/checkout")
    public ResponseEntity<OrderSummary> checkout() {
        return ResponseEntity.ok(ecommerceService.checkout());
    }

    // 4. Pay Now (Mock Payment)
    @PostMapping("/pay")
    public ResponseEntity<String> payNow() {
        return ResponseEntity.ok(ecommerceService.processPayment());
    }
}
