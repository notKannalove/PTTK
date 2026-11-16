package view.management;

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
import java.util.Date;

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
import model.ManagementStaff0;
import model.SupplierStat0;

/** Giao dien danh sach phieu nhap cua mot nha cung cap trong khoang thoi gian dang thong ke. */
public class SupplierReceiptFrm0 extends JFrame implements ActionListener {
    private JLabel lblSupplierInfo;
    private JTable tblReceipt;
    private JButton btnBack;
    private ImportReceipt0[] listReceipt;
    private SupplierStat0 supplier;
    private Date startDate;
    private Date endDate;
    private ManagementStaff0 staff;

    public SupplierReceiptFrm0(ManagementStaff0 staff, SupplierStat0 supplier,
            Date startDate, Date endDate) {
        super("Phiếu nhập của nhà cung cấp");
        this.staff = staff;
        this.supplier = supplier;
        this.startDate = startDate;
        this.endDate = endDate;

        JLabel lblTitle = new JLabel("Phiếu nhập của nhà cung cấp", SwingConstants.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 18f));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        lblSupplierInfo = new JLabel("<html>Nhà cung cấp: " + supplier.getSupplierName()
                + "<br>Từ ngày: " + dateFormat.format(startDate)
                + " đến ngày: " + dateFormat.format(endDate) + "</html>");
        JPanel pnNorth = new JPanel(new BorderLayout(10, 10));
        pnNorth.add(lblTitle, BorderLayout.NORTH);
        pnNorth.add(lblSupplierInfo, BorderLayout.CENTER);

        tblReceipt = new JTable();
        tblReceipt.setRowHeight(24);
        tblReceipt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblReceipt.rowAtPoint(e.getPoint());
                if (row >= 0 && row < listReceipt.length) {
                    openReceiptDetail(listReceipt[row]);
                }
            }
        });

        btnBack = new JButton("Quay lại");
        btnBack.addActionListener(this);
        JPanel pnSouth = new JPanel(new BorderLayout());
        pnSouth.add(btnBack, BorderLayout.EAST);

        JPanel pnMain = new JPanel(new BorderLayout(10, 10));
        pnMain.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnMain.add(pnNorth, BorderLayout.NORTH);
        pnMain.add(new JScrollPane(tblReceipt), BorderLayout.CENTER);
        pnMain.add(pnSouth, BorderLayout.SOUTH);
        setContentPane(pnMain);

        setSize(600, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        loadReceipts();
    }

    /** Lay cac phieu nhap cua nha cung cap trong khoang thoi gian va hien len bang. */
    private void loadReceipts() {
        listReceipt = new ImportReceiptDAO0().getReceiptsBySupplier(supplier.getId(), startDate, endDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DecimalFormat moneyFormat = new DecimalFormat("#,###", symbols);

        String[] columns = {"Ngày nhập", "Nhân viên lập", "Tổng tiền", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (ImportReceipt0 receipt : listReceipt) {
            model.addRow(new Object[] {
                dateFormat.format(receipt.getImportDate()),
                receipt.getManagementStaff().getFullName(),
                moneyFormat.format(receipt.getTotalAmount()),
                receipt.getStatus()
            });
        }
        tblReceipt.setModel(model);
    }

    private void openReceiptDetail(ImportReceipt0 receipt) {
        ImportReceiptDetailFrm0 frm = new ImportReceiptDetailFrm0(staff, receipt);
        // khi giao dien chi tiet phieu nhap dong lai (Quay lai) thi hien lai giao dien nay
        frm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent we) {
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
