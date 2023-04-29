package edu.tcu.cs.superfrogscheduler.notification;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class MailService {

    private final JavaMailSenderImpl mailSender;

    private final TemplateEngine templateEngine;

    public MailService(JavaMailSenderImpl mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    private void sendEmail(String to, String subject, String templateName, Context context) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);

        String htmlContent = templateEngine.process(templateName, context);

        helper.setText(htmlContent, true);

        // Add image attachment to the email
        ClassPathResource imageResource = new ClassPathResource("/static/images/logo.png");
        helper.addInline("logo", imageResource);

        mailSender.send(message);
    }

    public void sendCreateAccountMail(String name, String email, String password) throws MessagingException {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("username", email);
        context.setVariable("password", password);

        sendEmail(email, "Super Frog Account Created", "email-template", context);
    }
}
