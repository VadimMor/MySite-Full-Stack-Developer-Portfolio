package main.backend.Service.Impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Entity.Post;
import main.backend.Entity.UserMail;
import main.backend.Service.MailService;
import main.backend.Service.UserMailService;
import main.backend.enums.StatusUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
public class MailServiceImpl implements MailService {
    @Value("${spring.mail.email}")
    private String email;

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    @Lazy
    private UserMailService userMailService;

    /**
     * Сохраняет данные пользователя для отправки писем и отправляет письмо подтверждения
     * @param emailUser почта пользователя
     * @param name Имя и фамилия пользователя
     * @throws MessagingException
     */
    @Override
    public void subscribeUser(String emailUser, String name) throws MessagingException {
        log.trace("Entering subscribeUser for email: {}", emailUser);
        UserMail userMail = userMailService.getuserMail(emailUser);

        if (userMail != null) {
            if (userMail.getStatus() == StatusUser.CREATED) {
                log.trace("User exists with status CREATED, resending verification");
                sendVerificationMail(emailUser, name);
                return;
            } else {
                log.error("The user has already been created and confirmed - {}", emailUser);
                throw new RuntimeException("The user has already been created");
            }
        }

        log.trace("Creating new UserMail entry for: {}", emailUser);
        userMailService.createUserMail(emailUser, name);
        sendVerificationMail(emailUser, name);
    }

    /**
     * Изменяет статус пользователя на "CONFIRMATION"
     * @param emailUser почта пользователя
     */
    public void confirmationUser(String emailUser) throws MessagingException {
        log.trace("Entering confirmationUser for email: {}", emailUser);
        UserMail userMail = userMailService.confirmationUser(emailUser);

        if (userMail == null || userMail.getStatus() != StatusUser.CONFIRMATION) {
            log.error("The user not found or user banned - {}", emailUser);
            throw new RuntimeException("The user not found");
        }

        log.trace("User confirmed successfully, sending confirmation success mail");
        sendConfirmationMail(emailUser, userMail.getName());
    }

    /**
     * Удаляет пользователей, которые не подтвердили почту
     */
    @Override
    public void deleteUserMailNotConfirmed() {
        log.trace("Triggering scheduled deletion of unconfirmed users");
        userMailService.deleteUserMailNotConfirmed();
    }

    /**
     * Отпарвка письма о новом посте
     * @param emailUser новый пост
     * @param newPost новый пост
     */
    @Async
    @Override
    public void sendMailPost(String emailUser, Post newPost) throws MessagingException {
        log.trace("Async: Preparing 'New Post' email for: {}", emailUser);
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariable("title", newPost.getName());
        context.setVariable("text", newPost.getDescription());
        context.setVariable("url", "https://animego.me/anime/magicheskaya-bitva-smertelnaya-migraciya-2903");

        String emailContent = templateEngine.process("newPost", context);

        mimeMessageHelper.setTo(emailUser);
        mimeMessageHelper.setSubject("Новый пост");
        mimeMessageHelper.setFrom(email);
        mimeMessageHelper.setText(emailContent, true);

        log.trace("Sending 'New Post' email via JavaMailSender to: {}", emailUser);
        emailSender.send(message);
    }


    private void sendVerificationMail(String emailUser, String name) throws MessagingException {
        log.trace("Preparing verification email for: {}", emailUser);
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("url", "https://animego.me/anime/magicheskaya-bitva-smertelnaya-migraciya-2903");

        String emailContent = templateEngine.process("verification", context);

        mimeMessageHelper.setTo(emailUser);
        mimeMessageHelper.setSubject("Подтверждение почты");
        mimeMessageHelper.setFrom(email);
        mimeMessageHelper.setText(emailContent, true);

        log.trace("Sending verification mail to: {}", emailUser);
        emailSender.send(message);
    }

    private void sendConfirmationMail(String emailUser, String name) throws MessagingException {
        log.trace("Preparing final confirmation email for: {}", emailUser);
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariable("name", name);

        String emailContent = templateEngine.process("confirmation", context);

        mimeMessageHelper.setTo(emailUser);
        mimeMessageHelper.setSubject("Спасибо за подписку");
        mimeMessageHelper.setFrom(email);
        mimeMessageHelper.setText(emailContent, true);

        log.trace("Sending confirmation success mail to: {}", emailUser);
        emailSender.send(message);
    }
}
