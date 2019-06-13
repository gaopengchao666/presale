package cn.com.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.dao.PresaleMapper;
import cn.com.entity.Presale;
import cn.com.service.PresaleService;
import cn.com.service.ProjectService;
import cn.com.util.CrawlText;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gaopengchao
 * 2019年3月27日
 */
@Slf4j
@Service
public class PresaleServiceImpl implements PresaleService
{
    //预售url
    private String url = "http://fgj.xa.gov.cn/ygsf/index.aspx";
    @Resource
    private PresaleMapper preSaleMapper;
    
    @Resource
    private ProjectService projectService;
    
    @Override
    public List<Presale> queryPresales(Map<String,Object> params)
    {
        return preSaleMapper.queryPresales(params);
    }
    
    /**
     * 定时任务
     */
    @Scheduled(cron="${cron}")
    public void timeToQueryData()
    {
        processAllPresale();
        projectService.processAllProject();
    }
    
    /**
             * 全量更新数据预售证数据
     */
    private void processAllPresale()
    {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
        //查询分页
        int totalPage = getPreSalePage(url);
        if (totalPage == 0)
        {
            log.error("预售证没有查询到分页信息...");
            return;
        }
        //最大预售证号
        String maxCertificate = preSaleMapper.queryMaxCertificate();
        if (! maxCertificate.equals("0"))
        {
            log.info("预售证全量更新结束，开始增量更新...");
            processUpdatePresale(maxCertificate);
            return;
        }
        //分20个线程采集数据
        for (int i = 1;i <= totalPage; i++)
        {
            fixedThreadPool.execute(new PresaleThread(i,url,this));
        }
    }

    /**
                * 增量更新数据 预售证
     */
    @Transactional
    private void processUpdatePresale(String maxCertificate)
    {
        //查询分页
        List<Presale> preSales = new ArrayList<Presale>();
        int totalPage = getPreSalePage(url);
        if (totalPage == 0)
        {
            log.error("预售证没有查询到分页信息...");
            return;
        }
        
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
            List<Presale> sales = getPresales(urls);
            log.info(sales.toString());
            for (Presale presale : sales)
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
                preSaleMapper.insertPresales(preSales.subList(i, length));
                i += MAX_SIZE;
            }
        }
        log.info("预售证采集到数据集合大小为：" + preSales.size());
    }
    
    /**
     * 获取预售证所有页数
     * @param Url
     * @return
     */
    public int getPreSalePage(String Url)
    {
        Document document = new CrawlText().getText(Url);
        Elements totalPageA = document.select(".ygsf_pager>a:eq(7)");
        String totalPageUtl = totalPageA.attr("href");
        int totalPage = Integer.parseInt(totalPageUtl.split("=")[1]);
        return totalPage;
    }
    
    /***
     * 根据给定url获取所有的预售信息
     * 
     */
    public List<Presale> getPresales(String Url)
    {
        Document document = new CrawlText().getText(Url);
        Elements trs = document.select(".ygsf_table1 .ysztr");
        List<Presale> presales = new ArrayList<Presale>();
        for (Element tr : trs)
        {
            Presale presale = new Presale();
            Elements tds = tr.select("td");
            if (tds.get(0).select("a").size() > 0)
            {
                presale.setCertificate(tds.get(0).select("a").html());
            }
            if (tds.get(1).select("a").size() > 0)
            {
                presale.setProjectName(tds.get(1).select("a").html());
            }
            presale.setLocation(tds.get(2).html());
            presale.setDeveloper(tds.get(3).html());
            presale.setSaleable(tds.get(4).html());
            presale.setCreateTime(tds.get(5).html());
            presales.add(presale);
        }
        return presales;
    }

    @Override
    public void insertPresales(List<Presale> sales)
    {
        preSaleMapper.insertPresales(sales);
    }
}
