/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author hp
 */
public class Gestion_voiture extends javax.swing.JFrame {
    
Connection conx = null;
PreparedStatement prepared = null;
ResultSet resultat = null;
static String mat_initiale = "";
static int Recherche = 0;
static File imageFile = null;
static String matricule;
//0 si recherche par matricule, sinon 1


    /**
     * Creates new form Gestion_voiture
     */
    public Gestion_voiture() {
        
        
        conx = ConnexionMySQL.ConnectDb();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        initComponents();
        table_vehicule();
        Add.setEnabled(false);
        Add.setForeground(new Color(0, 0, 0));
        Search.setEnabled(false);
        Search.setForeground(new Color(0, 0, 0));
        Drop.setEnabled(false);
        Drop.setForeground(new Color(0, 0, 0));
        update.setEnabled(false);
        update.setForeground(new Color(0, 0, 0));
        Changer.setEnabled(false);
    }

    
    
    public void validateFields(){
        
String matricule = Matricule.getText(); 
String modele = Modele.getText();
String marque = Marque.getText();
String couleur = Couleur.getText();
String prix = Prix.getText();

if((!matricule.equals("")) && (!modele.equals("")) && (!marque.equals("")) && (!couleur.equals("")) && (!prix.equals(""))){
    
    Add.setEnabled(true);
    Add.setForeground(new Color(240, 240, 240));
    Add.setBackground(new Color(204, 102, 0));
    Changer.setEnabled(true);
  
}

else {
       Add.setEnabled(false);
       Add.setForeground(new Color(0, 0, 0));
       Add.setBackground(new Color(240, 240, 240));
       Changer.setEnabled(false);
    
       
       
}
        
    }
    
    public void validateUpdate(){
        
               
String marque = Marque.getText(); 
String matricule = Matricule.getText();
String modele = Modele.getText();
String couleur = Couleur.getText();
String prix = Prix.getText();

   if((!marque.equals("")) && (matricule.equals(""))){
    Recherche = 1; 
       if((!modele.equals("")) || (!couleur.equals("")) || (!prix.equals(""))){
           
            update.setEnabled(true);
    update.setForeground(new Color(240, 240, 240));
    update.setBackground(new Color(204, 102, 0));
       }
       
       else{
           update.setEnabled(false);
       update.setBackground(new Color(240, 240, 240));
       update.setForeground(new Color(0, 0, 0)); 
           
       }
   
   
}
   else if((marque.equals("")) && (!matricule.equals(""))) {
       Recherche = 0;
       
         if((!modele.equals("")) || (!couleur.equals("")) || (!prix.equals(""))){
           
            update.setEnabled(true);
    update.setForeground(new Color(240, 240, 240));
    update.setBackground(new Color(204, 102, 0));
       }
       
       else{
           update.setEnabled(false);
       update.setBackground(new Color(240, 240, 240));
       update.setForeground(new Color(0, 0, 0)); 
           
       }
   }
   
   
   else if ((!matricule.equals("")) && (!modele.equals("")) && (!marque.equals("")) && (!couleur.equals("")) && (!prix.equals(""))){

       Recherche = 0;
         update.setEnabled(true);
    update.setForeground(new Color(240, 240, 240));
    update.setBackground(new Color(204, 102, 0));
       
   }
   else{
       
         update.setEnabled(false);
       update.setBackground(new Color(240, 240, 240));
       update.setForeground(new Color(0, 0, 0)); 
       
   }
  
    }
    
    
    
    public void validateSearch(){
        
String marque = Marque.getText(); 
String matricule = Matricule.getText();

   if((!marque.equals("")) || (!matricule.equals(""))){
    
    Search.setEnabled(true);
    Search.setForeground(new Color(240, 240, 240));
    Search.setBackground(new Color(204, 102, 0));
   
}
   else {
       
       Search.setEnabled(false);
       Search.setBackground(new Color(240, 240, 240));
       Search.setForeground(new Color(0, 0, 0));
       clear();
   }
  
    }
    
    public void validateDrop(){
        
        String marque = Marque.getText();
        String matricule = Matricule.getText();
        
        if(!marque.equals("") || (!matricule.equals(""))){
            
            Drop.setEnabled(true);
            Drop.setForeground(new Color(240, 240, 240));
            Drop.setBackground(new Color(204, 102, 0));
        }
        
        else {
                       Drop.setEnabled(false);
                       Drop.setForeground(new Color(0, 0, 0));
                       Drop.setBackground(new Color(240, 240, 240));
                       clear();
        }
    }
    
 
    
   public void clear(){
       
Matricule.setText(""); 
Modele.setText("");
Marque.setText("");
Couleur.setText("");
Prix.setText("");
Dispo.setSelectedItem("Oui");
    table_vehicule(); 
    Marque.setEditable(true);
Matricule.setEditable(true); 
 
       
labelimg.setIcon(null);

Table.clearSelection();
   } 
   
   
   public void table_vehicule(){
    
    String sql = "Select * from voiture_enregistrees";
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
                
                for(int i = 1; i <= c; i++){
                    
                    v2.add(resultat.getString("n°matricule"));
                    v2.add(resultat.getString("marque"));
                    v2.add(resultat.getString("model"));
                    v2.add(resultat.getString("couleur"));
                    v2.add(resultat.getFloat("prix_par_jour"));
                    v2.add(resultat.getString("Disponibilite"));
                   
                }
                
                df.addRow(v2);
                
            }
 
    } catch (SQLException ex) {
        Logger.getLogger(Gestion_voiture.class.getName()).log(Level.SEVERE, null, ex);
    }
           
}
   
   public Icon resizeIcon(ImageIcon icon, int width, int height){
       
       Image img = icon.getImage();
       Image resizeImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
       return new ImageIcon(resizeImg);
       
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Matricule = new javax.swing.JTextField();
        Modele = new javax.swing.JTextField();
        Marque = new javax.swing.JTextField();
        Couleur = new javax.swing.JTextField();
        Prix = new javax.swing.JTextField();
        Drop = new javax.swing.JButton();
        update = new javax.swing.JButton();
        Annuler = new javax.swing.JButton();
        Add = new javax.swing.JButton();
        Search = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        Dispo = new javax.swing.JComboBox<>();
        Changer = new javax.swing.JButton();
        labelimg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestion des voitures");
        setBackground(new java.awt.Color(255, 204, 102));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setBackground(new java.awt.Color(255, 255, 153));

        jLabel1.setText("N°Matricule");

        jLabel2.setText("Modèle");

        jLabel3.setText("Marque");

        jLabel4.setText("Couleur");

        jLabel5.setText("Prix par jour");

        Matricule.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Matricule.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                MatriculeKeyReleased(evt);
            }
        });

        Modele.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Modele.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModeleActionPerformed(evt);
            }
        });
        Modele.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ModeleKeyReleased(evt);
            }
        });

        Marque.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Marque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                MarqueKeyReleased(evt);
            }
        });

        Couleur.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Couleur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                CouleurKeyReleased(evt);
            }
        });

        Prix.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Prix.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                PrixKeyReleased(evt);
            }
        });

        Drop.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Drop.setText("Supprimer");
        Drop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DropActionPerformed(evt);
            }
        });

        update.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        update.setText("Modifier");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        Annuler.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Annuler.setText("Annuler");
        Annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnnulerActionPerformed(evt);
            }
        });

        Add.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Add.setForeground(new java.awt.Color(240, 240, 240));
        Add.setText("Ajouter");
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });

        Search.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Search.setText("Rechercher");
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchActionPerformed(evt);
            }
        });
        Search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SearchKeyPressed(evt);
            }
        });

        Table.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N° Matricule ", "Marque", "Modèle", "Couleur", "Prix par jour", "Disponibilité"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table.setRowHeight(25);
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Table);
        if (Table.getColumnModel().getColumnCount() > 0) {
            Table.getColumnModel().getColumn(0).setResizable(false);
            Table.getColumnModel().getColumn(0).setPreferredWidth(25);
            Table.getColumnModel().getColumn(1).setResizable(false);
            Table.getColumnModel().getColumn(1).setPreferredWidth(25);
            Table.getColumnModel().getColumn(2).setResizable(false);
            Table.getColumnModel().getColumn(2).setPreferredWidth(25);
            Table.getColumnModel().getColumn(3).setResizable(false);
            Table.getColumnModel().getColumn(3).setPreferredWidth(25);
            Table.getColumnModel().getColumn(4).setResizable(false);
            Table.getColumnModel().getColumn(4).setPreferredWidth(25);
            Table.getColumnModel().getColumn(5).setResizable(false);
            Table.getColumnModel().getColumn(5).setPreferredWidth(25);
        }

        jButton1.setBackground(new java.awt.Color(204, 102, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(240, 240, 240));
        jButton1.setText("Déconnexion");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("Retour");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setText("Actualiser");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel6.setText("Disponibilité");

        Dispo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Dispo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Oui", "Non" }));

        Changer.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Changer.setText("Changer l'image");
        Changer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(526, 526, 526))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel3)
                                        .addComponent(Dispo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(Prix)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(2, 2, 2)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(Modele)
                                                .addComponent(Couleur, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                                                .addComponent(jLabel1)
                                                .addComponent(jLabel4)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(Matricule)))
                                        .addComponent(Marque))
                                    .addComponent(Changer, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(66, 66, 66)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Annuler, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Add, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Drop, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1062, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelimg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1051, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel3)
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Marque, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Matricule, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Annuler, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Modele, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Couleur, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Add, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Prix, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Drop, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Dispo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addComponent(Changer, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelimg, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(500, Short.MAX_VALUE))
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
                .addGap(0, 88, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ModeleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModeleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ModeleActionPerformed

    
    
  
    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed

        
        
matricule = Matricule.getText(); 
String modele = Modele.getText();
String marque = Marque.getText();
String couleur = Couleur.getText();
String prix = Prix.getText();
String dispo = Dispo.getSelectedItem().toString();


   if(imageFile != null){
       
       
String sql = "Select * from voiture_enregistrees where n°matricule like '"+matricule+"' ";
int i = 0;
    try {
        prepared = conx.prepareStatement(sql);
        resultat = prepared.executeQuery();

        while(resultat.next()){
            i = 1;
        }
        
           }
     catch (SQLException ex) {
System.out.println(ex);

    }
        if(i == 0) {
            
              /* try {
                ajouterImageDansBaseDeDonnees(imageFile);
            } catch (IOException ex) {
                Logger.getLogger(admin.InterfaceAjoutEtAffichageImage.class.getName()).log(Level.SEVERE, null, ex);
            }   
               */ 
            sql = "INSERT INTO voiture_enregistrees (n°matricule, model, marque, couleur, prix_par_jour, Disponibilite, Image) VALUES (?,?,?,?,?,?,?) ";
           


    try {

        prepared = conx.prepareStatement(sql);
                        prepared.setString(1, matricule);
                        prepared.setString(2, modele);
                        prepared.setString(3, marque);
                        prepared.setString(4, couleur);
                        prepared.setString(5, prix);
                        prepared.setString(6, dispo);
                        FileInputStream fis = new FileInputStream(imageFile);
                        prepared.setBinaryStream(7, fis, (int) imageFile.length());


                prepared.executeUpdate();
                
                  } catch (SQLException ex) {
System.out.println(ex);

    } catch (FileNotFoundException ex) {
        Logger.getLogger(Gestion_voiture.class.getName()).log(Level.SEVERE, null, ex);
    }
     
                
                JOptionPane.showMessageDialog(null, "Voiture ajoutée avec succès");
                table_vehicule();
                 clear();
                       Drop.setEnabled(false);
                       Drop.setForeground(new Color(0, 0, 0));
                       Drop.setBackground(new Color(240, 240, 240));
                       
                        Search.setEnabled(false);
                        Search.setBackground(new Color(240, 240, 240));
                        Search.setForeground(new Color(0, 0, 0));
                
                        update.setEnabled(false);
                        update.setBackground(new Color(240, 240, 240));
                        update.setForeground(new Color(0, 0, 0));
                        
                        Add.setEnabled(false);
                        Add.setBackground(new Color(240, 240, 240));
                        Add.setForeground(new Color(0, 0, 0));
                
                            if(dispo.equals("Oui")){

           sql = "INSERT INTO voiture_disponible (n°matricule, model, marque, couleur, prix_par_jour, Image) VALUES (?,?,?,?,?,?)";

            try {
        prepared = conx.prepareStatement(sql);
                     prepared.setString(1, matricule);
                        prepared.setString(2, modele);
                        prepared.setString(3, marque);
                        prepared.setString(4, couleur);
                        prepared.setString(5, prix);
                        
                        FileInputStream fis = new FileInputStream(imageFile);
                        prepared.setBinaryStream(6, fis, (int) imageFile.length());
                        
                prepared.executeUpdate();
                
                  } catch (SQLException ex) {
System.out.println(ex);

    }           catch (FileNotFoundException ex) { 
                    Logger.getLogger(Gestion_voiture.class.getName()).log(Level.SEVERE, null, ex);
                } 
               
                

  
        }
            
            else {
                    sql = "Insert into voiture_non_disponible (n°matricule, model, marque, couleur, prix_par_jour, Image) values (?, ?, ?, ?, ?, ?)"; 

            try {
        prepared = conx.prepareStatement(sql);
                      
                        prepared.setString(1, matricule);
                        prepared.setString(2, modele);
                        prepared.setString(3, marque);
                        prepared.setString(4, couleur);
                        prepared.setString(5, prix);
  FileInputStream fis = new FileInputStream(imageFile);
                        prepared.setBinaryStream(6, fis, (int) imageFile.length());
                        
                prepared.executeUpdate();
                
                  } catch (SQLException ex) {
System.out.println(ex);

    }           catch (FileNotFoundException ex) {   
                    Logger.getLogger(Gestion_voiture.class.getName()).log(Level.SEVERE, null, ex);
                }   
                
            }
        
            }
           
        else {
            
                            JOptionPane.showMessageDialog(null, "Voiture existante");


                    
        }
        
      
   }
   
   else {
       
       JOptionPane.showMessageDialog(null, "Aucune image choisie", "Erreur", JOptionPane.ERROR_MESSAGE);
   }


    
     

   
 
// TODO add your handling code here:
    }//GEN-LAST:event_AddActionPerformed

    private void MatriculeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MatriculeKeyReleased
validateFields(); 
validateSearch();
validateDrop();
validateUpdate();

    }//GEN-LAST:event_MatriculeKeyReleased

    private void ModeleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ModeleKeyReleased
validateFields(); 
validateUpdate();
    }//GEN-LAST:event_ModeleKeyReleased

    private void MarqueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MarqueKeyReleased
validateFields();
validateSearch();
validateDrop();
validateUpdate();

// TODO add your handling code here:
    }//GEN-LAST:event_MarqueKeyReleased

    private void CouleurKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CouleurKeyReleased
validateFields();
validateUpdate();
    }//GEN-LAST:event_CouleurKeyReleased

    private void PrixKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrixKeyReleased
validateFields();
validateUpdate();
    }//GEN-LAST:event_PrixKeyReleased

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        
    int i;
        
       String matricule = Matricule.getText();
       String modele = Modele.getText();
       String marque = Marque.getText();
       String couleur = Couleur.getText();
       String prix = Prix.getText();
       String dispo = Dispo.getSelectedItem().toString();
       
      
           
              if(Recherche == 1){ 
                      ///Modification par marque
                      
                      Marque.setEditable(false);
                      Matricule.setEditable(false);

           try {
           

           prepared = conx.prepareStatement("select * from voiture_enregistrees where marque like '"+marque+"' ");   
           resultat = prepared.executeQuery();
           
           if(resultat.equals("")){
              JOptionPane.showMessageDialog(null, "Marque inexistante");
               
           }
           
           
           else {
                        if(!modele.equals("")){
               
               try {
                   prepared = conx.prepareStatement("update voiture_enregistrees set model = '"+modele+"' where marque like '"+marque+"' ");
               prepared.executeUpdate();
               
               
               } catch (SQLException ex) {
                   Logger.getLogger(Gestion_voiture.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
           
             if(!couleur.equals("")){
                 
                  try {
                   prepared = conx.prepareStatement("update voiture_enregistrees set couleur = '"+couleur+"' where marque like '"+marque+"' ");
               prepared.executeUpdate();
               
               
               } catch (SQLException ex) {
                   Logger.getLogger(Gestion_voiture.class.getName()).log(Level.SEVERE, null, ex);
               }
             }
             
             
              if(!prix.equals("")){
                 
                  try {
                   prepared = conx.prepareStatement("update voiture_enregistrees set prix_par_jour = '"+prix+"' where marque like '"+marque+"' ");
               prepared.executeUpdate();
               
               
               } catch (SQLException ex) {
                   Logger.getLogger(Gestion_voiture.class.getName()).log(Level.SEVERE, null, ex);
               }
             }
              
                          
                
JOptionPane.showMessageDialog(null, "Marque mis à jour avec succès");


           try {
               prepared = conx.prepareStatement("Select * from voiture_enregistrees where marque like '"+marque+"' ");
               resultat = prepared.executeQuery();
               int c;
               ResultSetMetaData rd = resultat.getMetaData();
               c = rd.getColumnCount();
               DefaultTableModel df = (DefaultTableModel)Table.getModel();
               df.setRowCount(0);
               
               while(resultat.next()){
                   
                   Vector v2 = new Vector();
                   
                   for(i = 0; i < c; i++){
                v2.add(resultat.getString("n°matricule"));
                v2.add(marque);
                v2.add(resultat.getString("model"));
                v2.add(resultat.getString("couleur"));
                v2.add(resultat.getFloat("prix_par_jour"));
                v2.add(resultat.getString("Disponibilite"));
                      
                   }
                   
                   df.addRow(v2);
               }
               
               
               
           } catch (SQLException ex) {
               Logger.getLogger(Gestion_voiture.class.getName()).log(Level.SEVERE, null, ex);
           }
    
        
           
               
           }

        } catch (SQLException ex) {
            Logger.getLogger(Gestion_voiture.class.getName()).log(Level.SEVERE, null, ex);
        }
   
           
       }
       
       else {   //Modification par matricule
            String  sql = "Select * from voiture_enregistrees where n°matricule like '"+mat_initiale+"' ";
        
        
    try {
                prepared = conx.prepareStatement(sql);
                resultat = prepared.executeQuery();
                i = 0;
                while(resultat.next()){
                    
                    String mod = resultat.getString("model");
                    String mar = resultat.getString("marque");
                    String cou = resultat.getString("couleur");
                    String pri = resultat.getString("prix_par_jour");
                    String dis = resultat.getString("Disponibilite");
                    i = 1;
if((!matricule.equals(mat_initiale)) || (!modele.equals(mod)) || (!marque.equals(mar)) || (!couleur.equals(cou)) || (!prix.equals(pri)) || (!dis.equals(dispo))){
    
     
    prepared = conx.prepareStatement("Update voiture_enregistrees set n°matricule = '"+matricule+"' where n°matricule like '"+mat_initiale+"' ");
    prepared.executeUpdate();
    prepared = conx.prepareStatement("Update voiture_enregistrees set model = '"+modele+"' where n°matricule like '"+mat_initiale+"' ");
    prepared.executeUpdate();
    prepared = conx.prepareStatement("Update voiture_enregistrees set marque = '"+marque+"' where n°matricule like '"+mat_initiale+"' ");
    prepared.executeUpdate();
    prepared = conx.prepareStatement("Update voiture_enregistrees set couleur = '"+couleur+"' where n°matricule like '"+mat_initiale+"' ");
    prepared.executeUpdate();
    prepared = conx.prepareStatement("Update voiture_enregistrees set prix_par_jour = '"+prix+"' where n°matricule like '"+mat_initiale+"' ");
    prepared.executeUpdate();
    prepared = conx.prepareStatement("Update voiture_enregistrees set Image = ? where n°matricule like '"+mat_initiale+"' ");
    
             FileInputStream fis = null;

    if(imageFile != null){
        
                        try {
                            fis = new FileInputStream(imageFile);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Gestion_voiture.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        prepared.setBinaryStream(1, fis, (int) imageFile.length());
                        
    prepared.executeUpdate();
    }
    
    
    
    
     prepared = conx.prepareStatement("Update voiture_disponible set n°matricule = '"+matricule+"' where n°matricule like '"+mat_initiale+"' ");
    prepared.executeUpdate();
    prepared = conx.prepareStatement("Update voiture_disponible set model = '"+modele+"' where n°matricule like '"+mat_initiale+"' ");
    prepared.executeUpdate();
    prepared = conx.prepareStatement("Update voiture_disponible set marque = '"+marque+"' where n°matricule like '"+mat_initiale+"' ");
    prepared.executeUpdate();
    prepared = conx.prepareStatement("Update voiture_disponible set couleur = '"+couleur+"' where n°matricule like '"+mat_initiale+"' ");
    prepared.executeUpdate();
    prepared = conx.prepareStatement("Update voiture_disponible set prix_par_jour = '"+prix+"' where n°matricule like '"+mat_initiale+"' ");
    prepared.executeUpdate();
    prepared = conx.prepareStatement("Update voiture_disponible set Image = ? where n°matricule like '"+mat_initiale+"' ");
    
     fis = null;
                     if(imageFile != null){
        
                        try {
                            fis = new FileInputStream(imageFile);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Gestion_voiture.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        prepared.setBinaryStream(1, fis, (int) imageFile.length());
                        
    prepared.executeUpdate();
    }
    
    
        prepared = conx.prepareStatement("Update voiture_non_disponible set n°matricule = '"+matricule+"' where n°matricule like '"+mat_initiale+"' ");
    prepared.executeUpdate();
    prepared = conx.prepareStatement("Update voiture_non_disponible set model = '"+modele+"' where n°matricule like '"+mat_initiale+"' ");
    prepared.executeUpdate();
    prepared = conx.prepareStatement("Update voiture_non_disponible set marque = '"+marque+"' where n°matricule like '"+mat_initiale+"' ");
    prepared.executeUpdate();
    prepared = conx.prepareStatement("Update voiture_non_disponible set couleur = '"+couleur+"' where n°matricule like '"+mat_initiale+"' ");
    prepared.executeUpdate();
    prepared = conx.prepareStatement("Update voiture_non_disponible set prix_par_jour = '"+prix+"' where n°matricule like '"+mat_initiale+"' ");
    prepared.executeUpdate();
 
    
    
    
  
  
        
        if((dis.equals("Non")) && (dispo.equals("Oui"))){  //Si la voiture n'est plus indisponible

            
                        prepared = conx.prepareStatement("Update voiture_enregistrees set Disponibilite = 'Oui' where n°matricule like '"+mat_initiale+"' ");
             prepared.executeUpdate();
             
               
            
           sql = "insert into voiture_disponible (n°matricule, model, marque, couleur, prix_par_jour, Image) select n°matricule, model, marque, couleur, prix_par_jour, Image from voiture_enregistrees where n°matricule like '"+mat_initiale+"' ";
            
            prepared = conx.prepareStatement(sql);
            prepared.executeUpdate();
          
            sql = "delete from voiture_non_disponible where n°matricule like '"+mat_initiale+"' ";
            
            prepared = conx.prepareStatement(sql);
            prepared.executeUpdate();
            
            
            
        }
        
        else{
            
               
            
            sql = "insert into voiture_non_disponible (n°matricule, model, marque, couleur, prix_par_jour) select n°matricule, model, marque, couleur, prix_par_jour from voiture_enregistrees where n°matricule like '"+mat_initiale+"' ";
            
            prepared = conx.prepareStatement(sql);
            prepared.executeUpdate();
            
            sql = "delete from voiture_disponible where n°matricule like '"+mat_initiale+"' ";
            
            prepared = conx.prepareStatement(sql);
            prepared.executeUpdate();
            
             prepared = conx.prepareStatement("Update voiture_enregistrees set Disponibilite = 'Non' where n°matricule like '"+mat_initiale+"' ");
             prepared.executeUpdate();
         
            
            
            
        }
        

   

JOptionPane.showMessageDialog(null, "Voiture mis à jour avec succès");

    table_vehicule();
    clear();
}

else {
    
    JOptionPane.showMessageDialog(null, "Aucune modification faite");
   
   
}
                }
                
                if(i == 0){
                    
                    JOptionPane.showMessageDialog(null, "Voiture inexistante");
                }
    } catch (SQLException ex) {
        Logger.getLogger(Gestion_voiture.class.getName()).log(Level.SEVERE, null, ex);
    }






           
       }
       
       
     
    
     

    }//GEN-LAST:event_updateActionPerformed

    private void AnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnnulerActionPerformed
        
clear();
validateFields();
validateSearch();
validateDrop();
validateUpdate();
table_vehicule();



// TODO add your handling code here:
    }//GEN-LAST:event_AnnulerActionPerformed

    private void DropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DropActionPerformed
             String marque = Marque.getText();
             String matricule = Matricule.getText();
             String dispo = "";
             int i = 0;
             String sql = "";
             if((!matricule.equals("")) && (marque.equals(""))){  // Suppresion par matricule
                 
                           i = 0;
             
            sql = "Select * From voiture_enregistrees where n°matricule like '"+matricule+"' ";

     try {
        prepared = conx.prepareStatement(sql);
        resultat = prepared.executeQuery();
        
       
        while(resultat.next()){
            
            i = 1;
        }
     }
     
     catch (SQLException ex) {
System.out.println(ex); 
    
    }
     
     if(i == 1){
         
             int a = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette voiture ?", "Suppression", JOptionPane.YES_NO_OPTION);

if(a == 0){
    
    
       sql = "select Disponibilite from voiture_enregistrees where n°matricule like '"+matricule+"' ";
     
                 try {
                     prepared = conx.prepareStatement(sql);
                          resultat  = prepared.executeQuery();
                            
     while(resultat.next()){
         
         dispo = resultat.getString("Disponibilite");
     }
     
     if(dispo.equals("Oui")){
         
         sql = "Delete from voiture_disponible where n°matricule like '"+matricule+"' ";
         
         prepared = conx.prepareStatement(sql);
         prepared.executeUpdate();
         
         
     }
     
     else {
         
   sql = "Delete from voiture_non_disponible where n°matricule like '"+matricule+"' ";
         
         prepared = conx.prepareStatement(sql);
         prepared.executeUpdate();
              }

                 } catch (SQLException ex) {
                     Logger.getLogger(Gestion_voiture.class.getName()).log(Level.SEVERE, null, ex);
                 }
   
       
    

sql = "Delete from voiture_enregistrees where  n°matricule like '"+matricule+"' ";

    try {
        prepared = conx.prepareStatement(sql);
        prepared.executeUpdate();
        
        JOptionPane.showMessageDialog(null, "Voiture supprimée avec succès");
        
  
clear();

                       Drop.setEnabled(false);
                       Drop.setForeground(new Color(0, 0, 0));
                       Drop.setBackground(new Color(240, 240, 240));
                       
                        Search.setEnabled(false);
                        Search.setBackground(new Color(240, 240, 240));
                        Search.setForeground(new Color(0, 0, 0));
                
                        update.setEnabled(false);
                        update.setBackground(new Color(240, 240, 240));
                        update.setForeground(new Color(0, 0, 0));
                        
                        Add.setEnabled(false);
                        Add.setBackground(new Color(240, 240, 240));
                        Add.setForeground(new Color(0, 0, 0));

        
    } catch (SQLException ex) {
System.out.println(ex); 
    
    }


   
     
     }

     }
     
     else {
         
         JOptionPane.showMessageDialog(null, "Voiture inexistante");
     }

             }
     
             
             
    
             
               else if((!marque.equals("")) && (matricule.equals(""))) {  ///Suppresion par marque
                   
                           i = 0;
             
             sql = "Select * From voiture_enregistrees where marque like '"+marque+"' ";
            
     try {
        prepared = conx.prepareStatement(sql);
        resultat = prepared.executeQuery();
        
       
        while(resultat.next()){
            
            i = 1;
        }
     }
     
     catch (SQLException ex) {
System.out.println(ex); 
    
    }
     
        
     if(i == 1){
         
             int a = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette marque ?", "Suppression", JOptionPane.YES_NO_OPTION);

if(a == 0){
    
sql = "Delete from voiture_enregistrees where  marque like '"+marque+"' ";

    try {
        prepared = conx.prepareStatement(sql);
        prepared.executeUpdate();
        
        JOptionPane.showMessageDialog(null, "Marque supprimée avec succès");
       
clear();

    Drop.setEnabled(false);
                       Drop.setForeground(new Color(0, 0, 0));
                       Drop.setBackground(new Color(240, 240, 240));
                       
                        Search.setEnabled(false);
                        Search.setBackground(new Color(240, 240, 240));
                        Search.setForeground(new Color(0, 0, 0));
                
                        update.setEnabled(false);
                        update.setBackground(new Color(240, 240, 240));
                        update.setForeground(new Color(0, 0, 0));
                        
                        Add.setEnabled(false);
                        Add.setBackground(new Color(240, 240, 240));
                        Add.setForeground(new Color(0, 0, 0));
                        
                        sql = "delete from voiture_disponible where marque like '"+marque+"' ";
                        
                        prepared = conx.prepareStatement(sql);
                        prepared.executeUpdate();
                        
                         sql = "delete from voiture_non_disponible where marque like '"+marque+"' ";
                        
                        prepared = conx.prepareStatement(sql);
                        prepared.executeUpdate();

        
    } catch (SQLException ex) {
System.out.println(ex); 
    
    }


}
     
     }
     
     else {
         
         JOptionPane.showMessageDialog(null, "Marque inexistante");
     }
             
               }
             
               else {  //Cas ou les 2 champs ne sont pas vides, on lancera une suppresion par matricule
                   
                                  i = 0;
             
           sql = "Select * From voiture_enregistrees where n°matricule like '"+matricule+"' ";

     try {
        prepared = conx.prepareStatement(sql);
        resultat = prepared.executeQuery();
        
       
        while(resultat.next()){
            
            i = 1;
        }
     }
     
     catch (SQLException ex) {
System.out.println(ex); 
    
    }
     
     if(i == 1){
         
             int a = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette voiture ?", "Suppression", JOptionPane.YES_NO_OPTION);

if(a == 0){
    

sql = "Delete from voiture_enregistrees where  n°matricule like '"+matricule+"' ";

    try {
        prepared = conx.prepareStatement(sql);
        prepared.executeUpdate();
        
        JOptionPane.showMessageDialog(null, "Voiture supprimée avec succès");
       
clear();
    Drop.setEnabled(false);
                       Drop.setForeground(new Color(0, 0, 0));
                       Drop.setBackground(new Color(240, 240, 240));
                       
                        Search.setEnabled(false);
                        Search.setBackground(new Color(240, 240, 240));
                        Search.setForeground(new Color(0, 0, 0));
                
                        update.setEnabled(false);
                        update.setBackground(new Color(240, 240, 240));
                        update.setForeground(new Color(0, 0, 0));
                        
                        Add.setEnabled(false);
                        Add.setBackground(new Color(240, 240, 240));
                        Add.setForeground(new Color(0, 0, 0));
                        
         
         sql = "Delete from voiture_disponible where n°matricule like '"+matricule+"' ";
         
         prepared = conx.prepareStatement(sql);
         prepared.executeUpdate();
         
         
  
         
   sql = "Delete from voiture_non_disponible where n°matricule like '"+matricule+"' ";
         
         prepared = conx.prepareStatement(sql);
         prepared.executeUpdate();
              

        
    } catch (SQLException ex) {
System.out.println(ex); 
    
    }


}
     
     }
     
     else {
         
         JOptionPane.showMessageDialog(null, "Voiture inexistante");
     }
 
               }
             
             
                   
             
  
    }//GEN-LAST:event_DropActionPerformed

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
        String marque = Marque.getText();
        String matricule = Matricule.getText();
int i = 0;
        if((!matricule.equals("")) && (marque.equals(""))) {  // Recherche par matricule
           
            String sql = "Select * From voiture_enregistrees where n°matricule like '"+matricule+"' ";
 try {
        prepared = conx.prepareStatement(sql);
        resultat = prepared.executeQuery();
        
        i = 0;
        while(resultat.next()){
            
            Modele.setText(resultat.getString("model"));
            Marque.setText(resultat.getString("marque"));
            Couleur.setText(resultat.getString("couleur"));
            Prix.setText(resultat.getString("prix_par_jour"));
            Dispo.setSelectedItem(resultat.getString("Disponibilite"));
            
            mat_initiale = matricule;    // Recuperer cette matricule pour faire la mise à jour sur cette voiture si besoin  
            
          validateFields();
                      
            update.setEnabled(true);
    update.setForeground(new Color(240, 240, 240));
    update.setBackground(new Color(204, 102, 0));

            
            i = 1;
           byte [] imageData = null;
    try {
        prepared = conx.prepareStatement("Select Image from voiture_enregistrees where n°matricule like '"+mat_initiale+"'");
        resultat = prepared.executeQuery();
while(resultat.next()){
      try {
        imageData = resultat.getBytes("Image");
    } catch (SQLException ex) {
        Logger.getLogger(Gestion_voiture.class.getName()).log(Level.SEVERE, null, ex);
    }
                    ImageIcon imageIcon = new ImageIcon(imageData);
                    labelimg.setIcon(resizeIcon(imageIcon, 1051, 505));
    
}
    } catch (SQLException ex) {
        Logger.getLogger(Gestion_voiture.class.getName()).log(Level.SEVERE, null, ex);
    }
            
        }
        
         if(i == 0){
                 JOptionPane.showMessageDialog(null, "Voiture introuvable");

    
 }
            
        }
 catch (SQLException ex) {
System.out.println(ex);  
    
    }
 

 
        }
        
        else if((!marque.equals("")) && (matricule.equals("")))  {  ///Recherche par marque
            String sql = "Select * From voiture_enregistrees where marque like '"+marque+"' ";

            
            
    try {
        prepared = conx.prepareStatement(sql);
        resultat = prepared.executeQuery();
        
        if(!resultat.equals("")){/////////
            
         
            i = 1;
            

        
        int c;
        ResultSetMetaData rd = resultat.getMetaData();
        c = rd.getColumnCount();
        DefaultTableModel df = (DefaultTableModel)Table.getModel();
        df.setRowCount(0);
        
         i = 0;
        while(resultat.next()){
            
       
            
             Vector v2 = new Vector();
            
            for(i = 0; i < c; i++){
                
                v2.add(resultat.getString("n°matricule"));
                v2.add(marque);
                v2.add(resultat.getString("model"));
                v2.add(resultat.getString("couleur"));
                v2.add(resultat.getFloat("prix_par_jour"));
                v2.add(resultat.getString("Disponibilite"));
            }
            
            df.addRow(v2);
           
Matricule.setText(""); 
Modele.setText("");
Couleur.setText("");
Prix.setText("");
Marque.setEditable(false);
Matricule.setEditable(false);
validateFields();
          
   

        }
     
        if(i == 0){
            
            JOptionPane.showMessageDialog(null, "Marque introuvable");
           
            
            
        }

    }
        
      
    }
    catch (SQLException ex) {
System.out.println(ex);  
    
    }
    
        }
        
        else {  //Cas ou les deux champs ne sont pas vides, on lancera une recherche par matricule 
            
                   String sql = "Select * From voiture_enregistrees where n°matricule like '"+matricule+"' ";
 try {
        prepared = conx.prepareStatement(sql);
        resultat = prepared.executeQuery();
        
         i = 0;
        while(resultat.next()){
            
            Modele.setText(resultat.getString("model"));
            Marque.setText(resultat.getString("marque"));
            Couleur.setText(resultat.getString("couleur"));
            Prix.setText(resultat.getString("prix_par_jour"));
            Dispo.setSelectedItem(resultat.getString("Disponibilite"));

            mat_initiale = matricule;
            
          validateFields();
            
            
            i = 1;
           byte [] imageData = null;
    try {
        prepared = conx.prepareStatement("Select Image from voiture_enregistrees where n°matricule like '"+mat_initiale+"'");
        resultat = prepared.executeQuery();
while(resultat.next()){
      try {
        imageData = resultat.getBytes("Image");
    } catch (SQLException ex) {
        Logger.getLogger(Gestion_voiture.class.getName()).log(Level.SEVERE, null, ex);
    }
                    ImageIcon imageIcon = new ImageIcon(imageData);
                    labelimg.setIcon(resizeIcon(imageIcon, 1051, 505));
    
}
    } catch (SQLException ex) {
        Logger.getLogger(Gestion_voiture.class.getName()).log(Level.SEVERE, null, ex);
    }
            
        }
        
         if(i == 0){
                 JOptionPane.showMessageDialog(null, "Voiture introuvable");

    
 }
            
        }
 catch (SQLException ex) {
System.out.println(ex);  
    
    }
 

            
        }
 

 // TODO add your handling code here:
    }//GEN-LAST:event_SearchActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
Acceuil a = new Acceuil();
this.hide();
a.setVisible(true);// TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
Login l = new Login();
this.hide();
l.setVisible(true);// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void SearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchKeyPressed
       
  // TODO add your handling code here:
    }//GEN-LAST:event_SearchKeyPressed

    
   
    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked

DefaultTableModel d1 = (DefaultTableModel)Table.getModel();

int selectIndex = Table.getSelectedRow();

Matricule.setText(d1.getValueAt(selectIndex, 0).toString());
Marque.setText(d1.getValueAt(selectIndex, 1).toString());
Modele.setText(d1.getValueAt(selectIndex, 2).toString());
Couleur.setText(d1.getValueAt(selectIndex, 3).toString());
Prix.setText(d1.getValueAt(selectIndex, 4).toString());
Dispo.setSelectedItem((d1.getValueAt(selectIndex, 5).toString()));
mat_initiale = Matricule.getText();
  byte [] imageData = null;
    try {
        prepared = conx.prepareStatement("Select Image from voiture_enregistrees where n°matricule like '"+mat_initiale+"'");
        resultat = prepared.executeQuery();
while(resultat.next()){
      try {
        imageData = resultat.getBytes("Image");
    } catch (SQLException ex) {
        Logger.getLogger(Gestion_voiture.class.getName()).log(Level.SEVERE, null, ex);
    }
                    ImageIcon imageIcon = new ImageIcon(imageData);
                    labelimg.setIcon(resizeIcon(imageIcon, 1051, 505));
    
}
    } catch (SQLException ex) {
        Logger.getLogger(Gestion_voiture.class.getName()).log(Level.SEVERE, null, ex);
    }

 
  
                    
                    //v2.add(resultat.getBinaryStream("Image"));

   validateFields();
   validateDrop();
   validateSearch();
   validateUpdate();


        // TODO add your handling code here:
    }//GEN-LAST:event_TableMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
clear();



// TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void ChangerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangerActionPerformed

        
        choisirImage();
// TODO add your handling code here:
        
        
       
       
    }//GEN-LAST:event_ChangerActionPerformed

     public void choisirImage() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers JPEG", "jpg", "jpeg");
        fileChooser.setFileFilter(filter);

      int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
             imageFile = fileChooser.getSelectedFile();
            
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
    }
    /*
    public void ajouterImageDansBaseDeDonnees(File imageFile) throws FileNotFoundException, IOException {
       

        try 
        
        {
            // Préparation de la requête SQL
            String sql = "INSERT INTO voiture_enregistrees (n°matricule, model, marque, couleur, prix_par_jour, Disponibilite, Image) VALUES (?,?,?,?,?,?,?)";
            try (PreparedStatement prepared = conx.prepareStatement(sql);
                 FileInputStream fis = new FileInputStream(imageFile)) {

                // Paramètres de la requête
             prepared.setString(1, matricule);
             prepared.setString(2, "");
             prepared.setString(3, "");
             prepared.setString(4, "");
             prepared.setFloat(5, 0);
             prepared.setString(6, "");
             prepared.setBinaryStream(7, fis, (int) imageFile.length());

                // Exécution de la requête
                prepared.executeUpdate();
                
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }*/
    
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
            java.util.logging.Logger.getLogger(Gestion_voiture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gestion_voiture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gestion_voiture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gestion_voiture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gestion_voiture().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add;
    private javax.swing.JButton Annuler;
    private javax.swing.JButton Changer;
    private javax.swing.JTextField Couleur;
    private javax.swing.JComboBox<String> Dispo;
    private javax.swing.JButton Drop;
    private javax.swing.JTextField Marque;
    private javax.swing.JTextField Matricule;
    private javax.swing.JTextField Modele;
    private javax.swing.JTextField Prix;
    private javax.swing.JButton Search;
    private javax.swing.JTable Table;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelimg;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}
