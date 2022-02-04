package com.project.controller;

import java.io.File;
import java.io.FileNotFoundException;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.exceptions.FileStorageException;
import com.project.responseDTO.UploadFileResponse;
import com.project.services.FileStorageService;


@RestController
@RequestMapping(value = "/api/v2")
@CrossOrigin(origins = "*")
public class FileTestController {
	@Autowired
	private FileStorageService fileStorageService;
	@PostMapping(value = "/uploads")
	public UploadFileResponse upload(@RequestParam("file") MultipartFile file) {
		String fileName = null;
		try {
			fileName = fileStorageService.storeFile(file);
		} catch (FileStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().
				path("/api/v2/downloadFile/")
				.path(fileName)
				.toUriString();
		return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}
	@PostMapping(value = "/uploadMultipleFiles")
	public List<UploadFileResponse> uploadMultiple(@RequestParam("files") MultipartFile[] files){
		System.out.println(files.length);
		return Arrays.asList(files)
				.stream()
				.map(file -> upload(file))
				.collect(Collectors.toList()); 
	}
	
	@GetMapping(value = "/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws FileNotFoundException{
		Resource resource = fileStorageService.loadFileAsResource(fileName);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=\""+resource.getFilename()+"\"")
				.body(resource);
	}
	
}
