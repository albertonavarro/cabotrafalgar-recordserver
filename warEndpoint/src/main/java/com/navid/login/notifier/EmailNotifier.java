package com.navid.trafalgar.recordservernotifier;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author alberto
 */
@Service
public class EmailNotifier implements MessageListener {

    @Value("${email.provider}")
    private String provider;
    
    @Value("${email.username}")
    private String username;
    
    @Value("${email.password}")
    private String password;
    
    public void notify(String notify){
        username="du.no.riplai";
        password="";
        
        Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("du.no.riplai@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(""));
			message.setSubject("Testing Subject");
			message.setText("http://localhost:8080/login/rest/validate/" + notify);
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    
    }

    @Override
    public void onMessage(javax.jms.Message message) {
        try {
            notify(((ActiveMQTextMessage) message).getText());
        } catch (JMSException ex) {
            Logger.getLogger(EmailNotifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
