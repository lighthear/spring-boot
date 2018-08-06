package com.example.springboot.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@Service("TransUrlService")
public class TransUrlServiceImpl implements TransUrlService {
    @Override
    public String getTransUrl(String hostPath, String item_id, String baiduFlag) {
        String api = "http://api.weibo.com/2/short_url/shorten.json"; // json
        // String api = 'http://api.t.sina.com.cn/short_url/shorten.xml'; // xml
        String source = "2849184197";     //iPad新浪客户端App Key：2849184197
        String url_item = hostPath + "/item.html?item_id=" + item_id;  //自动获取服务器路径，并根据get的item_id生成商品链接
        try {
            //百度翻译网页版---直接调用翻译网页的URL
            String url_baidu = "http://fanyi.baidu.com/transpage?query="+ URLEncoder.encode(url_item, "UTF-8") + "&from=en&to=zh&source=url&render=1";//调用百度翻译网页翻译的url，将原商品链接编码后传给百度翻译接口，防止商品url中的参数被识别成百度翻译参数的问题
            String url_long = url_baidu;	//若选择百度翻译则长链接为翻译链接
            if("0".equals(baiduFlag)){
                url_long = url_item;	//若不选择百度翻译则长链接为商品链接
            }

            //由于之前的网址中含有#号，在短链接转换时会被当成参数，因此需要将链接先进行编码，再传给新浪短链接转换的接口
            url_long = URLEncoder.encode(url_long, "UTF-8");

            String request_url = api + "?source=" + source + "&url_long=" + url_long;

            return httpGetMethod(request_url);	//打开网页URL获得网页内容，返回字符串，内容为微博短链接api返回的json格式

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 向指定URL发送GET方法的请求
     *
     */
    private String httpGetMethod(String url) {
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            System.out.println("Exception occur when send http get request!");
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
}
