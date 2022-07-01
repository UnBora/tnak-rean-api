package com.kshrd.tnakrean.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.kshrd.tnakrean.model.FileInfo;
import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.service.serviceInter.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;


@RestController
//@CrossOrigin("http://localhost:8080")
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/file")
public class FilesController {
    @Autowired
    FilesStorageService storageService;
    @Value("${base.url}")
    String baseurl;
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<FileInfo> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = storageService.save(file);
            return ApiResponse.<FileInfo>ok(FileInfo.class.getSimpleName()).setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage()).setData(new FileInfo(fileName));
        } catch (Exception e) {
            return ApiResponse.<FileInfo>setError(e.getMessage()).setData(new FileInfo());
        }
    }

    @GetMapping("/get/all")
    public ApiResponse<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();

           // String url = MvcUriComponentsBuilder.fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();
            String url = baseurl+filename;
                    //MvcUriComponentsBuilder.fromMethodName(FilesController.class, "getFile", (baseurl
            return new FileInfo(url);
        }).toList();
        return ApiResponse.<List<FileInfo>>ok(FileInfo.class.getSimpleName()).setData(fileInfos);
    }


    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

//    @GetMapping("/files/{filename:.+}")
//    public ApiResponse<FileInfo> getFiles(@PathVariable String filename) {
//        Resource file = storageService.load(filename);
//
//        return ApiResponse.<FileInfo>ok().setData(new FileInfo(filename));
//
//    }


}