package com.hexaware.test;

import static org.junit.jupiter.api.Assertions.*;
import com.hexaware.dao.AssetManagementService;
import com.hexaware.service.AssetManagementServiceImpl;
import com.hexaware.entity.Asset;
import com.hexaware.exception.AssetNotFoundException;
import com.hexaware.exception.AssetNotMaintainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

public class AssetMaintenanceTest {

    private AssetManagementService service;

    @BeforeEach
    public void setUp() {
        service = new AssetManagementServiceImpl();
    }

    @Test
    public void testAssetMaintenance() throws AssetNotMaintainException, SQLException, AssetNotFoundException {
        // Setup
        Asset asset = new Asset(2, "Laptop", "Electronics", "SN12345", LocalDate.now(), "New York", "Available", 2);
        service.addAsset(asset);  // Ensure the asset exists

        // Test
        boolean result = service.performMaintenance(asset.getAssetId(), LocalDate.now(), "Repair", 150.0);

        // Verify
        assertTrue(result, "Asset should be added to maintenance successfully.");
    }
}
