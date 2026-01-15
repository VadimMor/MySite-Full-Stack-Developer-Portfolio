package main.backend.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Document(collection = "file_metadata")
@NoArgsConstructor
public class File {
    @Id
    private String id;

    private String filename;

    private String contentType;

    private String gridFsId;

    private LocalDateTime uploadDate;

    private LocalDateTime updateDate;

    public File(String filename, String contentType, String gridFsId, LocalDateTime uploadDate, LocalDateTime updateDate) {
        this.filename = filename;
        this.contentType = contentType;
        this.gridFsId = gridFsId;
        this.uploadDate = uploadDate;
        this.updateDate = updateDate;
    }
}
