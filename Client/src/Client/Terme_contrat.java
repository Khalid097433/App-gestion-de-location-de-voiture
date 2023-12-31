/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import javax.swing.*;
/**
 *
 * @author hp
 */
public class Terme_contrat extends JFrame {

    
    Connection conx = null;
    PreparedStatement prepared = null;
    ResultSet resultat = null;
    static String Email_Login;
    static String Matricule;
    static String num_cli = "";
    static JFrame R;
    /**
     * Creates new form contrat
     */
    public Terme_contrat(String Mail, String Mat, JFrame r) {
        
        
        R = r;
        Email_Login = Mail;
        Matricule = Mat;
        initComponents();
       
        conx = ConnexionMySQL.ConnectDb();
      
        this.setTitle("Termes du contrat");
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        date2 = new com.toedter.calendar.JDateChooser();
        date1 = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Date début");

        jLabel2.setText("Date fin");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Retour");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("Valider");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(84, 273, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(date2, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                    .addComponent(date1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(date2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        
        
         ///Fermeture de la fentre reservation 
        String sql;

        SimpleDateFormat dateformat  = new SimpleDateFormat("yyyy-MM-dd");
        
        String Date1 = dateformat.format(date1.getDate());
        String Date2 = dateformat.format(date2.getDate());
        int nb_jours = 0;
        
              sql = "Select DATEDIFF('"+Date2+"' , '"+Date1+"') ";
        
        try {//// Determine le nombre du jour du contrat
            prepared = conx.prepareStatement(sql);
                    resultat = prepared.executeQuery();

                    while(resultat.next()){
                        
                        nb_jours = resultat.getInt("DATEDIFF('"+Date2+"' , '"+Date1+"')");
                    }
                    
                    
                    if(nb_jours < 0){  ///Cas ou il ya une erreur au niveau des dates
                        
                        JOptionPane.showMessageDialog(null, "Validation non accordées");
                    }
                    
                    else if(nb_jours == 0){ ////Si le client a reserve que pour quelques heures, ca sera compte pour une journee
                        
                        nb_jours = 1;
                                          sql = "Select n°client from client where Email like '"+Email_Login+"' ";
        
        try { ///Determine l'id du client
            prepared = conx.prepareStatement(sql);
                    resultat = prepared.executeQuery();

                    
                    
                    
                    while(resultat.next()){
                        
                        num_cli = resultat.getString("n°client");
                    }
                    
                    
        } catch (SQLException ex) {
            Logger.getLogger(Terme_contrat.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
  
        
        
        
        
        sql = "Insert into contrat (n°client, N°matricule, Date_debut, Date_fin, Nombre_de_jours) values ( ?, ?, ?, ?, ?)";
       
        try { ///Insertion d'un nouveau
            
            prepared = conx.prepareStatement(sql);
            
            prepared.setString(1, num_cli);
            prepared.setString(2, Matricule);
            prepared.setString(3, Date1);
            prepared.setString(4, Date2);
            prepared.setInt(5, nb_jours);
            
            prepared.executeUpdate();
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Terme_contrat.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
        
        
            sql = "insert into voiture_non_disponible (n°matricule, model, marque, couleur, prix_par_jour) select n°matricule, model, marque, couleur, prix_par_jour from voiture_enregistrees where n°matricule like '"+Matricule+"' ";
            
            prepared = conx.prepareStatement(sql);
            prepared.executeUpdate();
            
            sql = "delete from voiture_disponible where n°matricule like '"+Matricule+"' ";
            
            prepared = conx.prepareStatement(sql);
            prepared.executeUpdate();
            
             prepared = conx.prepareStatement("Update voiture_enregistrees set Disponibilite = 'Non' where n°matricule like '"+Matricule+"' ");
             prepared.executeUpdate();
            
           
        
        
        
        
        Generer_facture gf = new Generer_facture(Date1, Date2, num_cli, Matricule, nb_jours);
        this.hide();
        R.hide();
        gf.setVisible(true);
                        
                    }
                    else {
                              sql = "Select n°client from client where Email like '"+Email_Login+"' ";
        
        try { ///Determine l'id du client
            prepared = conx.prepareStatement(sql);
                    resultat = prepared.executeQuery();

                    
                    
                    
                    while(resultat.next()){
                        
                        num_cli = resultat.getString("n°client");
                    }
                    
                    
        } catch (SQLException ex) {
            Logger.getLogger(Terme_contrat.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
  
        
        
        
        
        sql = "Insert into contrat (n°client, N°matricule, Date_debut, Date_fin, Nombre_de_jours) values ( ?, ?, ?, ?, ?)";
       
        try { ///Insertion d'un nouveau
            
            prepared = conx.prepareStatement(sql);
            
            prepared.setString(1, num_cli);
            prepared.setString(2, Matricule);
            prepared.setString(3, Date1);
            prepared.setString(4, Date2);
            prepared.setInt(5, nb_jours);
            
            prepared.executeUpdate();
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Terme_contrat.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
        
        
            sql = "insert into voiture_non_disponible (n°matricule, model, marque, couleur, prix_par_jour) select n°matricule, model, marque, couleur, prix_par_jour from voiture_enregistrees where n°matricule like '"+Matricule+"' ";
            
            prepared = conx.prepareStatement(sql);
            prepared.executeUpdate();
            
            sql = "delete from voiture_disponible where n°matricule like '"+Matricule+"' ";
            
            prepared = conx.prepareStatement(sql);
            prepared.executeUpdate();
            
             prepared = conx.prepareStatement("Update voiture_enregistrees set Disponibilite = 'Non' where n°matricule like '"+Matricule+"' ");
             prepared.executeUpdate();
            
           
        
        
        
        
        Generer_facture gf = new Generer_facture(Date1, Date2, num_cli, Matricule, nb_jours);
        this.hide();
        R.hide();
        gf.setVisible(true);
                    }
        } catch (SQLException ex) {
            Logger.getLogger(Terme_contrat.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
 
// TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
this.hide();

// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Terme_contrat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Terme_contrat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Terme_contrat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Terme_contrat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Terme_contrat(Email_Login, Matricule, R).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser date1;
    private com.toedter.calendar.JDateChooser date2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
