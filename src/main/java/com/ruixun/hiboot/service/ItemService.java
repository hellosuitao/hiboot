package com.ruixun.hiboot.service;

import com.ruixun.hiboot.bean.Item;

import java.util.List;

public interface ItemService {
    void addItem(Item item);
    List<Item> findAll();

    List<Item> findByPageSize(Integer page, Integer size);

    Item findById(Integer id);

    void update(Item item);

    void deleteItemById(Integer id);
}
