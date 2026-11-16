package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import model.Commodity0;
import model.ImportReceipt0;
import model.ImportReceiptDetail0;
import model.ManagementStaff0;
import model.Supplier0;

public class ImportReceiptDAO0 extends DAO0 {

    private static final String SQL_RECEIPT = "SELECT r.id, r.importDate, r.status, "
            + "s.id AS supplierId, s.supplierName, m.id AS staffId, m.fullName, "
            + "(SELECT SUM(d.quantity * d.purchasePrice) FROM tblImportReceiptDetail0 d "
            + " WHERE d.tblImportReceipt0id = r.id) AS totalAmount "
            + "FROM tblImportReceipt0 r "
            + "JOIN tblSupplier0 s ON r.tblSupplier0id = s.id "
            + "JOIN tblMember0 m ON r.managementStaffid = m.id ";

    public ImportReceiptDAO0() {
        super();
    }

    /** Lay cac phieu nhap cua mot nha cung cap trong khoang thoi gian. */
    public ImportReceipt0[] getReceiptsBySupplier(int supplierId, Date startDate, Date endDate) {
        ArrayList<ImportReceipt0> list = new ArrayList<ImportReceipt0>();
        String sql = SQL_RECEIPT
                + "WHERE r.tblSupplier0id = ? AND r.importDate BETWEEN ? AND ? "
                + "ORDER BY r.importDate, r.id";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, supplierId);
            ps.setDate(2, new java.sql.Date(startDate.getTime()));
            ps.setDate(3, new java.sql.Date(endDate.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(readReceipt(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list.toArray(new ImportReceipt0[list.size()]);
    }

    /** Lay thong tin chi tiet cua mot phieu nhap: phieu nhap, cac chi tiet va hang hoa. */
    public ImportReceipt0 getImportReceiptDetail(int receiptId) {
        ImportReceipt0 receipt = null;
        String sql = SQL_RECEIPT + "WHERE r.id = ?";
        String sqlDetail = "SELECT d.id, d.quantity, d.purchasePrice, c.id AS commodityId, "
                + "c.commodityName "
                + "FROM tblImportReceiptDetail0 d JOIN tblCommodity0 c ON d.tblCommodity0id = c.id "
                + "WHERE d.tblImportReceipt0id = ? ORDER BY d.id";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, receiptId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                receipt = readReceipt(rs);

                ArrayList<ImportReceiptDetail0> details = new ArrayList<ImportReceiptDetail0>();
                PreparedStatement psDetail = con.prepareStatement(sqlDetail);
                psDetail.setInt(1, receiptId);
                ResultSet rsDetail = psDetail.executeQuery();
                while (rsDetail.next()) {
                    Commodity0 commodity = new Commodity0();
                    commodity.setId(rsDetail.getInt("commodityId"));
                    commodity.setCommodityName(rsDetail.getString("commodityName"));

                    ImportReceiptDetail0 detail = new ImportReceiptDetail0();
                    detail.setId(rsDetail.getInt("id"));
                    detail.setQuantity(rsDetail.getInt("quantity"));
                    detail.setPurchasePrice(rsDetail.getFloat("purchasePrice"));
                    detail.setLineTotal(detail.getQuantity() * detail.getPurchasePrice());
                    detail.setCommodity(commodity);
                    details.add(detail);
                }
                receipt.setDetails(details.toArray(new ImportReceiptDetail0[details.size()]));
            }
        } catch (Exception e) {
            e.printStackTrace();
            receipt = null;
        }
        return receipt;
    }

    /** Dong goi mot dong ket qua thanh ImportReceipt0 kem Supplier0 va ManagementStaff0. */
    private ImportReceipt0 readReceipt(ResultSet rs) throws Exception {
        Supplier0 supplier = new Supplier0();
        supplier.setId(rs.getInt("supplierId"));
        supplier.setSupplierName(rs.getString("supplierName"));

        ManagementStaff0 staff = new ManagementStaff0();
        staff.setId(rs.getInt("staffId"));
        staff.setFullName(rs.getString("fullName"));

        ImportReceipt0 receipt = new ImportReceipt0();
        receipt.setId(rs.getInt("id"));
        receipt.setImportDate(rs.getDate("importDate"));
        receipt.setStatus(rs.getString("status"));
        receipt.setTotalAmount(rs.getFloat("totalAmount"));
        receipt.setSupplier(supplier);
        receipt.setManagementStaff(staff);
        return receipt;
    }
}
