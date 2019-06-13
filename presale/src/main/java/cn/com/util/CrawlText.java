package cn.com.util;

import java.io.IOException;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
public class CrawlText
{
    /***
     * 获取文本
     * @param Url 网站链接
     * @param params 参数
     */
    public Document getText(String Url,Map<String,String> params)
    {
        Document document = null;
        try
        {
            document = Jsoup.connect(Url).data(params).timeout(8000).ignoreContentType(true)
                    .userAgent("Mozilla\" to \"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:50.0)").get();
        }
        catch(IOException e)
        {
            log.error("远程获取数据超时或报错url:" + Url + "详情：" + e.getStackTrace());
        }
        return document;
    }
    
    /***
     * 获取文本
     * @param Url网站链接
     */
    public Document getText(String Url)
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
        return document;
    }
}
