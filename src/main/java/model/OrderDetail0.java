package model;

public class OrderDetail0 {
    private int id;
    private int quantity;
    private float unitPrice;
    private float lineTotal;
    private Commodity0 commodity;

    public OrderDetail0() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(float lineTotal) {
        this.lineTotal = lineTotal;
    }

    public Commodity0 getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity0 commodity) {
        this.commodity = commodity;
    }
}
