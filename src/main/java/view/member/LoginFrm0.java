package view.member;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.MemberDAO0;
import model.Customer0;
import model.ManagementStaff0;
import model.Member0;
import view.customer.CustomerHomeFrm0;
import view.management.ManagementStaffFrm0;

/**
 * Giao dien dang nhap (use case Login0): diem bat dau cua chuong trinh,
 * thuc hien tien dieu kien "da dang nhap" cua hai module.
 */
public class LoginFrm0 extends JFrame implements ActionListener {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblMessage;

    public LoginFrm0() {
        super("Đăng nhập");

        JLabel lblTitle = new JLabel("Đăng nhập", SwingConstants.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 18f));

        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        JPanel pnInput = new JPanel(new GridLayout(2, 2, 8, 8));
        pnInput.add(new JLabel("Tên đăng nhập:"));
        pnInput.add(txtUsername);
        pnInput.add(new JLabel("Mật khẩu:"));
        pnInput.add(txtPassword);

        lblMessage = new JLabel(" ", SwingConstants.CENTER);
        lblMessage.setForeground(Color.RED);
        btnLogin = new JButton("Đăng nhập");
        btnLogin.addActionListener(this);
        JPanel pnButton = new JPanel();
        pnButton.add(btnLogin);
        JPanel pnSouth = new JPanel(new GridLayout(2, 1));
        pnSouth.add(lblMessage);
        pnSouth.add(pnButton);

        JPanel pnMain = new JPanel(new BorderLayout(10, 15));
        pnMain.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        pnMain.add(lblTitle, BorderLayout.NORTH);
        pnMain.add(pnInput, BorderLayout.CENTER);
        pnMain.add(pnSouth, BorderLayout.SOUTH);
        setContentPane(pnMain);

        setSize(380, 240);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());
            Member0 member = new MemberDAO0().checkLogin(username, password);
            if (member == null) {
                lblMessage.setText("Sai tên đăng nhập hoặc mật khẩu");
            } else if (member instanceof Customer0) {
                new CustomerHomeFrm0((Customer0) member).setVisible(true);
                dispose();
            } else if (member instanceof ManagementStaff0) {
                new ManagementStaffFrm0((ManagementStaff0) member).setVisible(true);
                dispose();
            } else {
                lblMessage.setText("Vai trò này chưa có giao diện trong hai module đã cài đặt");
            }
        }
    }

    public static void main(String[] args) {
        new LoginFrm0().setVisible(true);
    }
}
