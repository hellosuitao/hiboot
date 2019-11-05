package com.ruixun.hiboot.controller;

import com.ruixun.hiboot.bean.Item;
import com.ruixun.hiboot.service.ItemService;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class Crud {

    @Autowired
    private ItemService itemService;

//    @Autowired
//    private ItemRepository itemRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @RequestMapping("/citem")
    public String addItem(String name, String title, Integer price, MultipartFile file,
                          Model model) {
        Item item = new Item();
        String uuid = UUID.randomUUID().toString();
        String imageName = uuid+".jpg";
        String filePath = "d://";
        File dest = new File(filePath + imageName);
        item.setName(name);
        item.setTitle(title);
        item.setPrice(price);
        item.setImage(imageName);
        itemService.addItem(item);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int totalCount = itemService.findAll().size();
        int pageCount = totalCount/2;
        int yu = totalCount%2;
        if(yu!=0){
            pageCount = pageCount+1;
        }
        List<Item> items = itemService.findByPageSize(1, 2);
        System.out.println(items);
        model.addAttribute("items",items);
        model.addAttribute("page",1);
        model.addAttribute("size",2);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("items",items);
//        Page<Item> page = itemRepository.findAll(PageRequest.of(0,1, Sort.by(Sort.Direction.DESC)));
//        model.addAttribute("page",page);
        return "item";
    }

    @RequestMapping("/update")
    public String updateItem(Integer id,String name, String title, Integer price, MultipartFile file,
                          Model model) {
        Item item = new Item();
        String uuid = UUID.randomUUID().toString();
        String imageName = uuid+".jpg";
        String filePath = "d://";
        File dest = new File(filePath + imageName);
        item.setName(name);
        item.setTitle(title);
        item.setPrice(price);
        item.setImage(imageName);
        itemService.update(item);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int totalCount = itemService.findAll().size();
        int pageCount = totalCount/2;
        int yu = totalCount%2;
        if(yu!=0){
            pageCount = pageCount+1;
        }
        List<Item> items = itemService.findByPageSize(1, 2);
        System.out.println(items);
        model.addAttribute("items",items);
        model.addAttribute("page",1);
        model.addAttribute("size",2);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("items",items);
//        Page<Item> page = itemRepository.findAll(PageRequest.of(0,1, Sort.by(Sort.Direction.DESC)));
//        model.addAttribute("page",page);
        return "item";
    }

    @RequestMapping("/delete")
    public String delete(Integer id,Model model) {
        itemService.deleteItemById(id);
        int totalCount = itemService.findAll().size();
        int pageCount = totalCount/2;
        int yu = totalCount%2;
        if(yu!=0){
            pageCount = pageCount+1;
        }
        List<Item> items = itemService.findByPageSize(1, 2);
        System.out.println(items);
        model.addAttribute("items",items);
        model.addAttribute("page",1);
        model.addAttribute("size",2);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("items",items);
//        Page<Item> page = itemRepository.findAll(PageRequest.of(0,1, Sort.by(Sort.Direction.DESC)));
//        model.addAttribute("page",page);
        return "item";
    }

    @RequestMapping("/toupdate")
    public String updateItem(Integer id,Model model){
        Item item = itemService.findById(id);
        model.addAttribute("item",item);
        return "update";

    }

    @RequestMapping("/pageSize")
    public String pageSize(@RequestParam("page") Integer page, @RequestParam(defaultValue = "2") Integer size, Model model){
        int totalCount = itemService.findAll().size();
        int pageCount = totalCount/size;
        int yu = totalCount%size;
        if(yu!=0){
            pageCount = pageCount+1;
        }
        List<Item> items = itemService.findByPageSize(page, size);
        System.out.println(items);
        model.addAttribute("items",items);
        model.addAttribute("page",page);
        model.addAttribute("size",size);
        model.addAttribute("pageCount",pageCount);
        return "item";
    }

    @RequestMapping("/findByNameTitle")
    public String findByNameTitle(@RequestParam(name = "page",defaultValue = "1") Integer page,
                           @RequestParam(name = "size",defaultValue = "2") Integer size,
                           String name,String title,Model model){
        FuzzyQueryBuilder matchQueryBuilder = QueryBuilders.fuzzyQuery("name",name);
        NativeSearchQuery query = new NativeSearchQuery(matchQueryBuilder);
        List<Item> items = elasticsearchTemplate.queryForList(query, Item.class);
        int pageCount = items.size()/size;
        int yu = items.size()%size;
        if(yu!=0){
            pageCount = pageCount+1;
        }
        model.addAttribute("items",items);
        model.addAttribute("page",page);
        model.addAttribute("size",size);
        model.addAttribute("pageCount",pageCount);
        return "item";
    }
}
