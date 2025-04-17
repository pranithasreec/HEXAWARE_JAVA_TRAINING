package com.hexaware.service;

import com.hexaware.entity.Asset;

import com.hexaware.exception.AssetNotFoundException;
import com.hexaware.exception.AssetNotMaintainException;
import com.hexaware.util.DBConnUtil;
import com.hexaware.dao.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AssetManagementServiceImpl implements AssetManagementService {

	@Override
	public boolean addAsset(Asset asset) {
	    String query = "INSERT INTO assets (name, type, serial_number, purchase_date, location, status, owner_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    try (Connection conn = DBConnUtil.getConnection();
	         PreparedStatement ps = conn.prepareStatement(query)) {

	        ps.setString(1, asset.getName());
	        ps.setString(2, asset.getType());
	        ps.setString(3, asset.getSerialNumber());
	        ps.setDate(4, Date.valueOf(asset.getPurchaseDate()));
	        ps.setString(5, asset.getLocation());
	        ps.setString(6, asset.getStatus());
	        ps.setInt(7, asset.getOwnerId());

	        int rowsAffected = ps.executeUpdate();
	        System.out.println("Rows affected: " + rowsAffected); 
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        System.out.println("Error adding asset: " + e.getMessage());
	        return false;
	    }
	}
	@Override
    public boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
    @Override
    public boolean addEmployee(String name, String department,String email, String password) {
        String sql = "INSERT INTO employees (name,department,email, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, department);
            ps.setString(3, email);
            ps.setString(4, password);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding employee: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateAsset(Asset asset) {
        String query = "UPDATE assets SET name=?, type=?, serial_number=?, purchase_date=?, location=?, status=?, owner_id=? WHERE asset_id=?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, asset.getName());
            ps.setString(2, asset.getType());
            ps.setString(3, asset.getSerialNumber());
            ps.setDate(4, Date.valueOf(asset.getPurchaseDate()));
            ps.setString(5, asset.getLocation());
            ps.setString(6, asset.getStatus());
            ps.setInt(7, asset.getOwnerId());
            ps.setInt(8, asset.getAssetId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating asset: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAsset(int assetId) {
        String query = "DELETE FROM assets WHERE asset_id=?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, assetId);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new AssetNotFoundException("Asset ID " + assetId + " not found.");
            }
            return true;
        } catch (SQLException | AssetNotFoundException e) {
            System.out.println("Error deleting asset: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean allocateAsset(int assetId, int employeeId, LocalDate allocationDate) throws AssetNotFoundException {
        System.out.println("Attempting to allocate asset with ID: " + assetId + " to employee ID: " + employeeId);
        String query = "INSERT INTO asset_allocations (asset_id, employee_id, allocation_date) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            // Check if the asset exists before allocation
            String checkAssetQuery = "SELECT COUNT(*) FROM assets WHERE asset_id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkAssetQuery)) {
                checkStmt.setInt(1, assetId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    System.out.println("Asset with ID " + assetId + " not found.");
                    throw new AssetNotFoundException("Asset with ID " + assetId + " not found.");
                }
            }

            // Proceed to allocate asset if exists
            ps.setInt(1, assetId);
            ps.setInt(2, employeeId);
            ps.setDate(3, java.sql.Date.valueOf(allocationDate));

            int rowsAffected = ps.executeUpdate();
            System.out.println("Allocation successful, rows affected: " + rowsAffected);

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error allocating asset: " + e.getMessage());
            return false;
        }
    }


    @Override
    public boolean deallocateAsset(int assetId, int employeeId, LocalDate returnDate) {
        String query = "UPDATE asset_allocations SET return_date=? WHERE asset_id=? AND employee_id=? AND return_date IS NULL";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setDate(1, java.sql.Date.valueOf(returnDate));
            ps.setInt(2, assetId);
            ps.setInt(3, employeeId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deallocating asset: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean performMaintenance(int assetId, LocalDate maintenanceDate, String description, double cost)
            throws AssetNotFoundException, AssetNotMaintainException, SQLException {
        System.out.println("Performing maintenance for Asset ID: " + assetId);
        if (cost <= 0) {
            throw new AssetNotMaintainException("Maintenance cost must be greater than 0.");
        }
        String checkQuery = "SELECT COUNT(*) FROM assets WHERE asset_id = ?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setInt(1, assetId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new AssetNotFoundException("Asset with ID " + assetId + " not found.");
            }
        }

        String query = "INSERT INTO maintenance_records (asset_id, maintenance_date, description, cost) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, assetId);
            ps.setDate(2, java.sql.Date.valueOf(maintenanceDate));
            ps.setString(3, description);
            ps.setDouble(4, cost);

            int rowsAffected = ps.executeUpdate();
            System.out.println("Maintenance record added, rows affected: " + rowsAffected); 

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error recording maintenance: " + e.getMessage());
            throw e; 
        }
    }

    @Override
    public boolean reserveAsset(int assetId, int employeeId, LocalDate reservationDate, LocalDate startDate, LocalDate endDate) {
        String query = "INSERT INTO reservations (asset_id, employee_id, reservation_date, start_date, end_date, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, assetId);
            ps.setInt(2, employeeId);
            ps.setDate(3, java.sql.Date.valueOf(reservationDate));
            ps.setDate(4, java.sql.Date.valueOf(startDate));
            ps.setDate(5, java.sql.Date.valueOf(endDate));
            ps.setString(6, "pending");

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error reserving asset: " + e.getMessage());
            return false;
        }
    }
    
    

    @Override
    public boolean withdrawReservation(int reservationId) {
        String query = "UPDATE reservations SET status='canceled' WHERE reservation_id=?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, reservationId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error withdrawing reservation: " + e.getMessage());
            return false;
        }
    }
        public List<Asset> getAllAssets() {
            List<Asset> assetList = new ArrayList<>();
            String query = "SELECT * FROM assets";

            try (Connection conn = DBConnUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Asset asset = new Asset();
                    asset.setAssetId(rs.getInt("asset_id"));
                    asset.setName(rs.getString("name"));
                    asset.setType(rs.getString("type"));
                    asset.setSerialNumber(rs.getString("serial_number"));
                    asset.setPurchaseDate(rs.getDate("purchase_date").toLocalDate());
                    asset.setLocation(rs.getString("location"));
                    asset.setStatus(rs.getString("status"));
                    asset.setOwnerId(rs.getInt("owner_id"));
                    assetList.add(asset);
                }
                

            } catch (SQLException e) {
                System.out.println("Error retrieving assets: " + e.getMessage());
            }

            return assetList;
        }
    }
