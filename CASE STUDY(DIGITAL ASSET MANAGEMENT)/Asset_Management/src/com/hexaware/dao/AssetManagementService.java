package com.hexaware.dao;

import com.hexaware.entity.Asset;

import com.hexaware.exception.AssetNotFoundException;
import com.hexaware.exception.AssetNotMaintainException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface AssetManagementService {
    boolean addAsset(Asset asset) throws SQLException;
    boolean addEmployee(String name, String department,String email, String password);
    boolean isValidEmail(String email);
    boolean updateAsset(Asset asset) throws SQLException, AssetNotFoundException;
    boolean deleteAsset(int assetId) throws SQLException, AssetNotFoundException;
    boolean allocateAsset(int assetId, int employeeId, LocalDate allocationDate) throws SQLException, AssetNotFoundException;
    boolean deallocateAsset(int assetId, int employeeId, LocalDate returnDate) throws SQLException, AssetNotFoundException;
    boolean performMaintenance(int assetId, LocalDate maintenanceDate, String description, double cost) throws SQLException, AssetNotFoundException , AssetNotMaintainException ;
    boolean reserveAsset(int assetId, int employeeId, LocalDate reservationDate, LocalDate startDate, LocalDate endDate) throws SQLException, AssetNotFoundException;   
    boolean withdrawReservation(int reservationId) throws SQLException, AssetNotFoundException;
    List<Asset> getAllAssets() throws SQLException;
}