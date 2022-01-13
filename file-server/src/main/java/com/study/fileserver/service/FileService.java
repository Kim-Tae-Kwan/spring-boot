package com.study.fileserver.service;

import com.study.fileserver.exception.FileStorageException;
import com.study.fileserver.exception.MyFileNotFoundException;
import com.study.fileserver.model.UploadFileResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@AllArgsConstructor
@Service
public class FileService {
    private final Path path;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(this.path);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public List<UploadFileResponse> saveFiles(MultipartFile[] files){
        return Arrays.asList(files)
                .stream()
                .map(this::save)
                .collect(Collectors.toList());
    }

    private UploadFileResponse save(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.path.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            if (this.checkImageType(file)){
                Path thumbnailPath = this.path.resolve("thumb_" + fileName);
                FileOutputStream thumbnail = new FileOutputStream(new File(thumbnailPath.toString()));
                Thumbnailator.createThumbnail(file.getInputStream(), thumbnail, 100, 100);
            }

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("file/download/")
                    .path(fileName)
                    .toUriString();

            return UploadFileResponse.builder()
                    .fileName(fileName)
                    .fileDownloadUri(fileDownloadUri)
                    .fileType(file.getContentType())
                    .size(file.getSize())
                    .build();

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    private boolean checkImageType(MultipartFile file){
        String contentType = file.getContentType();
        return contentType.startsWith("image");
    }

    public ResponseEntity<Resource> loadFile(String fileName) {
        try {
            Path filePath = this.path.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if(!resource.exists()) {
                throw new MyFileNotFoundException("File not found " + fileName);
            }

            String contentType = this.getContentType(resource);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException exception) {
            throw new MyFileNotFoundException("File not found " + fileName, exception);
        }
    }

    private String getContentType(Resource resource){
        try{
            String contentType = Files.probeContentType(resource.getFile().toPath());
            return contentType == null ? "application/octet-stream" : contentType;
        }catch (IOException exception){
            return "application/octet-stream";
        }
    }
}
