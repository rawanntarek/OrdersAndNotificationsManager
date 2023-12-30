package com.example.OrdersAndNotificationsManager;// MessageTemplate.java

import com.example.OrdersAndNotificationsManager.Customers.Customer;
import com.example.OrdersAndNotificationsManager.Orders.SimpleOrder;

import java.util.List;

public class MessageTemplate {
    public static String generateConfirmationMessage(Customer customer, SimpleOrder order) {
        // Customize the message as needed
        String username = customer.getEmail(); // You might want to use the customer's actual name
        List<String> productNames = order.getProductName();

        // For demonstration, using a placeholder for the product name
        String productName = productNames.isEmpty() ? "" : productNames.get(0);

        return "Dear " + username + ", your order of " + productName + " is confirmed";
    }
}
