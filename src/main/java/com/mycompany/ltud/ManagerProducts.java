/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.ltud;

import java.awt.Image;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 08567
 */
public class ManagerProducts extends javax.swing.JFrame {
    private ManagerForm parent;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ManagerProducts.class.getName());

    /**
     * Creates new form ManagerProducts
     */
    public ManagerProducts(ManagerForm parent) {
        this.parent = parent;
        initComponents();
        loadSanPhamData();
        displaySelectedRow(0);
        updateImageByRow(0);
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                parent.setVisible(true);
            }
        });
                tableProducts.addMouseListener(new java.awt.event.MouseAdapter(){
                @Override 
                public void mouseClicked(java.awt.event.MouseEvent evt){
                    int selectedRow = tableProducts.getSelectedRow();
                    displaySelectedRow(selectedRow);
        } 
        });
    }
    void displaySelectedRow( int row){
        if(row >=0 && row < tableProducts.getRowCount()){
            txtMasp.setText(tableProducts.getValueAt(row, 0).toString());
            txtTensp.setText(tableProducts.getValueAt(row, 1).toString());
            txtDongia.setText(tableProducts.getValueAt(row, 2).toString());
            txtSoluong.setText(tableProducts.getValueAt(row, 3).toString());
            String madanhmuc = tableProducts.getValueAt(row, 6).toString();
            String tendanhmuc="";
            try (
                    Connection conn = DatabaseConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement("SELECT TENDANHMUC FROM danh_muc_san_pham WHERE MADANHMUC = ?");
                ) {
                    ps.setString(1, madanhmuc); 

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            tendanhmuc = rs.getString("TENDANHMUC");
                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage());
                }

            txtDanhmuc.setText(tendanhmuc);
           
            String mathuonghieu = (tableProducts.getValueAt(row, 7).toString());
            String tenthuonghieu="";
            try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT TENTHUONGHIEU FROM thuong_hieu WHERE MATHUONGHIEU = ?");
            ) {
                ps.setString(1,mathuonghieu); 

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        tenthuonghieu = rs.getString("TENTHUONGHIEU");
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage());
            }

            txtThuonghieu.setText(tenthuonghieu);
            }
        updateImageByRow(row);
        }
    
    void loadSanPhamData() {
       DefaultTableModel model = (DefaultTableModel) tableProducts.getModel();
       model.setColumnIdentifiers(new String[]{"Mã Sản Phẩm", "Tên Sản Phẩm", "Đơn Giá", "Số Lượng","Mô tả","Hình Ảnh","Mã Danh Mục", "Mã Thương Hiệu","Ngày Tạo", "Ngày Sửa"});
       model.setRowCount(0); // Xóa dữ liệu cũ
//       tableUsers.getColumnModel().getColumn(0).setPreferredWidth(30);  // ID
//       tableUsers.getColumnModel().getColumn(1).setPreferredWidth(150); // Username
       
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM san_pham");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Object[] row = {
                    rs.getString("MASP"),
                    rs.getString("TENSP"),
                    rs.getDouble("DONGIA"),
                    rs.getInt("SOLUONG"),
                    rs.getString("MOTA"),
                    rs.getString("HINHANH"),
                    rs.getString("MADANHMUC"),
                    rs.getString("MATHUONGHIEU"),
                    rs.getString("NGAYTAO"),
                    rs.getString("NGAYSUA")
                };
                model.addRow(row);               
            } 
            tableProducts.removeColumn(tableProducts.getColumnModel().getColumn(9));
            //tableUsers.setModel(model); // Gắn model mới cho bảng
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }       
    }
    private String getTenDanhMuc(String madanhmuc) {
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement("SELECT TENDANHMUC FROM danh_muc_san_pham WHERE MADANHMUC = ?")) {
        ps.setString(1, madanhmuc);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("TENDANHMUC");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    private String getTenThuongHieu(String mathuonghieu) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT TENTHUONGHIEU FROM thuong_hieu WHERE MATHUONGHIEU = ?")) {
            ps.setString(1, mathuonghieu);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("TENTHUONGHIEU");
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
      private void updateImageByRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < tableProducts.getRowCount()) {
            String masanpham = tableProducts.getValueAt(rowIndex, 0).toString();
            String imageFile = getImageFile(masanpham);
            if (imageFile != null) {
                setImageToLabel("images/" + imageFile);
            } else {
                setImageToLabel("images/default.jpg");
            }
        }
    }

    private String getImageFile(String masanpham) {
        String imageFile = null;
        String sql = "SELECT * FROM san_pham WHERE MASP = ?";

        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)){
//            CallableStatement cs = conn.prepareCall("{CALL GetsanphamByMaSanPham(?)}")) { 
            stmt.setString(1, masanpham);
//            cs.setString(1, masanpham);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                imageFile = rs.getString("HINHANH"); // Giả sử chỉ chứa tên file như 'neel-1.jpg'
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
                    lblImages.getWidth(),
                    lblImages.getHeight(),
                    Image.SCALE_SMOOTH
            );
            lblImages.setIcon(new ImageIcon(scaledImage));
        } else {
            System.out.println("Không tìm thấy ảnh: " + imagePath);
            lblImages.setIcon(null);
        }
    } catch (Exception e) {
        e.printStackTrace();
        lblImages.setIcon(null);
    }
}
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableProducts = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        lblMasp = new javax.swing.JLabel();
        lblDongia = new javax.swing.JLabel();
        lblTensp = new javax.swing.JLabel();
        lblSoluong = new javax.swing.JLabel();
        lblDanhmuc = new javax.swing.JLabel();
        lblThuonghieu = new javax.swing.JLabel();
        txtSoluong = new javax.swing.JTextField();
        txtDongia = new javax.swing.JTextField();
        txtMasp = new javax.swing.JTextField();
        txtDanhmuc = new javax.swing.JTextField();
        txtThuonghieu = new javax.swing.JTextField();
        txtTensp = new javax.swing.JTextField();
        lblImages = new javax.swing.JLabel();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Manager Products");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ SẢN PHẨM");

        tableProducts.setModel(new javax.swing.table.DefaultTableModel(
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
        tableProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableProductsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableProducts);

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnAdd.setText("Add");
        btnAdd.setActionCommand("");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        lblMasp.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMasp.setText("Mã Sản Phẩm: ");

        lblDongia.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDongia.setText("Đơn giá: ");

        lblTensp.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTensp.setText("Tên Sản Phẩm: ");

        lblSoluong.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSoluong.setText("Số Lượng:");

        lblDanhmuc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDanhmuc.setText("Danh Mục: ");

        lblThuonghieu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblThuonghieu.setText("Thương Hiệu:");

        lblImages.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblImages.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImages.setText("Hình Ảnh");
        lblImages.setPreferredSize(new java.awt.Dimension(200, 200));
        lblImages.setRequestFocusEnabled(false);
        lblImages.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblSoluong, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblThuonghieu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMasp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDanhmuc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTensp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDongia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtThuonghieu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtMasp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtDanhmuc, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(txtDongia, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtSoluong, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtTensp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(29, 29, 29)
                                .addComponent(lblImages, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(171, 171, 171)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMasp)
                            .addComponent(txtMasp, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTensp, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTensp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDongia)
                            .addComponent(txtDongia, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSoluong)
                            .addComponent(txtSoluong, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDanhmuc)
                            .addComponent(txtDanhmuc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblThuonghieu)
                            .addComponent(txtThuonghieu, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblImages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(41, 41, 41)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        new AddProdust(this).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
         int selectedRow = tableProducts.getSelectedRow();
         if(selectedRow == -1){
             JOptionPane.showMessageDialog(this,"Vui lòng chọn Sản Phẩm để hiệu chỉnh", "Thông báo",JOptionPane.WARNING_MESSAGE);
             return;
         }

         String masp = (String)tableProducts.getValueAt(selectedRow, 0);
         String tensp = (String)tableProducts.getValueAt(selectedRow, 1);
         double dongia = (Double)tableProducts.getValueAt(selectedRow, 2);
         int soluong = (int)tableProducts.getValueAt(selectedRow, 3);
         String mota = (String)tableProducts.getValueAt(selectedRow, 4);
         String hinhanh =(String)tableProducts.getValueAt(selectedRow, 7);
         DefaultTableModel model = (DefaultTableModel) tableProducts.getModel();
         String madanhmuc = (String) model.getValueAt(selectedRow, 6);
         String mathuonghieu = (String) model.getValueAt(selectedRow, 5);
         String danhmuc = getTenDanhMuc(madanhmuc);
         String thuonghieu = getTenThuongHieu(mathuonghieu);
         EditProducts editProducts = new EditProducts(this, masp, tensp, dongia, soluong, mota, hinhanh, danhmuc, thuonghieu);
         editProducts.setVisible(true);
         this.setVisible(false);
    }//GEN-LAST:event_btnEditActionPerformed

    private void tableProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProductsMouseClicked
        // TODO add your handling code here:
        int selectedRow = tableProducts.getSelectedRow();
        updateImageByRow(selectedRow);
    }//GEN-LAST:event_tableProductsMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
           int selectedRow = tableProducts.getSelectedRow();
        if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn Sản Phẩm để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
        }
        int option = JOptionPane.showConfirmDialog(this,"Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
        int userID = Integer.parseInt(tableProducts.getValueAt(selectedRow, 0).toString());
        try { Connection conn = DatabaseConnection.getConnection();
            String sql = " DELETE from san_pham WHERE MASp = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userID);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                loadSanPhamData(); // Load lại bảng
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi " + e.getMessage());
        }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new ManagerProducts(null).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDanhmuc;
    private javax.swing.JLabel lblDongia;
    private javax.swing.JLabel lblImages;
    private javax.swing.JLabel lblMasp;
    private javax.swing.JLabel lblSoluong;
    private javax.swing.JLabel lblTensp;
    private javax.swing.JLabel lblThuonghieu;
    private javax.swing.JTable tableProducts;
    private javax.swing.JTextField txtDanhmuc;
    private javax.swing.JTextField txtDongia;
    private javax.swing.JTextField txtMasp;
    private javax.swing.JTextField txtSoluong;
    private javax.swing.JTextField txtTensp;
    javax.swing.JTextField txtThuonghieu;
    // End of variables declaration//GEN-END:variables
}
