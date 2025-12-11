package main.backend.Service;

import main.backend.Entity.Category;
import main.backend.dto.Request.CategoryRequest;
import main.backend.dto.Response.CategoryFullResponse;
import main.backend.dto.Response.CategoryResponse;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    /**
     * Создает и сохраняет категорию в бд
     * @param categoryRequest информация категории
     * @return информации о успешном сохранении категории
     */
    CategoryResponse createCategory(CategoryRequest categoryRequest);

    /**
     * Обновление и сохранение категории в бд
     * @param categoryRequest обноваленная информация категории
     * @param id категории
     * @return информация о успешном обновление категории
     */
    CategoryResponse updateCategory(CategoryRequest categoryRequest, Long id);

    /**
     * Вывод всех категорий
     * @return массив категорий
     */
    CategoryFullResponse[] getAllCategory();

    /**
     * Обновление статуса  категории
     * @param id категории
     * @param status статус для категории
     * @return статус успешном обновлении статуса категории
     */
    CategoryResponse updateStatusCategory(Long id, String status);

    /**
     * Вывод категории по названию
     * @param name название категории
     * @return категория
     */
    Category getCategoryByName(String name);
}
