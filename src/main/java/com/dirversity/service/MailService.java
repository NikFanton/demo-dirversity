package com.dirversity.service;

import com.dirversity.domain.Email;
import com.dirversity.domain.Resource;
import com.dirversity.domain.User;
import io.github.jhipster.config.JHipsterProperties;
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

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.logstash.logback.encoder.org.apache.commons.lang3.ArrayUtils.toArray;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    public static final String BODY = "body";
    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    public MailService(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender,
                       MessageSource messageSource, SpringTemplateEngine templateEngine) {

        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String[] to, String[] cc, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to Users '{}'", Arrays.toString(to));
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to users '{}'", Arrays.toString(to), e);
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(new String[]{user.getEmail()}, null, subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
    }

    @Async
    public void sendResourceEmailToEachUser(Email email, User sender, Locale locale) {
        String[] toEmails = extractToEmails(email);
        String[] ccEmails = extractCCEmails(email);

        Context context = new Context(locale);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        context.setVariable(BODY, email.getBody());
        context.setVariable("resources", email.getResources());
        context.setVariable("sender", sender);
        String content = templateEngine.process("mail/resourceEmail", context);
        String subject = messageSource.getMessage("email.resource.title", null, locale);
        sendEmail(toEmails, ccEmails, subject, content, false, true);
    }

    private String[] extractToEmails(Email email) {
        return extractEmailsAddressesFromUsers(Stream.concat(
            email.getToUsers().stream(),
            email.getToUsersGroups().stream()
                .flatMap(userGroup -> userGroup.getUsers().stream())));
    }

    private String[] extractCCEmails(Email email) {
        return extractEmailsAddressesFromUsers(Stream.concat(
            email.getCcUsers().stream(),
            email.getCcUserGroups().stream()
                .flatMap(userGroup -> userGroup.getUsers().stream())));
    }

    private String[] extractEmailsAddressesFromUsers(Stream<User> userStream) {
        return userStream
            .map(User::getEmail)
            .toArray(String[]::new);
    }
}
