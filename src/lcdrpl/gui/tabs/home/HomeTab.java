/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lcdrpl.gui.tabs.home;

import lcdrpl.database.Database;
import lcdrpl.database.PreparedStatement;
import lcdrpl.database.ResultSet;
import lcdrpl.system.ItemSubsystem;
import lcdrpl.system.structs.OverallStatusLCD;

/**
 *
 * @author Lenovo2
 */
public class HomeTab extends lcdrpl.gui.ImagePanel {

    /**
     * Creates new form HomePanel
     */
    public HomeTab() {
        initComponents();
        this.setImage("bg-main-panel-w-shape.jpg");
        refresh();
    }
    
    public void refresh(){
        OverallStatusLCD status = ItemSubsystem.getOverallStatusLCD();
        availableLabel.setText(String.valueOf(status.availableCount));
        lateLabel.setText(String.valueOf(status.lateCount));
        unavailableLabel.setText(String.valueOf(status.unavailableCount));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        availableLabel = new javax.swing.JLabel();
        lateLabel = new javax.swing.JLabel();
        unavailableLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 0, 100));
        setLayout(new java.awt.GridBagLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(246, 234, 205));
        jLabel1.setText("SELAMAT DATANG DI APLIKASI");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(246, 234, 205));
        jLabel2.setText("PEMINJAMAN LCD PROYEKTOR");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(246, 234, 205));
        jLabel3.setText("FAKULTAS SAINS DAN TEKNOLOGI");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel1.add(jLabel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.ipady = 60;
        add(jPanel1, gridBagConstraints);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(246, 234, 205));
        jLabel4.setText("Proyektor tersedia : ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel2.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(246, 234, 205));
        jLabel5.setText("Proyektor terlambat kembali : ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel2.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(246, 234, 205));
        jLabel6.setText("Proyektor tidak dapat dipinjam : ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel2.add(jLabel6, gridBagConstraints);

        availableLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        availableLabel.setForeground(new java.awt.Color(246, 234, 205));
        availableLabel.setText("99");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        jPanel2.add(availableLabel, gridBagConstraints);

        lateLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lateLabel.setForeground(new java.awt.Color(246, 234, 205));
        lateLabel.setText("99");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        jPanel2.add(lateLabel, gridBagConstraints);

        unavailableLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        unavailableLabel.setForeground(new java.awt.Color(246, 234, 205));
        unavailableLabel.setText("99");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        jPanel2.add(unavailableLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.ipady = 30;
        add(jPanel2, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel availableLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lateLabel;
    private javax.swing.JLabel unavailableLabel;
    // End of variables declaration//GEN-END:variables
}
