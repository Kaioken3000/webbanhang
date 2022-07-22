package com.mywebsite.webbanhang.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(name = "cart_item", joinColumns = {
            @JoinColumn(name = "cart_id", referencedColumnName = "id") }, inverseJoinColumns = {
                    @JoinColumn(name = "item_id", referencedColumnName = "id")
            })
    private Set<Item> itemCart;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int number;

    private boolean hasAddCart;
    private boolean hasBuy;

    public Cart() {
    }

    public Cart(Set<Item> itemCart, User user, int number, boolean hasAddCart, boolean hasBuy) {
        this.itemCart = itemCart;
        this.user = user;
        this.number = number;
        this.hasAddCart = hasAddCart;
        this.hasBuy = hasBuy;
    }

    public Cart(long id, Set<Item> itemCart, User user, int number, boolean hasAddCart, boolean hasBuy) {
        this.id = id;
        this.itemCart = itemCart;
        this.user = user;
        this.number = number;
        this.hasAddCart = hasAddCart;
        this.hasBuy = hasBuy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Set<Item> getItemCart() {
        return itemCart;
    }

    public void setItemCart(Set<Item> itemCart) {
        this.itemCart = itemCart;
    }

    public boolean isHasAddCart() {
        return hasAddCart;
    }

    public void setHasAddCart(boolean hasAddCart) {
        this.hasAddCart = hasAddCart;
    }

    public boolean isHasBuy() {
        return hasBuy;
    }

    public void setHasBuy(boolean hasBuy) {
        this.hasBuy = hasBuy;
    }

}
