package main.backend.Service.Impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Entity.Category;
import main.backend.Repository.CategoryRepository;
import main.backend.Service.CategoryService;
import main.backend.dto.Request.CategoryRequest;
import main.backend.dto.Response.FullCategoryResponse;
import main.backend.dto.Response.CategoryResponse;
import main.backend.enums.StatusVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@NoArgsConstructor
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Создает и сохраняет категорию
     * @param categoryRequest информация категории
     * @return информации о успешном сохранении категории
     */
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        log.trace("Search category by name - {}", categoryRequest.getName());
        Category category = categoryRepository.getByName(categoryRequest.getName());

        if (category != null) {
            log.error("The category has already been created - {}", categoryRequest.getName());
            throw new RuntimeException("The category has already been created");
        }

        log.trace("Create now timestamp");
        Timestamp now = Timestamp.from(Instant.now());

        log.trace("Create category by name - {}", categoryRequest.getName());
        category = new Category(
                now,
                now,
                categoryRequest.getName(),
                StatusVisibility.DEVELOPMENT
        );

        log.trace("Save category by name - {}", category.getName());
        categoryRepository.saveAndFlush(category);

        return new CategoryResponse(
                category.getName(),
                category.getStatus().getStatus()
        );
    }

    /**
     * Обновление и сохранение категории в бд
     * @param categoryRequest обноваленная информация категории
     * @param id категории
     * @return информация о успешном обновление категории
     */
    @Override
    public CategoryResponse updateCategory(CategoryRequest categoryRequest, Long id) {
        log.trace("Search category by id - {}", id);
        Category category = categoryRepository.getReferenceById(id);

        if (category == null) {
            log.error("The category not found - {}", id);
            throw new RuntimeException("The category not found");
        }

        log.trace("Create now timestamp");
        Timestamp now = Timestamp.from(Instant.now());

        log.trace("Update category by id - {}", category.getId());
        category.setUpdateDate(now);
        category.setName(categoryRequest.getName());

        log.trace("Save category by name - {}", category.getId());
        categoryRepository.save(category);

        return new CategoryResponse(
                category.getName(),
                StatusVisibility.UPDATE.getStatus()
        );
    }

    /**
     * Выводит все категории
     * @return массив категорий
     */
    @Override
    public FullCategoryResponse[] getAllCategory() {
        log.trace("Search all category");
        List<Category> categoryList = categoryRepository.findAllByStatus(StatusVisibility.OPEN);
        return categoryList.stream().map(category -> new FullCategoryResponse(
                category.getName()
        )).toArray(FullCategoryResponse[]::new);
    }

    /**
     * Обновление статуса  категории
     * @param id категории
     * @param status статус для категории
     * @return статус успешном обновлении статуса категории
     */
    @Override
    public CategoryResponse updateStatusCategory(Long id, String status) {
        log.trace("Search category by id - {}", id);
        Category category = categoryRepository.getReferenceById(id);

        if (category == null) {
            log.error("The category not found - {}", id);
            throw new RuntimeException("The category not found");
        }

        log.trace("Create now timestamp");
        Timestamp now = Timestamp.from(Instant.now());

        log.trace("Update category by id - {}", category.getId());
        category.setUpdateDate(now);
        category.setStatus(
                StatusVisibility.fromStatus(status)
        );

        log.trace("Save category by name - {}", category.getId());
        categoryRepository.save(category);

        return new CategoryResponse(
                category.getName(),
                StatusVisibility.UPDATE.getStatus()
        );
    }

    /**
     * Вывод категории по названию
     * @param name название категории
     * @return категория
     */
    @Override
    public Category getCategoryByName(String name) {
        log.trace("Search category by name - {}", name);
        return categoryRepository.getByName(name);
    }


}