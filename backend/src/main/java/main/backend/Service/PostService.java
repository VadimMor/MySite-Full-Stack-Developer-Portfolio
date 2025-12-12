package main.backend.Service;

import main.backend.dto.Request.PostRequest;
import main.backend.dto.Response.FullPostResponse;
import main.backend.dto.Response.PostResponse;
import main.backend.dto.Response.ShortPostResponse;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    /**
     * Создание поста и сохранение в бд
     * @param postRequest информация о посте
     * @return статус о успешном создании поста
     */
    PostResponse createPost(PostRequest postRequest);

    /**
     * Изменение информации поста и сохранение поста
     * @param postRequest информация о посте
     * @param id поста
     * @return статус о успешном изменении поста
     */
    PostResponse updatePost(PostRequest postRequest, Long id);

    /**
     * Обновление статуса поста
     * @param id поста
     * @param status изменный статус поста
     * @return  статус о успешном изменении статуса поста
     */
    PostResponse updateStatusPost(Long id, String status);

    /**
     * Вывод информации поста
     * @param id поста
     * @return информация поста
     */
    FullPostResponse getInfoPost(Long id);

    /**
     * Вывод массива постов по страницам и сортировке
     * @param page номер страницы
     * @param sort сортировка
     * @return массив постов
     */
    ShortPostResponse[] getMassivePost(Integer page, String sort);
}
