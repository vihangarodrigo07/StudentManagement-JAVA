/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package studentmanagementsystem;


import java.io.InputStream;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author USER
 */
public class Enrollment extends javax.swing.JFrame {

    Connection con;
    PreparedStatement pst;
    DefaultTableModel model;
    /**
     * Creates new form Enrollment
     */
    public Enrollment() {
        initComponents();
        connect();
        loadStudentCourses();
        fetchEnrollments();
    }
    
    private void connect() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/school","root","");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed");
        }
    }

    private void loadStudentCourses() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT studentID FROM student");
            cmbStudent.removeAllItems();
            while (rs.next()) {
                cmbStudent.addItem(rs.getString("studentID"));
            }

            rs = stmt.executeQuery("SELECT courseID FROM course");
            cmbCourse.removeAllItems();
            while (rs.next()) {
                cmbCourse.addItem(rs.getString("courseID"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to load Student and Course data");
        }
        
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // or your desired format
        txtDate.setText(formatter.format(date));
    }
    
    private void fetchEnrollments() {
        try {
            pst = con.prepareStatement("SELECT * FROM enrollment");
            ResultSet rs = pst.executeQuery();
            model = (DefaultTableModel) enrollmentTable.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("StudentID"), rs.getString("CourseID"), rs.getString("EnrollmentDate")});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to retrieve data from Enrollment table");
        }
    }
    
        private void addEnrollment() {
        String studentID = (String) cmbStudent.getSelectedItem();
        String courseID = (String) cmbCourse.getSelectedItem();
        String date = txtDate.getText();

        try {
            pst = con.prepareStatement("INSERT INTO enrollment (StudentID, CourseID, EnrollmentDate) VALUES (?, ?, ?)");
            pst.setString(1, studentID);
            pst.setString(2, courseID);
            pst.setString(3, date);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Enrollment Added");
            fetchEnrollments();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to add enrollment");
        }
    }

    private void updateEnrollment() {
        String studentID = (String) cmbStudent.getSelectedItem();
        String courseID = (String) cmbCourse.getSelectedItem();
        String date = txtDate.getText();

        try {
            pst = con.prepareStatement("UPDATE enrollment SET EnrollmentDate=? WHERE StudentID=? AND CourseID=?");
            pst.setString(1, date);
            pst.setString(2, studentID);
            pst.setString(3, courseID);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Enrollment Updated");
            fetchEnrollments();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to update enrollment");
        }
    }

    private void deleteEnrollment() {
        String studentID = (String) cmbStudent.getSelectedItem();
        String courseID = (String) cmbCourse.getSelectedItem();

        try {
            pst = con.prepareStatement("DELETE FROM enrollment WHERE StudentID=? AND CourseID=?");
            pst.setString(1, studentID);
            pst.setString(2, courseID);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Enrollment Deleted");
            fetchEnrollments();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to delete enrollment");
        }
    }
    

    private void generateReport(){
    try {
        String reportFile = "C:\\Users\\USER\\JaspersoftWorkspace\\MyReports\\enrollment.jasper";
        JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile);
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "");
        
        // Fill the report (using an empty data source here; replace if using a real data source)
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);

        // Display the report in a viewer
        JasperViewer.viewReport(jasperPrint, false);
        
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Database connection error: " + ex.getMessage());
        ex.printStackTrace();
    }
        catch (JRException ex) {
        JOptionPane.showMessageDialog(this, "Error generating report: " + ex.getMessage());
        ex.printStackTrace();
    }
}
    
//    private void generateReport(){
//    try {
//        // Load the compiled Jasper report file (.jasper) using InputStream
//
//        InputStream reportStream = getClass().getResourceAsStream("/studentmanagementsystem/enrollment.jasper");
//        
//        if (reportStream == null) {
//            throw new JRException("Report file not found at the specified path.");
//        }
//
//        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
//
//        // Database connection
//        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "");
//
//        // Fill the report with data from the database
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
//
//        // Display the report in a viewer
//        JasperViewer.viewReport(jasperPrint, false);
//
//    } catch (SQLException ex) {
//        JOptionPane.showMessageDialog(this, "Database connection error: " + ex.getMessage());
//        ex.printStackTrace();
//    } catch (JRException ex) {
//        JOptionPane.showMessageDialog(this, "Error generating report: " + ex.getMessage());
//        ex.printStackTrace();
//    }
//}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        btnupdate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        enrollmentTable = new javax.swing.JTable();
        btnback = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btndelete = new javax.swing.JButton();
        btnrefresh = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnadd = new javax.swing.JButton();
        cmbStudent = new javax.swing.JComboBox<>();
        cmbCourse = new javax.swing.JComboBox<>();
        txtDate = new javax.swing.JTextField();
        btnGenerateRep = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("Date (y-m-d)");

        btnupdate.setText("Update");
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        enrollmentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Course ID", "Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(enrollmentTable);

        btnback.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagementsystem/back-button (1).png"))); // NOI18N
        btnback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbackActionPerformed(evt);
            }
        });

        jLabel4.setText("Course ID");

        btndelete.setText("Delete");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        btnrefresh.setText("Refresh");
        btnrefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrefreshActionPerformed(evt);
            }
        });

        jLabel1.setText("Student ID");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("ENROLLMENT");

        btnadd.setText("Add");
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });

        cmbStudent.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbCourse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDateActionPerformed(evt);
            }
        });

        btnGenerateRep.setText("Generate Report");
        btnGenerateRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateRepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnback, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cmbCourse, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnadd)
                        .addGap(30, 30, 30)
                        .addComponent(btnupdate)
                        .addGap(30, 30, 30)
                        .addComponent(btndelete)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnrefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGenerateRep)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnrefresh)
                    .addComponent(jLabel6)
                    .addComponent(btnGenerateRep))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cmbStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cmbCourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(83, 83, 83)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnadd)
                            .addComponent(btnupdate)
                            .addComponent(btndelete)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnback)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        // TODO add your handling code here:
        updateEnrollment();
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btnbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbackActionPerformed
        // TODO add your handling code here:
        Dashboard dbForm = new Dashboard();  // replace StudentForm with your actual class name for the student form
        dbForm.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnbackActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
        deleteEnrollment();
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefreshActionPerformed
        // TODO add your handling code here:
        loadStudentCourses();
    }//GEN-LAST:event_btnrefreshActionPerformed

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed
        // TODO add your handling code here:
        addEnrollment();
    }//GEN-LAST:event_btnaddActionPerformed

    private void btnGenerateRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateRepActionPerformed
        // TODO add your handling code here:
        generateReport();
    }//GEN-LAST:event_btnGenerateRepActionPerformed

    private void txtDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDateActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtDateActionPerformed

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
            java.util.logging.Logger.getLogger(Enrollment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Enrollment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Enrollment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Enrollment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Enrollment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerateRep;
    private javax.swing.JButton btnadd;
    private javax.swing.JButton btnback;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnrefresh;
    private javax.swing.JButton btnupdate;
    private javax.swing.JComboBox<String> cmbCourse;
    private javax.swing.JComboBox<String> cmbStudent;
    private javax.swing.JTable enrollmentTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtDate;
    // End of variables declaration//GEN-END:variables
}
