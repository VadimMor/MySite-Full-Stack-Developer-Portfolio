package main.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Service.CategoryService;
import main.backend.dto.Request.CategoryRequest;
import main.backend.dto.Request.SkillRequest;
import main.backend.dto.Response.CategoryFullResponse;
import main.backend.dto.Response.CategoryResponse;
import main.backend.dto.Response.SkillResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api-v0.2/post")
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/category")
    @Operation(
            summary = "Вывод всех категорий для пользователей",
            description = "Выводит все категории из бд для пользователя"
    )
    public ResponseEntity<CategoryFullResponse[]> getAllCategory() {
        CategoryFullResponse[] categoryFullResponses = categoryService.getAllCategory();
        return ResponseEntity.ok(categoryFullResponses);
    }

    @PostMapping("/category")
    @Operation(
            summary = "Создание категории",
            description = "Создает категорию и сохраняет в бд"
    )
    public ResponseEntity<CategoryResponse> createCategory(
            @Parameter(description = "Информация о категории", required = true)
            @RequestBody CategoryRequest categoryRequest
    ) {
        CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponse);
    }

    @PutMapping("/category")
    @Operation(
            summary = "Изменение категории",
            description = "Изменяет категорию и сохраняет в бд"
    )
    public ResponseEntity<CategoryResponse> updateCategory(
            @Parameter(description = "Информация о категории", required = true)
            @RequestBody CategoryRequest categoryRequest,
            @Parameter(description = "id категории", required = true, example = "1L")
            @RequestParam Long id
    ) {
        CategoryResponse categoryResponse = categoryService.updateCategory(categoryRequest, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponse);
    }

    @PutMapping("/category/status")
    @Operation(
            summary = "Изменение статуса категории",
            description = "Изменяет статус категории и сохраняет в бд"
    )
    public ResponseEntity<CategoryResponse> updateCategory(
            @Parameter(description = "Статус категории", required = true, example = "OPEN")
            @RequestParam String status,
            @Parameter(description = "id категории", required = true, example = "1L")
            @RequestParam Long id
    ) {
        CategoryResponse categoryResponse = categoryService.updateStatusCategory(id, status);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponse);
    }
}
