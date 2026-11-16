package model;

public class ImportReceiptDetail0 {
    private int id;
    private int quantity;
    private float purchasePrice;
    private float lineTotal;
    private Commodity0 commodity;

    public ImportReceiptDetail0() {
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

    public float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(float purchasePrice) {
        this.purchasePrice = purchasePrice;
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
