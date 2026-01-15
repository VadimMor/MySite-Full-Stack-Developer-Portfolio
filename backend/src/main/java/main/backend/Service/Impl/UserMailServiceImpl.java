package main.backend.Service.Impl;

import lombok.extern.slf4j.Slf4j;
import main.backend.Entity.Post;
import main.backend.Entity.UserMail;
import main.backend.Service.MailService;
import main.backend.Service.UserMailService;
import main.backend.Repository.UserMailRepository;
import main.backend.enums.StatusUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
@Service
public class UserMailServiceImpl implements UserMailService {
    @Autowired
    private UserMailRepository userMailRepository;

    @Autowired
    @Lazy
    private MailService mailService;

    /**
     * Поиск пользователя по почте
     * @param email почта пользователя
     * @return информация пользователя
     */
    @Override
    public UserMail getuserMail(String email) {
        log.trace("Entering getuserMail for email: {}", email);
        UserMail user = userMailRepository.getByEmail(email);
        log.trace("Repository returned user: {}", user);
        return user;
    }

    /**
     * Поиск пользователя по почте и подтверждение почты
     * @param email почта пользователя
     * @return информация пользователя
     */
    @Override
    public UserMail confirmationUser(String email) {
        log.trace("Starting confirmation process for email: {}", email);
        UserMail userMail = userMailRepository.getByEmail(email);

        log.trace("Setting status to CONFIRMATION and updating timestamp");
        Timestamp now = Timestamp.from(Instant.now());
        userMail.setStatus(StatusUser.CONFIRMATION);
        userMail.setUpdateDate(now);

        log.trace("Saving updated user to repository");
        return userMailRepository.save(userMail);
    }

    /**
     * Создание и сохранение пользователя в бд
     * @param email почта пользователя
     * @param name имя пользователя
     */
    @Override
    public void createUserMail(String email, String name) {
        log.trace("Attempting to create user: {} with email: {}", name, email);
        UserMail userMail = userMailRepository.getByEmail(email);

        if (userMail != null) {
            log.error("The user has already been created - {}", email);
            throw new RuntimeException("The user has already been created");
        }

        Timestamp now = Timestamp.from(Instant.now());
        userMail = new UserMail(name, email, StatusUser.CREATED, now, now);

        log.trace("Saving new user: {}", userMail);
        userMailRepository.saveAndFlush(userMail);
    }

    /**
     * Удаляет пользователей, которые не подтвердили почту
     */
    @Override
    public void deleteUserMailNotConfirmed() {
        Timestamp oneHourAgo = new Timestamp(System.currentTimeMillis() - (3600 * 1000));
        log.trace("Deleting users with status CREATED before: {}", oneHourAgo);
        userMailRepository.deleteAllByStatusAndDate(StatusUser.CREATED, oneHourAgo);
    }

    /**
     * Отпарвка письма о новом посте
     * @param newPost новый пост
     */
    @Override
    public void sendMailPostByUsers(Post newPost) {
        Integer pageSize = 500;
        log.trace("Starting bulk mail process for post: {}. Page size: {}", newPost.getName(), pageSize);

        Page<UserMail> userPage = userMailRepository.findAllByStatus(PageRequest.of(0, pageSize), StatusUser.CONFIRMATION);
        Integer totalPages = userPage.getTotalPages();
        log.trace("Total pages to process: {}", totalPages);

        for (Integer i = 0; i < totalPages; i++) {
            log.trace("Processing page {} of {}", i + 1, totalPages);
            if (i > 0) {
                userPage = userMailRepository.findAllByStatus(PageRequest.of(i, pageSize), StatusUser.CONFIRMATION);
            }

            userPage.forEach(user -> {
                log.trace("Triggering mail send for user: {}", user.getEmail());
                try {
                    mailService.sendMailPost(user.getEmail(), newPost);
                } catch (Exception e) {
                    log.error("Failed to send email to {}: {}", user.getEmail(), e.getMessage());
                }
            });

            try {
                log.trace("Sleeping for 100ms after page {} to avoid overload", i + 1);
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }
        log.trace("Bulk mail process finished");
    }
}
