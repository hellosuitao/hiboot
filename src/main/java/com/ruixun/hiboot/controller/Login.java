package com.ruixun.hiboot.controller;

import com.ruixun.hiboot.bean.Item;
import com.ruixun.hiboot.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class Login {
    @Autowired
    private ItemService itemService;
    @RequestMapping("/userLogin")
    public String login(@RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "2") Integer size,
                        Model model, String username, String password, HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("username",username);
//        List<Item> items = itemService.findAll();
        int totalCount = itemService.findAll().size();
        int pageCount = totalCount/size;
        int yu = totalCount/size;
        if(yu!=0){
            pageCount = pageCount+1;
        }
        List<Item> items = itemService.findByPageSize(page, size);
        model.addAttribute("items",items);
        model.addAttribute("page",page);
        model.addAttribute("size",size);
        model.addAttribute("pageCount",pageCount);
        return "item";
    }
}
