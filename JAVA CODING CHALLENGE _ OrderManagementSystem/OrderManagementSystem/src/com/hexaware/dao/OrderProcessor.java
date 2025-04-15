package com.hexaware.dao;

import java.sql.*;
import java.util.*;
import com.hexaware.entity.*;
import com.hexaware.util.DBConnUtil;
import com.hexaware.exception.*;

public class OrderProcessor implements IOrderManagementRepository {

    private Connection conn;

    public OrderProcessor() throws Exception {
        conn = DBConnUtil.getDBConn();
    }

    @Override
    public void createUser(User user) throws SQLException {
        String query = "INSERT INTO users (userId, username, password, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, user.getUserId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.executeUpdate();
        }
    }

    @Override
    public void createProduct(User user, Product product) throws Exception {
        if (!user.getRole().equalsIgnoreCase("Admin")) {
            throw new Exception("Only admin can create products.");
        }

        String query = "INSERT INTO products (productId, productName, description, price, quantityInStock, type) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, product.getProductId());
            stmt.setString(2, product.getProductName());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getQuantityInStock());
            stmt.setString(6, product.getType());
            stmt.executeUpdate();
        }
    }

    @Override
    public void createOrder(User user, List<Product> products) throws Exception {
        String checkUser = "SELECT * FROM users WHERE userId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(checkUser)) {
            stmt.setInt(1, user.getUserId());
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                createUser(user);
            }
        }

        String orderQuery = "INSERT INTO orders (userId) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, user.getUserId());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                int orderId = keys.getInt(1);

                String itemQuery = "INSERT INTO order_items (orderId, productId) VALUES (?, ?)";
                for (Product p : products) {
                    try (PreparedStatement itemStmt = conn.prepareStatement(itemQuery)) {
                        itemStmt.setInt(1, orderId);
                        itemStmt.setInt(2, p.getProductId());
                        itemStmt.executeUpdate();
                    }
                }
            }
        }
    }

    @Override
    public void cancelOrder(int userId, int orderId) throws Exception {
        String check = "SELECT * FROM orders WHERE orderId = ? AND userId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(check)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new OrderNotFoundException("Order not found for userId: " + userId);
            }
        }

        String deleteItems = "DELETE FROM order_items WHERE orderId = ?";
        String deleteOrder = "DELETE FROM orders WHERE orderId = ?";
        try (PreparedStatement itemStmt = conn.prepareStatement(deleteItems);
             PreparedStatement orderStmt = conn.prepareStatement(deleteOrder)) {

            itemStmt.setInt(1, orderId);
            itemStmt.executeUpdate();

            orderStmt.setInt(1, orderId);
            orderStmt.executeUpdate();
        }
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM products";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("productId"),
                    rs.getString("productName"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("quantityInStock"),
                    rs.getString("type")
                );
                list.add(p);
            }
        }
        return list;
    }

    @Override
    public List<Product> getOrderByUser(User user) throws SQLException {
        List<Product> list = new ArrayList<>();
        String query = "SELECT p.* FROM products p JOIN order_items oi ON p.productId = oi.productId JOIN orders o ON oi.orderId = o.orderId WHERE o.userId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, user.getUserId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("productId"),
                    rs.getString("productName"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("quantityInStock"),
                    rs.getString("type")
                );
                list.add(p);
            }
        }
        return list;
    }
}
