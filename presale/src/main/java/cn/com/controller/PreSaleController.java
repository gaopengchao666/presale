package cn.com.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.entity.PreSale;
import cn.com.service.PreSaleService;

/**
 * 预售证
 * @author gaopengchao
 * 2019年3月27日
 */
@RequestMapping("/presale")
@Controller
public class PreSaleController
{
    @Resource
    private PreSaleService preSaleService;
    
    @RequestMapping("/queryPreSales")
    @ResponseBody
    public List<PreSale> queryPreSales() 
    {
        return preSaleService.queryPreSales();
    }
    
    @RequestMapping("/queryPreSaleList")
    public String queryPreSaleList(Model model) 
    {
        List<PreSale> preSales = preSaleService.queryPreSales();
        model.addAttribute("preSales", preSales);
        return "index";
    }
}
