import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    public static void main(String[] args) {
        // Informations sur l'expéditeur
        String senderEmail = "rentforyou599@gmail.com"; // Remplacez par votre adresse e-mail
        String password = "fdhw tyjz xehl aabx"; // Remplacez par votre mot de passe MAHPAPA77186866
        String recipientEmail = "amedhoch38@gmail.com"; // Adresse e-mail du destinataire issah6965@gmail.com

        // Configuration des propriétés
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Serveur SMTP pour Gmail
        props.put("mail.smtp.port", "587"); // Port SMTP pour Gmail

        // Création d'une session
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmail, password);
                    }
                });
//        props.put("mail.smtp.connectiontimeout", "5000"); // 5000 milliseconds
//        props.put("mail.smtp.timeout", "5000"); // 5000 milliseconds


        try {
            // Création de l'objet Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Test d'envoi d'e-mail via JavaMail");
            message.setText("Bonjour,\n\nvotre email a ete enregistre avec succes bitch 🤌");

            // Envoi de l'e-mail
            Transport.send(message);

            System.out.println("L'e-mail a été envoyé avec succès.");

        } catch (MessagingException e) {
            System.out.println("Une erreur s'est produite lors de l'envoi de l'e-mail : " + e.getMessage());
        }
    }
}
