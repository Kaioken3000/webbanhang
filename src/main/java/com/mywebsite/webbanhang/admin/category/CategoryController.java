package com.mywebsite.webbanhang.admin.category;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/category")
    public String viewCategoryPage(Model model) {
        return findPaginated(1, "id", "asc", model);
    }

    @GetMapping("/admin/category/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDir") String sortDir,
            Model model) {
        int pageSize = 6;

        Page<Category> page = categoryService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Category> listCategories = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listCategories", listCategories);
        Category category = new Category();
        model.addAttribute("category", category);

        return "/admin/category_/category";
    }


    @RequestMapping("/admin/category/showSearch")
    public String findPaginatedSearch( Model model, String keyword) {
        if (keyword != null) {
            List<Category> list = categoryService.getByKeyword(keyword);
            model.addAttribute("listCategories", list);
        } else {
            List<Category> list = categoryService.listAllCategory();
            model.addAttribute("listCategories", list);
        }
        Category category = new Category();
        model.addAttribute("category", category);
        return "/admin/category_/categorySearch";
    }

    @PostMapping("/admin/addCategory")
    public String addCategory(@ModelAttribute("Category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/admin/deleteCategory/{id}")
    public String deleteCourse(@PathVariable long id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/category";
    }

    @GetMapping("/admin/showUpdateCategory/{id}")
    public String showUpdateCategory(@PathVariable long id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "/admin/category_/categoryUpdate";
    }

    @PostMapping("/admin/updateCategory/{id}")
    public String updateCategory(@PathVariable long id, @ModelAttribute("category") Category category) {
        Category extingCategory = categoryService.getCategoryById(id);
        extingCategory.setId(category.getId());
        extingCategory.setCode(category.getCode());
        extingCategory.setName(category.getName());

        categoryService.updateCategory(extingCategory);
        return "redirect:/admin/category";
    }

}
