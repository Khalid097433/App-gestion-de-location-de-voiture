/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.print.PrinterException;
import java.io.File;
import java.sql.*;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;


/**
 *
 * @author hp
 */
public class Generer_facture extends javax.swing.JFrame {

    
    static String Date1;
   static String Date2;
 
static String numero_client;
static String Matricule;
    static int nombre_jours;
        Date Datefact = new Date();
        Timestamp dateheure = new Timestamp(Datefact.getTime());
    Connection conx = null;
    PreparedStatement prepared = null;
    ResultSet resultat = null;
      String fact = "";
      int montant = 0;
        

    /**
     * Creates new form Generer_facture
     */
    public Generer_facture(String date1, String date2, String num_cli, String Mat, int nb_jours) {
        
        Date1 = date1;
        Date2 = date2;
numero_client = num_cli;
Matricule = Mat;
        nombre_jours = nb_jours;
        this.setTitle("Génerer une facture");
        
        
        
        initComponents();
        conx = ConnexionMySQL.ConnectDb();
        insertion_facture();
        area();
    }
    public String RenvoieEmail(){
        
       String sql = "Select Email from client where n°client like '"+numero_client+"' ", mail = "";
       
        try {
            prepared = conx.prepareStatement(sql);
                   resultat = prepared.executeQuery();

                   
                   while(resultat.next()){
                       
                       mail = resultat.getString("Email");
                   }
        } catch (SQLException ex) {
            Logger.getLogger(Generer_facture.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
       return mail;
        
       
        
    }
    
    
    public void insertion_facture(){
        
          int prix = 0;
         

String sql = "Select prix_par_jour from voiture_enregistrees where n°matricule like '"+Matricule+"' ";

        try {///Dtermine le prix par jour de la voiture choisis par le client
            prepared = conx.prepareStatement(sql);
            resultat = prepared.executeQuery();
            
            while(resultat.next()){
                             prix = resultat.getInt("prix_par_jour");

                
            }
            
        } catch (SQLException ex) {
System.out.println(ex);        }

sql = "Insert into facture (n°client, n°matricule, n°contrat, montant) values (?, ?, ?, ?)";

montant = prix * nombre_jours + 50000 ;////+++Caution;

        try {
            prepared = conx.prepareStatement(sql);
            
            prepared.setString(1, numero_client);
            prepared.setString(2, Matricule);
            prepared.setInt(3, 0); // Pour l'instant le n°contrat sera nul car on ne sais pas le numero du contrat que le client a validé
            prepared.setInt(4, montant);
           
            
            prepared.executeUpdate();
            
            
        } catch (SQLException ex) {
System.out.println(ex);          }
        
        sql = "Select n°facture from facture where n°contrat = 0";
      
        try {////Determine la facture dont le n°contrat est nul
            prepared = conx.prepareStatement(sql);
                    resultat = prepared.executeQuery();

                    
                    while (resultat.next()){
                        
                        fact = resultat.getString("n°facture");
                    }
                    
                    
        } catch (SQLException ex) {
System.out.println(ex);          }

        sql = "Update facture set n°contrat = '"+fact+"' where n°facture = '"+fact+"' ";
        
        try {////Mise à jour de n°contrat, n°contrat sera egale au n°facture car il n'ya pas de facture sans contrat et inversement
            prepared = conx.prepareStatement(sql);
                    prepared.executeUpdate();

        } catch (SQLException ex) {
System.out.println(ex);          }

        
        
    }
    
    public void area(){
        
        area.setEditable(false);
       String sql = "Select nom_prenom from client where n°client like '"+numero_client+"' ";
       String nom_cli = "";
        String modele = "";
        try {
            prepared = conx.prepareStatement(sql);
                   resultat = prepared.executeQuery();

                   
                   while(resultat.next()){
                       
                       nom_cli = resultat.getString("nom_prenom");
                   }
                   
        } catch (SQLException ex) {
System.out.println(ex);          }
        
        sql = "select marque, model from voiture_enregistrees where n°matricule like '"+Matricule+"' ";
       
        
        
        try {
            
            prepared = conx.prepareStatement(sql);
                    resultat = prepared.executeQuery();

                   
                    
                    while(resultat.next()){
                        
                         facture.setText(fact);
                         num_cli.setText(numero_client);
                         nom.setText(nom_cli);
                         mat.setText(Matricule);
                         marque.setText(resultat.getString("marque"));
                         modele = resultat.getString("model");
                        
                    }
        } catch (SQLException ex) {
System.out.println(ex);          }
        
        
       
       
        
        
        
        
         area.setText("------------------------------");
            area.setText(area.getText() + "         Facture          ");
            area.setText(area.getText() + "--------------------------\n");
                    Date obj = new Date();
                    
        
String date = obj.toString();

area.setText(area.getText() + "\n" + date + "\n\n");
area.setText(area.getText() + "Numéro facture : "+fact + "\n");
area.setText(area.getText() + "Numéro client : "+numero_client + "\n");
area.setText(area.getText() + "Nom client : "+nom_cli + "\n");
area.setText(area.getText() + "Matricule de la voiture : "+Matricule + "\n");
area.setText(area.getText() + "Marque de la voiture : "+marque.getText() + "\n");
area.setText(area.getText() + "Modèle de la voiture : "+ modele + "\n");
area.setText(area.getText() + "Date début du contrat : "+Date1+"\n");
area.setText(area.getText() + "Date fin du contrat : "+Date2+"\n");
area.setText(area.getText() + "Montant total : "+montant+"\n");
area.setText(area.getText() + "\nMerci  \n\n");


       /* try {
            
            
            area.print();
        } catch (PrinterException ex) {
            Logger.getLogger(Generer_facture.class.getName()).log(Level.SEVERE, null, ex);
        }

*/




    }
    /*
    public String Recherche_mail(String cli){
        
       
        
        String sql = "Select Email from client where n°client like '"+cli+"' ";
        String mail = "";
        
        try {
            prepared = conx.prepareStatement(sql);
                    resultat  = prepared.executeQuery();

                    while(resultat.next()){
                        
                        mail = resultat.getString("Email");
                    }
                    
                    
                    
                    
        } catch (SQLException ex) {
System.out.println(ex);        }
        
        return mail;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        area = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        nom = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        mat = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        num_cli = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        marque = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        facture = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 153));
        jPanel1.setToolTipText("");

        area.setColumns(20);
        area.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        area.setRows(5);
        jScrollPane1.setViewportView(area);

        jButton1.setBackground(new java.awt.Color(204, 102, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(240, 240, 240));
        jButton1.setText("Déconnexion");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Nom du client");

        nom.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel2.setText("N°matricule");

        mat.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel7.setText("N°client");

        num_cli.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel8.setText("Marque");

        marque.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel3.setText("N°facture");

        facture.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("Imprimer");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setText("Enregistrer en PDF");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nom, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addComponent(mat, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(num_cli)
                    .addComponent(jLabel8)
                    .addComponent(marque)
                    .addComponent(jLabel3)
                    .addComponent(facture))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 174, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(1475, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(94, 94, 94)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(facture, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(num_cli, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nom, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mat, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(marque, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 30, Short.MAX_VALUE)))
                .addGap(56, 56, 56)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
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
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
Login Connexion = new Login();
this.hide();
Connexion.setVisible(true);// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
 String senderEmail = "rentforyou599@gmail.com"; // Remplacez par votre adresse e-mail
 String password = "fdhw tyjz xehl aabx"; // Remplacez par votre mot de passe MAHPAPA77186866
 String recipientEmail =  RenvoieEmail();

     // Configuration des propriétés
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Serveur SMTP pour Gmail
        props.put("mail.smtp.port", "587"); // Port SMTP pour Gmail

     Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmail, password);
                    }
                });
//        props.put("mail.smtp.connectiontimeout", "5000"); // 5000 milliseconds
//        props.put("mail.smtp.timeout", "5000"); // 5000 milliseconds


String contenu = area.getText();


//
//
//  try {
//            // Création de l'objet Message
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(senderEmail));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
//            message.setSubject("Test d'envoi d'e-mail via JavaMail");
//            message.setText(filename);
//            
//
//            Transport.send(message);
//
//            System.out.println("L'e-mail a été envoyé avec succès.");
//
//        } catch (MessagingException e) {
//            System.out.println("Une erreur s'est produite lors de l'envoi de l'e-mail : " + e.getMessage());
//        }
//
// // Adresse e-mail du destinataire issah6965@gmail.com    
 
 
 
 

    // ... (code pour envoyer l'e-mail)

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
 try {
         area.print();        // TODO add your handling code here:
        } catch (PrinterException ex) {
            Logger.getLogger(Generer_facture.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(Generer_facture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Generer_facture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Generer_facture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Generer_facture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Generer_facture(Date1, Date2, numero_client, Matricule, nombre_jours).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea area;
    private javax.swing.JTextField facture;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField marque;
    private javax.swing.JTextField mat;
    private javax.swing.JTextField nom;
    private javax.swing.JTextField num_cli;
    // End of variables declaration//GEN-END:variables
}
