package main.backend.Service;

import jakarta.mail.MessagingException;
import main.backend.Entity.AbstractEmailContext;
import main.backend.Entity.Post;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
public interface MailService {

    /**
     * Сохраняет данные пользователя для отправки писем и отправляет письмо подтверждения
     * @param email почта пользователя
     * @param name Имя и фамилия пользователя
     * @throws MessagingException
     */
    void subscribeUser(String email, String name) throws MessagingException;

    /**
     * Изменяет статус пользователя на "CONFIRMATION"
     * @param email почта пользователя
     */
    void confirmationUser(String email) throws MessagingException;

    /**
     * Удаляет пользователей, которые не подтвердили почту
     */
    void deleteUserMailNotConfirmed();

    /**
     * Отпарвка письма о новом посте
     * @param emailUser новый пост
     * @param newPost новый пост
     */
    void sendMailPost(String emailUser, Post newPost) throws MessagingException;
}
