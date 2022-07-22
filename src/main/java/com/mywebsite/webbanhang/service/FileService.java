package com.mywebsite.webbanhang.service;

import java.util.List;

import com.mywebsite.webbanhang.model.FileModal;

public interface FileService {
	List<FileModal> getAllFiles();
	void saveAllFilesList(List<FileModal> fileList);
}
