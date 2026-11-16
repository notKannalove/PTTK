package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Commodity0;
import model.Invoice0;
import model.Order0;
import model.OrderDetail0;

public class InvoiceDAO0 extends DAO0 {

    public InvoiceDAO0() {
        super();
    }

    /**
     * Lay danh sach hoa don ma mot khach hang da dat, moi nhat xep truoc.
     * Tong tien la du lieu dan xuat, tinh tu cac chi tiet don hang.
     */
    public Invoice0[] getInvoicesByCustomer(int customerId) {
        ArrayList<Invoice0> list = new ArrayList<Invoice0>();
        String sql = "SELECT i.id, i.issueDate, i.status, o.id AS orderId, o.orderDate, "
                + "o.shippingAddress, o.cancellationReason, "
                + "(SELECT SUM(d.quantity * d.unitPrice) FROM tblOrderDetail0 d "
                + " WHERE d.tblOrder0id = o.id) AS totalAmount "
                + "FROM tblInvoice0 i JOIN tblOrder0 o ON i.tblOrder0id = o.id "
                + "WHERE o.tblCustomer0id = ? "
                + "ORDER BY o.orderDate DESC, i.id DESC";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(readInvoice(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list.toArray(new Invoice0[list.size()]);
    }

    /**
     * Lay thong tin chi tiet cua mot hoa don: hoa don, don hang tuong ung,
     * cac chi tiet don hang va hang hoa.
     */
    public Invoice0 getInvoiceDetail(int invoiceId) {
        Invoice0 invoice = null;
        String sql = "SELECT i.id, i.issueDate, i.status, o.id AS orderId, o.orderDate, "
                + "o.shippingAddress, o.cancellationReason, "
                + "(SELECT SUM(d.quantity * d.unitPrice) FROM tblOrderDetail0 d "
                + " WHERE d.tblOrder0id = o.id) AS totalAmount "
                + "FROM tblInvoice0 i JOIN tblOrder0 o ON i.tblOrder0id = o.id "
                + "WHERE i.id = ?";
        String sqlDetail = "SELECT d.id, d.quantity, d.unitPrice, c.id AS commodityId, "
                + "c.commodityName, c.image "
                + "FROM tblOrderDetail0 d JOIN tblCommodity0 c ON d.tblCommodity0id = c.id "
                + "WHERE d.tblOrder0id = ? ORDER BY d.id";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, invoiceId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                invoice = readInvoice(rs);

                ArrayList<OrderDetail0> details = new ArrayList<OrderDetail0>();
                PreparedStatement psDetail = con.prepareStatement(sqlDetail);
                psDetail.setInt(1, invoice.getOrder().getId());
                ResultSet rsDetail = psDetail.executeQuery();
                while (rsDetail.next()) {
                    Commodity0 commodity = new Commodity0();
                    commodity.setId(rsDetail.getInt("commodityId"));
                    commodity.setCommodityName(rsDetail.getString("commodityName"));
                    commodity.setImage(rsDetail.getString("image"));

                    OrderDetail0 detail = new OrderDetail0();
                    detail.setId(rsDetail.getInt("id"));
                    detail.setQuantity(rsDetail.getInt("quantity"));
                    detail.setUnitPrice(rsDetail.getFloat("unitPrice"));
                    detail.setLineTotal(detail.getQuantity() * detail.getUnitPrice());
                    detail.setCommodity(commodity);
                    details.add(detail);
                }
                invoice.getOrder().setDetails(details.toArray(new OrderDetail0[details.size()]));
            }
        } catch (Exception e) {
            e.printStackTrace();
            invoice = null;
        }
        return invoice;
    }

    /** Dong goi mot dong ket qua thanh Invoice0 kem Order0. */
    private Invoice0 readInvoice(ResultSet rs) throws Exception {
        Order0 order = new Order0();
        order.setId(rs.getInt("orderId"));
        order.setOrderDate(rs.getDate("orderDate"));
        order.setShippingAddress(rs.getString("shippingAddress"));
        order.setCancellationReason(rs.getString("cancellationReason"));
        order.setTotalAmount(rs.getFloat("totalAmount"));
        order.setStatus(rs.getString("status"));

        Invoice0 invoice = new Invoice0();
        invoice.setId(rs.getInt("id"));
        invoice.setIssueDate(rs.getDate("issueDate"));
        invoice.setStatus(rs.getString("status"));
        invoice.setTotalAmount(rs.getFloat("totalAmount"));
        invoice.setOrder(order);
        return invoice;
    }
}
