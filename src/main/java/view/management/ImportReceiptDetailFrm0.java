package view.management;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import dao.ImportReceiptDAO0;
import model.ImportReceipt0;
import model.ImportReceiptDetail0;
import model.ManagementStaff0;

/** Giao dien chi tiet mot phieu nhap duoc chon. */
public class ImportReceiptDetailFrm0 extends JFrame implements ActionListener {
    private JLabel lblReceiptInfo;
    private JTable tblItem;
    private JButton btnBack;
    private ImportReceipt0 receipt;
    private ManagementStaff0 staff;

    public ImportReceiptDetailFrm0(ManagementStaff0 staff, ImportReceipt0 receipt) {
        super("Chi tiết phiếu nhập");
        this.staff = staff;
        this.receipt = receipt;

        JLabel lblTitle = new JLabel("Chi tiết phiếu nhập", SwingConstants.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 18f));

        lblReceiptInfo = new JLabel();
        JPanel pnNorth = new JPanel(new BorderLayout(10, 10));
        pnNorth.add(lblTitle, BorderLayout.NORTH);
        pnNorth.add(lblReceiptInfo, BorderLayout.CENTER);

        tblItem = new JTable();
        tblItem.setRowHeight(24);

        btnBack = new JButton("Quay lại");
        btnBack.addActionListener(this);
        JPanel pnSouth = new JPanel(new BorderLayout());
        pnSouth.add(btnBack, BorderLayout.EAST);

        JPanel pnMain = new JPanel(new BorderLayout(10, 10));
        pnMain.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnMain.add(pnNorth, BorderLayout.NORTH);
        pnMain.add(new JScrollPane(tblItem), BorderLayout.CENTER);
        pnMain.add(pnSouth, BorderLayout.SOUTH);
        setContentPane(pnMain);

        setSize(600, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        showDetail();
    }

    /** Lay thong tin chi tiet cua phieu nhap va hien len giao dien. */
    private void showDetail() {
        receipt = new ImportReceiptDAO0().getImportReceiptDetail(receipt.getId());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DecimalFormat moneyFormat = new DecimalFormat("#,###", symbols);

        lblReceiptInfo.setText("<html>Nhà cung cấp: " + receipt.getSupplier().getSupplierName()
                + "<br>Ngày nhập: " + dateFormat.format(receipt.getImportDate())
                + "<br>Nhân viên lập: " + receipt.getManagementStaff().getFullName()
                + "<br>Tổng tiền: " + moneyFormat.format(receipt.getTotalAmount())
                + "<br>Trạng thái: " + receipt.getStatus() + "</html>");

        String[] columns = {"Tên hàng hóa", "Số lượng", "Đơn giá nhập", "Thành tiền"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (ImportReceiptDetail0 detail : receipt.getDetails()) {
            model.addRow(new Object[] {
                detail.getCommodity().getCommodityName(),
                detail.getQuantity(),
                moneyFormat.format(detail.getPurchasePrice()),
                moneyFormat.format(detail.getLineTotal())
            });
        }
        tblItem.setModel(model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            dispose();
        }
    }
}
