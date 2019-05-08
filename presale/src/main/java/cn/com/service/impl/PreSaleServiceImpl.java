package cn.com.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.dao.PreSaleMapper;
import cn.com.entity.PreSale;
import cn.com.service.PreSaleService;
import cn.com.util.CrawlText;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gaopengchao
 * 2019年3月27日
 */
@Slf4j
@Service
public class PreSaleServiceImpl implements PreSaleService
{
    private String url = "http://fgj.xa.gov.cn/ygsf/index.aspx";
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
                * 增量更新数据 预售证
     */
    @Transactional
    private void processPresale()
    {
        //查询分页
        List<PreSale> preSales = new ArrayList<PreSale>();
        int totalPage = CrawlText.getPreSalePage(url);
        if (totalPage == 0)
        {
            log.error("没有查询到分页信息...");
            return;
        }
        
        //最大预售证号
        String maxCertificate = preSaleMapper.queryMaxCertificate();
        // 西安预售证查询
        boolean idContinue = true;
        for (int i = 1;i <= totalPage; i++)
        {
            //如果有旧数据则跳出循环
            if (!idContinue)
            {
                break;
            }
            String urls = url + "?page=" + i;
            List<PreSale> sales = CrawlText.getText(urls);
            log.info(sales.toString());
            for (PreSale presale : sales)
            {
                if (presale.getCertificate().compareTo(maxCertificate) > 0)
                {
                    preSales.add(presale);
                }
                else
                {
                    idContinue = false;
                }
            }
        }
        int size = preSales.size();
        if (size > 0) 
        {
            // 目标库插入数据 首次集合分割大小
            int MAX_SIZE = 1000;
            int subLength = size < MAX_SIZE ? size : MAX_SIZE;
            for (int i = 0; i < size;)
            {
                // 最后一次 数组集合分割大小
                int length = (size - i) < MAX_SIZE ? size : subLength + i;
                preSaleMapper.insertPreSales(preSales.subList(i, length));
                i += MAX_SIZE;
            }
        }
        log.info("采集到数据集合大小为：" + preSales.size());
    }
}
