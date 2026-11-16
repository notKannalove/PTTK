package view.customer;

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

import model.Customer0;

/** Giao dien chinh cua khach hang sau khi dang nhap. */
public class CustomerHomeFrm0 extends JFrame implements ActionListener {
    private JButton btnTrackInvoice;
    private Customer0 customer;

    public CustomerHomeFrm0(Customer0 customer) {
        super("Trang chủ khách hàng");
        this.customer = customer;

        JLabel lblTitle = new JLabel("Trang chủ khách hàng", SwingConstants.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 18f));

        btnTrackInvoice = new JButton("Theo dõi hóa đơn");
        btnTrackInvoice.addActionListener(this);
        JPanel pnButton = new JPanel();
        pnButton.add(btnTrackInvoice);

        JPanel pnMain = new JPanel(new BorderLayout(10, 30));
        pnMain.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        pnMain.add(lblTitle, BorderLayout.NORTH);
        pnMain.add(pnButton, BorderLayout.CENTER);
        setContentPane(pnMain);

        setSize(420, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnTrackInvoice) {
            InvoiceTrackingFrm0 frm = new InvoiceTrackingFrm0(customer);
            // khi giao dien danh sach hoa don dong lai (Quay lai) thi hien lai giao dien nay
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
