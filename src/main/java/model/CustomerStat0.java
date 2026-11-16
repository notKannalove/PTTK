package model;

public class CustomerStat0 extends Customer0 {
    private int invoiceCount;
    private float revenue;

    public CustomerStat0() {
        super();
    }

    public int getInvoiceCount() {
        return invoiceCount;
    }

    public void setInvoiceCount(int invoiceCount) {
        this.invoiceCount = invoiceCount;
    }

    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }
}
