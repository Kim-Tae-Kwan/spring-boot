package com.study.fileserver.controller;

import com.study.fileserver.model.UploadFileResponse;
import com.study.fileserver.service.FileService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@AllArgsConstructor
@RestController
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<List<UploadFileResponse>> upload(@RequestParam("files") MultipartFile[] files) {
        List<UploadFileResponse> responses = fileService.saveFiles(files);
        return ResponseEntity.ok()
                .body(responses);
    }

    @GetMapping("/downloadFile/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) {
        return fileService.loadFile(fileName);
    }

}
