package com.configme.service;

import com.configme.domain.User;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import tech.jhipster.config.JHipsterProperties;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private static final String CHAR_TO_REPLACE = "[\n\r\t]";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    public MailService(
        JHipsterProperties jHipsterProperties,
        JavaMailSender javaMailSender,
        MessageSource messageSource,
        SpringTemplateEngine templateEngine
    ) {
        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String paramTo, String paramSubject, String paramContent, boolean paramIsMultipart, boolean paramIsHtml) {
        String to = paramTo.replaceAll(CHAR_TO_REPLACE, "_");
        String subject = paramSubject.replaceAll(CHAR_TO_REPLACE, "_");
        String content = paramContent.replaceAll(CHAR_TO_REPLACE, "_");
        Boolean isMultipart = paramIsMultipart;
        Boolean isHtml = paramIsHtml;

        log.debug(
            "Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart,
            isHtml,
            to,
            subject,
            content
        );

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        log.debug("1");
        log.debug(user.getLangKey());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        log.debug("2");
        Context context = new Context(locale);
        log.debug("3");
        context.setVariable(USER, user);
        log.debug("4");
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        log.debug("5");
        String content = templateEngine.process(templateName, context);
        log.debug("6");
        String subject = messageSource.getMessage(titleKey, null, locale);
        log.debug("7");
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(User user) {
        String emailUser = user.getEmail().replaceAll(CHAR_TO_REPLACE, "_");
        log.debug("Sending activation email to '{}'", emailUser);
        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(User user) {
        String emailUser = user.getEmail().replaceAll(CHAR_TO_REPLACE, "_");
        log.debug("Sending creation email to '{}'", emailUser);
        sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        String emailUser = user.getEmail().replaceAll(CHAR_TO_REPLACE, "_");
        log.debug("Sending password reset email to '{}'", emailUser);
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
    }
}
