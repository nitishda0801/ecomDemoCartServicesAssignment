package com.ecom.ecomDemo.dto;

import com.ecom.ecomDemo.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderSummary {
    private List<CartItem> items;
    private double totalAmount;
}
