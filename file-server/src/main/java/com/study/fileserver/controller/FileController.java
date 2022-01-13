package com.study.fileserver.controller;

import com.study.fileserver.model.UploadFileResponse;
import com.study.fileserver.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@AllArgsConstructor
@RequestMapping("/file")
@RestController
public class FileController {
    private final FileService fileService;

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName) {
        return fileService.loadFile(fileName);
    }

    @GetMapping("/download/{fileName}/thumbnail")
    public ResponseEntity<Resource> downloadThumbnail(@PathVariable String fileName) {
        return fileService.loadFile("thumb_" + fileName);
    }

    @PostMapping("/upload")
    public ResponseEntity<List<UploadFileResponse>> upload(@RequestPart("files") MultipartFile[] files) {
        List<UploadFileResponse> responses = fileService.saveFiles(files);
        return ResponseEntity.ok()
                .body(responses);
    }
}
