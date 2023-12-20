/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Date;

import java.awt.print.PrinterException;
/**
 *
 * @author hp
 */
public class Factures extends javax.swing.JFrame {

/**
     * Creates new form Factures
     */
    
    Connection conx = null;
    PreparedStatement prepared = null;
    ResultSet resultat = null;
    
    PreparedStatement prepared1 = null;
    ResultSet resultat1 = null;
    
    String Marque = "", Modele = "";
    
    public Factures() {
        initComponents();
        conx = ConnexionMySQL.ConnectDb();
        area.setEditable(false);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Search.setEnabled(false);
        Search.setForeground(new Color(0, 0, 0));
        Search.setBackground(new Color(240, 240, 240));
        
        con.setEditable(false);
        cli.setEditable(false);
        mat.setEditable(false);
        nom.setEditable(false);
        nb_jours.setEditable(false);
        prix_par_jour.setEditable(false);
        montant.setEditable(false);
        
        table();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight(); // Set any desired height
        this.setSize(width, height);

        
        
        
        
    }
    
    
    public String Search_nom_cli(String fact){ ///Détermine le nom de client associe à ce facture
        
        String sql = "Select nom_prenom from client where n°client IN (Select n°client from facture where n°facture like '"+fact+"')";
        String nom_cli = "";
        
    try {
        prepared1 = conx.prepareStatement(sql);
        resultat1 = prepared1.executeQuery();
        
        while(resultat1.next()){
            
            nom_cli = resultat1.getString("nom_prenom");
        }
        

    } catch (SQLException ex) {
        Logger.getLogger(Factures.class.getName()).log(Level.SEVERE, null, ex);
    }
      
    
    
    return nom_cli;
    }
    
    
    public String Search_prix(String fact){  ///Détermine le prix par jour de la voiture associe à ce facture
        
        String sql = "Select marque, model, prix_par_jour from voiture_enregistrees where n°matricule IN (Select n°matricule from facture where n°facture like '"+fact+"')  ";
        String prix = "";
       
        
    try {
                prepared1 = conx.prepareStatement(sql);
                resultat1 = prepared1.executeQuery();
                
                while(resultat1.next()){
                    
                    prix = resultat1.getString("prix_par_jour");
                    Marque = resultat1.getString("marque");
                    Modele = resultat1.getString("model");
                }
                
                
                
           

    } catch (SQLException ex) {
        Logger.getLogger(Factures.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return prix;
    }
    
    public String Search_nb_jour(String fact){  ///Détermine le nombre de jour du contrat associe à ce facture
        
        String sql = "Select Nombre_de_jours from contrat where N°contrat IN (Select n°contrat from facture where n°facture like '"+fact+"') ";
        String nb_jour = "";
        
        try {
                    prepared1 = conx.prepareStatement(sql);
                    resultat1 = prepared1.executeQuery();
                    
                    
                    while(resultat1.next()){
                        
                        nb_jour = resultat1.getString("Nombre_de_jours");
                    }
                    

        } catch (SQLException ex) {
            Logger.getLogger(Factures.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nb_jour;
    }
    
    public void table(){
        
       
        
    
    String sql = "Select * from facture";
        int c;
        
        
        
        try {
                    prepared = conx.prepareStatement(sql);
                    resultat = prepared.executeQuery();
                    
                    ResultSetMetaData rd = resultat.getMetaData();
                    c = rd.getColumnCount();
                    DefaultTableModel df = (DefaultTableModel)Table.getModel();
                    df.setRowCount(0);
                    
                    while(resultat.next()){
                        
                        Vector v2 = new Vector();
                        
                        for(int i = 0; i < c; i++){
        
                            String facture = resultat.getString("n°facture");
                            
                            v2.add(facture);
                            v2.add(resultat.getString("n°client"));
                            v2.add(Search_nom_cli(facture));
                            v2.add(resultat.getString("n°contrat"));
                            v2.add(resultat.getString("n°matricule"));
                            v2.add(Search_nb_jour(facture));
                            v2.add(Search_prix(facture));
                            v2.add(resultat.getFloat("montant"));
                            
                        }
                        
                        df.addRow(v2);
                    }
                    
           
                    

        } catch (SQLException ex) {
            Logger.getLogger(Factures.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void clear(){
        
        nom.setText("");       
        fact.setText("");
        cli.setText("");
        mat.setText("");
        con.setText("");
        nb_jours.setText("");
        prix_par_jour.setText("");
        montant.setText("");
        Table.clearSelection();
        area.setText("");
        
    }
    
    public void validate_Search(){
        
        String facture = fact.getText();
        
        if(!facture.equals("")){
            
            Search.setEnabled(true);
            Search.setForeground(new Color(240, 240, 240));
            Search.setBackground(new Color(204, 102, 0));
            
        }
        
        else {
            
            Search.setEnabled(false);
            Search.setForeground(new Color(0, 0, 0));
            Search.setBackground(new Color(240, 240, 240));
            clear();
        }
            
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
        fact = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cli = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        con = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        mat = new javax.swing.JTextField();
        nb_jours = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        prix_par_jour = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        montant = new javax.swing.JTextField();
        Search = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        nom = new javax.swing.JTextField();
        Print = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        area = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Factures");
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 153));

        jLabel1.setText("N°facture");

        fact.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        fact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                factKeyReleased(evt);
            }
        });

        jLabel2.setText("N°client");

        cli.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel3.setText("N°contrat");

        con.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel4.setText("N°matricule");

        mat.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        mat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matActionPerformed(evt);
            }
        });

        nb_jours.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel5.setText("Nombre de jours");

        jLabel6.setText("Prix par jour");

        prix_par_jour.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel7.setText("Montant");

        montant.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        Search.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Search.setText("Rechercher");
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("Annuler");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setText("Retour");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        Table.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N°facture", "N°client", "Nom du client", "n°contrat", "N°matricule", "Nombre de jours", "Prix par jour", "Montant"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table);
        if (Table.getColumnModel().getColumnCount() > 0) {
            Table.getColumnModel().getColumn(0).setResizable(false);
            Table.getColumnModel().getColumn(1).setResizable(false);
            Table.getColumnModel().getColumn(2).setResizable(false);
            Table.getColumnModel().getColumn(3).setResizable(false);
            Table.getColumnModel().getColumn(4).setResizable(false);
            Table.getColumnModel().getColumn(5).setResizable(false);
            Table.getColumnModel().getColumn(6).setResizable(false);
            Table.getColumnModel().getColumn(7).setResizable(false);
        }

        jButton4.setBackground(new java.awt.Color(204, 102, 0));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(240, 240, 240));
        jButton4.setText("Déconnexion");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setText("Actualiser");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel8.setText("Nom du client");

        nom.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        Print.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Print.setText("Imprimer");
        Print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintActionPerformed(evt);
            }
        });

        area.setColumns(20);
        area.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        area.setRows(5);
        area.setText("\n");
        jScrollPane2.setViewportView(area);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1607, 1607, 1607)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jButton4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(nom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                                    .addComponent(cli, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fact, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(con, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(52, 52, 52)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(Search, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Print, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jLabel5)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(nb_jours, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                                .addComponent(prix_par_jour, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(mat, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(montant, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1175, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))))
                .addContainerGap(979, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fact, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addComponent(jLabel2)
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cli, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nom, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(con, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Print, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mat, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nb_jours, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(prix_par_jour, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(montant, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 93, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void matActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_matActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
Acceuil a = new Acceuil();
this.hide();
a.setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
clear();

// TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void factKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_factKeyReleased
validate_Search();        // TODO add your handling code here:
    }//GEN-LAST:event_factKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
Login Connexion = new Login();
this.hide();
Connexion.setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
DefaultTableModel d1 = (DefaultTableModel)Table.getModel();

int selectIndex = Table.getSelectedRow();

fact.setText(d1.getValueAt(selectIndex, 0).toString());
cli.setText(d1.getValueAt(selectIndex, 1).toString());
nom.setText(d1.getValueAt(selectIndex, 2).toString());
con.setText(d1.getValueAt(selectIndex, 3).toString());
mat.setText(d1.getValueAt(selectIndex, 4).toString());
nb_jours.setText(d1.getValueAt(selectIndex, 5).toString());
prix_par_jour.setText(d1.getValueAt(selectIndex, 6).toString());
montant.setText(d1.getValueAt(selectIndex, 7).toString());

validate_Search();

 area.setText("****************************");
            area.setText(area.getText() + "         Facture          ");
            area.setText(area.getText() + "****************************\n");
        
//String date = obj.toString();
Date date = new Date();

area.setText(area.getText() + "\n" + date + "\n\n");
area.setText(area.getText() + "N°facture : " + fact.getText() + "\n");
area.setText(area.getText() + "N°client : " + cli.getText()+ "\n");
area.setText(area.getText() + "Nom du client : " + nom.getText() + "\n");
area.setText(area.getText() + "N°contrat : " + con.getText()+ "\n");
area.setText(area.getText() + "N°matricule : " + mat.getText()+ "\n");
area.setText(area.getText() + "Marque de la voiture : " + Marque + "\n");
area.setText(area.getText() + "Modèle de la voiture : " + Modele + "\n");
area.setText(area.getText() + "Nombre de jours : " + nb_jours.getText()+ "\n");
area.setText(area.getText() + "Prix par jour : " + prix_par_jour.getText()+ "\n");
area.setText(area.getText() + "Montant total : " + montant.getText()+ "\n\n");

area.setText(area.getText() + "Adresse : Quartier 5 Boulevard de Gaulle Rue Farahad\n");
area.setText(area.getText() + "E-mail : rentforyou@gmail.com\n");
area.setText(area.getText() + "Téléphone : +25377585576\n");
area.setText(area.getText() + "Merci");



// TODO add your handling code here:
    }//GEN-LAST:event_TableMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

clear();
table();
// TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed

String facture = fact.getText();
String sql = "Select * from facture where n°facture like '"+facture+"' ";

    try {
        prepared = conx.prepareStatement(sql);
        resultat = prepared.executeQuery();

        int i = 0;
        
        while(resultat.next()){
            
            cli.setText(resultat.getString("n°client"));
            nom.setText(Search_nom_cli(facture));
            con.setText(resultat.getString("n°contrat"));
            mat.setText(resultat.getString("n°matricule"));
            nb_jours.setText(Search_nb_jour(facture));
            prix_par_jour.setText(Search_prix(facture));
            montant.setText(resultat.getString("montant"));
            
            area.setText("****************************");
            area.setText(area.getText() + "         Facture          ");
            area.setText(area.getText() + "****************************\n");
                    Date obj = new Date();
        
String date = obj.toString();
   
area.setText(area.getText() + "\n" + date + "\n\n");
area.setText(area.getText() + "N°facture : " + fact.getText() + "\n");
area.setText(area.getText() + "N°client : " + cli.getText()+ "\n");
area.setText(area.getText() + "Nom du client : " + nom.getText() + "\n");
area.setText(area.getText() + "N°contrat : " + con.getText()+ "\n");
area.setText(area.getText() + "N°matricule : " + mat.getText()+ "\n");
area.setText(area.getText() + "Nombre de jours : " + nb_jours.getText()+ "\n");
area.setText(area.getText() + "Prix par jour : " + prix_par_jour.getText()+ "\n");
area.setText(area.getText() + "Montant total : " + montant.getText()+ "\n\n");

area.setText(area.getText() + "Adresse : Quartier 5 Boulevard de Gaulle Rue Farahad\n");
area.setText(area.getText() + "E-mail : rentforyou@gmail.com\n");
area.setText(area.getText() + "Téléphone : +25377585576\n");
area.setText(area.getText() + "Merci");
















        i = 1;
            
        }
        
        if(i == 0){
            
            JOptionPane.showMessageDialog(null, "Facture inexistante");
        }
        
    
        
    } catch (SQLException ex) {
        Logger.getLogger(Factures.class.getName()).log(Level.SEVERE, null, ex);
    }






// TODO add your handling code here:
    }//GEN-LAST:event_SearchActionPerformed

    
    private void PrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintActionPerformed

        try {
            area.print();
        } catch (PrinterException ex) {
            Logger.getLogger(Factures.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
        
    }//GEN-LAST:event_PrintActionPerformed

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
            java.util.logging.Logger.getLogger(Factures.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Factures.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Factures.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Factures.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Factures().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Print;
    private javax.swing.JButton Search;
    private javax.swing.JTable Table;
    private javax.swing.JTextArea area;
    private javax.swing.JTextField cli;
    private javax.swing.JTextField con;
    private javax.swing.JTextField fact;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField mat;
    private javax.swing.JTextField montant;
    private javax.swing.JTextField nb_jours;
    private javax.swing.JTextField nom;
    private javax.swing.JTextField prix_par_jour;
    // End of variables declaration//GEN-END:variables

    
    }






