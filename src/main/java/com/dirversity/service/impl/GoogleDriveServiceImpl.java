package com.dirversity.service.impl;

import com.dirversity.service.CloudStorageService;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

@Service
public class GoogleDriveServiceImpl implements CloudStorageService {
    private final Logger log = LoggerFactory.getLogger(GoogleDriveServiceImpl.class);

    private final Drive drive;

    public GoogleDriveServiceImpl(Drive drive) {
        this.drive = drive;
    }

    private java.io.File convertBytesToFile(byte[] bytes) throws IOException {
        String fileName = "upload.tmp";
        java.io.File file = new java.io.File("src/main/resources/" + fileName);
        OutputStream os = new FileOutputStream(file);
        os.write(bytes);
        os.close();
        return file;
    }

    @Override
    public Optional<File> uploadFileData(String dataContentType, String dataDisplayName, byte[] data) {
        try {
            File fileMetadata = new File();
            fileMetadata.setName(dataDisplayName);
            java.io.File filePath = convertBytesToFile(data);
            FileContent mediaContent = new FileContent(dataContentType, filePath);
            File uploadedFile = drive.files().create(fileMetadata, mediaContent)
                .setFields("id, name, mimeType, webViewLink")
                .execute();
            addReaderPermissionToAnyone(uploadedFile);

            log.info("Uploaded file with name = {}, mimeType = {}, id = {}", uploadedFile.getName(),
                uploadedFile.getMimeType(),
                uploadedFile.getId());
            log.debug("Uploaded file webViewLink {}", uploadedFile.getWebViewLink());
            return Optional.of(uploadedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private void addReaderPermissionToAnyone(File uploadedFile) throws IOException {
        BatchRequest batch = drive.batch();
        Permission domainPermission = new Permission()
            .setType("anyone")
            .setRole("reader");
        drive.permissions().create(uploadedFile.getId(), domainPermission)
            .setFields("id")
            .queue(batch, new JsonBatchCallback<Permission>() {
                @Override
                public void onSuccess(Permission permission, HttpHeaders httpHeaders) {
                    log.debug("Added permission with id {}", permission.getId());
                }

                @Override
                public void onFailure(GoogleJsonError googleJsonError, HttpHeaders httpHeaders) {
                    log.debug(googleJsonError.getMessage());
                }
            });
        batch.execute();
    }
}
