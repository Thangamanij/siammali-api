package com.siammali.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.siammali.service.DocumentService;

@RestController
@RequestMapping("/api")
public class DocumentController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DocumentService documentService;

	@PostMapping(value = "/uploadProof")
	public ResponseEntity<Boolean> uploadProof(@RequestParam("file") MultipartFile file,
			@RequestParam String documentType, Long userId) {
		Boolean result = documentService.uploadProof(file, documentType, userId);
		return ResponseEntity.ok(result);

	}
}