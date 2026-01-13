package main.backend.Service.Impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Entity.Post;
import main.backend.Repository.PostRepository;
import main.backend.Service.CategoryService;
import main.backend.Service.MailService;
import main.backend.Service.PostService;
import main.backend.Service.UserMailService;
import main.backend.Utils.MarkdownUtils;
import main.backend.dto.Request.PostRequest;
import main.backend.dto.Response.*;
import main.backend.enums.SortVisibility;
import main.backend.enums.StatusVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@NoArgsConstructor
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MarkdownUtils markdownUtils;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserMailService userMailService;

    /**
     * Создание поста и сохранение в бд
     * @param postRequest информация о посте
     * @return статус о успешном создании поста
     */
    @Override
    public PostResponse createPost(PostRequest postRequest) {
        log.trace("Search post by name - {}", postRequest.getName());
        Post post = postRepository.getByName(postRequest.getName());

        if (post != null) {
            log.error("The post has already been created - {}", postRequest.getName());
            throw new RuntimeException("The post has already been created");
        }

        log.trace("Create now timestamp");
        Timestamp now = Timestamp.from(Instant.now());

        log.trace("Create post by name - {}", postRequest.getName());
        Post newPost = new Post(
             now,
             now,
             StatusVisibility.DEVELOPMENT,
                postRequest.getName(),
                postRequest.getDescription(),
                postRequest.getUrl(),
                markdownUtils.countWordsInString(
                        postRequest.getDescription()
                )
        );

        postRequest.getCategories().forEach(categoryRequest -> {
            log.trace("Add post by name - {}", categoryRequest.getName());
            newPost.addCategorySet(
                    categoryService.getCategoryByName(categoryRequest.getName())
            );
        });


        log.trace("Save post by name - {}", newPost.getName());
        postRepository.saveAndFlush(newPost);

        return new PostResponse(
                newPost.getName(),
                StatusVisibility.DEVELOPMENT.getStatus()
        );
    }

    /**
     * Изменение информации поста и сохранение поста
     * @param postRequest информация о посте
     * @param id поста
     * @return статус о успешном изменении поста
     */
    @Override
    public PostResponse updatePost(PostRequest postRequest, Long id) {
        log.trace("Search post by id - {}", id);
        Post post = postRepository.getReferenceById(id);

        if (post == null) {
            log.error("The post not found - {}", id);
            throw new RuntimeException("The post not found");
        }

        log.trace("Create now timestamp");
        Timestamp now = Timestamp.from(Instant.now());

        log.trace("Update post by id - {}", post.getId());
        post.setName(postRequest.getName());
        post.setDescription(postRequest.getDescription());
        post.setUrl(postRequest.getUrl());
        post.setLength(
                markdownUtils.countWordsInString(
                        postRequest.getDescription()
                )
        );
        post.setUpdateDate(now);

        log.trace("Clear categories post by id - {}", post.getId());
        post.setCategories(new HashSet<>());

        postRequest.getCategories().forEach(categoryRequest -> {
            log.trace("Add post by name - {}", categoryRequest.getName());
            post.addCategorySet(
                    categoryService.getCategoryByName(categoryRequest.getName())
            );
        });

        log.trace("Save post by name - {}", post.getName());
        postRepository.save(post);

        return new PostResponse(
                post.getName(),
                StatusVisibility.UPDATE.getStatus()
        );
    }

    /**
     * Обновление статуса поста
     * @param id поста
     * @param status изменный статус поста
     * @return  статус о успешном изменении статуса поста
     */
    @Override
    public PostResponse updateStatusPost(Long id, String status) {
        log.trace("Search post by id - {}", id);
        Post post = postRepository.getReferenceById(id);

        if (post == null) {
            log.error("The post not found - {}", id);
            throw new RuntimeException("The post not found");
        }

        log.trace("Create now timestamp");
        Timestamp now = Timestamp.from(Instant.now());

        log.trace("Update post by id - {}", post.getId());
        post.setStatus(StatusVisibility.fromStatus(status));
        post.setUpdateDate(now);

        log.trace("Save post by name - {}", post.getName());
        postRepository.save(post);

        if (post.getStatus() == StatusVisibility.OPEN) {
            log.trace("Send mail post - {}", post.getName());
            String preview = markdownUtils.getCleanPreview(post.getDescription(), 120);

            String fullDescription = post.getDescription();

            try {
                post.setDescription(preview);
                userMailService.sendMailPostByUsers(post);
            } finally {
                post.setDescription(fullDescription);
            }
        }

        return new PostResponse(
                post.getName(),
                post.getStatus().getStatus()
        );
    }

    /**
     * Вывод информации поста
     * @param id поста
     * @return информация поста
     */
    @Override
    public FullPostResponse getInfoPost(Long id) {
        log.trace("Search post by id - {}", id);
        Post post = postRepository.getReferenceById(id);

        if (post == null || post.getStatus() != StatusVisibility.OPEN) {
            log.error("The post not found - {}", id);
            throw new RuntimeException("The post not found");
        }

        return new FullPostResponse(
                post.getName(),
                post.getDescription(),
                post.getUrl(),
                post.getLength(),
                post.getCreateDate(),
                post.getCategories().stream().map(category -> new FullCategoryResponse(
                        category.getName()
                )).toArray(FullCategoryResponse[]::new)
        );
    }

    /**
     * Вывод массива постов по страницам и сортировке
     * @param page номер страницы
     * @param sort сортировка
     * @return массив постов
     */
    @Override
    public ShortPostResponse[] getMassivePost(Integer page, String sort) {
        log.trace("Getting posts with sort: '{}' and page: {}", sort, page);
        List<Post> postList = null;

        if (sort != null && page == null) {
            String[] sortMassive = sort.split("_");
            log.trace("Sorting requested without page: field='{}', direction='{}', using page 0", sortMassive[0], sortMassive[1]);

            if (Objects.equals(sortMassive[0], "date")) {
                postList = findPostsBySortAndPage(
                        "createDate",
                        sortMassive[1],
                        0
                );
            } else if (Objects.equals(sortMassive[0], "name")) {
                postList = findPostsBySortAndPage(
                        "name",
                        sortMassive[1],
                        0
                );
            }
        } else if (sort == null && page != null) {
            log.trace("Paging requested without sort: calculating offset {}", page * 10);
            postList = postRepository.findPostsByPage(page * 10);
        } else if (sort != null && page != null) {
            String[] sortMassive = sort.split("_");
            log.trace("Sorting and paging requested: field='{}', direction='{}', page={}", sortMassive[0], sortMassive[1], page);

            if (Objects.equals(sortMassive[0], "date")) {
                postList = findPostsBySortAndPage(
                        "createDate",
                        sortMassive[1],
                        page
                );
            } else if (Objects.equals(sortMassive[0], "name")) {
                postList = findPostsBySortAndPage(
                        "name",
                        sortMassive[1],
                        page
                );
            }
        } else {
            log.trace("No parameters provided, defaulting to page 0 without sort.");
            postList = postRepository.findPostsByPage(0);
        }

        if (postList == null) {
            log.trace("No posts found (empty list), returning empty array.");
            return new ShortPostResponse[0];
        }

        log.trace("Found {} posts. Mapping to InfoAnArrayProject[].", postList.size());
        return postList.stream().map(
                post -> new ShortPostResponse(
                        post.getId(),
                        post.getName(),
                        post.getCreateDate(),
                        post.getCategories().stream().map(
                                category -> new FullCategoryResponse(
                                        category.getName()
                                )
                        ).toArray(FullCategoryResponse[]::new)
                )
        ).toArray(ShortPostResponse[]::new);
    }


    private List<Post> findPostsBySortAndPage(String name, String status, Integer page) {
        log.trace("Preparing query: sort by '{}', direction '{}', page {}", name, status, page);

        if (SortVisibility.fromStatus(status) == SortVisibility.DOWN) {
            log.trace("Executing DOWN query (ASC) on field '{}' (Page: {})", name, page);
            Pageable pageable = PageRequest.of(page, 20, Sort.by(name).ascending());
            return postRepository.findDownPostsBySortAndPage(pageable);
        } else {
            log.trace("Executing UP query (DESC) on field '{}' (Page: {})", name, page);
            Pageable pageable = PageRequest.of(page, 10, Sort.by(name).descending());
            return postRepository.findUpPostsBySortAndPage(pageable);
        }
    }
}
