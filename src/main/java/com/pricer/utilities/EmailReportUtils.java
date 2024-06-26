package com.pricer.utilities;

import com.pricer.base.BaseTestMobile;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.pricer.base.BaseTestWebClassContext.end;
import static com.pricer.base.BaseTestWebClassContext.start;

public class EmailReportUtils {

    static String pass;
    static String fail;
    static String skip;

    public void sendEmailWithAttachment(String recipientEmail, String allureReportPath) throws Exception {

        long executionDuration = Duration.between(start, end).toMillis();
        String overallTimeLapsed = getTimeLapsed(executionDuration);
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

            JSONParser jsonParser = new JSONParser();
            try {
                JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(System.getProperty("user.dir") + "/TestExecutionTime.json"));
                BaseTestMobile.startTime = (Long) jsonObject.get("startTime");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }  catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }

            System.out.println("wfbiechigrbigvhbwekjgvhjkrgk 2"+BaseTestMobile.startTime);
            BaseTestMobile.endTime= System.currentTimeMillis();
            System.out.println("wfbiechigrbigvhbwekjgvhjkrgk 2"+BaseTestMobile.endTime);
            long differenceInMilliSeconds
                    = Math.abs(BaseTestMobile.endTime - BaseTestMobile.startTime);
            long differenceInHours
                    = (differenceInMilliSeconds / (60 * 60 * 1000))
                    % 24;
            long differenceInMinutes
                    = (differenceInMilliSeconds / (60 * 1000)) % 60;
            long differenceInSeconds
                    = (differenceInMilliSeconds / 1000) % 60;
            JSONParser jsonParser2 = new JSONParser();
            try {
                //Parsing the contents of the JSON file
                JSONObject jsonObject = (JSONObject) jsonParser2.parse(new FileReader(System.getProperty("user.dir") + "/TestResult.json"));
                pass = (String) jsonObject.get("pass");
                fail = (String) jsonObject.get("fail");
                skip = (String) jsonObject.get("skip");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }  catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }

            String str = "Hi, Below are the some stats of Executed test suite " + "\n" + "\n";


            str = str.concat("Environment: " + FrameworkConfig.getStringConfigProperty("ApplicationEnvironment") + "\n");

            str = str.concat("Execution Time: "+ differenceInHours + " hours "
                    + differenceInMinutes + " minutes "
                    + differenceInSeconds + " Seconds " +"\n");


            int totalCount = Integer.parseInt(pass)+Integer.parseInt(fail)+Integer.parseInt(skip);

            str = str.concat("Total Test Case count: "+totalCount+"\n");

            str = str.concat("Passed Test Case Count: " + pass + "\n" +
                    "Failed Test Case Count: " + fail + "\n" +
                    "Skipped Test Case Count: " + skip + "\n");


            float testCaseExecutedPer = (float) (Integer.parseInt(pass) + Integer.parseInt(fail)) / totalCount * 100;
            float testCasePassPer = (float) Integer.parseInt(pass) / totalCount * 100;
            float testCaseFailPer = (float) Integer.parseInt(fail) / totalCount * 100;
            float testCaseSkipPer = (float) Integer.parseInt(skip) / totalCount * 100;

            EmailSSLUtils emailUtil = new EmailSSLUtils();

//            String htmlMessage = emailUtil.prepareHTMLMessage(Long.toString(differenceInMilliSeconds), totalCount, Integer.parseInt(pass), Integer.parseInt(fail), Integer.parseInt(skip));
            String htmlMessage = emailUtil.prepareHTMLMessage(overallTimeLapsed, totalCount, Integer.parseInt(pass), Integer.parseInt(fail), Integer.parseInt(skip));

            String contentType = FrameworkConfig.getStringConfigProperty("ContentType");
            messageBodyPart.setContent(htmlMessage, contentType);
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

    private String getTimeLapsed(long duration) {
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
        String durationAsString = hours + "h " + minutes + "m " + seconds + "s";
        return durationAsString;
    }


/*    public static void main(String[] args) throws Exception {
        // Specify the recipient's email address
        String recipientEmail = FrameworkConfig.getStringConfigProperty("ToEmailAddresses"); // Replace with recipient's email address
        // Specify the path to the Allure report
        String allureReportPath = System.getProperty("user.dir") + "\\allure-results.zip"; // Replace with the actual path
        // Send email with Allure report attachment
        sendEmailWithAttachment(recipientEmail, allureReportPath);
    }*/
}
