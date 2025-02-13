/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hotelmanagementsystem.costumer;

import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author bugtong
 */
public class room_selection extends javax.swing.JFrame {
    
    public room_selection(int type, int userID, String Name) {
        this.type = type;
        this.userID = userID;
        this.name = Name;
        initComponents();
    }
    private boolean updating = false;
    public void enforceDateRules() {
      if (updating) {
            return;
        }

        updating = true;

        Date date1 = jDateChooser1.getDate();
        Date date2 = jDateChooser2.getDate();
        Calendar calendar = Calendar.getInstance();

        if (date1 != null) {
            Date minDate1 = jDateChooser1.getMinSelectableDate();
            if (date1.before(minDate1)) {
                jDateChooser1.setDate(minDate1);
                updating = false;
                return;
            }

            calendar.setTime(date1);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date newMinDate2 = calendar.getTime();
            jDateChooser2.setMinSelectableDate(newMinDate2);

            calendar.add(Calendar.DAY_OF_YEAR, 13);
            Date newMaxDate2 = calendar.getTime();
            jDateChooser2.setMaxSelectableDate(newMaxDate2);

            if (date2 != null) {
                if (date2.before(newMinDate2)) {
                    jDateChooser2.setDate(newMinDate2);
                } else if (date2.after(newMaxDate2)) {
                    jDateChooser2.setDate(newMaxDate2);
                }
            }
        }

        updating = false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        doubleBeds = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(173, 223, 179));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 76, 84));
        jLabel2.setText("BED ROOMS");
        if(type == 1){
            jLabel2.setText("SINGLE TYPE BED ROOMS");
        }
        if(type == 2){
            jLabel2.setText("DOUBLE TYPE BED ROOMS");
        }
        if(type == 3){
            jLabel2.setText("SUITE TYPE BED ROOMS");
        }
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 490, -1));

        doubleBeds.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ROOM NO.", "PRICE", "ACTION"
            }
        ));
        doubleBeds.getColumn("ACTION").setCellRenderer(new ButtonRenderer());
        doubleBeds.getColumn("ACTION").setCellEditor(new ButtonEditor(new JCheckBox()));
        jScrollPane1.setViewportView(doubleBeds);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 700, 320));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel1.setText("DATE:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        calendar.add(Calendar.DAY_OF_YEAR, 2);
        Date minDate1 = calendar.getTime();
        jDateChooser1.setDate(minDate1);
        jDateChooser1.setMinSelectableDate(minDate1);

        calendar.add(Calendar.DAY_OF_YEAR, 78);
        Date maxDate1 = calendar.getTime();
        jDateChooser1.setMaxSelectableDate(maxDate1);

        calendar.setTime(minDate1);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date minDate2 = calendar.getTime();
        jDateChooser2.setDate(minDate2);
        jDateChooser2.setMinSelectableDate(minDate2);

        calendar.add(Calendar.DAY_OF_YEAR, 13);
        Date maxDate2 = calendar.getTime();
        jDateChooser2.setMaxSelectableDate(maxDate2);

        // Temporarily remove listeners while setting the initial dates
        jDateChooser1.getDateEditor().removePropertyChangeListener(evt -> enforceDateRules());
        jDateChooser2.getDateEditor().removePropertyChangeListener(evt -> enforceDateRules());

        jDateChooser1.getDateEditor().addPropertyChangeListener(evt -> enforceDateRules());
        jDateChooser2.getDateEditor().addPropertyChangeListener(evt -> enforceDateRules());
        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });
        jPanel2.add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, 140, -1));
        jPanel2.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 140, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel3.setText("-");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 10, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(665, 10, 40, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 718, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 440, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new customer_dashboard(name, true, userID).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange
        if ("date".equals(evt.getPropertyName())) {
            java.util.Date checkInDate = jDateChooser1.getDate();
            java.util.Date checkOutDate = jDateChooser2.getDate();
            java.sql.Date sqlCheckInDate = new java.sql.Date(checkInDate.getTime());
            java.sql.Date sqlCheckOutDate = new java.sql.Date(checkOutDate.getTime());
            fetchAvailableRooms(sqlCheckInDate, sqlCheckOutDate);
        }
    }//GEN-LAST:event_jDateChooser2PropertyChange

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new room_selection(1, 1, "ryan").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable doubleBeds;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    private int type;
    private int userID;
    private String name;
    Calendar calendar = Calendar.getInstance();
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setText("Book");
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);

            // Add action listener for the button
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    int row = doubleBeds.getSelectedRow();
                    Object roomNo = doubleBeds.getValueAt(row, 0);
                    int roomId = Integer.parseInt(roomNo.toString());
                    java.sql.Date checkIn = java.sql.Date.valueOf("2025-02-15");
                    java.sql.Date checkOut = java.sql.Date.valueOf("2025-02-18");
                    boolean success = addBooking(userID, roomId, checkIn, checkOut);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }
    }
    private void fetchAvailableRooms(Date checkIn,Date checkOut) {
        String query = "SELECT r.room_id, r.price FROM room r "
                     + "LEFT JOIN booking b ON b.room_id = r.room_id "
                     + "AND (b.check_in_date < ? AND b.check_out_date > ?) "
                     + "WHERE b.room_id IS NULL AND r.room_type = ?";
        String rT = getRoomType();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setDate(1, (java.sql.Date) checkOut); 
            pst.setDate(2, (java.sql.Date) checkIn); 
            pst.setString(2, rT); 
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) doubleBeds.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                int roomId = rs.getInt("room_id");
                double price = rs.getDouble("price");

                model.addRow(new Object[]{roomId, price});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean addBooking(int customerId, int roomId, Date checkIn, Date checkOut) {
        String query = "INSERT INTO booking (customer_id, room_id, check_in_date, check_out_date, status) VALUES (?, ?, ?, ?, 'Confirmed')";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
             PreparedStatement pst = con.prepareStatement(query)) {

            // Set values for placeholders
            pst.setInt(1, customerId);
            pst.setInt(2, roomId);
            pst.setDate(3, (java.sql.Date) checkIn);
            pst.setDate(4, (java.sql.Date) checkOut);

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(null, "Booking added successfully!");
                fetchAvailableRooms(checkIn, checkOut);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }

        return false;
    }
    String getRoomType(){
        String roomType = null;
        if(type == 1){
            roomType = "Single";
        }
        if(type == 2){
            roomType = "Double";
        }
        if(type == 3){
            roomType = "Suite";
        }
        return roomType;
    }
}
