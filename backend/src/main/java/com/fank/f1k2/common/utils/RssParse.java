package com.fank.f1k2.common.utils;

import java.net.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fank.f1k2.business.entity.BookDetailInfo;
import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class  RssParse {
    public static List<String> getImgSrc(String htmlStr) {
        String img = "";
        Pattern p_image;
        Matcher m_image;
        List<String> pics = new ArrayList<String>();
//		 String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            img = img + "," + m_image.group();
            // Matcher m =
            // Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); //匹配src
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }
    public static List<BookDetailInfo> parseRss(String rss) {
        List<BookDetailInfo>  rssList = new ArrayList<>();
        try {
//            // 初始化proxy对象参数为代理IP地址，访问端口
//            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
//            URL url = new URL(rss);
//            URLConnection UC = url.openConnection(proxy);
//            UC.setRequestProperty("accept", "accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
//            UC.setRequestProperty("connection", "Keep-Alive");
//            UC.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
//            UC.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 不使用代理，直接创建URL连接
            URL url = new URL(rss);
            URLConnection UC = url.openConnection(); // 移除了代理参数

            UC.setRequestProperty("accept", "accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            UC.setRequestProperty("connection", "Keep-Alive");
            UC.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            UC.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//重点

            // https 忽略证书验证
            if (rss.startsWith("https")) { // 优化了判断条件
                SSLContext ctx = MyX509TrustManagerUtils();
                ((HttpsURLConnection) UC).setSSLSocketFactory(ctx.getSocketFactory());
                ((HttpsURLConnection) UC).setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String arg0, SSLSession arg1) {
                        return true;
                    }
                });
            }

            // 读取Rss源
            XmlReader reader = new XmlReader(UC);
            System.out.println("Rss源的编码格式为：" + reader.getEncoding());
            SyndFeedInput input = new SyndFeedInput();
            // 得到SyndFeed对象，即得到Rss源里的所有信息
            SyndFeed feed = input.build(reader);
            //System.out.println(feed);
            // 得到Rss新闻中子项列表
            List entries = feed.getEntries();
            // 循环得到每个子项信息
            for (int i = 0; i < entries.size(); i++) {
                BookDetailInfo rssHistory = new BookDetailInfo();
                SyndEntry entry = (SyndEntry) entries.get(i);
                // System.out.println("==="+entry.getContents());
                // System.out.println("---"+entry.getContents().size());
                // 标题、连接地址、标题简介、时间是一个Rss源项最基本的组成部分
                // System.out.println("标题：" + entry.getTitle());
                rssHistory.setTitle(entry.getTitle());
                rssHistory.setName(entry.getTitle());
                // System.out.println("连接地址：" + entry.getLink());
                rssHistory.setLink(entry.getLink());
                SyndContent description = entry.getDescription();
                if(entry.getContents().size()!=0){
                    rssHistory.setWebImg(String.join(",", getImgSrc(entry.getContents().get(0).toString())));
                    // System.out.println("图片地址"+rssHistory.getWebImg());
                }else{
                    rssHistory.setWebImg(String.join(",", getImgSrc(description.getValue())));
                    // System.out.println("图片地址"+rssHistory.getWebImg());
                }
                // System.out.println("标题简介：" + description.getValue());
                rssHistory.setValue(description.getValue());
                // System.out.println("发布时间：" + entry.getPublishedDate());
                if(entry.getPublishedDate() != null) {
                    rssHistory.setPublishedDate(entry.getPublishedDate().toString());
                }
                // 以下是Rss源可先的几个部分
                // System.out.println("标题的作者：" + entry.getAuthor());
                rssHistory.setAuthor(entry.getAuthor());
                // 此标题所属的范畴
                List categoryList = entry.getCategories();
                if (categoryList != null) {
                    List<String> typeList = new ArrayList<>();
                    for (int m = 0; m < categoryList.size(); m++) {
                        SyndCategory category = (SyndCategory) categoryList.get(m);
                        // System.out.println("此标题所属的范畴：" + category.getName());
                        typeList.add(category.getName());
                    }
                    rssHistory.setType(typeList.toString());
                }
                // 得到流媒体播放文件的信息列表
                List enclosureList = entry.getEnclosures();
                if (enclosureList != null) {
                    List<List> videoList = new ArrayList<>();
                    for (int n = 0; n < enclosureList.size(); n++) {
                        SyndEnclosure enclosure = (SyndEnclosure) enclosureList.get(n);
                        System.out.println("流媒体播放文件：" + entry.getEnclosures());
                        videoList.add(entry.getEnclosures());
                    }
                    rssHistory.setVideo(videoList.toString());
                }
                rssList.add(rssHistory);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rssList;
    }

    public static SSLContext MyX509TrustManagerUtils() {

        TrustManager[] tm = { new RssParse().new MyX509TrustManager() };
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, tm, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ctx;
    }

    /*
     * HTTPS忽略证书验证,防止高版本jdk因证书算法不符合约束条件,使用继承X509ExtendedTrustManager的方式
     */
    class MyX509TrustManager extends X509ExtendedTrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1, Socket arg2) throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1, SSLEngine arg2)
                throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1, Socket arg2) throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1, SSLEngine arg2)
                throws CertificateException {
            // TODO Auto-generated method stub

        }

    }


    public static void setProxy(String host) {
        System.setProperty("proxySet", "true");
        System.setProperty("proxyHost", host);
    }

}
