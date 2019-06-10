package cn.com.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import cn.com.dao.PriceMapper;
import cn.com.dao.ProjectMapper;
import cn.com.entity.Price;
import cn.com.entity.Project;
import cn.com.service.ProjectService;
import cn.com.util.CrawlText;
import lombok.extern.slf4j.Slf4j;

/**
 * 项目所有项目信息
 * @author gaopengchao
 * 2019年5月10日
 */
@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService
{
    // 查询批次所有项目 项目所有价格详情 baseUrl  
    //&decbatchId=8a901c286a0163fd016a10bed2541e3d&page=1&size=15"
    private String baseUrl = "http://117.39.29.75:8085/pricePublic/house/public/";
    
    @Resource
    private ProjectMapper projectMapper;
    
    @Resource
    private PriceMapper priceMapper;
    
    @Override
    public void processAllProject()
    {
        Document text = new CrawlText().getText(baseUrl + "index");
        //获取所有批次
        Elements batchOptions = text.select("#alterBatchId option:gt(0)");
        
        //如果是第一次就全量更新
        int projectCount = projectMapper.queryProjectCount();
        if (projectCount == 0)
        {
            log.info("项目全量更新--------------------start");
            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
            for (Element batchOption : batchOptions)
            {
                fixedThreadPool.execute(new ProjectThread(batchOption.val(),this));
            }
        }
        else 
        {
            //默认第一个批次所有项目
            Elements projectEles = text.select(".listTable>tbody>tr");
            //第一个项目的业务id
            String projectId = projectEles.get(0).select("td").get(0).select("a").attr("href").split("=")[1];
            Project existProject = projectMapper.queryProject(projectId);
            if (existProject != null)
            {
                log.info("项目增量更新没有新数据--------------------" + projectId);
                return;
            }
        }
        
        /*log.info("项目增量更新--------------------start");
        //增加更新
        for (Element batchOption : batchOptions)
        {
            //获取每个批次的数据
            List<Project> projects = getAllProjects(batchOption.val(),false);
            log.info("批次号：" + batchOption.val() + "添加项目:" + projects.size());
            addProjects(projects);
        }*/
    }

    /**
     * 添加项目和价格
     * @param projects
     */
    @Override
    public void addProjects(List<Project> projects)
    {
        if (projects.size() > 0)
        {
            projectMapper.insertProjects(projects);//添加项目
            List<Price> prices = new ArrayList<Price>();
            for (Project project : projects)
            {
                for (Price price : project.getPrices())
                {
                    price.setProjectId(project.getId());
                    prices.add(price);
                }
            }
            priceMapper.insertPrices(prices);//添加价格
        }
    }

    /**
     * 根据批次号获取所有项目和价格
     * @param batchOptions
     * @param isFull是否全量
     */
    @Override
    public List<Project> getAllProjects(String decbatchId,boolean isFull)
    {
        log.info("开始处理批次号：" + decbatchId);
        List<Project> projects = new ArrayList<Project>();
        
        Map<String,String> params = new HashMap<String,String>();
        params.put("page", "1");
        params.put("size", "20");
        params.put("decbatchId", decbatchId);//批次号
        //批次中所有项目
        Document document = new CrawlText().getText(baseUrl + "index",params);
        Elements projectTrs = document.select(".listTable>tbody>tr");
        
        for (Element projectTr : projectTrs)
        {
            Project project = new Project();
            Elements fields = projectTr.select("td");
            String priceUrl = fields.get(0).select("a").attr("href").split("=")[1];
            
            if (! isFull) 
            {
                Project existProject = projectMapper.queryProject(priceUrl);
                if (existProject != null)
                {
                    //如果已经存在
                    break;
                }
            }
            project.setPriceUrl(priceUrl);
            project.setProjectName(fields.get(0).select("a").html());
            project.setBatch(fields.get(1).html());
            project.setSaleable(fields.get(2).html());
            project.setFloors(fields.get(3).html());
            
            Map<String,String> param = new HashMap<String,String>();
            param.put("page", "1");
            param.put("size", "10000");
            //项目所有价格
            Document priceDocument = new CrawlText().getText(baseUrl +"price?id=" + priceUrl,param);
            //项目其他基本信息
            Elements projectOther = priceDocument.select(".formTable span");
            project.setDecoration(projectOther.get(1).html());
            project.setAddress(projectOther.get(2).html());
            Elements priceTrs = priceDocument.select(".listTable>tbody>tr");
            List<Price> prices = new ArrayList<Price>();
            for (Element priceTr : priceTrs)
            {
                Price price = new Price();
                Elements priceTds = priceTr.select("td");
                price.setSort(Integer.parseInt(priceTds.get(0).html()));
                price.setRoomNo(priceTds.get(1).html());
                price.setArea(Float.parseFloat(priceTds.get(2).html()));
                price.setUnitPrice(Float.parseFloat(priceTds.get(3).html()));
                price.setTotalPrice(Float.parseFloat(priceTds.get(4).html()));
                prices.add(price);
            }
            project.setPrices(prices);
            projects.add(project);
        }
        log.info("处理批次号结束项目有：" + projects.size());
        return projects;
    }
    
    /**
     * 处理项目
     */
    @Override
    public void processProject()
    {
        
    }


}
