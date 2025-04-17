package com.hexaware.test;

import static org.junit.jupiter.api.Assertions.*;
import com.hexaware.dao.AssetManagementService;
import com.hexaware.service.AssetManagementServiceImpl;
import com.hexaware.entity.Asset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

public class AssetCreationTest {

    private AssetManagementService service;

    @BeforeEach
    public void setUp() {
        service = new AssetManagementServiceImpl();
    }

    @Test
    public void testAssetCreation() throws SQLException {
        Asset asset = new Asset(1, "Laptop", "Electronics", "SN12345", LocalDate.now(), "New York", "Available", 1);
        boolean result = service.addAsset(asset);
        assertTrue(result, "Asset should be created successfully.");
    }
}
