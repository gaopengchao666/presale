package cn.com.service.impl;

import java.util.List;

import cn.com.entity.PreSale;
import cn.com.service.PreSaleService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gaopengchao
 * 2019年5月9日
 */
@Slf4j
public class PreSaleThread extends Thread
{
    private PreSaleService preSaleService;
    private String Url;
    private int page;
    
    public PreSaleThread(int i,String url,PreSaleService service)
    {
        this.page = i;
        this.preSaleService = service;
        this.Url = url;
    }

    @Override
    public void run()
    {
        String urls = Url + "?page=" + page;
        List<PreSale> sales = preSaleService.getPreSales(urls);
        log.info(Thread.currentThread().getName() + ":" + sales.toString());
        preSaleService.insertPreSales(sales);
    }
}
