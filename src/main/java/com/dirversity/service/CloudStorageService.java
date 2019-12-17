package com.dirversity.service;

import com.google.api.services.drive.model.File;

import java.util.Optional;

public interface CloudStorageService {
    Optional<File> uploadFileData(String dataContentType, String dataDisplayName, byte[] data);
}
