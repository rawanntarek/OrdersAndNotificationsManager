package com.example.OrdersAndNotificationsManager.Orders;

import com.example.OrdersAndNotificationsManager.Customers.Customer;
import com.example.OrdersAndNotificationsManager.Notifications.MessageTemplate;
import com.example.OrdersAndNotificationsManager.Products.DummyProductList;
import com.example.OrdersAndNotificationsManager.Products.Products;

import java.util.ArrayList;
import java.util.List;

public class SimpleOrder implements Order {
    private Customer customer;
    private List<Products> products;
    private double shippingFee;


    // Constructor
    public SimpleOrder(Customer customer) {
        this.customer = customer;
        this.products = new ArrayList<>();
        this.shippingFee = 50.0;
    }

    @Override
    public String placeorder(List<String> ProductName) {
        List<Products> productss = DummyProductList.getDummyProducts();
        List<String> addedproducts = new ArrayList<>();
        for (String productName : ProductName) {
            boolean productFound = false;
            for (Products p : productss) {
                if (p.getName().equals(productName)) {
                    products.add(p);
                    addedproducts.add(productName);
                    productFound = true;
                    break;


                }

            }
            if (!productFound) {
                return "Product not available:" + productName;
            }

        }
        double total = calculateTotal();
        if (customer.getBalance() >= total) {
            customer.setBalance(customer.getBalance() - total - shippingFee);

            return "Purchased products: "+String.join(",", addedproducts) +
                    "Total Deducted Amount: " + total + "  shipping fee " + shippingFee ;
        } else {
            return "No enough balance";
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
        String orderDetails = "Simple Order: " + customer.getEmail() + ", Products:";

        for (int i = 0; i < products.size(); i++) {
            orderDetails += " Product: " + products.get(i).getName() + ", Price: " + products.get(i).getPrice() + " & ";
        }

        String totalamount_shipping="Total amount: "+calculateTotal()+" ,Shipping fee: "+shippingFee;
        return orderDetails+totalamount_shipping;
    }
    public String generateConfirmationMessage() {
        List<String> addedProducts = new ArrayList<>();
        for (Products product : products) {
            addedProducts.add(product.getName());
        }

        return MessageTemplate.generateConfirmationMessage(customer.getEmail(), addedProducts);
    }


}
