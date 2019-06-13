package cn.com.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.entity.Price;
import cn.com.service.PriceService;

/**
 * @author gaopengchao
 * 2019年6月13日
 */
@RequestMapping("price")
@Controller
public class PriceController extends BaseController
{
    @Resource
    private PriceService priceService;
    
    /**
     * 分页查询项目价格
     */
    @RequestMapping("queryPriceList")
    @ResponseBody
    public Map<String,Object> queryPriceList(HttpServletRequest request)
    {
        Map<String, Object> params = getRequestParams(request);
        List<Price> prices = priceService.queryPriceList(params);
        params.put("list", prices);
        return params;
    }
}
