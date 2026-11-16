package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import model.SupplierStat0;

public class SupplierStatDAO0 extends DAO0 {

    public SupplierStatDAO0() {
        super();
    }

    /**
     * Thong ke nha cung cap theo doanh thu (tong gia tri nhap) trong khoang thoi gian,
     * xep giam dan theo tong gia tri nhap.
     */
    public SupplierStat0[] getSupplierStat(Date startDate, Date endDate) {
        ArrayList<SupplierStat0> list = new ArrayList<SupplierStat0>();
        String sql = "SELECT s.id, s.supplierName, s.address, s.phoneNumber, s.email, "
                + "COUNT(DISTINCT r.id) AS receiptCount, "
                + "SUM(d.quantity * d.purchasePrice) AS totalImportValue "
                + "FROM tblSupplier0 s "
                + "JOIN tblImportReceipt0 r ON r.tblSupplier0id = s.id "
                + "JOIN tblImportReceiptDetail0 d ON d.tblImportReceipt0id = r.id "
                + "WHERE r.importDate BETWEEN ? AND ? "
                + "GROUP BY s.id, s.supplierName, s.address, s.phoneNumber, s.email "
                + "ORDER BY totalImportValue DESC";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(startDate.getTime()));
            ps.setDate(2, new java.sql.Date(endDate.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SupplierStat0 stat = new SupplierStat0();
                stat.setId(rs.getInt("id"));
                stat.setSupplierName(rs.getString("supplierName"));
                stat.setAddress(rs.getString("address"));
                stat.setPhoneNumber(rs.getString("phoneNumber"));
                stat.setEmail(rs.getString("email"));
                stat.setReceiptCount(rs.getInt("receiptCount"));
                stat.setTotalImportValue(rs.getFloat("totalImportValue"));
                list.add(stat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list.toArray(new SupplierStat0[list.size()]);
    }
}
