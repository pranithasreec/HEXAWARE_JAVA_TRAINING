package com.hexaware.test;

import static org.junit.jupiter.api.Assertions.*;
import com.hexaware.dao.AssetManagementService;
import com.hexaware.service.AssetManagementServiceImpl;
import com.hexaware.entity.Asset;
import com.hexaware.exception.AssetNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

public class AssetReservationTest {

    private AssetManagementService service;

    @BeforeEach
    public void setUp() {
        service = new AssetManagementServiceImpl();
    }

    @Test
    public void testAssetReservation() throws SQLException, AssetNotFoundException {
        // Setup
        Asset asset = new Asset(2, "Laptop", "Electronics", "SN12345", LocalDate.now(), "New York", "Available", 2);
        service.addAsset(asset);  // Ensure the asset exists

        // Test
        boolean result = service.reserveAsset(asset.getAssetId(), 2, LocalDate.now(), LocalDate.now().plusDays(1), LocalDate.now().plusDays(5));
        System.out.println("Reservation result: " + result);  

        // Verify
        assertTrue(result, "Asset should be reserved successfully.");
    }
}
