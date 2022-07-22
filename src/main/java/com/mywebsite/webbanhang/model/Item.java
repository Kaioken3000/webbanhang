package com.mywebsite.webbanhang.model;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private double price;
    private String info;
    private String publisher;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dayPublish;
    private double size;
    private String picture;
    @Column(name = "number_in_store")
    private long numberInStore;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(name = "item_category", 
        joinColumns = {
            @JoinColumn(name = "item_id", referencedColumnName = "id") }, 
        inverseJoinColumns = {
            @JoinColumn(name = "category_id", referencedColumnName = "id") })
    private Set<Category> category;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL})
    @JoinTable(name = "item_image", 
        joinColumns = {
            @JoinColumn(name = "item_id", referencedColumnName = "id") }, 
        inverseJoinColumns = {            
            @JoinColumn(name = "image_id", referencedColumnName = "id") })
    private Set<FileModal> image;

    public Item() {
    }

    public Item(long id, String name, double price, String info, String publisher, LocalDateTime dayPublish,
            double size, String picture, long numberInStore, Set<Category> category, Set<FileModal> image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.info = info;
        this.publisher = publisher;
        this.dayPublish = dayPublish;
        this.size = size;
        this.picture = picture;
        this.numberInStore = numberInStore;
        this.category = category;
        this.image = image;
    }

    public Item(String name, double price, String info, String publisher, LocalDateTime dayPublish, double size,
            String picture, long numberInStore, Set<Category> category) {
        this.name = name;
        this.price = price;
        this.info = info;
        this.publisher = publisher;
        this.dayPublish = dayPublish;
        this.size = size;
        this.picture = picture;
        this.numberInStore = numberInStore;
        this.category = category;
    }

    public Item(long id, String name, double price, String info, String publisher, LocalDateTime dayPublish,
            double size, String picture, long numberInStore, Set<Category> category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.info = info;
        this.publisher = publisher;
        this.dayPublish = dayPublish;
        this.size = size;
        this.picture = picture;
        this.numberInStore = numberInStore;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDateTime getDayPublish() {
        return dayPublish;
    }

    public void setDayPublish(LocalDateTime dayPublish) {
        this.dayPublish = dayPublish;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public long getNumberInStore() {
        return numberInStore;
    }

    public void setNumberInStore(long numberInStore) {
        this.numberInStore = numberInStore;
    }

    public Set<Category> getCategory() {
        return category;
    }

    public void setCategory(Set<Category> category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryObject() {
        Iterator<Category> irt = category.iterator();
        String ans = "";
        while (irt.hasNext()) {
            Category c = (Category) irt.next();
            ans += c.getCode();
            ans += " ";
        }
        return ans;
    }

    public Set<FileModal> getImage() {
        return image;
    }

    public void setImage(Set<FileModal> image) {
        this.image = image;
    }

}
