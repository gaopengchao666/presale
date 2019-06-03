package cn.com.util;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.com.entity.PreSale;

/**
 * @author gaopengchao 2019年3月26日
 */
public class Snippet
{
    public static void main(String[] args)
    {
        try
        {
            //查询第一页项目
            /*Document text = new CrawlText().getText("http://117.39.29.75:8085/pricePublic/house/public/index");
            System.out.println(text);*/
            //查询第一个项目价格列表
            /*Document text = new CrawlText().getText("http://117.39.29.75:8085/pricePublic/house/public/price?id=8a901c2869db98390169dbee41ef07f0");
            System.out.println(text);*/
            // 西安预售证查询
            /*Document text = new CrawlText().getText("http://fgj.xa.gov.cn/ygsf/index.aspx?page=1");
            System.out.println(text);*/
            List<PreSale> preSales = getPreSales("http://fgj.xa.gov.cn/ygsf/index.aspx?page=1");
            System.out.println(preSales);
        }
        catch(Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static List<PreSale> getPreSales(String Url)
    {
        Document document = new CrawlText().getText(Url);
        Elements trs = document.select(".ygsf_table1 .ysztr");
        List<PreSale> presales = new ArrayList<PreSale>();
        for (Element tr : trs)
        {
            PreSale presale = new PreSale();
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
}
