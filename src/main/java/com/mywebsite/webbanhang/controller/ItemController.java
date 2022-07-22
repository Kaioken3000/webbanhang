package com.mywebsite.webbanhang.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.mywebsite.webbanhang.model.FileModal;
import com.mywebsite.webbanhang.model.Item;
import com.mywebsite.webbanhang.service.CategoryService;
import com.mywebsite.webbanhang.service.ItemService;

@Controller
public class ItemController {

    ItemService itemService;
    CategoryService categoryService;
    HttpServletRequest request;

    public ItemController(ItemService itemService, CategoryService categoryService, HttpServletRequest request) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.request = request;
    }

    @GetMapping("/admin/item")
    public String viewItemPage(Model model) {
        return findPaginated(1, "id", "asc", model);
    }

    @GetMapping("/admin/item/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDir") String sortDir,
            Model model) {
        int pageSize = 5;

        Page<Item> page = itemService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Item> listItems = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        Item item = new Item();
        model.addAttribute("listItems", listItems);
        model.addAttribute("listCategories", categoryService.listAllCategory());
        model.addAttribute("item", item);
        return "/admin/item_/item";
    }

    @RequestMapping("/admin/item/showSearch")
    public String findPaginatedSearch(Model model, String keyword) {
        if (keyword != null) {
            List<Item> list = itemService.getByKeyword(keyword);
            model.addAttribute("listItems", list);
        } else {
            List<Item> list = itemService.listAllItem();
            model.addAttribute("listItems", list);
        }

        Item item = new Item();
        model.addAttribute("item", item);
        return "/admin/item_/itemSearch";
    }

    @PostMapping("/admin/addItem")
    public RedirectView addItem(@ModelAttribute("item") Item item, HttpServletRequest request,
            @RequestParam("images1") MultipartFile[] files, @RequestParam("picture1") MultipartFile picture) {
        try {
            List<FileModal> fileList = new ArrayList<FileModal>();
            for (MultipartFile file : files) {
                String fileContentType = file.getContentType();
                String fileName = file.getOriginalFilename();
                FileModal fileModal = new FileModal(fileName, fileContentType);
                fileList.add(fileModal);
            }
            Set<FileModal> image = new HashSet<>(fileList);
            item.setImage(image);
            item.setPicture(picture.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }

        itemService.addItem(item);
        String referer = request.getHeader("Referer");
        return new RedirectView(referer);
    }

    @GetMapping("/admin/deleteItem/{id}")
    public RedirectView deleteItem(@PathVariable long id) {
        itemService.deleteItem(id);
        String referer = request.getHeader("Referer");
        return new RedirectView(referer);
    }

    @GetMapping("/admin/showUpdateItem/{id}")
    public String showUpdateItem(@PathVariable long id, Model model) {
        model.addAttribute("item", itemService.getItemById(id));
        model.addAttribute("listCategories", categoryService.listAllCategory());
        return "/admin/item_/itemUpdate";
    }

    @PostMapping("/admin/updateItem/{id}")
    public String updateItem(@PathVariable long id, @ModelAttribute("item") Item item,
        @RequestParam("images1") MultipartFile[] files, @RequestParam("picture1") MultipartFile picture) {
        try {
            List<FileModal> fileList = new ArrayList<FileModal>();
            for (MultipartFile file : files) {
                String fileContentType = file.getContentType();
                String fileName = file.getOriginalFilename();
                FileModal fileModal = new FileModal(fileName, fileContentType);
                fileList.add(fileModal);
            }

            Set<FileModal> image = new HashSet<>(fileList);
            item.setImage(image);
            item.setPicture(picture.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Item extingItem = itemService.getItemById(id);
        extingItem.setId(item.getId());
        extingItem.setName(item.getName());
        extingItem.setPrice(item.getPrice());
        extingItem.setPublisher(item.getPublisher());
        extingItem.setDayPublish(item.getDayPublish());
        extingItem.setCategory(item.getCategory());
        extingItem.setSize(item.getSize());
        extingItem.setNumberInStore(item.getNumberInStore());
        extingItem.setPicture(item.getPicture());
        extingItem.setImage(item.getImage());
        extingItem.setInfo(item.getInfo());

        itemService.updateItem(extingItem);

        return "redirect:/admin/item";
    }
}
