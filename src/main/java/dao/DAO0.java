package dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Lop DAO dung chung: giu mot ket noi den CSDL cho toan he thong.
 * Sua dbUrl, username, password cho phu hop voi cau hinh MySQL tren may.
 */
public class DAO0 {
    public static Connection con;

    public DAO0() {
        if (con == null) {
            String dbUrl = "jdbc:mysql://localhost:3306/department_store"
                + "?useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true";
            String username = "root";
            String password = "kanna210805";
            try {
                con = DriverManager.getConnection(dbUrl, username, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
