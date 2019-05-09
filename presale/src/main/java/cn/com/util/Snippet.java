package cn.com.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.nodes.Document;

/**
 * @author gaopengchao 2019年3月26日
 */
public class Snippet
{
    public static void main(String[] args)
    {
        try
        {
            Document text = new CrawlText().getText("http://fgj.xa.gov.cn/ygsf/index.aspx");
            System.out.println(text);
            // 西安预售证查询
            //List<PreSale> preSales = CrawlText.getText("http://fgj.xa.gov.cn/ygsf/index.aspx?page=1");
            //System.out.println(preSales);
            // 商品房价格公示
            // CrawlText.getText(true, true,
            // "http://wjj.xa.gov.cn/ptl/def/def/index_1285_3936.jsp?recid=4416419");
            // 商品住房价格公示项目名单（2019年第6批）（总第八十二批）
            // CrawlText.getText(true, true,
            // "http://wjj.xa.gov.cn/ptl/def/def/index_1285_3892.jsp?trid=3108482");
            // 价格查看附件页面
            // CrawlText.getText(true, true,"http://wjj.xa.gov.cn/ptl/def/def//websac/cat/3108482.html");
            // 下载附件
            //downLoadFromUrl("http://wjj.xa.gov.cn/attached/file/20190301/1551426294668068449.rar","1551426294668068449.rar","D:\\filePath");
            //System.out.println("success");

        }
        catch(Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 从网络Url中下载文件
     * 
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static String downLoadFromUrl(String urlStr, String fileName, String savePath)
    {
        try
        {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            // 防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // 得到输入流
            InputStream inputStream = conn.getInputStream();
            // 获取自己数组
            byte[] getData = readInputStream(inputStream);

            // 文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists())
            {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null)
            {
                fos.close();
            }
            if (inputStream != null)
            {
                inputStream.close();
            }
            return saveDir + File.separator + fileName;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 从输入流中获取字节数组
     * 
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException
    {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1)
        {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
