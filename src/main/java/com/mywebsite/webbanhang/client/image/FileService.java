package com.mywebsite.webbanhang.client.image;

import java.util.List;

public interface FileService {
	List<FileModal> getAllFiles();
	void saveAllFilesList(List<FileModal> fileList);
}
