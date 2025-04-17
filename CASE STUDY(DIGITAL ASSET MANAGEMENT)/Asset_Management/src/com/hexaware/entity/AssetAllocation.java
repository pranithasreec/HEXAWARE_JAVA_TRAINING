package com.hexaware.entity;

import java.time.LocalDate;

public class AssetAllocation {
    private int allocationId;
    private int assetId;
    private int employeeId;
    private LocalDate allocationDate;
    private LocalDate returnDate;

    public AssetAllocation() {}

    public AssetAllocation(int allocationId, int assetId, int employeeId, LocalDate allocationDate, LocalDate returnDate) {
        this.allocationId = allocationId;
        this.assetId = assetId;
        this.employeeId = employeeId;
        this.allocationDate = allocationDate;
        this.returnDate = returnDate;
    }

    public int getAllocationId() { return allocationId; }
    public void setAllocationId(int allocationId) { this.allocationId = allocationId; }

    public int getAssetId() { return assetId; }
    public void setAssetId(int assetId) { this.assetId = assetId; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public LocalDate getAllocationDate() { return allocationDate; }
    public void setAllocationDate(LocalDate allocationDate) { this.allocationDate = allocationDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
}
