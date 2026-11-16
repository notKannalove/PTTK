package dao;

import java.sql.PreparedStatement;

public class OrderDAO0 extends DAO0 {

    public OrderDAO0() {
        super();
    }

    /**
     * Khach hang huy don: chi huy duoc khi hoa don con Cho xu ly.
     * Chuyen hoa don sang Da huy, ghi ly do Khach hang huy cho don hang,
     * hoan lai so luong hang da giu vao ton kho. Ca ba buoc nam trong mot giao dich.
     * @return true neu huy thanh cong
     */
    public boolean cancelOrder(int orderId) {
        boolean result = false;
        String sqlInvoice = "UPDATE tblInvoice0 SET status = 'Đã hủy' "
                + "WHERE tblOrder0id = ? AND status = 'Chờ xử lý'";
        String sqlOrder = "UPDATE tblOrder0 SET cancellationReason = 'Khách hàng hủy' WHERE id = ?";
        String sqlStock = "UPDATE tblCommodity0 c JOIN tblOrderDetail0 d ON d.tblCommodity0id = c.id "
                + "SET c.stockQuantity = c.stockQuantity + d.quantity "
                + "WHERE d.tblOrder0id = ?";
        try {
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement(sqlInvoice);
            ps.setInt(1, orderId);
            if (ps.executeUpdate() == 1) {
                ps = con.prepareStatement(sqlOrder);
                ps.setInt(1, orderId);
                ps.executeUpdate();

                ps = con.prepareStatement(sqlStock);
                ps.setInt(1, orderId);
                ps.executeUpdate();

                con.commit();
                result = true;
            } else {
                con.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            result = false;
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
