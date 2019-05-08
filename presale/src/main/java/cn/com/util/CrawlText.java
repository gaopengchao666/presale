package cn.com.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.com.entity.PreSale;

public class CrawlText
{

    /**
     * 获取预售证所有页数
     * @param Url
     * @return
     */
    public static int getPreSalePage(String Url)
    {
        Document document = null;
        try
        {
            document = Jsoup.connect(Url).timeout(8000).ignoreContentType(true)
                    .userAgent("Mozilla\" to \"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:50.0)").get();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        Elements totalPageA = document.select(".ygsf_pager>a:eq(7)");
        String totalPageUtl = totalPageA.attr("href");
        int totalPage = Integer.parseInt(totalPageUtl.split("=")[1]);
        return totalPage;
    }
    
    /***
     * 获取文本
     * 
     * @param autoDownloadFile
     *            自动下载文件
     * @param Multithreading
     *            多线程 默认false
     * @param Url
     *            网站链接
     * @throws IOException
     */
    public static List<PreSale> getText(String Url)
    {
        Document document = null;
        try
        {
            document = Jsoup.connect(Url).timeout(8000).ignoreContentType(true)
                    .userAgent("Mozilla\" to \"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:50.0)").get();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        Elements trs = document.select(".ygsf_table1 tr:gt(0)");
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
