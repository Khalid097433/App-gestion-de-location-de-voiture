package admin;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterfaceAjoutEtAffichageImage extends JFrame {
    private JButton choisirImageButton;
    private JButton afficherImageButton;

    public InterfaceAjoutEtAffichageImage() {
        setTitle("Ajouter et Afficher une image dans la base de données");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        choisirImageButton = new JButton("Choisir une image");
        choisirImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choisirImage();
            }
        });

        afficherImageButton = new JButton("Afficher l'image par nom");
        afficherImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherImageParNom();
            }
        });

        JPanel panel = new JPanel();
        panel.add(choisirImageButton);
        panel.add(afficherImageButton);

        getContentPane().add(panel);
    }

    private void choisirImage() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers JPEG", "jpg", "jpeg");
        fileChooser.setFileFilter(filter);

      int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File imageFile = fileChooser.getSelectedFile();
            try {
                ajouterImageDansBaseDeDonnees(imageFile);
            } catch (IOException ex) {
                Logger.getLogger(InterfaceAjoutEtAffichageImage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
    }

    private void ajouterImageDansBaseDeDonnees(File imageFile) throws FileNotFoundException, IOException {
        String url = "jdbc:mysql://localhost:3306/votre_base_de_donnees";
        String utilisateur = "votre_utilisateur";
        String motDePasse = "votre_mot_de_passe";

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse)) {
            // Préparation de la requête SQL
            String sql = "INSERT INTO table_images (nom, image) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 FileInputStream fis = new FileInputStream(imageFile)) {

                // Paramètres de la requête
                statement.setString(1, imageFile.getName());
                statement.setBinaryStream(2, fis, (int) imageFile.length());

                // Exécution de la requête
                statement.executeUpdate();
                System.out.println("Image ajoutée à la base de données.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void afficherImageParNom() {
        String nomImage = JOptionPane.showInputDialog("Entrez le nom de l'image à afficher :");

        if (nomImage != null && !nomImage.isEmpty()) {
            String url = "jdbc:mysql://localhost:3306/votre_base_de_donnees";
            String utilisateur = "votre_utilisateur";
            String motDePasse = "votre_mot_de_passe";

            try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse)) {
                // Préparation de la requête SQL
                String sql = "SELECT image FROM table_images WHERE nom = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, nomImage);

                    // Exécution de la requête
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        // Récupération de l'image depuis la base de données
                        byte[] imageData = resultSet.getBytes("image");

                            // Affichage de l'image
                        ImageIcon imageIcon = new ImageIcon(imageData);
                        
                        JOptionPane.showMessageDialog(null, new JLabel(imageIcon));
                    } else {
                        JOptionPane.showMessageDialog(null, "Aucune image trouvée avec ce nom.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfaceAjoutEtAffichageImage interfaceAjoutEtAffichageImage = new InterfaceAjoutEtAffichageImage();
            interfaceAjoutEtAffichageImage.setVisible(true);
        });
    }
}

