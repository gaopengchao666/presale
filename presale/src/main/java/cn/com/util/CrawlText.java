package cn.com.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlText {

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
    public static void getText(boolean autoDownloadFile, boolean Multithreading, String Url) throws IOException {

        //String rule = "abs:href";

        List<String> urlList = new ArrayList<String>();
            
        Document document = Jsoup.connect(Url)
                .timeout(8000)
                .ignoreContentType(true)
                .userAgent("Mozilla\" to \"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:50.0)")
                .get();
        System.out.println(document.toString());
        //Elements urlNode = document.select("a[href^=Lpb.aspx]");
        Elements urlNode = document.select(".ygsf_table1 tr");
        
        for (Element element : urlNode) {
            //urlList.add(element.attr(rule));
            urlList.add(element.toString());
        }
        
        System.out.println(urlList.toString());
        //CrawTextThread crawTextThread = new CrawTextThread(urlList);
        //crawTextThread.start();
        
    }

}
