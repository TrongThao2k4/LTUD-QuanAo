/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.ltud;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class EditProducts extends javax.swing.JFrame {
    private ManagerProducts parent;
    private int soluong;
    private String masp, tensp, mota,danhmuc,thuonghieu,hinhanh;
    private double dongia;
    
    public EditProducts(ManagerProducts parent, String masp, String tensp,double dongia,int soluong,String mota,String danhmuc, String thuonghieu, String hinhanh){
    if (parent == null || masp == null) {
        throw new IllegalArgumentException("Dữ liệu đầu vào không hợp lệ!");
    }
    this.parent = parent;
    this.masp = masp;
    this.tensp = tensp;
    this.dongia = dongia;
    this.soluong = soluong;
    this.mota = mota;
    this.hinhanh = hinhanh != null ? hinhanh : ""; // Đảm bảo hinhanh không null
    this.danhmuc = danhmuc;
    this.thuonghieu = thuonghieu;
    
    initComponents();
    loadProductData();
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                parent.setVisible(true);
            }
        });
    }
    private void loadProductData() {
        txtTensp.setText(tensp);
        txtSoluong.setText(String.valueOf(soluong));
        txtDongia.setText(String.valueOf(dongia));
        txtMota.setText(mota);
        cmbThuonghieu.setSelectedItem(thuonghieu);
        cmbDanhmuc.setSelectedItem(danhmuc);
        updateImageByMaSanPham(masp); // Gọi hàm cập nhật ảnh
    }


    private void updateImageByMaSanPham(String masanpham) {
    String imageFile = getImageFile(masanpham);
    if (imageFile != null) {
        setImageToLabel("images/" + imageFile);
    } else {
        setImageToLabel("images/default.jpg");
    }
}
    private void setImageToLabel(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        lblImages.setIcon(new ImageIcon(img));
    }

    private String getImageFile(String masanpham) {
        String fileName = null;
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT HINHANH FROM san_pham WHERE MASP = ?");
            ps.setString(1, masanpham);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                fileName = rs.getString("HINHANH");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fileName;
    }
    private void displayImage(String imagePath) {
    System.out.println("[DEBUG] displayImage: " + imagePath); // Log để theo dõi

    lblImages.setIcon(null); // Xóa ảnh cũ (nếu có)

    if (imagePath != null && !imagePath.trim().isEmpty()) {
        try {
            File imageFile = new File("images/" + imagePath);
            System.out.println("[DEBUG] Đường dẫn tuyệt đối: " + imageFile.getAbsolutePath());

            if (imageFile.exists()) {
                ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());

                // Co ảnh theo kích thước của label
                Image scaledImage = icon.getImage().getScaledInstance(
                    lblImages.getWidth(),
                    lblImages.getHeight(),
                    Image.SCALE_SMOOTH
                );

                lblImages.setIcon(new ImageIcon(scaledImage));
                lblImages.setToolTipText(imagePath); // Tooltip hiển thị tên ảnh

            } else {
                System.out.println("[WARN] Không tìm thấy ảnh tại: " + imageFile.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Không tìm thấy ảnh: " + imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi hiển thị ảnh: " + e.getMessage());
        }
    } else {
        System.out.println("[WARN] Đường dẫn ảnh rỗng hoặc null");
        JOptionPane.showMessageDialog(this, "Không có ảnh để hiển thị!");
    }
}

   private String getMaDanhMuc(String tendanhmuc) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT MADANHMUC FROM danh_muc_san_pham WHERE TENDANHMUC = ?")) {
            ps.setString(1, tendanhmuc);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("MADANHMUC");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getMaThuongHieu(String tenthuonghieu) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT MATHUONGHIEU FROM thuong_hieu WHERE TENTHUONGHIEU = ?")) {
            ps.setString(1, tenthuonghieu);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("MATHUONGHIEU");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtSoluong = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMota = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbThuonghieu = new javax.swing.JComboBox<>();
        cmbDanhmuc = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        Update = new javax.swing.JButton();
        lblImages = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTensp = new javax.swing.JTextField();
        btnHinhanh = new javax.swing.JButton();
        jlab = new javax.swing.JLabel();
        txtDongia = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit User");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("EDIT PRODUCTS");

        txtSoluong.setToolTipText("");
        txtSoluong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoluongActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Tên Sản Phẩm:");

        txtMota.setToolTipText("");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Đơn Giá:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Danh Mục:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Mô Tả:");

        cmbThuonghieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nike", "Adidas", "Puma", "Under Amour" }));

        cmbDanhmuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Áo Thể Thao", "Quần Thể Thao", "Giày", "Phụ kiện" }));
        cmbDanhmuc.setToolTipText("");

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("Reset");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        Update.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Update.setText("Update");
        Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateActionPerformed(evt);
            }
        });

        lblImages.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblImages.setPreferredSize(new java.awt.Dimension(200, 300));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Thương Hiệu:");

        txtTensp.setToolTipText("");

        btnHinhanh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHinhanh.setText("Upload");
        btnHinhanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHinhanhActionPerformed(evt);
            }
        });

        jlab.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlab.setText("Số Lượng:");

        txtDongia.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cmbDanhmuc, javax.swing.GroupLayout.Alignment.LEADING, 0, 360, Short.MAX_VALUE)
                            .addComponent(cmbThuonghieu, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMota)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jlab, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtSoluong, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtTensp, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                                .addComponent(txtDongia)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblImages, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
            .addGroup(layout.createSequentialGroup()
                .addGap(192, 192, 192)
                .addComponent(Update, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnHinhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTensp, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDongia, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlab, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoluong, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbDanhmuc, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbThuonghieu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMota, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(lblImages, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Update, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHinhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(208, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateActionPerformed
        tensp = txtTensp.getText().trim();
        mota = txtMota.getText().trim();
        danhmuc = cmbDanhmuc.getSelectedItem().toString();
        thuonghieu = cmbThuonghieu.getSelectedItem().toString();
        String strsoluong = txtSoluong.getText().trim();
        String strdongia = txtDongia.getText().trim();

        if (strdongia.isEmpty() || strsoluong.isEmpty() || tensp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            soluong = Integer.parseInt(strsoluong);
            dongia = Double.parseDouble(strdongia);
            if (dongia < 0 || soluong < 0) {
                JOptionPane.showMessageDialog(this, "Đơn giá và số lượng phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection()) {
                String madanhmuc = getMaDanhMuc(danhmuc);
                if (madanhmuc == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy danh mục: " + danhmuc);
                    return;
                }

                String mathuonghieu = getMaThuongHieu(thuonghieu);
                if (mathuonghieu == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy thương hiệu: " + thuonghieu);
                    return;
                }

                String sql = "UPDATE san_pham SET TENSP = ?, DONGIA = ?, SOLUONG = ?, MOTA = ?, HINHANH = ?, MADANHMUC = ?, MATHUONGHIEU = ?, NGAYSUA = NOW() WHERE MASP = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, tensp);
                    ps.setDouble(2, dongia);
                    ps.setInt(3, soluong);
                    ps.setString(4, mota);
                    ps.setString(5, hinhanh); // Cập nhật đường dẫn ảnh
                    ps.setString(6, madanhmuc);
                    ps.setString(7, mathuonghieu);
                    ps.setString(8, masp);
                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(this, "Cập nhật dữ liệu thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        if (parent != null) {
                            parent.loadSanPhamData();
                        }
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy Sản Phẩm");
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Đơn giá hoặc số lượng phải là số!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_UpdateActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        txtDongia.setText(String.valueOf(dongia));
        txtSoluong.setText(String.valueOf(soluong));
        txtTensp.setText(tensp);
        txtMota.setText(mota);
        cmbDanhmuc.setSelectedItem(danhmuc);
        cmbThuonghieu.setSelectedItem(thuonghieu);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtSoluongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoluongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoluongActionPerformed

    private void btnHinhanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHinhanhActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("D:\\"));  // Mặc định mở ổ C

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();

            // Tên mới có timestamp để tránh trùng
            String newImageName = System.currentTimeMillis() + "_" + fileName;
            File destFile = new File("images/" + newImageName);

            try {
                // Copy thay vì rename để tránh lỗi giữa ổ đĩa
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Xóa ảnh cũ (nếu có và không phải ảnh mặc định)
                if (hinhanh != null && !hinhanh.equals("default.jpg")) {
                    File oldFile = new File("images/" + hinhanh);
                    if (oldFile.exists()) oldFile.delete();
                }

                // Cập nhật biến và hiển thị ảnh
                hinhanh = newImageName;
                displayImage(hinhanh); // Gọi hàm hiển thị
                updateImageByMaSanPham(masp); // Cập nhật DB

            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Không thể lưu ảnh: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnHinhanhActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditProducts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditProducts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditProducts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditProducts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new EditUserForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Update;
    private javax.swing.JButton btnHinhanh;
    private javax.swing.JComboBox<String> cmbDanhmuc;
    private javax.swing.JComboBox<String> cmbThuonghieu;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jlab;
    private javax.swing.JLabel lblImages;
    private javax.swing.JTextField txtDongia;
    private javax.swing.JTextField txtMota;
    private javax.swing.JTextField txtSoluong;
    private javax.swing.JTextField txtTensp;
    // End of variables declaration//GEN-END:variables
}
