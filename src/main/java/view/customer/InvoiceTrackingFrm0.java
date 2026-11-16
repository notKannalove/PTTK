package view.customer;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import dao.InvoiceDAO0;
import model.Customer0;
import model.Invoice0;

/** Giao dien danh sach hoa don ma khach hang da dat. */
public class InvoiceTrackingFrm0 extends JFrame implements ActionListener {
    private JTable tblInvoice;
    private JLabel lblMessage;
    private JButton btnBack;
    private Invoice0[] listInvoice;
    private Customer0 customer;

    public InvoiceTrackingFrm0(Customer0 customer) {
        super("Danh sách hóa đơn");
        this.customer = customer;

        JLabel lblTitle = new JLabel("Danh sách hóa đơn", SwingConstants.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 18f));

        tblInvoice = new JTable();
        tblInvoice.setRowHeight(24);
        tblInvoice.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblInvoice.rowAtPoint(e.getPoint());
                if (row >= 0 && row < listInvoice.length) {
                    openInvoiceDetail(listInvoice[row]);
                }
            }
        });

        lblMessage = new JLabel(" ");
        btnBack = new JButton("Quay lại");
        btnBack.addActionListener(this);
        JPanel pnSouth = new JPanel(new BorderLayout());
        pnSouth.add(lblMessage, BorderLayout.CENTER);
        pnSouth.add(btnBack, BorderLayout.EAST);

        JPanel pnMain = new JPanel(new BorderLayout(10, 10));
        pnMain.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnMain.add(lblTitle, BorderLayout.NORTH);
        pnMain.add(new JScrollPane(tblInvoice), BorderLayout.CENTER);
        pnMain.add(pnSouth, BorderLayout.SOUTH);
        setContentPane(pnMain);

        setSize(560, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        loadInvoices();
    }

    /** Lay danh sach hoa don cua khach hang va hien len bang. */
    private void loadInvoices() {
        listInvoice = new InvoiceDAO0().getInvoicesByCustomer(customer.getId());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DecimalFormat moneyFormat = new DecimalFormat("#,###", symbols);

        String[] columns = {"Mã hóa đơn", "Ngày đặt", "Tổng tiền", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Invoice0 invoice : listInvoice) {
            model.addRow(new Object[] {
                invoice.getId(),
                dateFormat.format(invoice.getOrder().getOrderDate()),
                moneyFormat.format(invoice.getTotalAmount()),
                invoice.getStatus()
            });
        }
        tblInvoice.setModel(model);
        lblMessage.setText(listInvoice.length == 0 ? "Chưa có hóa đơn" : " ");
    }

    private void openInvoiceDetail(Invoice0 invoice) {
        InvoiceDetailFrm0 frm = new InvoiceDetailFrm0(customer, invoice);
        // khi giao dien chi tiet dong lai (Quay lai) thi nap lai danh sach va hien lai giao dien nay
        frm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent we) {
                loadInvoices();
                setVisible(true);
            }
        });
        setVisible(false);
        frm.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            dispose();
        }
    }
}
