package com.nsano.utilities;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {

    public static void sendEmailWithAttachment(String recipientEmail, String allureReportPath) throws Exception {
        // Sender's email details
        String senderEmail = FrameworkConfig.getStringConfigProperty("FromEmailAddress"); // Replace with your Outlook email address
        String senderPassword = CryptoUtils.decryptTheValue(FrameworkConfig.getStringConfigProperty("EmailPassword")); // Replace with your Outlook email password

        // Mail server properties for Outlook
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com"); // Outlook SMTP server
        props.put("mail.smtp.port", "587");

        // Create a session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a MIME message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Allure Report");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Add text content
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Please find the Allure report attached.");
            multipart.addBodyPart(messageBodyPart);

            // Add attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(allureReportPath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("allure-report.zip"); // Name of the attachment file
            multipart.addBodyPart(messageBodyPart);
            Thread.sleep(8000); // Required
            // Set the content of the message
            message.setContent(multipart);

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully with Allure report attachment.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

   /* public static void main(String[] args) throws Exception {
        // Specify the recipient's email address
        String recipientEmail = FrameworkConfig.getStringConfigProperty("ToEmailAddresses"); // Replace with recipient's email address
        // Specify the path to the Allure report
        String allureReportPath = System.getProperty("user.dir") + "\\allure-results.zip"; // Replace with the actual path
        // Send email with Allure report attachment
        sendEmailWithAttachment(recipientEmail, allureReportPath);
    }*/
}
