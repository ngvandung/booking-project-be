/**
 * 
 */
package com.booking.util;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * @author ddung
 *
 */
public class EmailSender {
	private static final String MY_EMAIL = "d.dungg98@gmail.com";
	private static final String MY_PASSWORD = "@dungnv0910";

	private static final Logger _log = Logger.getLogger(EmailSender.class);

	public static boolean sendEmail(String toEmail, String name) {
		boolean result = false;
		try {
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

			mailSender.setHost("smtp.gmail.com");
			mailSender.setPort(587);
			mailSender.setUsername(MY_EMAIL);
			mailSender.setPassword(MY_PASSWORD);

			Properties javaMailProperties = new Properties();
			javaMailProperties.put("mail.smtp.auth", "true");
			javaMailProperties.put("mail.transport.protocol", "smtp");
			javaMailProperties.put("mail.debug", "true");
			javaMailProperties.put("mail.smtp.starttls.enable", "true");

			mailSender.setJavaMailProperties(javaMailProperties);
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
					message.setTo(toEmail);
					message.setFrom(mailSender.getUsername());
					message.setSubject("Booking Infomation");
					//message.setBcc(mailSender.getUsername());
					message.setText("Booking successfully: " + name, true);
				}
			};
			mailSender.send(preparator);
			result = true;
		} catch (Exception e) {
			_log.error(e);
		}

		return result;
	}
}
