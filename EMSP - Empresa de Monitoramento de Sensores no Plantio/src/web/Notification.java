package web;

import java.security.SecureRandom;

public class Notification {
	private int randomCode;
	
	public int forgotMyPasswordEmail(String email, String name) {
		Email emailNotification = new Email();
        SecureRandom rnd = new SecureRandom();
        randomCode = rnd.nextInt(1_000_000);
        String codeStr = String.format("%06d", randomCode);

        // Corpo do e-mail
        String subject = "Código de verificação — EMSP"; //Nome do email
        String body = Email.buildHtmlBody(name, codeStr);// Corpo todo do email

        // Envia e-mail
        boolean ok = emailNotification.sendEmail(email, subject, body);
        if (!ok) {
            return -1;
        }
        return randomCode;
    }
}
