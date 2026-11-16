package view.management;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.ManagementStaff0;

/** Giao dien chinh cua nhan vien quan ly sau khi dang nhap. */
public class ManagementStaffFrm0 extends JFrame implements ActionListener {
    private JButton btnViewStatistics;
    private ManagementStaff0 staff;

    public ManagementStaffFrm0(ManagementStaff0 staff) {
        super("Trang chủ nhân viên quản lý");
        this.staff = staff;

        JLabel lblTitle = new JLabel("Trang chủ nhân viên quản lý", SwingConstants.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 18f));

        btnViewStatistics = new JButton("Xem thống kê báo cáo");
        btnViewStatistics.addActionListener(this);
        JPanel pnButton = new JPanel();
        pnButton.add(btnViewStatistics);

        JPanel pnMain = new JPanel(new BorderLayout(10, 30));
        pnMain.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        pnMain.add(lblTitle, BorderLayout.NORTH);
        pnMain.add(pnButton, BorderLayout.CENTER);
        setContentPane(pnMain);

        setSize(440, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnViewStatistics) {
            StatisticsSelectionFrm0 frm = new StatisticsSelectionFrm0(staff);
            // khi giao dien chon loai thong ke dong lai (Quay lai) thi hien lai giao dien nay
            frm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent we) {
                    setVisible(true);
                }
            });
            setVisible(false);
            frm.setVisible(true);
        }
    }
}
