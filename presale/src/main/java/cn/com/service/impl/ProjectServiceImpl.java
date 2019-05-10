package cn.com.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import cn.com.entity.Price;
import cn.com.entity.Project;
import cn.com.service.ProjectService;
import cn.com.util.CrawlText;

/**
 * 项目所有项目信息
 * @author gaopengchao
 * 2019年5月10日
 */
@Service
public class ProjectServiceImpl implements ProjectService
{
    // 查询批次所有项目 项目所有价格详情 baseUrl
    //&decbatchId=8a901c286a0163fd016a10bed2541e3d&page=1&size=15"
    private String baseUrl = "http://117.39.29.75:8085/pricePublic/house/public/";
    
    /**
     * 处理项目
     */
    @Override
    public void processProject()
    {
        Document text = new CrawlText().getText(baseUrl + "index");
        //获取所有批次
        Elements batchOptions = text.select("#alterBatchId option:gt(0)");
        
        for (Element batchOption : batchOptions.subList(0, 1))
        {
            Map<String,String> params = new HashMap<String,String>();
            params.put("page", "1");
            params.put("size", "20");
            params.put("decbatchId", batchOption.val());//批次号
            //批次中所有项目
            Document document = new CrawlText().getText(baseUrl + "index",params);
            Elements projectTrs = document.select(".listTable>tbody>tr");
            
            for (Element projectTr : projectTrs.subList(0, 1))
            {
                Project project = new Project();
                Elements fields = projectTr.select("td");
                String priceUrl = fields.get(0).select("a").attr("href");
                project.setPriceUrl(priceUrl);
                project.setProjectName(fields.get(0).select("a").html());
                project.setBatch(fields.get(1).html());
                project.setSaleable(fields.get(2).html());
                project.setFloors(fields.get(3).html());
                
                Map<String,String> param = new HashMap<String,String>();
                param.put("page", "1");
                param.put("size", "10000");
                //项目所有价格
                Document priceDocument = new CrawlText().getText(baseUrl + priceUrl,param);
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
                System.out.println(project);
                System.out.println("集合大小：" + prices.size() + "集合数据：" + prices);
            }
        }
    }
}
