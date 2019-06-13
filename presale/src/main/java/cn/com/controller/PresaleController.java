package cn.com.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.entity.Presale;
import cn.com.service.PresaleService;

/**
 * 预售证
 * @author gaopengchao
 * 2019年3月27日
 */
@RequestMapping("/presale")
@Controller
public class PresaleController extends BaseController
{
    @Resource
    private PresaleService preSaleService;
    
    @RequestMapping("/queryPresales")
    public String queryPresales() 
    {
        return "presaleList";
    }
    
    @RequestMapping("/queryPresaleList")
    @ResponseBody
    public Map<String,Object> queryPresaleList(HttpServletRequest request) 
    {
        Map<String, Object> params = getRequestParams(request);
        List<Presale> preSales = preSaleService.queryPresales(params);
        params.put("list", preSales);
        return params;
    }
}
