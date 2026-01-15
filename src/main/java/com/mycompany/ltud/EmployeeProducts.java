/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.ltud;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 08567
 */
public class EmployeeProducts extends javax.swing.JFrame {


    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(EmployeeProducts.class.getName());
    String masp;
    String mahd, tensp, madanhmuc, mathuonghieu;
    double dongia;
    int soluong;
    int currentRow = 0;
    public EmployeeProducts() {       
        initComponents();
        txtTennv.setText(Session.loggedInUsername);
        txtManv.setText(Session.loggedInUserID);
        addEnterKeyHandler();
        addEscapeKeyToClose();
        addF9KeyBinding();
        addF8KeyBinding();
        setNgayHoadon();
        hienMaHoaDonTiepTheo();
        Timsp(masp);
        updateImageByMaNV(txtManv.getText().trim());
        DefaultTableModel model01 = new DefaultTableModel(
            new String[] { "MASP", "TENSP", "DONGIA", "SOLUONG", "DANHMUC", "THUONGHIEU", "THÀNH TIỀN", "CK", "THỰC THU" },
            0
        );
        DefaultTableModel model02 = new DefaultTableModel(
            new String[] { "MAHD", "TONGTIEN"},
            0
        );   
        tableHoadon.setModel(model02);
        TableChitiethoadon.setModel(model01);
        SetHoaDon();
    }
    
    private void addEscapeKeyToClose() {
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                     .put(escapeKeyStroke, "ESCAPE");

        getRootPane().getActionMap().put("ESCAPE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Đóng form
            }
        });
    }
    
    private void addEnterKeyHandler() {
        txtTienkhachdua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!txtTienkhachdua.getText().trim().isEmpty()) {
                    giaodich();
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số tiền!");
                }
            }
        });
    }
    
    private void addF9KeyBinding() {
        KeyStroke keyStroke = KeyStroke.getKeyStroke("F9");
        String actionKey = "XUAT_HOADON_ACTION";

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, actionKey);
        getRootPane().getActionMap().put(actionKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Giả lập sự kiện click nút
                btnXuatbillActionPerformed(null);
            }
        });
    }
    
    private void addF8KeyBinding() {
        KeyStroke keyStroke = KeyStroke.getKeyStroke("F8");
        String actionKey = "IN_LAI_HOADON_ACTION";

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, actionKey);
        getRootPane().getActionMap().put(actionKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Giả lập sự kiện click nút
                btnInlaibillActionPerformed(null);
            }
        });
    }
    
    private void setNgayHoadon() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txtNgayhd.setText(today.format(formatter));
    }
    void displaySelectedRow(int row) {
        if (row >= 0 && row < tableHoadon.getRowCount()) {
            tableHoadon.setRowSelectionInterval(row, row); // chọn dòng
            tableHoadon.scrollRectToVisible(tableHoadon.getCellRect(row, 0, true)); // tự cuộn đến dòng
            currentRow = row; // lưu lại chỉ số dòng
        }
    }
    
    private void Timsp(String masp){
        this.masp = txtMasp.getText();
        
    }

    public void setCTHD(String masp, String tensp, double dongia, int soluong, String madanhmuc, String mathuonghieu, int chietkhau) {
        DefaultTableModel model = (DefaultTableModel) TableChitiethoadon.getModel();
        DecimalFormat formatter = new DecimalFormat("#,###");
        formatter.setGroupingUsed(true);

        if (soluong <= 0) soluong = 1;
        if (dongia < 0) dongia = 0;
        if (chietkhau < 0) chietkhau = 0;

        boolean daCoSanPham = false;


        if (!daCoSanPham) {
            double giagoc = dongia;
            double thucThu = soluong * giagoc * (1 - chietkhau / 100.0);
            model.addRow(new Object[] {
                masp,
                tensp,
                formatter.format(dongia),
                soluong,
                getTenDanhMuc(madanhmuc),
                getTenThuongHieu(mathuonghieu),
                formatter.format(dongia),
                chietkhau,
                formatter.format(thucThu)
            });
        }

        capNhatgiatribill();  
    }
    
    public void setCTHD(int chietkhau) {
        DefaultTableModel model = (DefaultTableModel) TableChitiethoadon.getModel();
        DecimalFormat formatter = new DecimalFormat("#,###");
        formatter.setGroupingUsed(true);

        if (chietkhau < 0) chietkhau = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            // Lấy số lượng và đơn giá gốc
            int soluong = Integer.parseInt(model.getValueAt(i, 3).toString());
            double dongia = Double.parseDouble(model.getValueAt(i, 2).toString().replace(",", ""));
            // Cập nhật chiết khấu
            model.setValueAt(chietkhau, i, 7); // Cột chiết khấu

            // Tính lại thực thu
            double thucThu = soluong * dongia * (1 - (double) chietkhau / 100);
            model.setValueAt(formatter.format(thucThu*1000), i, 8); // Cột thực thu
        }

        capNhatgiatribill();
    }

    
    private void setCTHD(String mahd) {
        DefaultTableModel model = (DefaultTableModel) TableChitiethoadon.getModel();
        model.setRowCount(0);
        String sql = "SELECT cthd.MASP, sp.TENSP, dm.TENDANHMUC, th.TENTHUONGHIEU, " +
                     "cthd.DONGIA, cthd.SOLUONG, cthd.THANHTIEN, cthd.CHIETKHAU, cthd.THUCTHU " +
                     "FROM chi_tiet_hoa_don cthd " +
                     "JOIN san_pham sp ON cthd.MASP = sp.MASP " +
                     "JOIN danh_muc_san_pham dm ON sp.MADANHMUC = dm.MADANHMUC " +
                     "JOIN thuong_hieu th ON sp.MATHUONGHIEU = th.MATHUONGHIEU " +
                     "WHERE cthd.MAHD = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {

                pst.setString(1, mahd);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                    DecimalFormat formatter = new DecimalFormat("#,###");

                    model.addRow(new Object[]{
                        rs.getString("MASP"),
                        rs.getString("TENSP"),
                        formatter.format(rs.getDouble("DONGIA")),
                        rs.getInt("SOLUONG"),
                        rs.getString("TENDANHMUC"),
                        rs.getString("TENTHUONGHIEU"),
                        formatter.format(rs.getDouble("THANHTIEN")),
                        rs.getInt("CHIETKHAU"),
                        formatter.format(rs.getDouble("THUCTHU"))
                    });
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi load chi tiết hóa đơn: " + e.getMessage());
            }
        }


    
    public void SetHoaDon() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT MAHD, TONGTIEN FROM hoa_don");
             ResultSet rs = ps.executeQuery()) {

            DefaultTableModel model02 = (DefaultTableModel) tableHoadon.getModel();
            model02.setRowCount(0); // Xóa dữ liệu cũ

            DecimalFormat formatter = new DecimalFormat("#,###"); // Tạo 1 lần ngoài vòng lặp

            while (rs.next()) {
                String mahd = rs.getString("MAHD");
                double tongTien = rs.getDouble("TONGTIEN");

                Object[] row = {
                    mahd,
                    formatter.format(tongTien)
                };

                model02.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu hóa đơn: " + e.getMessage());
        }
    }



    
    public void capNhatgiatribill() {
        DecimalFormat formatter = new DecimalFormat("#,###");
        formatter.setGroupingUsed(true);
        DefaultTableModel model = (DefaultTableModel) TableChitiethoadon.getModel();
        double tongThucThu = 0;
        int tongsoluongxuat = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            try {
                // Cột "THỰC THU" là cột 8
                Object thucThuVal = model.getValueAt(i, 8);
                if (thucThuVal != null) {
                    // Xử lý trường hợp dữ liệu có dấu ,
                    tongThucThu += Double.parseDouble(thucThuVal.toString().replace(".", ""));

                }

                // Cột "SỐ LƯỢNG" là cột 3
                Object slVal = model.getValueAt(i, 3);
                if (slVal != null) {
                    tongsoluongxuat += Integer.parseInt(slVal.toString());
                }
            } catch (NumberFormatException e) {
                System.err.println("Dữ liệu không hợp lệ ở dòng " + i + ": " + e.getMessage());
            }
        }

        txtTonggiatrixuat.setText(formatter.format(tongThucThu));
        txtTongluongxuat.setText(String.valueOf(tongsoluongxuat));
    }

    public void giaodich() {
        capNhatgiatribill(); // Cập nhật lại tổng giá trị và số lượng

        String tienKhachDuaStr = txtTienkhachdua.getText().trim();
        String tongThucThuStr = txtTonggiatrixuat.getText().trim();

        if (tienKhachDuaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền khách đưa.");
            return;
        }

        try {
            
            double tienKhachDua = Double.parseDouble(tienKhachDuaStr.replace(".", "").replace(",", ""));
            double tongThucThu = Double.parseDouble(tongThucThuStr.replace(".", "").replace(",", ""));


            if (tienKhachDua < tongThucThu) {
                JOptionPane.showMessageDialog(this, "Số tiền khách đưa chưa đủ!");
            } else {
                double tienthoi = tienKhachDua - tongThucThu;
                DecimalFormat formatter = new DecimalFormat("#,###");
                txtTientra.setText(formatter.format(tienthoi));
                // Có thể reset form hoặc thực hiện hành động tiếp theo ở đây
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Hiển thị sản phẩm trên thanh
    public void setProductInfo(String masp, String tensp, double dongia, int soluong, String madanhmuc, String mathuonghieu ) {
        txtMasp.setText(masp);
        txtTensp.setText(tensp);
        txtGia.setText(String.valueOf(dongia));       
        txtSoluong.setText(String.valueOf(soluong));
        txtDanhmuc.setText(getTenDanhMuc(madanhmuc));
        txtThuonghieu.setText(getTenThuongHieu(mathuonghieu));
    }
    private String getTenDanhMuc(String madanhmuc) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT TENDANHMUC FROM danh_muc_san_pham WHERE MADANHMUC = ?")) {
            ps.setString(1, madanhmuc);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("TENDANHMUC");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getTenThuongHieu(String mathuonghieu) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT TENTHUONGHIEU FROM thuong_hieu WHERE MATHUONGHIEU = ?")) {
            ps.setString(1, mathuonghieu);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("TENTHUONGHIEU");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String sinhMaHoaDonTiepTheo() {
        String prefix = "HD";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT MAHD FROM hoa_don ORDER BY MAHD DESC LIMIT 1");
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String lastMa = rs.getString("MAHD");
                // Kiểm tra định dạng trước khi cắt chuỗi
                if (lastMa != null && lastMa.startsWith(prefix)) {
                    int num = Integer.parseInt(lastMa.substring(prefix.length())) + 1;
                    return String.format("%s%03d", prefix, num);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + "001";  // Nếu chưa có hóa đơn nào
    }

        public String sinhMaChiTietHoaDonTiepTheo() {
        String prefix = "CTHD";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT MACTHD FROM chi_tiet_hoa_don ORDER BY MACTHD DESC LIMIT 1");
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String lastMa = rs.getString("MACTHD");
                if (lastMa != null && lastMa.startsWith(prefix)) {
                    int num = Integer.parseInt(lastMa.substring(prefix.length())) + 1;
                    return String.format("%s%03d", prefix, num);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + "001"; // Nếu chưa có CTHD nào
    }

    public String layMaChiTietHoaDonCuoi() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT MACTHD FROM chi_tiet_hoa_don ORDER BY MACTHD DESC LIMIT 1");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getString("MACTHD");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "CTHD000"; // nếu chưa có dữ liệu
    }
        
    public int tachSoTuMa(String ma) {
        try {
            return Integer.parseInt(ma.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0;
        }
    }
    
    
    public void hienMaHoaDonTiepTheo() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT MAHD FROM hoa_don ORDER BY MAHD DESC LIMIT 1");
             ResultSet rs = ps.executeQuery()) {

            String nextMaHD = "HD001"; // Giá trị mặc định nếu bảng rỗng

            if (rs.next()) {
                String lastMa = rs.getString("MAHD"); // VD: HD007
                int num = Integer.parseInt(lastMa.substring(2)) + 1;
                nextMaHD = String.format("HD%03d", num); // Kết quả: HD008
            }

            txtMahd.setText(nextMaHD);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy mã hóa đơn tiếp theo: " + e.getMessage());
        }
    }
    //Hàm xử lí tiền tệ 
    private double parseCurrency(String str) throws NumberFormatException {
        return Double.parseDouble(str.replace(".", "").replace(",", "").trim());
    }
    


    //Hàm kiểm tra dữ liệu trước khi xuất 
    private boolean validateBeforeExport() {
        if (TableChitiethoadon.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Hóa đơn đang trống, không thể xuất!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String tienKhachDuaStr = txtTienkhachdua.getText().trim();
        if (tienKhachDuaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền khách đưa!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            double tienKhachDua = parseCurrency(tienKhachDuaStr);
            double tongGiaTri = parseCurrency(txtTonggiatrixuat.getText());
            if (tienKhachDua < tongGiaTri) {
                JOptionPane.showMessageDialog(this, "Số tiền khách đưa không đủ để thanh toán!", "Thiếu tiền", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số tiền khách đưa hoặc tổng tiền không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    //Hàm tạo khách hàng mới rồi truyền dữ liệu vào luôn
    public void setCustomerData(String maKH, String hoTen, String email, String sdt, String diaChi, String gioitinh) {
        txtMakh.setText(maKH);
        txtTenKh.setText(hoTen);
        txtSdt.setText(sdt);
        cmbGioitinh.setSelectedItem(gioitinh);
    }

    //Hàm kiểm tra Mã hóa đơn trống thì mặc định là VL001
    private String getOrCreateKhachVangLai(Connection conn) throws SQLException {
        String maKhachVangLai = "VL001"; // Mã khách mặc định
        
        // Nếu người dùng không nhập mã khách hàng
        if (txtMakh.getText().trim().isEmpty()) {
            // Kiểm tra xem đã tồn tại khách vãng lai chưa
            String checkSql = "SELECT MAKH FROM khach_hang WHERE MAKH = ?";
            try (PreparedStatement pstCheck = conn.prepareStatement(checkSql)) {
                pstCheck.setString(1, maKhachVangLai);
                try (ResultSet rs = pstCheck.executeQuery()) {
                    if (rs.next()) {
                        return maKhachVangLai; // Đã tồn tại
                    }
                }
            }

            // Nếu chưa có thì thêm mới
            String insertSql = "INSERT INTO khach_hang (MAKH, TENKH, GIOITINH, SDT) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstInsert = conn.prepareStatement(insertSql)) {
                pstInsert.setString(1, maKhachVangLai);
                pstInsert.setString(2, "Khách vãng lai");
                pstInsert.setString(3, "Khác");
                pstInsert.setString(4, "0000000000");
                pstInsert.executeUpdate();
            }

            return maKhachVangLai;
        }

        // Nếu người dùng có nhập mã khách, thì trả về mã đó
        return txtMakh.getText().trim();
    }

    
    private void printInvoice(String mahd, String maKH) {
        String tenKH = "Khách vãng lai";
        String sdtKH = "";

        // Nếu có mã khách hàng thì truy vấn thông tin
        if (maKH != null && !maKH.isEmpty()) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "SELECT HOTEN, SDT FROM khach_hang WHERE MAKH = ?";
                try (PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setString(1, maKH);
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            tenKH = rs.getString("HOTEN");
                            sdtKH = rs.getString("SDT");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                tenKH = "Khách không xác định";
            }
        }

        try {
            File file = new File("hoadon_" + mahd + ".txt");
            PrintWriter writer = new PrintWriter(file, "UTF-8");

            writer.println("========= HÓA ĐƠN BÁN HÀNG =========");
            writer.println("Mã hóa đơn: " + mahd);
            writer.println("Ngày lập: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            writer.println("Khách hàng: " + tenKH + (sdtKH.isEmpty() ? "" : " - " + sdtKH));
            writer.println("------------------------------------");
            writer.printf("%-10s %-15s %-10s %-10s %-10s\n", "Mã SP", "Tên SP", "Đơn giá", "SL", "Thành tiền");

            DefaultTableModel model = (DefaultTableModel) TableChitiethoadon.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                String masp = model.getValueAt(i, 0).toString();
                String tensp = model.getValueAt(i, 1).toString();
                String dongia = model.getValueAt(i, 2).toString();
                String soluong = model.getValueAt(i, 3).toString();
                String thanhtien = model.getValueAt(i, 8).toString(); // Chỉ số 8 là cột thành tiền

                writer.printf("%-10s %-15s %-10s %-10s %-10s\n", masp, tensp, dongia, soluong, thanhtien);
            }

            writer.println("------------------------------------");
            writer.println("Tổng số lượng: " + txtTongluongxuat.getText());
            writer.println("Tổng giá trị: " + txtTonggiatrixuat.getText() + " VNĐ");
            writer.println("Tiền khách đưa: " + txtTienkhachdua.getText());
            writer.println("Tiền trả lại: " + txtTientra.getText());
            writer.println("====================================");
            writer.println("Cảm ơn quý khách. Hẹn gặp lại!");

            writer.close();

            // Gửi lệnh in
            Desktop.getDesktop().print(file);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể in hóa đơn: " + e.getMessage(), "Lỗi in", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void printInvoice(String mahd) {
        String tenKH = "Khách vãng lai";
        String sdtKH = "";
        String makh = null;

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Lấy mã khách hàng từ mã hóa đơn
            String sql = "SELECT MAKH FROM hoa_don WHERE MAHD = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, mahd);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        makh = rs.getString("MAKH");
                    }
                }
            }

            // Lấy tên và số điện thoại khách hàng nếu có
            if (makh != null && !makh.trim().isEmpty()) {
                sql = "SELECT HOTEN, SDT FROM khach_hang WHERE MAKH = ?";
                try (PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setString(1, makh);
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            tenKH = rs.getString("HOTEN");
                            sdtKH = rs.getString("SDT");
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin khách hàng", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        try {
            File file = new File("hoadon_" + mahd + ".txt");
            PrintWriter writer = new PrintWriter(file, "UTF-8");

            writer.println("========= HÓA ĐƠN BÁN HÀNG =========");
            writer.println("Mã hóa đơn: " + mahd);
            writer.println("Ngày lập: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            writer.println("Khách hàng: " + tenKH + (sdtKH.isEmpty() ? "" : " - " + sdtKH));
            writer.println("------------------------------------");
            writer.printf("%-10s %-15s %-10s %-10s %-10s\n", "Mã SP", "Tên SP", "Đơn giá", "SL", "Thành tiền");

            DefaultTableModel model = (DefaultTableModel) TableChitiethoadon.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                String masp = model.getValueAt(i, 0).toString();
                String tensp = model.getValueAt(i, 1).toString();
                String dongia = model.getValueAt(i, 2).toString();
                String soluong = model.getValueAt(i, 3).toString();
                String thanhtien = model.getValueAt(i, 8).toString(); // Chỉ số 8 là cột thực thu

                writer.printf("%-10s %-15s %-10s %-10s %-10s\n", masp, tensp, dongia, soluong, thanhtien);
            }

            writer.println("------------------------------------");
            writer.println("Tổng số lượng: " + txtTongluongxuat.getText());
            writer.println("Tổng giá trị: " + txtTonggiatrixuat.getText() + " VNĐ");
            writer.println("Tiền khách đưa: " + txtTienkhachdua.getText());
            writer.println("Tiền trả lại: " + txtTientra.getText());
            writer.println("====================================");
            writer.println("Cảm ơn quý khách. Hẹn gặp lại!");

            writer.close();

            // Gửi lệnh in ra máy in mặc định
            Desktop.getDesktop().print(file);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể in hóa đơn: " + e.getMessage(), "Lỗi in", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updateImageByMaNV(String maNV) {
        if (maNV != null && !maNV.trim().isEmpty()) {
            String imageFile = getImageFileByMaNV(maNV); 
            if (imageFile != null && !imageFile.trim().isEmpty()) {
                setImageToLabel("images/" + imageFile);
            } else {
                setImageToLabel("images/default.jpg"); // fallback ảnh mặc định
            }
        }
    }

    private String getImageFileByMaNV(String maNV) {
        String imageFile = null;
        String sql = "SELECT profile_image FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNV);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                imageFile = rs.getString("profile_image"); // ✅ dùng đúng tên cột
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageFile;
    }

    private void setImageToLabel(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
                Image scaledImage = icon.getImage().getScaledInstance(
                        lblHinhanhNV.getWidth(),
                        lblHinhanhNV.getHeight(),
                        Image.SCALE_SMOOTH
                );
                lblHinhanhNV.setIcon(new ImageIcon(scaledImage));
            } else {
                System.out.println("Không tìm thấy ảnh: " + imagePath);
                lblHinhanhNV.setIcon(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblHinhanhNV.setIcon(null);
        }
    }


    @SuppressWarnings("unchecked")
 


    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        lblmahd = new javax.swing.JLabel();
        lblngay = new javax.swing.JLabel();
        txtMahd = new javax.swing.JTextField();
        txtNgayhd = new javax.swing.JTextField();
        blbTenChuongTrinh = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableHoadon = new javax.swing.JTable();
        lblmanv = new javax.swing.JLabel();
        lblTennv = new javax.swing.JLabel();
        lblSdt = new javax.swing.JLabel();
        lblgioitinh = new javax.swing.JLabel();
        lblTenKH = new javax.swing.JLabel();
        lblmakh = new javax.swing.JLabel();
        txtTennv = new javax.swing.JTextField();
        txtMakh = new javax.swing.JTextField();
        txtTenKh = new javax.swing.JTextField();
        txtManv = new javax.swing.JTextField();
        txtSdt = new javax.swing.JTextField();
        cmbGioitinh = new javax.swing.JComboBox<>();
        lblHinhanhNV = new javax.swing.JLabel();
        lblmasp = new javax.swing.JLabel();
        lbltensp = new javax.swing.JLabel();
        lblsoluong = new javax.swing.JLabel();
        lblgia = new javax.swing.JLabel();
        lbldanhmuc = new javax.swing.JLabel();
        lblthuonghieu = new javax.swing.JLabel();
        txtMasp = new javax.swing.JTextField();
        txtTensp = new javax.swing.JTextField();
        txtDanhmuc = new javax.swing.JTextField();
        txtSoluong = new javax.swing.JTextField();
        txtGia = new javax.swing.JTextField();
        txtThuonghieu = new javax.swing.JTextField();
        lblTienkhachdua = new javax.swing.JLabel();
        lbltientra = new javax.swing.JLabel();
        txtTientra = new javax.swing.JTextField();
        txtTienkhachdua = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableChitiethoadon = new javax.swing.JTable();
        jbxTaohoadon = new javax.swing.JCheckBox();
        lblLuongxuat = new javax.swing.JLabel();
        txtTongluongxuat = new javax.swing.JTextField();
        lbldvnd = new javax.swing.JLabel();
        txtTonggiatrixuat = new javax.swing.JTextField();
        lblTonggiatri = new javax.swing.JLabel();
        btnXuatbill = new javax.swing.JButton();
        btnInlaibill = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        btnDanhThu = new javax.swing.JButton();
        btnKhuyenMai = new javax.swing.JButton();
        btnDktv1 = new javax.swing.JButton();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CHUONGTRINHBANHANG");

        lblmahd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblmahd.setForeground(new java.awt.Color(0, 0, 204));
        lblmahd.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblmahd.setText("Mã hóa đơn");

        lblngay.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblngay.setForeground(new java.awt.Color(0, 0, 204));
        lblngay.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblngay.setText("Ngày hóa đơn");

        txtMahd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        txtNgayhd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        blbTenChuongTrinh.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        blbTenChuongTrinh.setForeground(new java.awt.Color(0, 0, 204));
        blbTenChuongTrinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        blbTenChuongTrinh.setText("GIAO DỊCH KHO");
        blbTenChuongTrinh.setToolTipText("");

        tableHoadon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tableHoadon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableHoadon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableHoadonMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableHoadon);

        lblmanv.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblmanv.setForeground(new java.awt.Color(0, 0, 204));
        lblmanv.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblmanv.setText("Mã nhân viên");

        lblTennv.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTennv.setForeground(new java.awt.Color(0, 0, 204));
        lblTennv.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTennv.setText("Tên nhân viên");

        lblSdt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSdt.setForeground(new java.awt.Color(0, 0, 204));
        lblSdt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSdt.setText("Số điện thoại");

        lblgioitinh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblgioitinh.setForeground(new java.awt.Color(0, 0, 204));
        lblgioitinh.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblgioitinh.setText("Giới tính ");

        lblTenKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTenKH.setForeground(new java.awt.Color(0, 0, 204));
        lblTenKH.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTenKH.setText("Tên khách hàng");

        lblmakh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblmakh.setForeground(new java.awt.Color(0, 0, 204));
        lblmakh.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblmakh.setText("Mã khách hàng");

        txtTennv.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        txtMakh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        txtTenKh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTenKh.setText("Khách vãng lai");

        txtManv.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        txtSdt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtSdt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSdtActionPerformed(evt);
            }
        });

        cmbGioitinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbGioitinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn", "Nam", "Nữ", "Khác" }));
        cmbGioitinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbGioitinhActionPerformed(evt);
            }
        });

        lblHinhanhNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblHinhanhNV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinhanhNV.setText("Hình ảnh ");

        lblmasp.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblmasp.setForeground(new java.awt.Color(0, 0, 204));
        lblmasp.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblmasp.setText("Mã sản phẩm");

        lbltensp.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbltensp.setForeground(new java.awt.Color(0, 0, 204));
        lbltensp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbltensp.setText("Tên sản phẩm ");

        lblsoluong.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblsoluong.setForeground(new java.awt.Color(0, 0, 204));
        lblsoluong.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblsoluong.setText("Số lượng ");

        lblgia.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblgia.setForeground(new java.awt.Color(0, 0, 204));
        lblgia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblgia.setText("Đơn giá ");

        lbldanhmuc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbldanhmuc.setForeground(new java.awt.Color(0, 0, 204));
        lbldanhmuc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbldanhmuc.setText("Danh mục");

        lblthuonghieu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblthuonghieu.setForeground(new java.awt.Color(0, 0, 204));
        lblthuonghieu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblthuonghieu.setText("Thương hiệu ");

        txtMasp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaspActionPerformed(evt);
            }
        });

        lblTienkhachdua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTienkhachdua.setForeground(new java.awt.Color(0, 0, 204));
        lblTienkhachdua.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTienkhachdua.setText("Tiền khách đưa:");

        lbltientra.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbltientra.setForeground(new java.awt.Color(0, 0, 204));
        lbltientra.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbltientra.setText("Tiền trả lại:");

        TableChitiethoadon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(TableChitiethoadon);

        jbxTaohoadon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbxTaohoadon.setForeground(new java.awt.Color(255, 0, 0));
        jbxTaohoadon.setText("Tạo hóa đơn mới ");
        jbxTaohoadon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbxTaohoadonMouseClicked(evt);
            }
        });

        lblLuongxuat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblLuongxuat.setForeground(new java.awt.Color(255, 0, 51));
        lblLuongxuat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLuongxuat.setText("Tổng lượng xuất ");

        txtTongluongxuat.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtTongluongxuat.setForeground(new java.awt.Color(204, 0, 51));
        txtTongluongxuat.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTongluongxuat.setText("0");

        lbldvnd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbldvnd.setForeground(new java.awt.Color(255, 0, 51));
        lbldvnd.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbldvnd.setText("VNĐ");

        txtTonggiatrixuat.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtTonggiatrixuat.setForeground(new java.awt.Color(204, 0, 51));
        txtTonggiatrixuat.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTonggiatrixuat.setText("0");

        lblTonggiatri.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTonggiatri.setForeground(new java.awt.Color(255, 0, 51));
        lblTonggiatri.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTonggiatri.setText("Tổng giá trị xuất ");

        btnXuatbill.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnXuatbill.setForeground(new java.awt.Color(0, 0, 204));
        btnXuatbill.setText("F9 XUẤT BILL");
        btnXuatbill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatbillActionPerformed(evt);
            }
        });

        btnInlaibill.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnInlaibill.setForeground(new java.awt.Color(0, 0, 204));
        btnInlaibill.setText("F8 IN LẠI BILL");
        btnInlaibill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInlaibillActionPerformed(evt);
            }
        });

        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThoat.setForeground(new java.awt.Color(255, 0, 0));
        btnThoat.setText("ESC THOÁT");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        btnDanhThu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDanhThu.setForeground(new java.awt.Color(0, 0, 204));
        btnDanhThu.setText("Danh Thu");
        btnDanhThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDanhThuActionPerformed(evt);
            }
        });

        btnKhuyenMai.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnKhuyenMai.setForeground(new java.awt.Color(0, 51, 204));
        btnKhuyenMai.setText("Khuyến Mãi");
        btnKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhuyenMaiActionPerformed(evt);
            }
        });

        btnDktv1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDktv1.setForeground(new java.awt.Color(0, 51, 204));
        btnDktv1.setText("ĐĂNG KÝ THÀNH VIÊN");
        btnDktv1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDktv1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(jbxTaohoadon)
                                .addGap(103, 103, 103)
                                .addComponent(lblLuongxuat, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtTongluongxuat, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(70, 70, 70)
                                .addComponent(lblTonggiatri, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTonggiatrixuat, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbldvnd, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lblngay, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                                            .addComponent(lblmahd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtMahd, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNgayhd, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblmanv, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblTennv, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(5, 5, 5)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtManv, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTennv, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(26, 26, 26)
                                .addComponent(lblHinhanhNV, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(159, 159, 159)
                                        .addComponent(blbTenChuongTrinh, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblmakh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblTenKH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(lblSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(lblgia, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(93, 93, 93)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtTenKh)
                                            .addComponent(txtMakh)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(lblgioitinh))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lbldanhmuc, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(97, 97, 97)
                                                        .addComponent(lblthuonghieu, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cmbGioitinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(35, 35, 35))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtMasp, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblmasp))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbltensp, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTensp, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtSoluong, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtDanhmuc, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblsoluong, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(488, 488, 488))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblTienkhachdua, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtTienkhachdua, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lbltientra, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtTientra, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(56, 56, 56)
                                        .addComponent(btnDktv1)
                                        .addGap(36, 36, 36)
                                        .addComponent(btnKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtThuonghieu, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(66, 66, 66))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(56, 56, 56)
                                        .addComponent(btnDanhThu)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnInlaibill)
                        .addGap(18, 18, 18)
                        .addComponent(btnXuatbill))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThoat)
                .addGap(119, 119, 119))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(blbTenChuongTrinh))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblmahd)
                                            .addComponent(txtMahd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblngay)
                                            .addComponent(txtNgayhd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtMakh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblmakh))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtTenKh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblTenKH))
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblgioitinh)
                                            .addComponent(cmbGioitinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblSdt)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblmanv)
                                            .addComponent(txtManv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblTennv)
                                            .addComponent(txtTennv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblHinhanhNV, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblmasp)
                            .addComponent(lbltensp)
                            .addComponent(lblsoluong)
                            .addComponent(lblgia)
                            .addComponent(lbldanhmuc)
                            .addComponent(lblthuonghieu))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMasp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTensp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDanhmuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtThuonghieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblTienkhachdua)
                                    .addComponent(txtTienkhachdua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbltientra)
                                    .addComponent(txtTientra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnDktv1)
                                .addComponent(btnKhuyenMai)
                                .addComponent(btnDanhThu)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jbxTaohoadon)
                                .addComponent(lblLuongxuat)
                                .addComponent(txtTongluongxuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbldvnd)
                                .addComponent(txtTonggiatrixuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblTonggiatri))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnInlaibill)
                            .addComponent(btnXuatbill))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(btnThoat)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMaspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaspActionPerformed
        String masp = txtMasp.getText().trim();

        Product_Inventory inventoryDialog;

        if (masp.isEmpty()) {
            // Không nhập mã => hiển thị toàn bộ
            inventoryDialog = new Product_Inventory(this); 
        } else {
            // Nhập mã => chỉ tìm sản phẩm theo mã
            inventoryDialog = new Product_Inventory(this, masp);
        }

        inventoryDialog.setLocationRelativeTo(this);
        inventoryDialog.setVisible(true);
    }//GEN-LAST:event_txtMaspActionPerformed

    private void txtSdtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSdtActionPerformed
        // TODO add your handling code here:
        String sdt = txtSdt.getText().trim();
            if (!sdt.isEmpty()) {
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "SELECT HOTEN, MAKH, GT FROM khach_hang WHERE SDT = ?")) {
                    ps.setString(1, sdt);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            txtTenKh.setText(rs.getString("HOTEN"));
                            txtMakh.setText(rs.getString("MAKH"));
                            cmbGioitinh.setSelectedItem(rs.getString("GT")); // GT là giới tính
                        } else {
                            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
    }//GEN-LAST:event_txtSdtActionPerformed

    private void btnXuatbillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatbillActionPerformed
        if (!validateBeforeExport()) return;

        DefaultTableModel model = (DefaultTableModel) TableChitiethoadon.getModel();
        String tongGiaTriStr = txtTonggiatrixuat.getText();
        String tongSoLuong = txtTongluongxuat.getText();
        String manv = txtManv.getText().trim();
        String tienKhachDuaStr = txtTienkhachdua.getText().trim();

        double tongGiaTri = parseCurrency(tongGiaTriStr);
        double tienKhachDua = parseCurrency(tienKhachDuaStr);

        int xacnhan = JOptionPane.showConfirmDialog(this,
            "Xác nhận xuất hóa đơn?\nTổng số lượng: " + tongSoLuong + "\nTổng giá trị: " + tongGiaTriStr + " VNĐ",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION);

        if (xacnhan == JOptionPane.YES_OPTION) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setAutoCommit(false); // Bắt đầu transaction

                String mahd = sinhMaHoaDonTiepTheo();
                Timestamp ngayLap = new Timestamp(System.currentTimeMillis());

                // Xử lý khách hàng
                String maKH;
                String txtMaKHValue = txtMakh.getText().trim();
                if (txtMaKHValue.isEmpty() || txtMaKHValue.equals("000")) {
                    maKH = getOrCreateKhachVangLai(conn);
                    txtTenKh.setText("Khách vãng lai");
                    txtMakh.setText("VL001");
                    cmbGioitinh.setSelectedItem("Khác");
                } else {
                    maKH = txtMaKHValue;
                }

                // 1. Thêm hóa đơn
                String sqlHD = "INSERT INTO hoa_don (MAHD, MAKH, user_id, TONGTIEN, NGAYLAP) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement pstHD = conn.prepareStatement(sqlHD)) {
                    pstHD.setString(1, mahd);
                    pstHD.setString(2, maKH);
                    pstHD.setString(3, manv);
                    pstHD.setDouble(4, tongGiaTri);
                    pstHD.setTimestamp(5, ngayLap);
                    pstHD.executeUpdate();
                }

                // 2. Sinh mã chi tiết hóa đơn mới
                String maCTHDCuoi = layMaChiTietHoaDonCuoi(); // ví dụ: CTHD025
                int soBatDau = tachSoTuMa(maCTHDCuoi);        // -> 25

                String sqlCT = "INSERT INTO chi_tiet_hoa_don (MACTHD, MAHD, MASP, SOLUONG, DONGIA, THANHTIEN, CHIETKHAU) VALUES (?, ?, ?, ?, ?, ?, ?)";
                String sqlUpdateTonKho = """
                    UPDATE ton_kho
                    SET 
                        SOLUONGTON = SOLUONGTON - ?, 
                        LUONGXUAT = LUONGXUAT + ?, 
                        SOLUONGCUOI = SOLUONGTON - ?
                    WHERE MASP = ?
                """;

                try (
                    PreparedStatement pstCT = conn.prepareStatement(sqlCT);
                    PreparedStatement pstUpdate = conn.prepareStatement(sqlUpdateTonKho)
                ) {
                    for (int i = 0; i < model.getRowCount(); i++) {
                        String masp = model.getValueAt(i, 0).toString();
                        int soluong = Integer.parseInt(model.getValueAt(i, 3).toString());
                        double donGia = parseCurrency(model.getValueAt(i, 2).toString());
                        double thanhtien = parseCurrency(model.getValueAt(i, 6).toString());
                        int chietkhau = (int) parseCurrency(model.getValueAt(i, 7).toString());

                        // Tạo mã mới
                        String macthd = String.format("CTHD%03d", soBatDau + i + 1);

                        // Thêm CTHD
                        pstCT.setString(1, macthd);
                        pstCT.setString(2, mahd);
                        pstCT.setString(3, masp);
                        pstCT.setInt(4, soluong);
                        pstCT.setDouble(5, donGia);
                        pstCT.setDouble(6, thanhtien);
                        pstCT.setInt(7, chietkhau);
                        pstCT.addBatch();

                        // Cập nhật tồn kho cho sản phẩm
                        pstUpdate.setInt(1, soluong); // - sl
                        pstUpdate.setInt(2, soluong); // + sl
                        pstUpdate.setInt(3, soluong); // cuối = tồn - sl
                        pstUpdate.setString(4, masp);
                        pstUpdate.executeUpdate();
                    }
                    pstCT.executeBatch();
                }

                conn.commit(); // Hoàn tất transaction

                JOptionPane.showMessageDialog(this, "Xuất hóa đơn thành công với mã: " + mahd, "Thành công", JOptionPane.INFORMATION_MESSAGE);

                // In hóa đơn
                printInvoice(mahd, maKH);

                // Reset form
                model.setRowCount(0);
                txtTongluongxuat.setText("0");
                txtTonggiatrixuat.setText("0");
                txtTenKh.setText("");
                txtMakh.setText("");
                txtSdt.setText("");
                txtTienkhachdua.setText("");
                cmbGioitinh.setSelectedIndex(0);
                txtMasp.requestFocus();

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            SetHoaDon();
            hienMaHoaDonTiepTheo();
        }
 
    }//GEN-LAST:event_btnXuatbillActionPerformed

    private void jbxTaohoadonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbxTaohoadonMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) TableChitiethoadon.getModel();
    
        // Xoá toàn bộ dữ liệu nhưng giữ tiêu đề cột
        model.setRowCount(0);

        // (Tuỳ chọn) Reset các ô hiển thị tổng tiền, tổng SL, mã hóa đơn...
        txtTongluongxuat.setText("0");
        txtTonggiatrixuat.setText("0");
        txtMahd.setText(sinhMaHoaDonTiepTheo()); 
        jbxTaohoadon.setSelected(false);
    }//GEN-LAST:event_jbxTaohoadonMouseClicked

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        new LoginForm().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnDktv1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDktv1ActionPerformed
        AddCustomer addCustomer = new AddCustomer(null, this);
        this.setVisible(false); // ẩn form cha
        addCustomer.setVisible(true);
    }//GEN-LAST:event_btnDktv1ActionPerformed

    private void cmbGioitinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbGioitinhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbGioitinhActionPerformed

    private void tableHoadonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableHoadonMouseClicked
        int selectedRow = tableHoadon.getSelectedRow();
        if (selectedRow >= 0) {
            String mahd = tableHoadon.getValueAt(selectedRow, 0).toString(); // Lấy mã hóa đơn

            setCTHD(mahd); // Load chi tiết hóa đơn dựa trên mã
            txtMahd.setText(mahd);

        try (Connection conn = DatabaseConnection.getConnection()) {
                    String sql = "SELECT kh.MAKH, kh.HOTEN, kh.SDT, kh.GT " +
                                 "FROM hoa_don hd " +
                                 "JOIN khach_hang kh ON hd.MAKH = kh.MAKH " +
                                 "WHERE hd.MAHD = ?";
                    try (PreparedStatement pst = conn.prepareStatement(sql)) {
                        pst.setString(1, mahd);
                        try (ResultSet rs = pst.executeQuery()) {
                            if (rs.next()) {
                                txtMakh.setText(rs.getString("MAKH"));
                                txtTenKh.setText(rs.getString("HOTEN"));
                                txtSdt.setText(rs.getString("SDT"));
                                cmbGioitinh.setSelectedItem(rs.getString("GT"));
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi tải thông tin khách hàng", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            // Kiểm tra nếu bảng chi tiết hóa đơn có dữ liệu thì hiển thị dòng đầu tiên
            if (TableChitiethoadon.getRowCount() > 0) {
                displaySelectedRow(0);
            }

            capNhatgiatribill(); // Cập nhật tổng tiền và tổng số lượng
        }  
    }//GEN-LAST:event_tableHoadonMouseClicked

    private void btnKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhuyenMaiActionPerformed
    new KhuyenMai(this, masp, tensp, dongia, soluong, madanhmuc, mathuonghieu).setVisible(true);
    this.setVisible(false);
    }//GEN-LAST:event_btnKhuyenMaiActionPerformed

    private void btnInlaibillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInlaibillActionPerformed
        int selectedRow = tableHoadon.getSelectedRow();
        if (selectedRow >= 0) {
            String mahd = tableHoadon.getValueAt(selectedRow, 0).toString();
            printInvoice(mahd); // chỉ cần truyền mahd là đủ
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để in lại");
        }
    }//GEN-LAST:event_btnInlaibillActionPerformed

    private void btnDanhThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDanhThuActionPerformed
        // TODO add your handling code here:
        new DANHTHU(this).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnDanhThuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new EmployeeProducts().setVisible(true));
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableChitiethoadon;
    private javax.swing.JLabel blbTenChuongTrinh;
    private javax.swing.JButton btnDanhThu;
    private javax.swing.JButton btnDktv1;
    private javax.swing.JButton btnInlaibill;
    private javax.swing.JButton btnKhuyenMai;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnXuatbill;
    private javax.swing.JComboBox<String> cmbGioitinh;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JCheckBox jbxTaohoadon;
    private javax.swing.JLabel lblHinhanhNV;
    private javax.swing.JLabel lblLuongxuat;
    private javax.swing.JLabel lblSdt;
    private javax.swing.JLabel lblTenKH;
    private javax.swing.JLabel lblTennv;
    private javax.swing.JLabel lblTienkhachdua;
    private javax.swing.JLabel lblTonggiatri;
    private javax.swing.JLabel lbldanhmuc;
    private javax.swing.JLabel lbldvnd;
    private javax.swing.JLabel lblgia;
    private javax.swing.JLabel lblgioitinh;
    private javax.swing.JLabel lblmahd;
    private javax.swing.JLabel lblmakh;
    private javax.swing.JLabel lblmanv;
    private javax.swing.JLabel lblmasp;
    private javax.swing.JLabel lblngay;
    private javax.swing.JLabel lblsoluong;
    private javax.swing.JLabel lbltensp;
    private javax.swing.JLabel lblthuonghieu;
    private javax.swing.JLabel lbltientra;
    private javax.swing.JTable tableHoadon;
    private javax.swing.JTextField txtDanhmuc;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMahd;
    private javax.swing.JTextField txtMakh;
    private javax.swing.JTextField txtManv;
    private javax.swing.JTextField txtMasp;
    private javax.swing.JTextField txtNgayhd;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtSoluong;
    private javax.swing.JTextField txtTenKh;
    private javax.swing.JTextField txtTennv;
    private javax.swing.JTextField txtTensp;
    private javax.swing.JTextField txtThuonghieu;
    private javax.swing.JTextField txtTienkhachdua;
    private javax.swing.JTextField txtTientra;
    private javax.swing.JTextField txtTonggiatrixuat;
    private javax.swing.JTextField txtTongluongxuat;
    // End of variables declaration//GEN-END:variables
}
