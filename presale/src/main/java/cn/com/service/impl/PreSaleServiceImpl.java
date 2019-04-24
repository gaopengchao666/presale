package cn.com.service.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.com.dao.PreSaleMapper;
import cn.com.entity.PreSale;
import cn.com.service.PreSaleService;
import cn.com.util.CrawlText;

/**
 * @author gaopengchao
 * 2019年3月27日
 */
@Service
public class PreSaleServiceImpl implements PreSaleService
{
    @Resource
    private PreSaleMapper preSaleMapper;
    
    @Override
    public List<PreSale> queryPreSales()
    {
        return preSaleMapper.queryPreSales();
    }
    
    /**
     * 定时任务
     */
    @Scheduled(cron="${cron}")
    public void timeToQueryData()
    {
        processPresale();
    }

    /**
     * 处理预售
     */
    private void processPresale()
    {
        // 西安预售证查询
        try
        {
            List<PreSale> preSales = CrawlText.getText(true, true,"http://fgj.xa.gov.cn/ygsf/index.aspx");
            preSaleMapper.insertPreSales(preSales);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
