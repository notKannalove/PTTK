package view.customer;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import dao.InvoiceDAO0;
import dao.OrderDAO0;
import model.Customer0;
import model.Invoice0;
import model.OrderDetail0;

/** Giao dien thong tin chi tiet va trang thai hien tai cua mot hoa don. */
public class InvoiceDetailFrm0 extends JFrame implements ActionListener {
    private JLabel lblInvoiceInfo;
    private JLabel lblCancelReason;
    private JTable tblItem;
    private JButton btnCancelOrder;
    private JButton btnBack;
    private Invoice0 invoice;
    private Customer0 customer;

    public InvoiceDetailFrm0(Customer0 customer, Invoice0 invoice) {
        super("Chi tiết hóa đơn");
        this.customer = customer;
        this.invoice = invoice;

        JLabel lblTitle = new JLabel("Chi tiết hóa đơn", SwingConstants.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 18f));

        lblInvoiceInfo = new JLabel();
        lblCancelReason = new JLabel();
        JPanel pnInfo = new JPanel(new BorderLayout());
        pnInfo.add(lblInvoiceInfo, BorderLayout.CENTER);
        pnInfo.add(lblCancelReason, BorderLayout.SOUTH);
        JPanel pnNorth = new JPanel(new BorderLayout(10, 10));
        pnNorth.add(lblTitle, BorderLayout.NORTH);
        pnNorth.add(pnInfo, BorderLayout.CENTER);

        tblItem = new JTable();
        tblItem.setRowHeight(52);

        btnCancelOrder = new JButton("Hủy đơn hàng");
        btnCancelOrder.addActionListener(this);
        btnBack = new JButton("Quay lại");
        btnBack.addActionListener(this);
        JPanel pnButton = new JPanel(new GridLayout(1, 2, 10, 0));
        pnButton.add(btnCancelOrder);
        pnButton.add(btnBack);
        JPanel pnSouth = new JPanel(new BorderLayout());
        pnSouth.add(pnButton, BorderLayout.EAST);

        JPanel pnMain = new JPanel(new BorderLayout(10, 10));
        pnMain.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnMain.add(pnNorth, BorderLayout.NORTH);
        pnMain.add(new JScrollPane(tblItem), BorderLayout.CENTER);
        pnMain.add(pnSouth, BorderLayout.SOUTH);
        setContentPane(pnMain);

        setSize(640, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        showDetail();
    }

    /** Lay thong tin chi tiet cua hoa don va hien len giao dien. */
    private void showDetail() {
        invoice = new InvoiceDAO0().getInvoiceDetail(invoice.getId());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DecimalFormat moneyFormat = new DecimalFormat("#,###", symbols);

        lblInvoiceInfo.setText("<html>Mã hóa đơn: " + invoice.getId()
                + "<br>Ngày đặt: " + dateFormat.format(invoice.getOrder().getOrderDate())
                + "<br>Địa chỉ giao: " + invoice.getOrder().getShippingAddress()
                + "<br>Tổng tiền: " + moneyFormat.format(invoice.getTotalAmount())
                + "<br>Trạng thái: " + invoice.getStatus() + "</html>");

        // ly do huy chi hien khi hoa don da huy
        boolean cancelled = invoice.getStatus().equals("Đã hủy");
        lblCancelReason.setText(cancelled
                ? "Lý do hủy: " + invoice.getOrder().getCancellationReason() : "");

        String[] columns = {"Tên hàng hóa", "Ảnh minh họa", "Số lượng", "Đơn giá", "Thành tiền"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 1 ? ImageIcon.class : Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (OrderDetail0 detail : invoice.getOrder().getDetails()) {
            model.addRow(new Object[] {
                detail.getCommodity().getCommodityName(),
                new ImageIcon(detail.getCommodity().getImage()),
                detail.getQuantity(),
                moneyFormat.format(detail.getUnitPrice()),
                moneyFormat.format(detail.getLineTotal())
            });
        }
        tblItem.setModel(model);

        // chi duoc huy khi hoa don con Cho xu ly
        btnCancelOrder.setVisible(invoice.getStatus().equals("Chờ xử lý"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCancelOrder) {
            String[] options = {"Có", "Không"};
            int choice = JOptionPane.showOptionDialog(this, "Bạn có chắc chắn muốn hủy đơn hàng?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[1]);
            if (choice == 0) {
                new OrderDAO0().cancelOrder(invoice.getOrder().getId());
                showDetail();
            }
        } else if (e.getSource() == btnBack) {
            dispose();
        }
    }
}
