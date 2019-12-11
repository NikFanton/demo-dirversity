package com.dirversity.service.impl;

import com.dirversity.service.CloudStorageService;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class GoogleDriveServiceImpl implements CloudStorageService {
    private final Logger log = LoggerFactory.getLogger(GoogleDriveServiceImpl.class);

    private final Drive drive;

    public GoogleDriveServiceImpl(Drive drive) {
        this.drive = drive;
    }

    private java.io.File convertBytesToFile(byte[] bytes) throws IOException {
        String fileName = "upload.tmp";
//        java.io.File file = new java.io.File("/Users/nikita/Projects/diploma/demo-dirversity/src/main/resources/" + fileName);
        java.io.File file = new java.io.File("src/main/resources/" + fileName);
        OutputStream os = new FileOutputStream(file);
        os.write(bytes);
        os.close();
        return file;
    }

    @Override
    public void uploadFileData(String dataContentType, byte[] data) {
        try {
            File fileMetadata = new File();
            fileMetadata.setName("photo.jpg");
            java.io.File filePath = convertBytesToFile(data);
            FileContent mediaContent = new FileContent(dataContentType, filePath);
            File file = drive.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
            System.out.println("File ID: " + file.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
