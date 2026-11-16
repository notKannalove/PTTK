package model;

import java.util.Date;

public class Invoice0 {
    private int id;
    private Date issueDate;
    private float totalAmount;
    private String status;
    private Order0 order;
    private SaleStaff0 saleStaff;
    private DeliveryStaff0 deliveryStaff;

    public Invoice0() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
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

    public Order0 getOrder() {
        return order;
    }

    public void setOrder(Order0 order) {
        this.order = order;
    }

    public SaleStaff0 getSaleStaff() {
        return saleStaff;
    }

    public void setSaleStaff(SaleStaff0 saleStaff) {
        this.saleStaff = saleStaff;
    }

    public DeliveryStaff0 getDeliveryStaff() {
        return deliveryStaff;
    }

    public void setDeliveryStaff(DeliveryStaff0 deliveryStaff) {
        this.deliveryStaff = deliveryStaff;
    }
}
