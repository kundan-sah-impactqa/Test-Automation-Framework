package com.starbucks.utilities;

import com.starbucks.base.BaseTestMobile;
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
import java.io.FileReader;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.starbucks.base.BaseTestWebClassContext.end;
import static com.starbucks.base.BaseTestWebClassContext.start;

public class EmailReportUtils {

    static String pass;
    static String fail;
    static String skip;

    public void sendEmailWithAttachment(String recipientEmail, String allureReportPath) throws Exception {
        // Ensure start and end times are not null
        if (start == null) {
            Instant now = Instant.now();
            Duration duration = Duration.ofMinutes(3).plusSeconds(40);
            Instant result = now.minus(duration);
            start = result;
//            start = Instant.now(); // Set start to the current time if it's null
        }
        if (end == null) {
            end = Instant.now(); // Set end to the current time if it's null
        }

        long executionDuration = Duration.between(start, end).toMillis();
        String overallTimeLapsed = getTimeLapsed(executionDuration);

        String senderEmail = FrameworkConfig.getStringConfigProperty("FromEmailAddress");
        String senderPassword = CryptoUtils.decryptTheValue(FrameworkConfig.getStringConfigProperty("EmailPassword"));

        Properties props = setupMailServerProperties();

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Test Automation Execution Report");

            Multipart multipart = new MimeMultipart();

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            loadTestExecutionTime();
            loadTestResults();

            String emailContent = prepareEmailContent(overallTimeLapsed);
            messageBodyPart.setContent(emailContent, FrameworkConfig.getStringConfigProperty("ContentType"));
            multipart.addBodyPart(messageBodyPart);

            addAttachment(multipart, allureReportPath);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Email sent successfully with Allure report attachment.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Properties setupMailServerProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");
        return props;
    }

    private void loadTestExecutionTime() {
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(System.getProperty("user.dir") + "/TestExecutionTime.json"));
            BaseTestMobile.startTime = (Long) jsonObject.get("startTime");
            BaseTestMobile.endTime = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTestResults() {
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(System.getProperty("user.dir") + "/TestResult.json"));
            pass = (String) jsonObject.get("pass");
            fail = (String) jsonObject.get("fail");
            skip = (String) jsonObject.get("skip");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addAttachment(Multipart multipart, String allureReportPath) throws MessagingException {
        MimeBodyPart attachmentBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(allureReportPath);
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName("allure-report.zip");
        multipart.addBodyPart(attachmentBodyPart);
    }

    private String prepareEmailContent(String overallTimeLapsed) {
        long differenceInMilliSeconds = Math.abs(BaseTestMobile.endTime - BaseTestMobile.startTime);
        long differenceInHours = (differenceInMilliSeconds / (60 * 60 * 1000)) % 24;
        long differenceInMinutes = (differenceInMilliSeconds / (60 * 1000)) % 60;
        long differenceInSeconds = (differenceInMilliSeconds / 1000) % 60;

        StringBuilder str = new StringBuilder("Hi, Below are the some stats of Executed test suite \n\n");
        str.append("Environment: ").append(FrameworkConfig.getStringConfigProperty("ApplicationEnvironment")).append("\n");
        str.append("Execution Time: ").append(differenceInHours).append(" hours ")
                .append(differenceInMinutes).append(" minutes ")
                .append(differenceInSeconds).append(" Seconds \n");

        int totalCount = Integer.parseInt(pass) + Integer.parseInt(fail) + Integer.parseInt(skip);
        str.append("Total Test Case count: ").append(totalCount).append("\n")
                .append("Passed Test Case Count: ").append(pass).append("\n")
                .append("Failed Test Case Count: ").append(fail).append("\n")
                .append("Skipped Test Case Count: ").append(skip).append("\n");

        String htmlMessage = prepareHTMLMessage(overallTimeLapsed, totalCount, Integer.parseInt(pass), Integer.parseInt(fail), Integer.parseInt(skip));
        return htmlMessage;
    }

    private String getTimeLapsed(long duration) {
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
        return hours + "h " + minutes + "m " + seconds + "s";
    }

    public String prepareHTMLMessage(String lapsedTime, int totalTests, int testPassed, int testFailed, int testSkipped) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        StringBuilder strbResult = new StringBuilder();
        strbResult.append("<!DOCTYPE html>")
                .append("<html><head><title>Automation Execution Results</title>")
                .append("<style>table, th, td { border: 1px solid;} ")
                .append("table#testResults { table-layout: fixed ; width: 90% ; margin-left: auto; margin-right: auto; word-wrap: normal; } ")
                .append("table#testStats { table-layout: fixed ; width: 100% ; margin-left: auto; margin-right: auto; word-wrap: normal; } ")
                .append("</style></head>")
                .append("<body><br>")
                .append("<table id=\"footer\" align=\"center\" style=\"border: 0;width: 100%;vertical-align: bottom;\"><tr><td style=\"border: 0;text-align: center;\"><i> **** This is an auto generated email from the QA Test Automation Suite. Please do not respond. **** </i></td></tr></table>")
                .append("<br><br><table id=\"testResults\" align=\"center\">")
                .append("<tr><td colspan=\"7\"><p style=\"text-align: left;\">")
//                .append("<b>Environment</b>: ").append(FrameworkConfig.getStringConfigProperty("ApplicationURL_QA")).append("<br>")
                .append("<b>Test Automation Suite</b>: Regression<br>")
//                .append("<b>Hostname</b>: ").append(getHostName()).append("<br>")
                .append("<b>Test Cases Executed</b>: ").append(totalTests).append("<br>")
//                .append("<b>Operating System</b>: ").append(System.getProperty("os.name")).append("<br>")
                .append("<b>Start Time (UTC)</b>: ").append(start).append("<br>")
                .append("<b>End Time (UTC)</b>: ").append(end).append("<br>")
                .append("<b>Execution Time</b>: ").append(lapsedTime).append("<br>");
        if (System.getProperty("suiteXmlFile") != null)
            strbResult.append("<b>Test Pack</b>: ").append(System.getProperty("suiteXmlFile")).append("<br>");
        strbResult.append("</p></td></tr>")
                .append("<tr>")
                .append("<td align=\"center\" rowspan=\"4\"><b><img style=\"width:100%;max-width:200px\" src=\"https://quickchart.io/chart?c={ type: 'doughnut', data: { datasets: [ { data: [")
                .append(testPassed).append(", ").append(testFailed).append(", ").append(testSkipped)
                .append("], backgroundColor: [ 'rgb(0, 128, 0)', 'rgb(255, 0, 0)', 'rgb(255, 127, 80)' ], label: 'results', }, ], labels: ['Pass', 'Fail', 'Skip'], }, options: { title: { display: true, text: 'Test Results', }, }, }\" alt=\"Test Results\"></b></td>")
                .append("<td align=\"center\" colspan=\"6\"><p><b>Total Test Cases Executed<br><br>").append(totalTests).append("</b></p></td>")
                .append("</tr>")
                .append("<tr><td colspan=\"2\">Passed Test Case Count</td><td colspan=\"1\" align=\"center\">").append(testPassed).append("</td><td colspan=\"2\">Passed Test Case %</td><td colspan=\"1\" align=\"center\">").append(decimalFormat.format(((float) testPassed * 100) / (float) totalTests)).append("%</td></tr>")
                .append("<tr><td colspan=\"2\">Failed Test Case Count</td><td colspan=\"1\" align=\"center\">").append(testFailed).append("</td><td colspan=\"2\">Failed Test Case %</td><td colspan=\"1\" align=\"center\">").append(decimalFormat.format(((float) testFailed * 100) / (float) totalTests)).append("%</td></tr>")
                .append("<tr><td colspan=\"2\">Skipped Test Case Count</td><td colspan=\"1\" align=\"center\">").append(testSkipped).append("</td><td colspan=\"2\">Skipped Test Case %</td><td colspan=\"1\" align=\"center\">").append(decimalFormat.format(((float) testSkipped * 100) / (float) totalTests)).append("%</td></tr>")
                .append("</table>")
                .append("</body></html>");
        return strbResult.toString();
    }

   /* public static void main(String[] args) throws Exception {
        // Specify the recipient's email address
        String recipientEmail = FrameworkConfig.getStringConfigProperty("ToEmailAddresses"); // Replace with recipient's email address
        // Specify the path to the Allure report
        String allureReportPath = System.getProperty("user.dir") + "\\allure-results.zip"; // Replace with the actual path
        // Send email with Allure report attachment
        EmailReportUtils emailReportUtils = new EmailReportUtils();
        emailReportUtils.sendEmailWithAttachment(recipientEmail, allureReportPath);
    }*/

}
