// NotificationsController.java
package com.example.OrdersAndNotificationsManager;

import com.example.OrdersAndNotificationsManager.Customers.Customer;
import com.example.OrdersAndNotificationsManager.Customers.CustomerService;
import com.example.OrdersAndNotificationsManager.Orders.SimpleOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationsController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/confirmation/{email}")
    public String sendOrderConfirmation(@PathVariable String email) {
        Customer customer = customerService.getCustomerByEmail(email);
        if (customer == null) {
            return "Customer not found";
        }

        List<SimpleOrder> simpleOrders = customer.getSimpleOrders();
        if (simpleOrders.isEmpty()) {
            return "No orders found for the customer";
        }

        // Get the latest simple order for demonstration purposes
        SimpleOrder latestOrder = simpleOrders.get(simpleOrders.size() - 1);

        // Generate confirmation message

        String confirmationMessage=MessageTemplate.generateConfirmationMessage(customer,latestOrder);
        // You can send the confirmation message through your preferred notification mechanism
        // For demonstration, let's just return the message
        return confirmationMessage;
    }
}
