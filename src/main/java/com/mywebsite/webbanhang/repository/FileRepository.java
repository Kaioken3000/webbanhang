package com.mywebsite.webbanhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mywebsite.webbanhang.model.FileModal;

@Repository
public interface FileRepository extends JpaRepository<FileModal, Long> {

}
