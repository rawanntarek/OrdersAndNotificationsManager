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

    @GetMapping("/email-confirmation/{email}")
    public String sendEmailOrderConfirmation(@PathVariable String email) {
        Customer customer = customerService.getCustomerByEmail(email);
        if (customer == null) {
            return "Customer not found";
        }

        List<SimpleOrder> simpleOrders = customer.getSimpleOrders();
        if (simpleOrders.isEmpty()) {
            return "No orders found for the customer";
        }
        SimpleOrder latestOrder = simpleOrders.get(simpleOrders.size() - 1);

        String confirmationMessage = MessageTemplate.generateConfirmationMessage(customer, latestOrder);
        return confirmationMessage;
    }

    @GetMapping("/sms-confirmation/{phonenum}")
    public String sendSMSOrderConfirmation(@PathVariable String phonenum) {
        Customer customer = customerService.getCustomerByphoneNum(phonenum);
        if (customer == null) {
            return "Customer not found";
        }

        List<SimpleOrder> simpleOrders = customer.getSimpleOrders();
        if (simpleOrders.isEmpty()) {
            return "No orders found for the customer";
        }
        SimpleOrder latestOrder = simpleOrders.get(simpleOrders.size() - 1);

        String confirmationMessage = MessageTemplate.generateConfirmationMessage(customer, latestOrder);
        return confirmationMessage;
    }
}
