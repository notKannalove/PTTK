package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Customer0;
import model.DeliveryStaff0;
import model.ManagementStaff0;
import model.Member0;
import model.SaleStaff0;
import model.Staff0;

public class MemberDAO0 extends DAO0 {

    public MemberDAO0() {
        super();
    }

    /**
     * Kiem tra dang nhap.
     * @return thanh vien dung vai tro (Customer0, ManagementStaff0, SaleStaff0, DeliveryStaff0),
     *         hoac null neu sai ten dang nhap hoac mat khau
     */
    public Member0 checkLogin(String username, String password) {
        Member0 result = null;
        String sql = "SELECT m.*, c.customerCode, s.staffCode, s.jobPosition "
                + "FROM tblMember0 m "
                + "LEFT JOIN tblCustomer0 c ON c.tblMember0id = m.id "
                + "LEFT JOIN tblStaff0 s ON s.tblMember0id = m.id "
                + "WHERE m.username = ? AND m.password = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String jobPosition = rs.getString("jobPosition");
                if (rs.getString("customerCode") != null) {
                    Customer0 customer = new Customer0();
                    customer.setCustomerCode(rs.getString("customerCode"));
                    result = customer;
                } else if (jobPosition != null) {
                    Staff0 staff;
                    if (jobPosition.equals("Nhân viên quản lý")) {
                        staff = new ManagementStaff0();
                    } else if (jobPosition.equals("Nhân viên bán hàng")) {
                        staff = new SaleStaff0();
                    } else {
                        staff = new DeliveryStaff0();
                    }
                    staff.setStaffCode(rs.getString("staffCode"));
                    staff.setJobPosition(jobPosition);
                    result = staff;
                }
                if (result != null) {
                    result.setId(rs.getInt("id"));
                    result.setUsername(rs.getString("username"));
                    result.setPassword(rs.getString("password"));
                    result.setFullName(rs.getString("fullName"));
                    result.setAddress(rs.getString("address"));
                    result.setDateOfBirth(rs.getDate("dateOfBirth"));
                    result.setEmail(rs.getString("email"));
                    result.setPhoneNumber(rs.getString("phoneNumber"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }
}
