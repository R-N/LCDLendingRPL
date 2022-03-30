/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lcdrpl;

import java.awt.CardLayout;
import java.awt.Dimension;

/**
 *
 * @author Lenovo2
 */
public class MainFrame extends javax.swing.JFrame {

    CardLayout mainPanelCardLayout;
    /**
     * Creates new form TestForm
     */
    public MainFrame() {
        initComponents();
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                initComponents2();
            }
        });
    }
    
    public void initComponents2(){
        int headerHeight = Math.max(
                leftTabPanel.getHeaderHeight(), 
                Math.max(
                        statusBarPanel.getHeight(), 
                        statusBarPanel.getPreferredSize().height
                )
        );
        System.out.println("Header height: " + headerHeight);
        this.statusBarPanel.setPreferredSize(new Dimension(this.statusBarPanel.getPreferredSize().width, headerHeight));
        this.statusBarPanel.setSize(this.statusBarPanel.getPreferredSize());
        leftTabPanel.setHeaderHeight(headerHeight);
        
        mainPanelHolderGridBagConstraints.insets.left += leftTabPanel.getWidth();
        mainLayer.remove(mainPanelHolder);
        mainLayer.add(mainPanelHolder, mainPanelHolderGridBagConstraints);
        
    }
    
    
    public void onMenuClick(int index){
        switch(index){
            case 0:{
                mainPanelCardLayout.show(mainPanel, "home");
                break;
            }
            case 1:{
                mainPanelCardLayout.show(mainPanel, "borrow");
                break;
            }
            case 2:{
                mainPanelCardLayout.show(mainPanel, "history");
                break;
            }
            case 3:{
                mainPanelCardLayout.show(mainPanel, "item");
                break;
            }
            case 4:{
                mainPanelCardLayout.show(mainPanel, "blacklist");
                break;
            }
            case 5:{
                close();
                break;
            }
        }
    }
    
    public void close(){
        dispose();
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

        jLayeredPane1 = new javax.swing.JLayeredPane();
        mainLayer = new javax.swing.JPanel();
        mainPanelHolder = new lcdrpl.gui.ImagePanel();
        statusBarPanel = new lcdrpl.gui.ImagePanel();
        jPanel1 = new javax.swing.JPanel();
        dateLabel = new javax.swing.JTextField();
        timeLabel = new javax.swing.JTextField();
        mainPanel = new lcdrpl.gui.ImagePanel();
        homeTab = new lcdrpl.gui.tabs.home.HomeTab();
        borrowTab = new lcdrpl.gui.tabs.lending.BorrowPanel();
        historyTab = new lcdrpl.gui.tabs.lending.LendingHistoryTab();
        itemTab = new lcdrpl.gui.tabs.item.ItemTab();
        blacklistTab = new lcdrpl.gui.tabs.blacklist.BlacklistTab();
        tabLayer = new javax.swing.JPanel();
        leftTabPanel = new lcdrpl.gui.lefttab.LeftTabPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1024, 600));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jLayeredPane1.setLayout(new javax.swing.OverlayLayout(jLayeredPane1));

        mainLayer.setLayout(new java.awt.GridBagLayout());

        mainPanelHolder.setLayout(new java.awt.BorderLayout());

        statusBarPanel.setBackground(new java.awt.Color(0, 0, 0));
        statusBarPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        dateLabel.setEditable(false);
        dateLabel.setBackground(new java.awt.Color(255, 255, 255));
        dateLabel.setForeground(new java.awt.Color(0, 0, 0));
        dateLabel.setText("Day, DD/MM/YYYY");
        dateLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));
        jPanel1.add(dateLabel, new java.awt.GridBagConstraints());

        timeLabel.setEditable(false);
        timeLabel.setBackground(new java.awt.Color(255, 255, 255));
        timeLabel.setForeground(new java.awt.Color(0, 0, 0));
        timeLabel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        timeLabel.setText("hh:mm WIB");
        timeLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanel1.add(timeLabel, gridBagConstraints);

        statusBarPanel.add(jPanel1);

        mainPanelHolder.add(statusBarPanel, java.awt.BorderLayout.PAGE_START);
        statusBarPanel.setImage("bg-header-w-shape.jpg");

        mainPanel.setOpaque(false);
        mainPanel.setLayout(new java.awt.CardLayout());
        mainPanel.add(homeTab, "home");
        mainPanel.add(borrowTab, "borrow");
        mainPanel.add(historyTab, "history");
        mainPanel.add(itemTab, "item");
        mainPanel.add(blacklistTab, "blacklist");

        mainPanelHolder.add(mainPanel, java.awt.BorderLayout.CENTER);
        mainPanelCardLayout = (CardLayout)mainPanel.getLayout();

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        mainLayer.add(mainPanelHolder, gridBagConstraints);
        mainPanelHolderGridBagConstraints = gridBagConstraints;

        jLayeredPane1.add(mainLayer);

        tabLayer.setBackground(null);
        tabLayer.setForeground(new java.awt.Color(0, 0, 0));
        tabLayer.setFocusable(false);
        tabLayer.setOpaque(false);
        tabLayer.setLayout(new java.awt.BorderLayout());
        tabLayer.add(leftTabPanel, java.awt.BorderLayout.LINE_START);
        leftTabPanel.setParent(this);

        jLayeredPane1.setLayer(tabLayer, javax.swing.JLayeredPane.POPUP_LAYER);
        jLayeredPane1.add(tabLayer);

        getContentPane().add(jLayeredPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private lcdrpl.gui.tabs.blacklist.BlacklistTab blacklistTab;
    private lcdrpl.gui.tabs.lending.BorrowPanel borrowTab;
    private javax.swing.JTextField dateLabel;
    private lcdrpl.gui.tabs.lending.LendingHistoryTab historyTab;
    private lcdrpl.gui.tabs.home.HomeTab homeTab;
    private lcdrpl.gui.tabs.item.ItemTab itemTab;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private lcdrpl.gui.lefttab.LeftTabPanel leftTabPanel;
    private javax.swing.JPanel mainLayer;
    private lcdrpl.gui.ImagePanel mainPanel;
    private lcdrpl.gui.ImagePanel mainPanelHolder;
    private java.awt.GridBagConstraints mainPanelHolderGridBagConstraints;
    private lcdrpl.gui.ImagePanel statusBarPanel;
    private javax.swing.JPanel tabLayer;
    private javax.swing.JTextField timeLabel;
    // End of variables declaration//GEN-END:variables
}