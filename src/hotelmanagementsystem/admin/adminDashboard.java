
package hotelmanagementsystem.admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.awt.BorderLayout; 
import com.mindfusion.scheduling.*;
import com.mindfusion.common.*;
import com.mindfusion.scheduling.model.Appointment; 
import com.mindfusion.scheduling.model.Schedule;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.TimeZone;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;


public final class adminDashboard extends javax.swing.JFrame {
    public adminDashboard() {
        initComponents();
        updateRoomAvailability();
    }
    
    public void fetchAndDisplayRooms() {
    DefaultTableModel model = (DefaultTableModel) roomList.getModel();
    model.setRowCount(0);
    
    String query = "SELECT room_type, price, availability_status FROM room ORDER BY room_type;";
    
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
         PreparedStatement pst = conn.prepareStatement(query);
         ResultSet rs = pst.executeQuery()) {
        
        while (rs.next()) {
            model.addRow(new Object[]{rs.getString("room_type"), rs.getDouble("price"), rs.getString("availability_status")});
        }
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    public void fetchAndDisplayCustomers() {
        DefaultTableModel model = (DefaultTableModel) listCustomer.getModel();
        model.setRowCount(0);

        String query = "SELECT name, email, phone_number FROM customer";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("name"), rs.getString("email"), rs.getString("phone_number")});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void setupDatabaseConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/hms";
            String user = "root";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to database: " + e.getMessage());
        }
    }

    private void getBookingsFromDatabase(int roomId) {
    String query = "SELECT b.check_in_date, b.check_out_date, c.name " +
                   "FROM booking b " +
                   "JOIN customer c ON c.customer_id = b.customer_id " +
                   "WHERE room_id = ?";

    try (PreparedStatement pst = connection.prepareStatement(query)) {
        pst.setInt(1, roomId);
        try (ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Timestamp checkInDate = rs.getTimestamp("check_in_date");
                Timestamp checkOutDate = rs.getTimestamp("check_out_date");
                String customerName = rs.getString("name");

                if (checkInDate != null && checkOutDate != null) {
                    TimeZone timeZone = TimeZone.getDefault();
                    Calendar calendar1 = Calendar.getInstance(timeZone);
                    calendar1.setTimeInMillis(checkInDate.getTime());

                    DateTime start = new DateTime(
                        calendar1.get(Calendar.YEAR),
                        calendar1.get(Calendar.MONTH) + 1,
                        calendar1.get(Calendar.DAY_OF_MONTH),
                        calendar1.get(Calendar.HOUR_OF_DAY),
                        calendar1.get(Calendar.MINUTE),
                        calendar1.get(Calendar.SECOND)
                    );

                    Calendar calendar2 = Calendar.getInstance(timeZone);
                    calendar2.setTimeInMillis(checkOutDate.getTime());
                    DateTime end = new DateTime(
                        calendar2.get(Calendar.YEAR),
                        calendar2.get(Calendar.MONTH) + 1,
                        calendar2.get(Calendar.DAY_OF_MONTH),
                        calendar2.get(Calendar.HOUR_OF_DAY),
                        calendar2.get(Calendar.MINUTE),
                        calendar2.get(Calendar.SECOND)
                    );

                    Appointment appointment = new Appointment();
                    appointment.setStartTime(start);
                    appointment.setEndTime(end);
                    appointment.setSubject("Booking: " + customerName);

                    System.out.println("Converted Start Time: " + start);
                    System.out.println("Check-in time (timestamp): " + checkInDate);

                    calendar.getSchedule().getItems().add(appointment);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error fetching data from database: " + e.getMessage());
    }
}
    
    public void closeDatabaseConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        confirmBook = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        roomChoice = new javax.swing.JComboBox<>();
        bookingCost = new javax.swing.JTextField();
        cancelBook = new javax.swing.JButton();
        inDate = new com.toedter.calendar.JDateChooser();
        outDate = new com.toedter.calendar.JDateChooser();
        customerBox = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        roomTypeText = new javax.swing.JTextField();
        TotalBookingCost = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        bookingList = new javax.swing.JTable();
        calendar = new com.mindfusion.scheduling.Calendar();
        Schedule schedule = calendar.getSchedule();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        priceTxt = new javax.swing.JTextField();
        roomType = new javax.swing.JComboBox<>();
        addRoom = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        roomList = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        nameCustomer = new javax.swing.JTextField();
        emailCustomer = new javax.swing.JTextField();
        contactCustomer = new javax.swing.JTextField();
        addCostumer = new javax.swing.JButton();
        deleteCostumer = new javax.swing.JButton();
        updateCostumer = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listCustomer = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 76, 84));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setBackground(new java.awt.Color(0, 76, 84));
        jTabbedPane1.setForeground(new java.awt.Color(0, 192, 163));
        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTabbedPane1.setName(""); // NOI18N
        jTabbedPane1.setRequestFocusEnabled(false);
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(173, 223, 179));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 76, 84));
        jLabel3.setText("Costumer Name:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 76, 84));
        jLabel5.setText("Check-In Date:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 76, 84));
        jLabel7.setText("Check-Out Date:");

        confirmBook.setBackground(new java.awt.Color(0, 192, 163));
        confirmBook.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        confirmBook.setForeground(new java.awt.Color(0, 76, 84));
        confirmBook.setText("CONFIRM");
        loadRoomsIntoComboBox();
        confirmBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmBookActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 76, 84));
        jLabel9.setText("Room:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 76, 84));
        jLabel10.setText("Price:");

        roomChoice.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        roomChoice.setForeground(new java.awt.Color(0, 76, 84));
        roomChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Room 1", "Room2", "Room 3" }));
        loadRoomsIntoComboBox();
        roomChoice.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                roomChoiceItemStateChanged(evt);
            }
        });

        bookingCost.setEditable(false);

        cancelBook.setBackground(new java.awt.Color(0, 192, 163));
        cancelBook.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cancelBook.setForeground(new java.awt.Color(0, 76, 84));
        cancelBook.setText("CANCEL");
        cancelBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBookActionPerformed(evt);
            }
        });

        inDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                inDatePropertyChange(evt);
            }
        });

        outDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                outDatePropertyChange(evt);
            }
        });

        customerBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        loadCustomersIntoComboBox();

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 76, 84));
        jLabel12.setText("Room Type:");

        roomTypeText.setEditable(false);

        TotalBookingCost.setEditable(false);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 76, 84));
        jLabel16.setText("Total Price:");

        bookingList.setBackground(new java.awt.Color(153, 255, 204));
        bookingList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Name", "Check In", "Check Out", "Room", "Status", "Total Cost"
            }
        ));
        jScrollPane4.setViewportView(bookingList);
        fetchAndDisplayBookings();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inDate, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                            .addComponent(outDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(customerBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(roomChoice, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(confirmBook)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelBook))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TotalBookingCost, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                            .addComponent(roomTypeText)
                            .addComponent(bookingCost))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(customerBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(inDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(outDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(roomChoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(roomTypeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(bookingCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(TotalBookingCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmBook)
                    .addComponent(cancelBook))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        Calendar calendar5 = Calendar.getInstance();
        calendar5.setTime(new Date());
        Date today = calendar5.getTime();
        inDate.setDate(today);
        calendar5.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar5.getTime();
        outDate.setDate(tomorrow);

        jTabbedPane1.addTab("Bookings", jPanel4);

        jPanel6.setBackground(new java.awt.Color(173, 223, 179));

        jPanel5.setLayout(null);

        jPanel5.setLayout(new BorderLayout());
        jPanel5.add(calendar, BorderLayout.CENTER);
        calendar.beginInit();
        calendar.setCurrentView(CalendarView.SingleMonth);
        calendar.endInit();
        calendar.setPreferredSize(new Dimension(jPanel5.getWidth(), jPanel5.getHeight()));
        calendar.setMinimumSize(new Dimension(0, 0));
        calendar.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        setupDatabaseConnection();

        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Bookings");

        jScrollPane5.setViewportView(jTree1);
        loadRoomTree();

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(270, 270, 270)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Reports", jPanel6);

        jPanel3.setBackground(new java.awt.Color(173, 223, 179));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 76, 84));
        jLabel11.setText("Add New Room");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 76, 84));
        jLabel13.setText("Room Type:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 76, 84));
        jLabel14.setText("Price:");

        roomType.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        roomType.setForeground(new java.awt.Color(0, 76, 84));
        roomType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Single", "Double" }));
        roomType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomTypeActionPerformed(evt);
            }
        });

        addRoom.setBackground(new java.awt.Color(0, 192, 163));
        addRoom.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        addRoom.setForeground(new java.awt.Color(0, 76, 84));
        addRoom.setText("ADD");
        addRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRoomActionPerformed(evt);
            }
        });

        roomList.setBackground(new java.awt.Color(0, 192, 163));
        roomList.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        roomList.setForeground(new java.awt.Color(0, 76, 84));
        roomList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Room Type", "Price", "Availability"
            }
        ));
        roomList.setShowGrid(true);
        fetchAndDisplayCustomers();
        jScrollPane3.setViewportView(roomList);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(390, 390, 390)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addRoom)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(priceTxt)
                                    .addComponent(roomType, 0, 129, Short.MAX_VALUE))))
                        .addGap(59, 59, 59)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel11)
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(roomType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(priceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addRoom))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        jTabbedPane1.addTab("Hotel Rooms", jPanel3);

        jPanel2.setBackground(new java.awt.Color(173, 223, 179));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 76, 84));
        jLabel2.setText("Name:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 76, 84));
        jLabel4.setText("Contact Number:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 76, 84));
        jLabel6.setText("Email:");

        nameCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameCustomerActionPerformed(evt);
            }
        });

        contactCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactCustomerActionPerformed(evt);
            }
        });

        addCostumer.setBackground(new java.awt.Color(0, 192, 163));
        addCostumer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        addCostumer.setForeground(new java.awt.Color(0, 76, 84));
        addCostumer.setText("ADD");
        addCostumer.setBorder(null);
        addCostumer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCostumerActionPerformed(evt);
            }
        });

        deleteCostumer.setBackground(new java.awt.Color(0, 192, 163));
        deleteCostumer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        deleteCostumer.setForeground(new java.awt.Color(0, 76, 84));
        deleteCostumer.setText("DELETE");
        deleteCostumer.setBorder(null);
        deleteCostumer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCostumerActionPerformed(evt);
            }
        });

        updateCostumer.setBackground(new java.awt.Color(0, 192, 163));
        updateCostumer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        updateCostumer.setForeground(new java.awt.Color(0, 76, 84));
        updateCostumer.setText("UPDATE");
        updateCostumer.setBorder(null);
        updateCostumer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateCostumerActionPerformed(evt);
            }
        });

        listCustomer.setBackground(new java.awt.Color(0, 192, 163));
        listCustomer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        listCustomer.setForeground(new java.awt.Color(0, 76, 84));
        listCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Name", "Email", "Contact Number"
            }
        ));
        listCustomer.setShowGrid(true);
        jScrollPane1.setViewportView(listCustomer);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 76, 84));
        jLabel8.setText("Password:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(emailCustomer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nameCustomer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(contactCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                                    .addComponent(passwordField)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(addCostumer, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(deleteCostumer, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(updateCostumer, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(nameCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(emailCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(contactCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addCostumer, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(deleteCostumer, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(updateCostumer, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Customer", jPanel2);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 97, -1, 447));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 192, 163));
        jLabel1.setText("HOTEL MANAGEMENT DASHBOARD");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 21, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 550));
        fetchAndDisplayCustomers();
        fetchAndDisplayRooms();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void updateCostumerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateCostumerActionPerformed
        int selectedRow = listCustomer.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String name = nameCustomer.getText().trim();
        String email = emailCustomer.getText().trim();
        String contact = contactCustomer.getText().trim();

        if (name.isEmpty() || email.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "UPDATE customer SET name = ?, email = ?, phone_number = ? WHERE email = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, contact);
            pst.setString(4, email);

            int rowsUpdated = pst.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Customer updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                fetchAndDisplayCustomers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update customer.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_updateCostumerActionPerformed

    private void deleteCostumerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCostumerActionPerformed
       int selectedRow = listCustomer.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a customer to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Fetch email from the selected row (assuming email is in column index 1)
    String email = listCustomer.getValueAt(selectedRow, 1).toString();

    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this customer?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    String query = "DELETE FROM customer WHERE email = ?";

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
         PreparedStatement pst = conn.prepareStatement(query)) {

        pst.setString(1, email);

        int rowsDeleted = pst.executeUpdate();

        if (rowsDeleted > 0) {
            JOptionPane.showMessageDialog(this, "Customer deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            fetchAndDisplayCustomers(); // Refresh the table
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete customer.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_deleteCostumerActionPerformed

    private void addCostumerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCostumerActionPerformed
        String name = nameCustomer.getText().trim();
        String email = emailCustomer.getText().trim();
        String contact = contactCustomer.getText().trim();
        String pass = passwordField.getText().trim();

        // Validate input
        if (name.isEmpty() || email.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "INSERT INTO customer (name, email, phone_number, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, contact);
            pst.setString(4, pass);

            int rowsInserted = pst.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Customer added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                fetchAndDisplayCustomers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add customer.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addCostumerActionPerformed

    private void contactCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contactCustomerActionPerformed

    private void nameCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameCustomerActionPerformed

    private void cancelBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBookActionPerformed
        int selectedRow = bookingList.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String customerName = bookingList.getValueAt(selectedRow, 0).toString();
        Date checkInDate = (Date) bookingList.getValueAt(selectedRow, 1);
        Date checkOutDate = (Date) bookingList.getValueAt(selectedRow, 2);

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this booking?", 
                                                    "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String getBookingQuery = "SELECT booking_id, status FROM booking b " +
                                 "JOIN customer c ON b.customer_id = c.customer_id " +
                                 "WHERE c.name = ? AND b.check_in_date = ? AND b.check_out_date = ?";

        String updateQuery = "UPDATE booking SET status = 'Cancelled' WHERE booking_id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
             PreparedStatement pstGetBooking = conn.prepareStatement(getBookingQuery);
             PreparedStatement pstUpdate = conn.prepareStatement(updateQuery)) {

            pstGetBooking.setString(1, customerName);
            pstGetBooking.setDate(2, new java.sql.Date(checkInDate.getTime()));
            pstGetBooking.setDate(3, new java.sql.Date(checkOutDate.getTime()));

            ResultSet rs = pstGetBooking.executeQuery();
            if (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                String status = rs.getString("status");

                if ("Cancelled".equalsIgnoreCase(status)||"Finished".equalsIgnoreCase(status)) {
                    JOptionPane.showMessageDialog(this, "This booking cannot be canceled anymore.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                pstUpdate.setInt(1, bookingId);
                int rowsUpdated = pstUpdate.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Booking status updated to 'Canceled'.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    fetchAndDisplayBookings();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update booking status.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Booking not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateRoomAvailability();
        loadRoomsIntoComboBox();
    }//GEN-LAST:event_cancelBookActionPerformed

    private void confirmBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmBookActionPerformed
       String customerName = customerBox.getSelectedItem().toString().trim();
Date checkInDate = inDate.getDate();
Date checkOutDate = outDate.getDate();
String roomType = roomTypeText.getText().trim();
String bookingCost1 = bookingCost.getText().trim();
String totalCost = TotalBookingCost.getText().trim();
String roomId = roomChoice.getSelectedItem().toString().trim();

// Validate input
if (customerName.isEmpty() || checkInDate == null || checkOutDate == null || roomType.isEmpty() || bookingCost1.isEmpty() || totalCost.isEmpty() || roomId.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}

try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "")) {
    // Retrieve customer_id based on customer name
    String getCustomerIdQuery = "SELECT customer_id FROM customer WHERE name = ?";
    PreparedStatement pstCustomer = conn.prepareStatement(getCustomerIdQuery);
    pstCustomer.setString(1, customerName);
    ResultSet rsCustomer = pstCustomer.executeQuery();
    
    int customerId = -1;
    if (rsCustomer.next()) {
        customerId = rsCustomer.getInt("customer_id");
    } else {
        JOptionPane.showMessageDialog(this, "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Insert booking details
    String insertBookingQuery = "INSERT INTO booking (customer_id, room_id, check_in_date, check_out_date, total_cost, status) VALUES (?, ?, ?, ?, ?, 'Confirmed')";
    PreparedStatement pstBooking = conn.prepareStatement(insertBookingQuery);
    pstBooking.setInt(1, customerId);
    pstBooking.setString(2, roomId);
    pstBooking.setDate(3, new java.sql.Date(checkInDate.getTime()));
    pstBooking.setDate(4, new java.sql.Date(checkOutDate.getTime()));
    pstBooking.setString(5, totalCost);
    
    int rowsInserted = pstBooking.executeUpdate();
    
    if (rowsInserted > 0) {
        JOptionPane.showMessageDialog(this, "Booking confirmed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        fetchAndDisplayBookings();
        
        customerBox.setSelectedIndex(0);
            inDate.setDate(null);
            outDate.setDate(null);
            roomChoice.setSelectedIndex(0);
            roomTypeText.setText("");
            bookingCost.setText("");
            TotalBookingCost.setText("");
    } else {
        JOptionPane.showMessageDialog(this, "Failed to confirm booking.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    
} catch (SQLException e) {
    JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
updateRoomAvailability();
    }//GEN-LAST:event_confirmBookActionPerformed
    private void fetchAndDisplayBookings() {
        DefaultTableModel model = (DefaultTableModel) bookingList.getModel();
        model.setRowCount(0);

        String query = "SELECT c.name, b.check_in_date, b.check_out_date, r.room_type, r.room_id, b.status, b.total_cost " +
                       "FROM booking b " +
                       "JOIN customer c ON b.customer_id = c.customer_id " +
                       "JOIN room r ON b.room_id = r.room_id";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                Date checkInDate = rs.getDate("check_in_date");
                Date checkOutDate = rs.getDate("check_out_date");
                String room = "Rm No. " + rs.getString("room_id") + " - " + rs.getString("room_type");
                String status = rs.getString("status");
                double totalCost = rs.getDouble("total_cost");

                model.addRow(new Object[]{name, checkInDate, checkOutDate, room, status, totalCost});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRoomActionPerformed
        String selected = (String) roomType.getSelectedItem();
        String priceText = priceTxt.getText().trim();

    // Validate input
    if (selected.isEmpty() || priceText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    double price;
    try {
        price = Double.parseDouble(priceText);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String query = "INSERT INTO room (room_type, price, availability_status) VALUES (?, ?, 'Available')";
    
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
         PreparedStatement pst = conn.prepareStatement(query)) {
        
        pst.setString(1, selected);
        pst.setDouble(2, price);
        
        int rowsInserted = pst.executeUpdate();

        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(this, "Room added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            fetchAndDisplayRooms();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add room.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_addRoomActionPerformed
    private void loadRoomTree() {
        String url = "jdbc:mysql://localhost:3306/hms";
        String user = "root";
        String pass = "";

        String query = "SELECT room_id, room_type FROM room ORDER BY room_type, room_id";

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Rooms");

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            Map<String, DefaultMutableTreeNode> roomTypeNodes = new HashMap<>();

            while (rs.next()) {
                String roomType = rs.getString("room_type");
                int roomId = rs.getInt("room_id");
                String displayText = "Room no." + roomId;

                DefaultMutableTreeNode typeNode = roomTypeNodes.get(roomType);
                if (typeNode == null) {
                    typeNode = new DefaultMutableTreeNode(roomType);
                    roomTypeNodes.put(roomType, typeNode);
                    root.add(typeNode);
                }

                DefaultMutableTreeNode roomNode = new DefaultMutableTreeNode(new RoomNode(roomId, displayText));
                typeNode.add(roomNode);
            }

            jTree1.setModel(new DefaultTreeModel(root));

            jTree1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TreePath selectedPath = jTree1.getPathForLocation(e.getX(), e.getY());
                    if (selectedPath != null) {
                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                        if (selectedNode.isLeaf() && selectedNode.getUserObject() instanceof RoomNode) {
                            RoomNode roomNode = (RoomNode) selectedNode.getUserObject();
                            int roomId = roomNode.getRoomId();

                            System.out.println("Selected Room ID: " + roomId); // Debugging line

                            jLabel15.setText("Room No." + roomId);

                            setupDatabaseConnection();
                            calendar.getSchedule().getItems().clear(); 
                            getBookingsFromDatabase(roomId);
                        }
                    }
                }
            }
        });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching room list: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    class RoomNode {
        private int roomId;
        private String displayText;

        public RoomNode(int roomId, String displayText) {
            this.roomId = roomId;
            this.displayText = displayText;
        }

        public int getRoomId() {
            return roomId;
        }

        @Override
        public String toString() {
            return displayText;
        }
    }
    private void roomTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roomTypeActionPerformed

    private void roomChoiceItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_roomChoiceItemStateChanged
        String selectedItem = (String) roomChoice.getSelectedItem(); 
        if (selectedItem != null) {
            try {
                int rmID = Integer.parseInt(selectedItem);
                loadRoomInfo(rmID); 
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid room number selected.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        updateRoomAvailability();
    }//GEN-LAST:event_roomChoiceItemStateChanged

    private void outDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_outDatePropertyChange
        Date checkIn = inDate.getDate();Date checkOut = outDate.getDate();
        if (checkIn != null && checkOut != null) {
            long diff = checkOut.getTime() - checkIn.getTime();
            int days = (int) (diff / (1000 * 60 * 60 * 24));
            if (days < 1) {JOptionPane.showMessageDialog(this, "Check-out must be after check-in!", "Error", JOptionPane.ERROR_MESSAGE);return;}
            System.out.println(Double.parseDouble(bookingCost.getText()));
                System.out.println(days);
            try {
                double costPerDay = Double.parseDouble(bookingCost.getText());
                double totalCost = days * costPerDay;
                TotalBookingCost.setText(String.format("%.2f", totalCost));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid cost value!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_outDatePropertyChange

    private void inDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_inDatePropertyChange
        if ("date".equals(evt.getPropertyName())) {
        Date selectedDate = inDate.getDate();

        // Get today's date without time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfToday = calendar.getTime();

        if (selectedDate != null && selectedDate.before(startOfToday)) {
            JOptionPane.showMessageDialog(this, "You cannot select a date earlier than today!", "Invalid Date", JOptionPane.WARNING_MESSAGE);
            inDate.setDate(startOfToday);
        }
    }
    }//GEN-LAST:event_inDatePropertyChange

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        updateRoomAvailability();
        System.out.println("Stonks");
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new adminDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField TotalBookingCost;
    private javax.swing.JButton addCostumer;
    private javax.swing.JButton addRoom;
    private javax.swing.JTextField bookingCost;
    private javax.swing.JTable bookingList;
    private javax.swing.JButton cancelBook;
    private javax.swing.JButton confirmBook;
    private javax.swing.JTextField contactCustomer;
    private javax.swing.JComboBox<String> customerBox;
    private javax.swing.JButton deleteCostumer;
    private javax.swing.JTextField emailCustomer;
    private com.toedter.calendar.JDateChooser inDate;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTree jTree1;
    private javax.swing.JTable listCustomer;
    private javax.swing.JTextField nameCustomer;
    private com.toedter.calendar.JDateChooser outDate;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField priceTxt;
    private javax.swing.JComboBox<String> roomChoice;
    private javax.swing.JTable roomList;
    private javax.swing.JComboBox<String> roomType;
    private javax.swing.JTextField roomTypeText;
    private javax.swing.JButton updateCostumer;
    // End of variables declaration//GEN-END:variables
    private com.mindfusion.scheduling.Calendar calendar;
    private Connection connection;
    private void loadCustomersIntoComboBox() {
        String url = "jdbc:mysql://localhost:3306/hms";
        String user = "root";  
        String pass = "";  

        String query = "SELECT name FROM customer";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            customerBox.removeAllItems(); 

            while (rs.next()) {
                String customerName = rs.getString("name");
                customerBox.addItem(customerName); 
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading customers: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void loadRoomsIntoComboBox() {
        String url = "jdbc:mysql://localhost:3306/hms"; 
        String user = "root";  
        String pass = "";  
        int roomID;

        String query = "SELECT room_id, room_type, price FROM room WHERE availability_status = 'Available';";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            roomChoice.removeAllItems(); 
            
            boolean firstRun = true; 

            while (rs.next()) {
                int roomid = rs.getInt("room_id");
                String roomNumber = String.valueOf(roomid); 
                roomChoice.addItem(roomNumber);

                if (firstRun) { 
                    loadRoomInfo(roomid); 
                    firstRun = false; 
                }
            }


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading Rooms: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void loadRoomInfo(int id) {
    String url = "jdbc:mysql://localhost:3306/hms"; 
    String user = "root";  
    String pass = "";  

    String query = "SELECT room_type, price FROM room WHERE room_id = ?;";

    try (Connection conn = DriverManager.getConnection(url, user, pass);
         PreparedStatement pst = conn.prepareStatement(query)) {
        pst.setInt(1, id);
        try (ResultSet rs = pst.executeQuery()) {
            if(rs.next()) {
                String roomType = rs.getString("room_type"); 
                double price = rs.getDouble("price"); 
                roomTypeText.setText(roomType);
                bookingCost.setText(String.valueOf(price)); 
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error loading Rooms: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}
    private void updateRoomAvailability() {
        String url = "jdbc:mysql://localhost:3306/hms";
        String user = "root";
        String pass = "";

        String query = "SELECT b.booking_id, b.check_in_date, b.check_out_date, b.status, r.room_id, r.availability_status " +
                       "FROM booking b " +
                       "JOIN room r ON r.room_id = b.room_id";

        String updateRoomQuery = "UPDATE room SET availability_status = ? WHERE room_id = ?";
        String updateBookingQuery = "UPDATE booking SET status = 'Finished' WHERE booking_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstSelect = conn.prepareStatement(query);
             PreparedStatement pstUpdateRoom = conn.prepareStatement(updateRoomQuery);
             PreparedStatement pstUpdateBooking = conn.prepareStatement(updateBookingQuery);
             ResultSet rs = pstSelect.executeQuery()) {

            Date today = new Date();

            while (rs.next()) {
                int roomId = rs.getInt("room_id");
                int bookingId = rs.getInt("booking_id");
                Date checkInDate = rs.getDate("check_in_date");
                Date checkOutDate = rs.getDate("check_out_date");
                String bookingStatus = rs.getString("status");
                String newRoomStatus = null;

                if ((today.equals(checkInDate) || today.after(checkInDate)) && today.before(checkOutDate)) {
                    newRoomStatus = "Booked";
                } else if (today.after(checkOutDate)) {
                    newRoomStatus = "Available";

                    // Mark booking as "Finished" if check-out is past and it's not already finished
                    if (!"Finished".equals(bookingStatus)) {
                        pstUpdateBooking.setInt(1, bookingId);
                        pstUpdateBooking.executeUpdate();
                    }
                }

                if (newRoomStatus != null) {
                    pstUpdateRoom.setString(1, newRoomStatus);
                    pstUpdateRoom.setInt(2, roomId);
                    pstUpdateRoom.executeUpdate();
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating status: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
