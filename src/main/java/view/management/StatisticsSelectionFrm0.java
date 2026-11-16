package view.management;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.ManagementStaff0;

/** Giao dien chon loai thong ke: nhap/xuat, khach hang, nha cung cap theo doanh thu. */
public class StatisticsSelectionFrm0 extends JFrame implements ActionListener {
    private JButton btnImportExportStat;
    private JButton btnCustomerStat;
    private JButton btnSupplierStat;
    private JButton btnBack;
    private ManagementStaff0 staff;

    public StatisticsSelectionFrm0(ManagementStaff0 staff) {
        super("Chọn loại thống kê");
        this.staff = staff;

        JLabel lblTitle = new JLabel("Chọn loại thống kê", SwingConstants.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 18f));

        btnImportExportStat = new JButton("Thống kê nhập/xuất");
        btnCustomerStat = new JButton("Thống kê khách hàng");
        btnSupplierStat = new JButton("Thống kê nhà cung cấp theo doanh thu");
        btnBack = new JButton("Quay lại");
        btnImportExportStat.addActionListener(this);
        btnCustomerStat.addActionListener(this);
        btnSupplierStat.addActionListener(this);
        btnBack.addActionListener(this);

        JPanel pnButton = new JPanel(new GridLayout(4, 1, 0, 10));
        pnButton.add(btnImportExportStat);
        pnButton.add(btnCustomerStat);
        pnButton.add(btnSupplierStat);
        pnButton.add(btnBack);

        JPanel pnMain = new JPanel(new BorderLayout(10, 15));
        pnMain.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
        pnMain.add(lblTitle, BorderLayout.NORTH);
        pnMain.add(pnButton, BorderLayout.CENTER);
        setContentPane(pnMain);

        setSize(440, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSupplierStat) {
            SupplierStatisticsFrm0 frm = new SupplierStatisticsFrm0(staff);
            // khi giao dien thong ke dong lai (Quay lai) thi hien lai giao dien nay
            frm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent we) {
                    setVisible(true);
                }
            });
            setVisible(false);
            frm.setVisible(true);
        } else if (e.getSource() == btnImportExportStat || e.getSource() == btnCustomerStat) {
            JOptionPane.showMessageDialog(this,
                    "Loại thống kê này nằm ngoài hai module đã cài đặt.");
        } else if (e.getSource() == btnBack) {
            dispose();
        }
    }
}
