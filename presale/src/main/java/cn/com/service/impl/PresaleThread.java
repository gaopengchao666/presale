package cn.com.service.impl;

import java.util.List;

import cn.com.entity.Presale;
import cn.com.service.PresaleService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gaopengchao
 * 2019年5月9日
 */
@Slf4j
public class PresaleThread extends Thread
{
    private PresaleService preSaleService;
    private String Url;
    private int page;
    
    public PresaleThread(int i,String url,PresaleService service)
    {
        this.page = i;
        this.preSaleService = service;
        this.Url = url;
    }

    @Override
    public void run()
    {
        String urls = Url + "?page=" + page;
        List<Presale> sales = preSaleService.getPresales(urls);
        log.info(Thread.currentThread().getName() + ":" + sales.toString());
        preSaleService.insertPresales(sales);
    }
}
