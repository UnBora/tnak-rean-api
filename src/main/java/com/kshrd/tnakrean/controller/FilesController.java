//package com.kshrd.tnakrean.controller;
//
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import com.kshrd.tnakrean.model.FileInfo;
//import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
//import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
//import com.kshrd.tnakrean.service.serviceImplementation.FileImplement;
//import com.kshrd.tnakrean.service.serviceInter.FilesStorageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
//
//import javax.servlet.http.HttpServletRequest;
//
//
//@RestController
//@CrossOrigin(origins = "*")
//@RequestMapping("/api/v1/file")
//public class FilesController {
////    @Autowired
////    FilesStorageService storageService;
////    @Value("${base.url}")
////    String baseurl;
////
////    @Value(value = "${file.upload.server.path}")
////    private String serverPath;
//
//    @Value("${file.base.url}")
//    private String imageUrl;
//
//
//
//
//    //Todo: Post New Image
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity uploadMultiFile(@RequestPart(value = "files") MultipartFile[] files) {
//        Map<String, Object> res = new HashMap<>();
//        List<String> data = new ArrayList<>();
//        try {
//            for (MultipartFile file : files) {
//                String fileName = storageService.store(file);
//                data.add(imageUrl + fileName);
//            }
//            res.put("message", "You have uploaded image successfully");
//            res.put("status", true);
//            res.put("data", data);
//            return ResponseEntity.status(HttpStatus.OK).body(res);
//        } catch (Exception e) {
//            res.put("message", "Could not upload the file:");
//            res.put("status", false);
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(res);
//        }
//    }
//
//    @PostMapping(value = "/one", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity uploadFile(@RequestPart(value = "file") MultipartFile file) {
//        Map<String, Object> res = new HashMap<>();
//        try {
//            String fileName = storageService.store(file);
//            res.put("message", "You have uploaded image successfully");
//            res.put("status", true);
//            res.put("data", imageUrl + fileName);
//            return ResponseEntity.status(HttpStatus.OK).body(res);
//        } catch (Exception e) {
//            res.put("message", "Could not upload the file:");
//            res.put("status", false);
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(res);
//        }
//    }
//
//
////    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
////    ApiResponse<FileInfo> uploadFile(@RequestParam("file") MultipartFile file) {
////        try {
////            String fileName = storageService.save(file);
////            return ApiResponse.<FileInfo>ok(FileInfo.class.getSimpleName()).setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage()).setData(new FileInfo(fileName));
////        } catch (Exception e) {
////            return ApiResponse.<FileInfo>setError(e.getMessage()).setData(new FileInfo());
////        }
////
//////        try {
//////            String fileName = storageService.save(file);
//////            Resource resource = storageService.load(fileName);
//////            String url = resource.getFile().getAbsolutePath();
//////            return ApiResponse.<FileInfo>ok(FileInfo.class.getSimpleName()).setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage()).setData(new FileInfo(url));
//////        } catch (Exception e) {
//////            return ApiResponse.<FileInfo>setError(e.getMessage()).setData(new FileInfo());
//////        }
////    }
////
////    @GetMapping("/get/all")
////    public ApiResponse<List<FileInfo>> getListFiles() {
////        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
////            String filename = path.getFileName().toString();
////
////            String url = MvcUriComponentsBuilder.fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();
//////            String url = baseurl+filename;
////            //   MvcUriComponentsBuilder.fromMethodName(FilesController.class, "getFile", (baseurl
////            return new FileInfo(url);
////        }).toList();
////        return ApiResponse.<List<FileInfo>>ok(FileInfo.class.getSimpleName()).setData(fileInfos);
////    }
////
////    @GetMapping("/{filename:.+}")
////    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
////        // Load file as Resource
////        Resource resource = storageService.load(fileName);
////
////        // Try to determine file's content type
////        String contentType = null;
////        try {
////            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
////        } catch (IOException ex) {
////            //  loggers.info("Could not determine file type.");
////        }
////
////        // Fallback to the default content type if type could not be determined
////        if (contentType == null) {
////            contentType = "application/octet-stream";
////        }
////
////        return ResponseEntity.ok()
////                .contentType(MediaType.parseMediaType(contentType))
////                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
////                .body(resource);
////    }
//
////    @GetMapping("/{filename:.+}")
////    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
////        Resource file = storageService.load(filename);
////        return ResponseEntity.ok()
////                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
////    }
//
////    @GetMapping("/files/{filename:.+}")
////    public ApiResponse<FileInfo> getFiles(@PathVariable String filename) {
////        Resource file = storageService.load(filename);
////
////        return ApiResponse.<FileInfo>ok().setData(new FileInfo(filename));
////
////    }
//
//
//}