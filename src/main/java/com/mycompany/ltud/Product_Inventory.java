/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.ltud;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 08567
 */
public class Product_Inventory extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Product_Inventory.class.getName());
    private EmployeeProducts parent;
    String masp;
    
    public Product_Inventory(EmployeeProducts parent) {
        this.parent = parent;
        initComponents();
        loadProducts();
        hienTatCaSanPham();
        tableProducts.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "EnterKey");

        tableProducts.getActionMap().put("EnterKey", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableProducts.getSelectedRow();
                if (selectedRow != -1) {
                    tableProductsMouseClicked(null); // Gọi lại xử lý click chuột
                }
            }
        });
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (parent != null) {
                    parent.setVisible(true);
                }
            }
        });        
    }
    public Product_Inventory(EmployeeProducts parent, String masp) {
        this.parent = parent;
        initComponents();
        timKiemSanPhamTheoMa(masp); // Chỉ gọi tìm theo mã
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    this.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
            if (parent != null) {
                parent.setVisible(true);
            }
        }
    });
    }
    void loadProducts() {
        DefaultTableModel model = (DefaultTableModel) tableProducts.getModel();
        model.setColumnIdentifiers(new String[]{
            "MASP", "TENSP", "DONGIA", "SOLUONG", "MOTA",
            "MADANHMUC", "MATHUONGHIEU", "TỒN KHO", "ĐÃ XUẤT", "CÒN LẠI"
        });
        model.setRowCount(0); 

        String sql = """
            SELECT sp.MASP, sp.TENSP, sp.DONGIA, sp.SOLUONG, sp.MOTA, sp.MADANHMUC, sp.MATHUONGHIEU,
                   tk.SOLUONGTON, tk.LUONGXUAT, tk.SOLUONGCUOI
            FROM san_pham sp
            LEFT JOIN ton_kho tk ON sp.MASP = tk.MASP
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int totalQuantity = 0;
            int tongluongxuat = 0;

            while (rs.next()) {
                int soluong = rs.getInt("SOLUONG");
                int luongxuat = rs.getInt("LUONGXUAT");
                totalQuantity += soluong;
                tongluongxuat += luongxuat;
                

                Object[] row = {
                    rs.getString("MASP"),
                    rs.getString("TENSP"),
                    rs.getDouble("DONGIA"),
                    rs.getInt("SOLUONG"),
                    rs.getString("MOTA"),
                    rs.getString("MADANHMUC"),
                    rs.getString("MATHUONGHIEU"),
                    rs.getObject("SOLUONGTON") != null ? rs.getInt("SOLUONGTON") : 0,
                    rs.getObject("LUONGXUAT") != null ? rs.getInt("LUONGXUAT") : 0,
                    rs.getObject("SOLUONGCUOI") != null ? rs.getInt("SOLUONGCUOI") : 0
                };

                model.addRow(row);
            }

            txtTonkho.setText(String.valueOf(totalQuantity));
            txtXuatban.setText(String.valueOf(tongluongxuat));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    private void timKiemSanPhamTheoMa(String masp) {
        DefaultTableModel model = (DefaultTableModel) tableProducts.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{
            "MASP", "TENSP", "DONGIA", "SOLUONG", "MOTA", 
            "MADANHMUC", "MATHUONGHIEU", "SOLUONGTON", "LUONGXUAT", "SOLUONGCUOI"
        });

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = """
                SELECT sp.*, tk.SOLUONGTON, tk.LUONGXUAT, tk.SOLUONGCUOI
                FROM san_pham sp
                LEFT JOIN ton_kho tk ON sp.MASP = tk.MASP
                WHERE sp.MASP = ?
            """;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, masp);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Object[] row = {
                            rs.getString("MASP"),
                            rs.getString("TENSP"),
                            rs.getDouble("DONGIA"),
                            rs.getInt("SOLUONG"),
                            rs.getString("MOTA"),
                            rs.getString("MADANHMUC"),
                            rs.getString("MATHUONGHIEU"),
                            rs.getObject("SOLUONGTON") != null ? rs.getInt("SOLUONGTON") : 0,
                            rs.getObject("LUONGXUAT") != null ? rs.getInt("LUONGXUAT") : 0,
                            rs.getObject("SOLUONGCUOI") != null ? rs.getInt("SOLUONGCUOI") : 0
                        };
                        model.addRow(row);
                        txtTonkho.setText(String.valueOf(rs.getInt("SOLUONG")));
                        txtXuatban.setText(String.valueOf(rs.getInt("LUONGXUAT")));
                        txtMasp.setText(masp);
                    } else {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm có mã: " + masp);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm sản phẩm: " + e.getMessage());
        }
    }
    private void hienTatCaSanPham() {
        DefaultTableModel model = (DefaultTableModel) tableProducts.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{
            "MASP", "TENSP", "DONGIA", "SOLUONG", "MOTA", 
            "MADANHMUC", "MATHUONGHIEU", "SOLUONGTON", "LUONGXUAT", "SOLUONGCUOI"
        });

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = """
                SELECT sp.*, tk.SOLUONGTON, tk.LUONGXUAT, tk.SOLUONGCUOI
                FROM san_pham sp
                LEFT JOIN ton_kho tk ON sp.MASP = tk.MASP
            """;

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Object[] row = {
                        rs.getString("MASP"),
                        rs.getString("TENSP"),
                        rs.getDouble("DONGIA"),
                        rs.getInt("SOLUONG"),
                        rs.getString("MOTA"),
                        rs.getString("MADANHMUC"),
                        rs.getString("MATHUONGHIEU"),
                        rs.getObject("SOLUONGTON") != null ? rs.getInt("SOLUONGTON") : 0,
                        rs.getObject("LUONGXUAT") != null ? rs.getInt("LUONGXUAT") : 0,
                        rs.getObject("SOLUONGCUOI") != null ? rs.getInt("SOLUONGCUOI") : 0
                    };
                    model.addRow(row);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu tồn kho: " + e.getMessage());
        }
    }

    
    void SearchProducts(String masp) {
        this.masp = masp;
        DefaultTableModel model = (DefaultTableModel) tableProducts.getModel();
        model.setColumnIdentifiers(new String[]{
            "MASP", "TENSP", "DONGIA", "SOLUONG", "MOTA",
            "MADANHMUC", "MATHUONGHIEU",
            "TON KHO", "ĐÃ XUẤT", "CÒN LẠI"
        });
        model.setRowCount(0);

        String sql = "SELECT sp.*, tk.SOLUONGTON, tk.LUONGXUAT, tk.SOLUONGCUOI " +
                     "FROM san_pham sp LEFT JOIN ton_kho tk ON sp.MASP = tk.MASP " +
                     "WHERE sp.MASP = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, masp);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                        rs.getString("MASP"),
                        rs.getString("TENSP"),
                        rs.getDouble("DONGIA"),
                        rs.getInt("SOLUONG"),
                        rs.getString("MOTA"),
                        rs.getString("MADANHMUC"),
                        rs.getString("MATHUONGHIEU"),
                        rs.getInt("SOLUONGTON"),
                        rs.getInt("LUONGXUAT"),
                        rs.getInt("SOLUONGCUOI")
                    };
                    model.addRow(row);

                    // Gán tồn kho lên ô txtTonkho nếu cần
                    txtTonkho.setText(String.valueOf(rs.getInt("SOLUONGCUOI")));
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm sản phẩm: " + e.getMessage());
        }
    }

    private boolean Kiemtratonkho(String masp) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT SOLUONG FROM san_pham WHERE MASP = ?")) {
            ps.setString(1, masp);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("SOLUONG") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableProducts = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtMasp = new javax.swing.JTextField();
        blbTonkho = new javax.swing.JLabel();
        txtTonkho = new javax.swing.JTextField();
        blbTonkho1 = new javax.swing.JLabel();
        txtXuatban = new javax.swing.JTextField();
        btnTonkho = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
        jScrollPane1.setViewportView(tableProducts);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("Mã Sản Phẩm");

        txtMasp.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtMasp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaspActionPerformed(evt);
            }
        });

        blbTonkho.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        blbTonkho.setForeground(new java.awt.Color(0, 0, 204));
        blbTonkho.setText("Tồn kho");

        txtTonkho.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        blbTonkho1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        blbTonkho1.setForeground(new java.awt.Color(0, 0, 204));
        blbTonkho1.setText("Xuất bán");

        txtXuatban.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        btnTonkho.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTonkho.setForeground(new java.awt.Color(0, 0, 153));
        btnTonkho.setText("Tồn Kho");
        btnTonkho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTonkhoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMasp, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(blbTonkho, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTonkho, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(blbTonkho1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtXuatban, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(btnTonkho)
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(blbTonkho1)
                        .addComponent(txtXuatban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTonkho))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(blbTonkho)
                        .addComponent(txtTonkho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtMasp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMaspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaspActionPerformed
        // TODO add your handling code here:
        String masp = txtMasp.getText().trim();
        SearchProducts(masp);
        

    }//GEN-LAST:event_txtMaspActionPerformed

    private void btnTonkhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTonkhoActionPerformed
        loadProducts();
    }//GEN-LAST:event_btnTonkhoActionPerformed

    private void tableProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProductsMouseClicked
    int selectedRow = tableProducts.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) tableProducts.getModel();
            String masp = (String) model.getValueAt(selectedRow, 0);
            String tensp = (String) model.getValueAt(selectedRow, 1);
            double dongia = Double.parseDouble(model.getValueAt(selectedRow, 2).toString());
            int soluong = 1;
            String mota = (String) model.getValueAt(selectedRow, 4);
            String madanhmuc = (String) model.getValueAt(selectedRow, 5);
            String mathuonghieu = (String) model.getValueAt(selectedRow, 6);

            if (!Kiemtratonkho(masp)) {
                JOptionPane.showMessageDialog(this, "Sản phẩm không tồn tại hoặc đã hết hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            parent.setProductInfo(masp, tensp, dongia, soluong, madanhmuc, mathuonghieu);
            parent.setCTHD( masp, tensp,  dongia,  soluong,  madanhmuc,  mathuonghieu, 0 );

            this.dispose();
        }
    }//GEN-LAST:event_tableProductsMouseClicked

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
//        java.awt.EventQueue.invokeLater(() -> new Product_Inventory(null).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel blbTonkho;
    private javax.swing.JLabel blbTonkho1;
    private javax.swing.JButton btnTonkho;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableProducts;
    private javax.swing.JTextField txtMasp;
    private javax.swing.JTextField txtTonkho;
    private javax.swing.JTextField txtXuatban;
    // End of variables declaration//GEN-END:variables
}
