package main.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Service.CategoryService;
import main.backend.Service.PostService;
import main.backend.dto.Request.CategoryRequest;
import main.backend.dto.Request.PostRequest;
import main.backend.dto.Response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api-v0.2/post")
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PostService postService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Вывод информации о посте",
            description = "Выводит информацию о посте по id"
    )
    public ResponseEntity<FullPostResponse> getPost(
            @Parameter(description = "id поста", required = true, example = "1")
            @PathVariable("id") Long id
    ) {
        FullPostResponse fullPostResponse = postService.getInfoPost(id);
        return ResponseEntity.ok(fullPostResponse);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Вывод массива постов для пользователей",
            description = "Выводит массив постов для пользователей по сортировке и страницам"
    )
    public ResponseEntity<ShortPostResponse[]> getMassivePost(
            @Parameter(description = "Сортировка по дате", required = false, example = "example_STATUS")
            @RequestParam(name = "sort", required = false) String sort,
            @Parameter(description = "Страница для вывода проектов", required = false, example = "1")
            @RequestParam(name = "page", required = false) Integer page
    ) {
        ShortPostResponse[] shortPostResponses = postService.getMassivePost(page, sort);
        return ResponseEntity.ok(shortPostResponses);
    }

    @PostMapping
    @Operation(
            summary = "Создание поста",
            description = "Создает пост и сохраняет в бд"
    )
    public ResponseEntity<PostResponse> createPost(
            @Parameter(description = "Информация поста", required = true)
            @RequestBody PostRequest postRequest
    ) {
        PostResponse postResponse = postService.createPost(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Изменение информации поста",
            description = "Изменяет информацию поста и сохраняет в бд"
    )
    public ResponseEntity<PostResponse> updatePost(
            @Parameter(description = "id поста", required = true, example = "1")
            @PathVariable("id") Long id,
            @Parameter(description = "Информация поста", required = true)
            @RequestBody PostRequest postRequest
    ) {
        PostResponse postResponse = postService.updatePost(postRequest, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(postResponse);
    }

    @PutMapping("/status")
    @Operation(
            summary = "Изменение статуса поста",
            description = "Изменяет статус поста и сохраняет в бд"
    )
    public ResponseEntity<PostResponse> updateStatusPost(
            @Parameter(description = "Статус категории", required = true, example = "OPEN")
            @RequestParam String status,
            @Parameter(description = "id категории", required = true, example = "1")
            @RequestParam Long id
    ) {
        PostResponse postResponse = postService.updateStatusPost(id, status);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(postResponse);
    }

    @GetMapping("/category")
    @Operation(
            summary = "Вывод всех категорий для пользователей",
            description = "Выводит все категории из бд для пользователя"
    )
    public ResponseEntity<FullCategoryResponse[]> getAllCategory() {
        FullCategoryResponse[] fullCategoryResponses = categoryService.getAllCategory();
        return ResponseEntity.ok(fullCategoryResponses);
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
            @Parameter(description = "id категории", required = true, example = "1")
            @RequestParam Long id
    ) {
        CategoryResponse categoryResponse = categoryService.updateStatusCategory(id, status);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponse);
    }
}
