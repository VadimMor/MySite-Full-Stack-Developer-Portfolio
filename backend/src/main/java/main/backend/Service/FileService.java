package main.backend.Service;

import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    /**
     * Save file (image or video) in db
     * @param file полностью файл
     * @return id сохраненного файла
     */
    String uploadFile(MultipartFile file) throws IOException;

    /**
     * Удаление файла по id
     * @param id файла
     */
    void deleteFile(String id);

    /**
     * Вывод файла по id
     * @param id файла
     * @return grid файла
     */
    GridFsResource getFileResource(String id);
}
