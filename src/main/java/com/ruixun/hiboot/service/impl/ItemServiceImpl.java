package com.ruixun.hiboot.service.impl;

import com.ruixun.hiboot.bean.Item;
import com.ruixun.hiboot.mapper.ItemMapper;
import com.ruixun.hiboot.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired(required = false)
    private ItemMapper itemMapper;

    /*@Autowired
    private ItemRepository itemRepository;*/

    @Override
    public void addItem(Item item) {
        itemMapper.insert(item);
//        itemRepository.save(item);
        System.out.println(item.getId());
        IndexQuery indexQuery = new IndexQueryBuilder().withId(item.getId().toString()).withObject(item).build();
        elasticsearchTemplate.index(indexQuery);
    }

    @Override
    public List<Item> findAll() {
        return itemMapper.findAll();
    }

    @Override
    public List<Item> findByPageSize(Integer page, Integer size) {
        int from = size*(page-1);
        System.out.println(from);
        return itemMapper.findByPageSize(from,size);
    }

    @Override
    public Item findById(Integer id) {
        return itemMapper.findById(id);
    }

    @Override
    public void update(Item item) {
        itemMapper.update(item);
    }

    @Override
    public void deleteItemById(Integer id) {
        itemMapper.deleteItemById(id);
    }
}
