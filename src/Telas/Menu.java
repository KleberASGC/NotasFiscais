/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Telas;

import Acoes.LeituraXML;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Kleber
 */
public class Menu extends javax.swing.JFrame {

    /**
     * Creates new form Menu
     */
    public Menu() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlNotasFiscais = new javax.swing.JPanel();
        lblSelecione = new javax.swing.JLabel();
        btnProcurar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlNotasFiscais.setBorder(javax.swing.BorderFactory.createTitledBorder("Notas Fiscais"));
        pnlNotasFiscais.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        lblSelecione.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblSelecione.setText("Selecione os arquivos .xml dos quais as informações serão retiradas:");

        btnProcurar.setText("Procurar");
        btnProcurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcurarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlNotasFiscaisLayout = new javax.swing.GroupLayout(pnlNotasFiscais);
        pnlNotasFiscais.setLayout(pnlNotasFiscaisLayout);
        pnlNotasFiscaisLayout.setHorizontalGroup(
            pnlNotasFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNotasFiscaisLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSelecione)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNotasFiscaisLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnProcurar)
                .addContainerGap())
        );
        pnlNotasFiscaisLayout.setVerticalGroup(
            pnlNotasFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNotasFiscaisLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSelecione, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnProcurar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblSelecione.getAccessibleContext().setAccessibleName("Selecione os arquivos .xml dos quais as informações serão retiradas:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlNotasFiscais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlNotasFiscais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProcurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcurarActionPerformed
        JFileChooser escolheArquivos = new JFileChooser();
        escolheArquivos.setFileSelectionMode(JFileChooser.FILES_ONLY);
        escolheArquivos.setMultiSelectionEnabled(true);
        String dir = "";
        File[] arquivos;
        if (escolheArquivos.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            dir = escolheArquivos.getSelectedFile().getAbsolutePath();
            arquivos = escolheArquivos.getSelectedFiles();
            for (int i = 0; i < arquivos.length; i++) 
                LeituraXML.lerXML(arquivos[i]);
            
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Os arquivos selecionados não são compatíveis com essa aplicação.");
        }
        JOptionPane.showMessageDialog(new JFrame(), "Fim da leitura e inserção dos arquivos no banco de dados.");
    }//GEN-LAST:event_btnProcurarActionPerformed

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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProcurar;
    private javax.swing.JLabel lblSelecione;
    private javax.swing.JPanel pnlNotasFiscais;
    // End of variables declaration//GEN-END:variables
}
