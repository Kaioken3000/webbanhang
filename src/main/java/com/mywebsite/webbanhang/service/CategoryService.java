package com.mywebsite.webbanhang.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mywebsite.webbanhang.model.Category;
import com.mywebsite.webbanhang.repository.CategoryRepository;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> listAllCategory() {
        return categoryRepository.findAll();
    }

    public Category addCategory(Category category){
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category){
        return categoryRepository.save(category);
    }

    public void deleteCategory(long id){
        categoryRepository.deleteById(id);
    }

    public Category getCategoryById(long id){
        return categoryRepository.findById(id).get();
    }

    public Page<Category> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.categoryRepository.findAll(pageable);
    }   

    public List<Category> getByKeyword(String keyword){
        return categoryRepository.findByKeyword(keyword);
    }
    
}
