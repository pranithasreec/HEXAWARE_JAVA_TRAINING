package com.hexaware.dao;

import java.util.List;
import com.hexaware.entity.Product;
import com.hexaware.entity.User;

public interface IOrderManagementRepository 
{
    void createUser(User user) throws Exception;
    void createProduct(User user, Product product) throws Exception;
    void createOrder(User user, List<Product> products) throws Exception;
    void cancelOrder(int userId, int orderId) throws Exception;
    List<Product> getAllProducts() throws Exception;
    List<Product> getOrderByUser(User user) throws Exception;
}
