package cn.com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author gaopengchao
 * 2019年6月11日
 */
@RequestMapping
@Controller
public class IndexController
{
    @RequestMapping
    public String queryPreSaleList(Model model) 
    {
        return "index";
    }
}
