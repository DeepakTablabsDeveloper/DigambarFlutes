package com.ecom.core.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.mail.javamail.MimeMessageHelper; 

public class CommonEmailService {

	public static void sendEmail(String toEmail,String subject,String emailbody) {
		String to="salved34@gmail.com";//change accordingly   
        final String user="salved34@gmail.com";//change accordingly   
        final String password="EdeepeepE";//change accordingly     

        //1) get the session object      
        Properties properties = System.getProperties();  
//        properties.setProperty("mail.smtp.host", "smtp.gmail.com");   
//        properties.put("mail.smtp.auth", "true");  
        
        properties.put("mail.smtp.host", "smtp.gmail.com");    
        properties.put("mail.smtp.socketFactory.port", "465");    
        properties.put("mail.smtp.socketFactory.class",    
                  "javax.net.ssl.SSLSocketFactory");    
        properties.put("mail.smtp.auth", "true");    
        properties.put("mail.smtp.port", "465"); 
        

        Session session = Session.getInstance(properties,   
                new javax.mail.Authenticator() {   
            protected PasswordAuthentication getPasswordAuthentication() {   
                return new PasswordAuthentication(user,password);    }   });       

        //2) compose message      
        try{    
//            MimeMessage message = new MimeMessage(session);    
//            message.setFrom(new InternetAddress(user));     
//            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
//            message.setSubject("Message Aleart");         

            //3) create MimeBodyPart object and set your message text        
            BodyPart messageBodyPart1 = new MimeBodyPart();     
            messageBodyPart1.setText("This is message body");          

            //4) create new MimeBodyPart object and set DataHandler object to this object        
//            MimeBodyPart messageBodyPart2 = new MimeBodyPart();      
//            String filename = "C:\\Users\\HP/Desktop/Deepak_Java.doc";//change accordingly     
//            DataSource source = new FileDataSource(filename);    
//            messageBodyPart2.setDataHandler(new DataHandler(source));    
//            messageBodyPart2.setFileName(filename);             

            //5) create Multipart object and add MimeBodyPart objects to this object        
//            Multipart multipart = new MimeMultipart();    
//            multipart.addBodyPart(messageBodyPart1);     
//            multipart.addBodyPart(messageBodyPart2);      

            //6) set the multiplart object to the message object    
//            message.setContent(multipart );        

            //7) send message    
            
            Message message = new MimeMessage(session);
    		
    		MimeMessageHelper helper;
    		message.setFrom(new InternetAddress("","ImInQ Notifications"));
    		message.setRecipients(Message.RecipientType.TO,
    			InternetAddress.parse("salved34@gmail.com"));
    		message.setSubject(subject);
    		message.setContent("txt","text/html");
            
            
            Transport.send(message);      
            System.out.println("message sent....");   

        }catch (MessagingException ex) {ex.printStackTrace();} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }
	
}
