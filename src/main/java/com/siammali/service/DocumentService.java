package com.siammali.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.siammali.domain.ExchangeDocument;
import com.siammali.domain.UserDocument;
import com.siammali.repository.ExchangeDocumentRepository;
import com.siammali.repository.UserDocumentRepository;

@Service
public class DocumentService {

	@Autowired
	UserDocumentRepository userDocumentRepository;

	@Autowired
	ExchangeDocumentRepository exchangeDocumentRepo;

	@Autowired
	ExchangeService exchangeService;

	public Boolean uploadProof(MultipartFile file, String documentType, Long userId) {
		Boolean result = Boolean.FALSE;
		UserDocument doc = new UserDocument();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		doc.setDocName(fileName);
		doc.setDocumentType(documentType);
		try {
			doc.setFile(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		UserDocument savedDoc = userDocumentRepository.save(doc);
		if (savedDoc != null)
			result = Boolean.TRUE;
		return result;
	}

	public Boolean uploadExchangeProof(MultipartFile file, String documentType, Long exchangeId) {
		Boolean result = Boolean.FALSE;

		ExchangeDocument doc = new ExchangeDocument();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		doc.setDocName(fileName);
		doc.setDocumentType(documentType);
		try {
			doc.setFile(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ExchangeDocument savedDoc = exchangeDocumentRepo.save(doc);
		if (savedDoc != null)
			result = Boolean.TRUE;
		return result;
	}

}
