package com.mywebsite.webbanhang.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mywebsite.webbanhang.model.FileModal;
import com.mywebsite.webbanhang.repository.FileRepository;

@Service
public class FileServiceImplementation implements FileService {

	@Autowired
	FileRepository fileRepository;

	@Override
	public List<FileModal> getAllFiles() {
		return fileRepository.findAll();
	}
	public void saveAllFilesList(List<FileModal> fileList) {
		for (FileModal fileModal : fileList)
			fileRepository.save(fileModal);
	}
}

