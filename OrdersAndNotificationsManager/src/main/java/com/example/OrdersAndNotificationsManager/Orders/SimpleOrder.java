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

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

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

    public OrderStatus getStatus() {
        return status;
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
        if (customer.getBalance() >= total) {
            customer.setBalance(customer.getBalance() - total);
            status = OrderStatus.CONFIRMED;
            String confirmationStatus = "---CONFIRMED---";

            if (status == OrderStatus.CONFIRMED) {
                String shippingConfirmationResult = confirmShipping();
                if (status == OrderStatus.SHIPPED) {
                    confirmationStatus = "---SHIPPED---";
                }
                return "---Confirmed---" +" Purchased products: " + String.join(",", addedProducts) +  ".  Total Deducted Amount: " + total + " \n" +
                        confirmationStatus + shippingConfirmationResult;
            } else {
                return "Order placed but confirmation failed";
            }
        } else {
            return "Not enough balance";
        }
    }

    // Method to confirm shipping
    public String confirmShipping() {
        if (status == OrderStatus.CONFIRMED) {
            double totalWithShipping = calculateTotal() + shippingFee;
            if (customer.getBalance() >= totalWithShipping) {
                customer.setBalance(customer.getBalance() - shippingFee);
                status = OrderStatus.SHIPPED;
                return "Order has been shipped. Total Amount with Shipping: " + totalWithShipping;
            } else {
                return "Not enough balance to cover shipping fees";
            }
        } else {
            return "Order has not been confirmed yet";
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

}
