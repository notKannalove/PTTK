package view.management;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import dao.SupplierStatDAO0;
import model.ManagementStaff0;
import model.SupplierStat0;

/** Giao dien thong ke nha cung cap theo doanh thu (tong gia tri nhap). */
public class SupplierStatisticsFrm0 extends JFrame implements ActionListener {
    private JTextField txtStartDate;
    private JTextField txtEndDate;
    private JButton btnViewStatistics;
    private JTable tblSupplierStat;
    private JLabel lblMessage;
    private JButton btnBack;
    private SupplierStat0[] listSupplierStat;
    private Date startDate;
    private Date endDate;
    private ManagementStaff0 staff;

    public SupplierStatisticsFrm0(ManagementStaff0 staff) {
        super("Thống kê nhà cung cấp theo doanh thu");
        this.staff = staff;
        this.listSupplierStat = new SupplierStat0[0];

        JLabel lblTitle = new JLabel("Thống kê nhà cung cấp theo doanh thu", SwingConstants.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 18f));

        txtStartDate = new JTextField(8);
        txtEndDate = new JTextField(8);
        btnViewStatistics = new JButton("Xem thống kê");
        btnViewStatistics.addActionListener(this);
        JPanel pnInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        pnInput.add(new JLabel("Ngày bắt đầu (dd/MM/yyyy):"));
        pnInput.add(txtStartDate);
        pnInput.add(new JLabel("Ngày kết thúc (dd/MM/yyyy):"));
        pnInput.add(txtEndDate);
        pnInput.add(btnViewStatistics);
        JPanel pnNorth = new JPanel(new BorderLayout(10, 10));
        pnNorth.add(lblTitle, BorderLayout.NORTH);
        pnNorth.add(pnInput, BorderLayout.CENTER);

        tblSupplierStat = new JTable();
        tblSupplierStat.setRowHeight(24);
        tblSupplierStat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblSupplierStat.rowAtPoint(e.getPoint());
                if (row >= 0 && row < listSupplierStat.length) {
                    openSupplierReceipts(listSupplierStat[row]);
                }
            }
        });
        showStatistics();

        lblMessage = new JLabel(" ");
        btnBack = new JButton("Quay lại");
        btnBack.addActionListener(this);
        JPanel pnSouth = new JPanel(new BorderLayout());
        pnSouth.add(lblMessage, BorderLayout.CENTER);
        pnSouth.add(btnBack, BorderLayout.EAST);

        JPanel pnMain = new JPanel(new BorderLayout(10, 10));
        pnMain.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnMain.add(pnNorth, BorderLayout.NORTH);
        pnMain.add(new JScrollPane(tblSupplierStat), BorderLayout.CENTER);
        pnMain.add(pnSouth, BorderLayout.SOUTH);
        setContentPane(pnMain);

        setSize(760, 330);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /** Doc ngay bat dau, ngay ket thuc, lay du lieu thong ke va hien len bang. */
    private void viewStatistics() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        listSupplierStat = new SupplierStat0[0];
        try {
            startDate = dateFormat.parse(txtStartDate.getText().trim());
            endDate = dateFormat.parse(txtEndDate.getText().trim());
        } catch (Exception ex) {
            startDate = null;
            endDate = null;
        }
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            lblMessage.setText("Khoảng thời gian không hợp lệ, vui lòng chọn lại");
        } else {
            listSupplierStat = new SupplierStatDAO0().getSupplierStat(startDate, endDate);
            lblMessage.setText(listSupplierStat.length == 0 ? "Không có dữ liệu thống kê" : " ");
        }
        showStatistics();
    }

    /** Hien danh sach thong ke dang giu len bang. */
    private void showStatistics() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DecimalFormat moneyFormat = new DecimalFormat("#,###", symbols);

        String[] columns = {"Tên nhà cung cấp", "Số phiếu nhập", "Tổng giá trị nhập"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (SupplierStat0 stat : listSupplierStat) {
            model.addRow(new Object[] {
                stat.getSupplierName(),
                stat.getReceiptCount(),
                moneyFormat.format(stat.getTotalImportValue())
            });
        }
        tblSupplierStat.setModel(model);
    }

    private void openSupplierReceipts(SupplierStat0 supplier) {
        SupplierReceiptFrm0 frm = new SupplierReceiptFrm0(staff, supplier, startDate, endDate);
        // khi giao dien phieu nhap dong lai (Quay lai) thi hien lai ket qua thong ke dang co
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
        if (e.getSource() == btnViewStatistics) {
            viewStatistics();
        } else if (e.getSource() == btnBack) {
            dispose();
        }
    }
}
