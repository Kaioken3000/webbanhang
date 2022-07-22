package com.mywebsite.webbanhang.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mywebsite.webbanhang.model.Item;
import com.mywebsite.webbanhang.repository.ItemRepository;

@Service
public class ItemService {
    ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> listAllItem(){
        return itemRepository.findAll();
    }

    public Item addItem(Item item){
        return itemRepository.save(item);
    }

    public Item updateItem(Item item){
        return itemRepository.save(item);
    }

    public void deleteItem(long id){
        itemRepository.deleteById(id);
    }

    public Item getItemById(long id){
        return itemRepository.findById(id).get();
    }

    public Page<Item> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.itemRepository.findAll(pageable);
    }

    public List<Item> getByKeyword(String keyword){
        return itemRepository.findByKeyword(keyword);
    }
}
