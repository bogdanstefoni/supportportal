package com.bogdan.supportportal.service;


import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.util.Properties;

import static com.bogdan.supportportal.constant.EmailConstant.*;

@Service
public class EmailService {

    public void sendNewPasswordEmail(String firstName, String password, String email) throws MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", GMAIL_SMTP_SERVER);
        props.put("mail.smtp.port", "587");
        props.put("mail.debug", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(USERNAME));
            InternetAddress[] address = {new InternetAddress(email)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(EMAIL_SUBJECT);
            msg.setText("Hello " + firstName + ", \n \n Your new account password is: " + password + "\n \n The Support Team");

            Transport.send(msg);

            System.out.println("Message Sent.");
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
    }

    //    public void sendNewPasswordEmail(String firstName, String password, String email) throws MessagingException {
//        Message message = createEmail(firstName, password, email);
//        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_EMAIL_TRANSFER_PROTOCOL);
//        smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
//        smtpTransport.sendMessage(message, message.getAllRecipients());
//        smtpTransport.close();
//    }
//
//    private Message createEmail(String firstName, String password, String email) throws MessagingException {
//        Message message = new MimeMessage(getEmailSession());
//        message.setFrom(new InternetAddress(FROM_EMAIL));
//        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
//        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(CC_EMAIL, false));
//        message.setSubject(EMAIL_SUBJECT);
//        message.setText("Hello " + firstName + ", \n \n Your new account password is: " + password + "\n \n The Support Team");
//        message.setSentDate(new Date());
//        message.saveChanges();
//
//        return message;
//    }
//
    private Session getEmailSession() {
        Properties properties = System.getProperties();
        properties.put(SMTP_HOST, GMAIL_SMTP_SERVER);
        properties.put(SMTP_AUTH, true);
        properties.put(SMTP_PORT, DEFAULT_PORT);
        properties.put(SMTP_STARTTLS_ENABLE, true);
        properties.put(SMTP_STARTTLS_REQUIRED, true);

        return Session.getInstance(properties, null);
    }
}
