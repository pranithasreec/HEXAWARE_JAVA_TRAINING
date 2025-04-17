package com.hexaware.test;

import static org.junit.jupiter.api.Assertions.*;
import com.hexaware.dao.AssetManagementService;
import com.hexaware.service.AssetManagementServiceImpl;
import com.hexaware.exception.AssetNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

public class AssetNotFoundExceptionTest {

    private AssetManagementService service;

    @BeforeEach
    public void setUp() {
        service = new AssetManagementServiceImpl();
    }

    @Test
    public void testAssetNotFoundException() {
        int nonExistentAssetId = 9999; // not in DB
        int employee_id = 1; // should exist

        assertThrows(AssetNotFoundException.class, () -> {
            service.allocateAsset(nonExistentAssetId, employee_id, LocalDate.now());
        });
    }
    @Test
    public void testAssetAllocationSuccess() throws AssetNotFoundException, SQLException {
        int validAssetId = 2; 
        int validEmployeeId = 1;
        boolean result = service.allocateAsset(validAssetId, validEmployeeId, LocalDate.now());

        assertTrue(result, "Asset should be allocated successfully");
    }

}
