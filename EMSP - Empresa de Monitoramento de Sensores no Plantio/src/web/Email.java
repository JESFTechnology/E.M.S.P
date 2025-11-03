package web;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Email {
    private String user = "" // Necessita do email do gmail;
    private String pass = "" // Necessita da senha de app do gmail;
    private String host = "smtp.gmail.com";
    private String port = "587";

    public static String buildHtmlBody(String recipientName, String code) {
        return "<!DOCTYPE html>"
                + "<html><body style='font-family: Arial, sans-serif; background-color:#f6f7fb; padding:20px;'>"
                + "<div style='max-width:600px; margin:0 auto; background:#ffffff; padding:24px; border-radius:8px; box-shadow:0 6px 18px rgba(0,0,0,0.06);'>"
                + "<h2 style='color:#111827; margin-bottom:10px;'>Olá " + recipientName + ",</h2>"
                + "<p style='color:#4b5563; font-size:16px;'>Você solicitou um código de verificação. Use-o no aplicativo para prosseguir.</p>"
                + "<div style='text-align:center; margin:24px 0;'>"
                + "<span style='display:inline-block; font-size:32px; letter-spacing:4px; font-weight:bold; padding:12px 24px; background:#f3f4f6; border-radius:6px;'>"
                + code
                + "</span>"
                + "</div>"
                + "<p style='color:#6b7280; font-size:14px;'>O código expira em 10 minutos. Se você não solicitou, ignore este e-mail.</p>"
                + "<hr style='border:none; border-top:1px solid #e5e7eb; margin:20px 0;'/>"
                + "<p style='font-size:12px; color:#9ca3af;'>— EMSP</p>"
                + "</div>"
                + "</body></html>";
    }

    public boolean sendEmail(String to, String subject, String htmlBody) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(htmlBody, "text/html; charset=utf-8");

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
