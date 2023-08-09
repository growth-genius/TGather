package com.ysdeveloper.tgather.modules.mail.service;

import com.ysdeveloper.tgather.infra.mail.EmailMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


public interface EmailService {

    void sendEmail(EmailMessage emailMessage);

    default void sendHttpEmail(EmailMessage emailMessage) throws MessagingException {
        MimeMessage mimeMessage = getJavaMailSender().createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        mimeMessageHelper.setTo(emailMessage.getTo());
        mimeMessageHelper.setSubject(emailMessage.getSubject());
        mimeMessageHelper.setText(setContext(emailMessage.getSubject(), emailMessage.getMessage(), emailMessage.getHtmlFileName()), true);
        getJavaMailSender().send(mimeMessage);
    }

    private String setContext(String subject, String message, String htmlCode) {
        Context context = new Context();
        context.setVariable("subject", subject);
        context.setVariable("message", message);
        return getSpringTemplateEngine().process(htmlCode, context);
    }

    JavaMailSender getJavaMailSender();

    SpringTemplateEngine getSpringTemplateEngine();

}
