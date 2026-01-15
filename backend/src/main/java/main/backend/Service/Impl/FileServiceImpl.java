package main.backend.Service.Impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Entity.File;
import main.backend.Repository.FileRepository;
import main.backend.Service.FileService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Service
@NoArgsConstructor
@AllArgsConstructor
public class FileServiceImpl implements FileService {
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private FileRepository fileRepository;

    /**
     * Save file (image or video) in db
     * @param file полностью файл
     * @return id сохраненного файла
     */
    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        log.trace("Preparing GridFS inner metadata for file: {}", file.getOriginalFilename());
        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize", file.getSize());
        metadata.put("contentType", file.getContentType());

        log.trace("Storing binary content in GridFS...");
        ObjectId id = gridFsTemplate.store(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType(),
                metadata
        );

        log.trace("Create now localdatetime");
        LocalDateTime now = LocalDateTime.now();

        log.trace("Create file by name - {}", file.getOriginalFilename());
        File fileMetadata = new File(
                file.getOriginalFilename(),
                file.getContentType(),
                id.toString(),
                now,
                now
        );

        log.trace("Save file by name - {}", file.getOriginalFilename());
        fileRepository.save(fileMetadata);

        return fileMetadata.getId();
    }

    /**
     * Удаление файла по id
     * @param id файла
     */
    @Override
    public void deleteFile(String id) {
        log.trace("Search file - {}", id);
        File metadata = fileRepository.findById(id)
                .orElseThrow(() ->{
                    log.error("The file not found - {}", id);
                    return new RuntimeException("The file not found");
                });

        String gridFsId =  metadata.getGridFsId();

        try {
            log.trace("Attempting to delete binary content from GridFS with ID: {}", gridFsId);
            gridFsTemplate.delete(new Query(Criteria.where("_id").is(gridFsId)));
        } catch (Exception e) {
            log.error("Failed to delete binary content from GridFS for ID: {}", gridFsId, e);
            throw new RuntimeException("Error deleting physical file");
        }

        fileRepository.deleteById(id);
    }

    /**
     * Вывод файла по id
     * @param id файла
     * @return grid файла
     */
    @Override
    public GridFsResource getFileResource(String id) {
        File metadata = fileRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Metadata not found for ID: {}", id);
                    return new RuntimeException("File not found");
                });

        GridFSFile gridFSFile = gridFsTemplate.findOne(
                new Query(Criteria.where("_id").is(metadata.getGridFsId()))
        );

        if (gridFSFile == null) {
            log.error("Binary content not found in GridFS for ID: {}", metadata.getGridFsId());
            throw new RuntimeException("Binary content not found");
        }

        return gridFsTemplate.getResource(gridFSFile);
    }
}
