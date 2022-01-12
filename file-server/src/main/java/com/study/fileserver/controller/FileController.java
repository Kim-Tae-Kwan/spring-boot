package com.study.fileserver.controller;

import com.study.fileserver.model.UploadFileResponse;
import com.study.fileserver.service.FileService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Log4j2
@AllArgsConstructor
@RestController
public class FileController {
    private final FileService fileService;

    @GetMapping("/downloadFile/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) {
        return fileService.loadFile(fileName);
    }

    @PostMapping("/upload")
    public ResponseEntity<List<UploadFileResponse>> upload(@RequestParam("files") MultipartFile[] files) {
        List<UploadFileResponse> responses = fileService.saveFiles(files);
        return ResponseEntity.ok()
                .body(responses);
    }
}
