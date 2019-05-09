package cn.com.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CrawlText
{
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
