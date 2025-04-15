package com.hexaware.main;

import com.hexaware.dao.*;
import com.hexaware.entity.*;
import java.util.*;

public class OrderManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); 

        try {
            OrderProcessor processor = new OrderProcessor();

            while (true) {
                System.out.println("\n--- Order Management Menu ---");
                System.out.println("1. Create User");
                System.out.println("2. Create Product");
                System.out.println("3. Create Order");
                System.out.println("4. Cancel Order");
                System.out.println("5. View All Products");
                System.out.println("6. View Orders by User");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter User ID: ");
                        int uid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Username: ");
                        String uname = sc.nextLine();
                        System.out.print("Enter Password: ");
                        String pwd = sc.nextLine();
                        System.out.print("Enter Role (Admin/User): ");
                        String role = sc.nextLine();
                        processor.createUser(new User(uid, uname, pwd, role));
                        break;

                    case 2:
                        System.out.print("Enter Admin ID: ");
                        int aid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Product ID: ");
                        int pid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Product Name: ");
                        String pname = sc.nextLine();
                        System.out.print("Enter Description: ");
                        String desc = sc.nextLine();
                        System.out.print("Enter Price: ");
                        double price = sc.nextDouble();
                        System.out.print("Enter Quantity: ");
                        int qty = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Type (Electronics/Clothing): ");
                        String type = sc.nextLine();
                        processor.createProduct(new User(aid, "", "", "Admin"),
                                new Product(pid, pname, desc, price, qty, type));
                        break;

                    case 3:
                        System.out.print("Enter User ID for order: ");
                        int ouid = sc.nextInt();
                        sc.nextLine();
                        List<Product> productList = processor.getAllProducts();
                        System.out.println("Available Products:");
                        for (Product p : productList) {
                            System.out.println(p);
                        }
                        System.out.print("Enter Product ID to Order: ");
                        int orderPid = sc.nextInt();
                        List<Product> orderProducts = new ArrayList<>();
                        for (Product p : productList) {
                            if (p.getProductId() == orderPid) {
                                orderProducts.add(p);
                            }
                        }
                        processor.createOrder(new User(ouid, "", "", "User"), orderProducts);
                        break;

                    case 4:
                        System.out.print("Enter User ID: ");
                        int cuid = sc.nextInt();
                        System.out.print("Enter Order ID: ");
                        int oid = sc.nextInt();
                        processor.cancelOrder(cuid, oid);
                        break;

                    case 5:
                        List<Product> all = processor.getAllProducts();
                        for (Product p : all) {
                            System.out.println(p);
                        }
                        break;

                    case 6:
                        System.out.print("Enter User ID: ");
                        int viewUid = sc.nextInt();
                        List<Product> orders = processor.getOrderByUser(new User(viewUid, "", "", "User"));
                        for (Product p : orders) {
                            System.out.println(p);
                        }
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        sc.close(); 
                        System.exit(0);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}
