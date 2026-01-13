package main.backend.Service;

import main.backend.Entity.Post;
import main.backend.Entity.UserMail;
import org.springframework.stereotype.Service;

@Service
public interface UserMailService {
    /**
     * Поиск пользователя по почте
     * @param email почта пользователя
     * @return информация пользователя
     */
    UserMail getuserMail(String email);

    /**
     * Поиск пользователя по почте и подтверждение почты
     * @param email почта пользователя
     * @return информация пользователя
     */
    UserMail confirmationUser(String email);

    /**
     * Создание и сохранение пользователя в бд
     * @param email почта пользователя
     * @param name имя пользователя
     */
    void createUserMail(String email, String name);

    /**
     * Удаляет пользователей, которые не подтвердили почту
     */
    void deleteUserMailNotConfirmed();

    /**
     * Отпарвка письма о новом посте
     * @param newPost новый пост
     */
    void sendMailPostByUsers(Post newPost);
}
