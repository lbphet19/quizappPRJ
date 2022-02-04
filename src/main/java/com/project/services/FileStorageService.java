package com.project.services;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.util.StringUtils;
import com.project.exceptions.FileStorageException;
import com.project.properties.FileStorageProperties;

@Service
public class FileStorageService {
	private final Path fileStorageLocation;
//	@PostConstruct
	@Autowired
	public FileStorageService(FileStorageProperties properties) throws FileStorageException {
		this.fileStorageLocation = Paths.get(properties.getUploadDir()).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			// TODO: handle exception
			throw new FileStorageException("Cannot create directory");
		}
	}
	public String storeFile(MultipartFile file) throws FileStorageException {
//		normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if(fileName.contains("..")) {
				throw new FileStorageException("File name cannot have relative path!");
			}
//			copy file to location
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation,StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		} catch (Exception e) {
			// TODO: handle exception
			throw new FileStorageException("Could not store file" + fileName);
		}
	}
	public Path load(String fileName) {
		return fileStorageLocation.resolve(fileName);
	}
	public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }
}
