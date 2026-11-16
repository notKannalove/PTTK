package model;

import java.util.Date;

public class ImportReceipt0 {
    private int id;
    private Date importDate;
    private float totalAmount;
    private String status;
    private Supplier0 supplier;
    private ManagementStaff0 managementStaff;
    private ImportReceiptDetail0[] details;

    public ImportReceipt0() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Supplier0 getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier0 supplier) {
        this.supplier = supplier;
    }

    public ManagementStaff0 getManagementStaff() {
        return managementStaff;
    }

    public void setManagementStaff(ManagementStaff0 managementStaff) {
        this.managementStaff = managementStaff;
    }

    public ImportReceiptDetail0[] getDetails() {
        return details;
    }

    public void setDetails(ImportReceiptDetail0[] details) {
        this.details = details;
    }
}
