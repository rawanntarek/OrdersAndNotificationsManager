package com.example.OrdersAndNotificationsManager.Orders;

import com.example.OrdersAndNotificationsManager.Customers.Customer;
import com.example.OrdersAndNotificationsManager.Notifications.NotificationObserver;
import com.example.OrdersAndNotificationsManager.Notifications.NotificationSubject;
import com.example.OrdersAndNotificationsManager.Products.DummyProductList;
import com.example.OrdersAndNotificationsManager.Products.Products;

import java.util.ArrayList;
import java.util.List;

public class SimpleOrder implements Order  {
    private Customer customer;
    private List<Products> products;
    private List<NotificationObserver> notificationObservers = new ArrayList<>();
    private double shippingFee;
    private OrderStatus status;



    public enum OrderStatus {
        PLACED,
        CONFIRMED,
        SHIPPED
    }

    // Constructor
    public SimpleOrder(Customer customer) {
        this.customer = customer;
        this.products = new ArrayList<>();
        this.shippingFee = 50.0;
        this.status = OrderStatus.PLACED;
    }

    @Override
    public String placeorder(List<String> ProductName) {
        List<Products> availableProducts = DummyProductList.getDummyProducts();
        List<String> addedProducts = new ArrayList<>();

        for (String productName : ProductName) {
            boolean productFound = false;
            for (Products p : availableProducts) {
                if (p.getName().equals(productName)) {
                    products.add(p);
                    addedProducts.add(productName);
                    productFound = true;
                    break;
                }
            }
            if (!productFound) {
                return "Product not available: " + productName;
            }
        }

        double total = calculateTotal();
        if (customer.getBalance() >= total + shippingFee) {
            customer.setBalance(customer.getBalance() - total - shippingFee);
            status = OrderStatus.CONFIRMED;
            if (status != OrderStatus.SHIPPED) {
                status = OrderStatus.CONFIRMED;
            }
            return "---" + status + "---  Purchased products: " + String.join(",", addedProducts) +
                    ". Total Deducted Amount: " + total + ". Shipping fee: " + shippingFee;

        } else {
            return "Not enough balance";
        }
    }


    public double calculateTotal() {
        double total = 0.0;
        for (Products product : products) {
            total += product.getPrice();
        }
        return total;
    }

    public String getOrderDetails() {
        String orderDetails = "Simple Order: " + customer.getEmail() + ", Products: ";

        for (int i = 0; i < products.size(); i++) {
            orderDetails += " Product: " + products.get(i).getName() + ", Price: " + products.get(i).getPrice() + " & ";
        }

        String totalAmountShipping = "Total amount: " + calculateTotal() + ", Shipping fee: " + shippingFee;
        return orderDetails + totalAmountShipping ;
    }


    public OrderStatus getStatus() {
        return status;
    }
}
