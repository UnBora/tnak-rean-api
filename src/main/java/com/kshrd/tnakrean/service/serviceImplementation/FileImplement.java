package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.controller.StorageProperties;
import com.kshrd.tnakrean.service.FileService;
import com.kshrd.tnakrean.service.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileImplement implements FileService {
 //   private final Path root = Paths.get("C:\\Users\\Asus\\IdeaProjects\\Media");
    private final Path rootLocation ;

    @Autowired
    public FileImplement(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }
    @Value("${file.upload.server.path}")
    private String serverPath;


    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

//    @Override
//    public String store(MultipartFile file) {
//        return null;
//    }
//
//    @Override
//    public String save(MultipartFile file) {
//
//        String fileName = "";
//        String fileDownloadUri = "";
//
//        if(!file.isEmpty()) {
//            fileName = file.getOriginalFilename();
//            fileDownloadUri = UUID.randomUUID() + "."
//                    + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
//            try {
//                Files.copy(file.getInputStream(), Paths.get(serverPath, fileDownloadUri));
////                Path filename = root.resolve(file.getName());
////                Resource resource = new UrlResource(filename.toUri());
////                System.out.println(resource.getFilename());
////                System.out.println("Upsolute path:"+resource.getFile().getAbsolutePath());
//                return fileDownloadUri;
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return fileDownloadUri;
//
//    }
//
//    @Override
//    public Resource load(String filename) {
//        try {
//            Path file = root.resolve(filename);
//            Resource resource = new UrlResource(file.toUri());
//            if (resource.exists() || resource.isReadable()) {
//                return resource;
//            } else {
//                throw new RuntimeException("Could not read the file!");
//            }
//        } catch (MalformedURLException e) {
//            throw new RuntimeException("Error: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public Resource loadAsResource(String filename) {
//        return null;
//    }
//
//
//    @Override
//    public void deleteAll() {
//        FileSystemUtils.deleteRecursively(root.toFile());
//    }
//
//    @Override
//    public Stream<Path> loadAll() {
//        try {
//            System.out.println(this.root);
//            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
//        } catch (IOException e) {
//            throw new RuntimeException("Could not load the files!");
//        }
//    }

    @Override
    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }

        return filename;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            return null;
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        Resource resource = null;
        try {
            Path file = load(filename);
             resource= new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
        }
        catch (MalformedURLException e) {
            //throw new FileNotFoundException("Could not read file: " + filename, e);
        }
        return resource;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
