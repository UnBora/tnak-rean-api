package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.repository.FileResponse;
import com.kshrd.tnakrean.service.FileService;
import com.kshrd.tnakrean.service.serviceImplementation.FileImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/upload")
public class ImageController {

    @Value(value = "${file.upload.server.path}")
    private String serverPath;

    @Value("${file.base.url}")
    private String imageUrl;

    @Autowired
    FileService storageService;

    //    //Todo: Post New Image
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadMultiFile(@RequestPart(value = "files") MultipartFile[] files) {
        Map<String, Object> res = new HashMap<>();
        List<String> data = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                String fileName = storageService.store(file);
                data.add(imageUrl + fileName);
                String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/download/")
                        //  .path(name)
                        .toUriString();
                res.put("res", uri);
            }

            res.put("message", "You have uploaded image successfully");
            res.put("status", true);
            res.put("data", data);

            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            res.put("message", "Could not upload the file:");
            res.put("status", false);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(res);
        }
    }

    @PostMapping(value = "/one", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFile(@RequestPart(value = "file") MultipartFile file, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        try {
            String fileName = storageService.store(file);
            res.put("message", "You have uploaded image successfully");
            res.put("status", true);
            Resource resource = new UrlResource(fileName);

//            System.out.println(resource.getFile().getAbsolutePath());
            HttpHeaders headers = new HttpHeaders();
            new ServletContextResource(request.getServletContext(), "/WEB-INF/images/image-example.jpg");
            //  res.put("data",resource.getFile().getAbsolutePath());
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            res.put("message", "Could not upload the file:");
            res.put("status", false);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(res);
        }
    }

    @GetMapping("/")
    public String listAllFiles(Model model) {

        model.addAttribute("files", storageService.loadAll().map(
                        path -> ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/download/")
                                .path(path.getFileName().toString())
                                .toUriString())
                .collect(Collectors.toList()));

        return "listFiles";
    }


}

