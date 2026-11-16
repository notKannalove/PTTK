package model;

import java.util.Date;

public class Order0 {
    private int id;
    private Date orderDate;
    private String shippingAddress;
    private float totalAmount;
    private String status;
    private String cancellationReason;
    private Customer0 customer;
    private OrderDetail0[] details;

    public Order0() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
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

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public Customer0 getCustomer() {
        return customer;
    }

    public void setCustomer(Customer0 customer) {
        this.customer = customer;
    }

    public OrderDetail0[] getDetails() {
        return details;
    }

    public void setDetails(OrderDetail0[] details) {
        this.details = details;
    }
}
