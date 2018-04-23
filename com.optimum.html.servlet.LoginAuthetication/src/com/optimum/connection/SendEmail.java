package com.optimum.connection;

import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;

import com.optimumn.pojo.Employee; 

public class SendEmail  {  
		
	public void registerMail(Employee refEmp){  
		String to =refEmp.getEmail();//change accordingly  
		String from = "optimum.batch5@gmail.com";
		String passwordEmail = "Optimum2018";
		String host ="smtp.gmail.com";//or IP address  

		//Get the session object  
		Properties properties = System.getProperties();  
		properties.setProperty("mail.smtp.host", host);  // SMTP Host
		properties.put("mail.smtp.socketFactory.port", "465"); // SSL Port
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL Factory Class
		properties.put("mail.smtp.auth", "true"); // Enabling SMTP Authentication		
		properties.put("mail.smtp.port", "465");// SMTP Port
		
		Authenticator authenticator = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, passwordEmail);
			}
		};
		
		Session session = Session.getDefaultInstance(properties,authenticator); 

		//compose the message  
		try{  
			MimeMessage message = new MimeMessage(session);  
			message.setFrom(new InternetAddress(from));  
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
			message.setSubject("You have been registered");  
			message.setText("Dear "+refEmp.getFullName()+", your temporary password is: "+refEmp.getPassword());  

			// Send message  
			Transport.send(message);  
			System.out.println("Message Sent Successfully....");  

		}catch (MessagingException mex) {//mex.printStackTrace(); 
			 mex.printStackTrace();
			 }
	}  //end of registerMail
	
	
	public void forgotMail(String name,String password,String email){  
		String to =email;//change accordingly  
		String from = "optimum.batch5@gmail.com";
		String passwordEmail = "Optimum2018";
		String host ="smtp.gmail.com";//or IP address  

		//Get the session object  
		Properties properties = System.getProperties();  
		properties.setProperty("mail.smtp.host", host);  // SMTP Host
		properties.put("mail.smtp.socketFactory.port", "465"); // SSL Port
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL Factory Class
		properties.put("mail.smtp.auth", "true"); // Enabling SMTP Authentication		
		properties.put("mail.smtp.port", "465");
		
		Authenticator authenticator = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, passwordEmail);
			}
		};
		
		Session session = Session.getDefaultInstance(properties,authenticator); 

		//compose the message  
		try{  
			MimeMessage message = new MimeMessage(session);  
			message.setFrom(new InternetAddress(from));  
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
			message.setSubject("Request for new password");  
			message.setText("Dear "+name+", your requested temporary password is: "+password);  

			// Send message  
			Transport.send(message);  
			System.out.println("Message Sent Successfully....");  

		}catch (MessagingException mex) {//mex.printStackTrace(); 
			 mex.printStackTrace();
			 }
	}  //end of forgotMail
	
	
	public void lockMail(String name,String email){  
		String to =email;//change accordingly  
		String from = "optimum.batch5@gmail.com";
		String passwordEmail = "Optimum2018";
		String host ="smtp.gmail.com";//or IP address  

		//Get the session object  
		Properties properties = System.getProperties();  
		properties.setProperty("mail.smtp.host", host);  // SMTP Host
		properties.put("mail.smtp.socketFactory.port", "465"); // SSL Port
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL Factory Class
		properties.put("mail.smtp.auth", "true"); // Enabling SMTP Authentication		
		properties.put("mail.smtp.port", "465");
		
		Authenticator authenticator = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, passwordEmail);
			}
		};
		
		Session session = Session.getDefaultInstance(properties,authenticator); 


		//compose the message  
		try{  
			MimeMessage message = new MimeMessage(session);  
			message.setFrom(new InternetAddress(from));  
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
			message.setSubject("Locked account");  
			message.setText("Dear "+name+", your account has been locked due to multiple login attempts or by adminstrative side. Please contact the web admin to unlock your account. ");  

			// Send message  
			Transport.send(message);  
			System.out.println("Message Sent Successfully....");  

		}catch (MessagingException mex) {//mex.printStackTrace(); 
			 mex.printStackTrace();
			 }
	}  //end of lockMail
	
	public void unlockMail(String name,String email,String pass){  
		String to =email;//change accordingly  
		String from = "optimum.batch5@gmail.com";
		String passwordEmail = "Optimum2018";
		String host ="smtp.gmail.com";//or IP address  

		//Get the session object  
		Properties properties = System.getProperties();  
		properties.setProperty("mail.smtp.host", host);  // SMTP Host
		properties.put("mail.smtp.socketFactory.port", "465"); // SSL Port
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL Factory Class
		properties.put("mail.smtp.auth", "true"); // Enabling SMTP Authentication		
		properties.put("mail.smtp.port", "465");
		
		Authenticator authenticator = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, passwordEmail);
			}
		};
		
		Session session = Session.getDefaultInstance(properties,authenticator); 


		//compose the message  
		try{  
			MimeMessage message = new MimeMessage(session);  
			message.setFrom(new InternetAddress(from));  
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
			message.setSubject("Unlocked account");  
			message.setText("Dear "+name+", your account has been unlocked. Your new password is "+pass+ ".");  

			// Send message  
			Transport.send(message);  
			System.out.println("Message Sent Successfully....");  

		}catch (MessagingException mex) {//mex.printStackTrace(); 
			 mex.printStackTrace();
			 }
	}  //end of lockMail
}  