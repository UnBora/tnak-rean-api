package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileImplement implements FileService {
    private final Path root = Paths.get("C:\\Users\\Asus\\IdeaProjects\\Media");
    @Value("${file.upload.server.path}")
    private String serverPath;

    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public String save(MultipartFile file) {

        String fileName = "";
        String fileDownloadUri = "";

        if(!file.isEmpty()) {
            fileName = file.getOriginalFilename();
            fileDownloadUri = UUID.randomUUID() + "."
                    + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            try {
                Files.copy(file.getInputStream(), Paths.get(serverPath, fileDownloadUri));
                Path filename = root.resolve(file.getName());
                Resource resource = new UrlResource(filename.toUri());
                System.out.println(resource.getFilename());
                System.out.println("Upsolute path:"+resource.getFile().getAbsolutePath());
                return resource.getFile().getAbsolutePath();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileDownloadUri;

    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            System.out.println(this.root);
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

}
