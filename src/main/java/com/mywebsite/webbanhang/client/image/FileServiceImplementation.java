package com.mywebsite.webbanhang.client.image;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

