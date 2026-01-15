package main.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api-v0.2/file")
@RequiredArgsConstructor
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping
    @Operation(
            summary = "Сохранение файла",
            description = "Сохраняет файл в бд и выводит id файла"
    )
    public ResponseEntity<String> upload(@RequestPart("file") MultipartFile file) {
        try {
            String fileId = fileService.uploadFile(file);
            return ResponseEntity.ok(fileId);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Ошибка загрузки");
        }
    }

    @PostMapping("/all")
    @Operation(
            summary = "Сохранение все файлы",
            description = "Сохраняет все файлы в бд и выводит список id файлов"
    )
    public ResponseEntity<List<String>> uploadAll(@RequestPart("files") List<MultipartFile> files) {
        List<String> idFiles = new ArrayList<>();

        files.stream().forEach(file -> {
            try {
                String fileId = fileService.uploadFile(file);
                idFiles.add(fileId);
            } catch (IOException e) {
                idFiles.stream().forEach(id -> {
                    fileService.deleteFile(id);
                });
                throw new RuntimeException("Ошибка загрузки");
            }
        });

        return ResponseEntity.ok(idFiles);
    }

    @DeleteMapping
    @Operation(
            summary = "Удаление файла",
            description = "Удаляет файл из бд по id"
    )
    public void delete(@RequestParam("id") String id) {
        fileService.deleteFile(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> viewFile(@PathVariable String id) {
        GridFsResource resource = fileService.getFileResource(id);

        String contentType = resource.getContentType();
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                // "inline" позволяет браузеру отобразить файл (в плеере или как картинку)
                // а не просто скачивать его
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
