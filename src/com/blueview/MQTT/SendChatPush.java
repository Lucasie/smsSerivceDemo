package com.blueview.MQTT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class SendChatPush {

    public void chatPush25(String tilt) throws Exception {
        System.setProperty("sun.net.client.defaultConnectTimeout", "30000"); /*连接超时：30秒*/
        System.setProperty("sun.net.client.defaultReadTimeout", "30000");	/*读取超时：30秒*/

        String url = "http://push.ijingniu.cn/send?key=2b5fbdcb528c4633bdda35291facacd5&head=蓝景国际&body=尊敬的zhangdong,您好!您管理的<展箱-1>发生倾斜,倾斜角度为:"+tilt+",请立即检处理!" ;
        StringBuffer buff = new StringBuffer();
        buff.append(url);
        URL url1 = new URL(buff.toString());
//        /*把buffer链接存入新建的URL中*/
        HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
        /*使用POST方式发送*/
        connection.setRequestMethod("POST");

        /*使用长链接方式*/
        connection.setRequestProperty("Connection", "Keep-Alive");

        /*微信推送*/
        BufferedReader reader = new BufferedReader(new InputStreamReader(url1.openStream()));

        /*获取返回值*/
        String result = reader.readLine();
        System.out.println(result);

    }

    public void chatPush13(int stat) throws IOException {
        String str ="" ;
        System.setProperty("sun.net.client.defaultConnectTimeout", "30000"); /*连接超时：30秒*/
        System.setProperty("sun.net.client.defaultReadTimeout", "30000");	/*读取超时：30秒*/
        // String tipGateway = new String("您好,尊敬的" + user + ",你管理的节点:" + adder + "发生"+str+",当前电流为:"+stat+"A,请检查处理!");
        if (stat == 1 ){
            str = "电流降低故障";
        }else if (stat == 2 ){
            str = "电流增加故障";
        }

        String url = "http://push.ijingniu.cn/send?key=2b5fbdcb528c4633bdda35291facacd5&head=蓝景国际&body=尊敬的zhangdong,您好!您管理的<展箱>发生"+str+",请立即检!" ;
        StringBuffer buff = new StringBuffer();
        buff.append(url);
        URL url1 = new URL(buff.toString());
//        /*把buffer链接存入新建的URL中*/
        HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
        /*使用POST方式发送*/
        connection.setRequestMethod("POST");

        /*使用长链接方式*/
        connection.setRequestProperty("Connection", "Keep-Alive");

        /*微信推送*/
        BufferedReader reader = new BufferedReader(new InputStreamReader(url1.openStream()));

        /*获取返回值*/
        String result = reader.readLine();
        System.out.println(result);

    }

    public void chatPush1a() throws IOException {
        System.setProperty("sun.net.client.defaultConnectTimeout", "30000"); /*连接超时：30秒*/
        System.setProperty("sun.net.client.defaultReadTimeout", "30000");	/*读取超时：30秒*/

        String url = "http://push.ijingniu.cn/send?key=2b5fbdcb528c4633bdda35291facacd5&head=蓝景国际&body=尊敬的zhangdong,您好!您管理的<展箱-1>出现温度过高,请立即检!" ;
        StringBuffer buff = new StringBuffer();
        buff.append(url);
        URL url1 = new URL(buff.toString());
//        /*把buffer链接存入新建的URL中*/
        HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
        /*使用POST方式发送*/
        connection.setRequestMethod("POST");

        /*使用长链接方式*/
        connection.setRequestProperty("Connection", "Keep-Alive");

        /*微信推送*/
        BufferedReader reader = new BufferedReader(new InputStreamReader(url1.openStream()));

        /*获取返回值*/
        String result = reader.readLine();
        System.out.println(result);

    }

    public void chatPush20() throws IOException {
        System.setProperty("sun.net.client.defaultConnectTimeout", "30000"); /*连接超时：30秒*/
        System.setProperty("sun.net.client.defaultReadTimeout", "30000");	/*读取超时：30秒*/

        String url = "http://push.ijingniu.cn/send?key=2b5fbdcb528c4633bdda35291facacd5&head=蓝景国际&body=尊敬的zhangdong,您好!您管理的<展箱-烟雾传感器>出现报警,请立即检!" ;
        StringBuffer buff = new StringBuffer();
        buff.append(url);
        URL url1 = new URL(buff.toString());
//        /*把buffer链接存入新建的URL中*/
        HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
        /*使用POST方式发送*/
        connection.setRequestMethod("POST");

        /*使用长链接方式*/
        connection.setRequestProperty("Connection", "Keep-Alive");

        /*微信推送*/
        BufferedReader reader = new BufferedReader(new InputStreamReader(url1.openStream()));

        /*获取返回值*/
        String result = reader.readLine();
        System.out.println(result);

    }

    public void chatPush19(String user,Double d,String adder) throws IOException {
        System.setProperty("sun.net.client.defaultConnectTimeout", "30000"); /*连接超时：30秒*/
        System.setProperty("sun.net.client.defaultReadTimeout", "30000");    /*读取超时：30秒*/
        String url = "http://push.ijingniu.cn/send?key=5068509ddaa949418ed4652af8647430&head=蓝景国际&body=您好,尊敬的"+user+":,你管理的节点:"+adder+"发生短路故障,当前电压为:"+d+"V,已经自动切断输出,请检查处理!";
        StringBuffer buff = new StringBuffer();
        buff.append(url);
        URL url1 = new URL(buff.toString());
//        /*把buffer链接存入新建的URL中*/
        HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
        /*使用POST方式发送*/
        connection.setRequestMethod("POST");

        /*使用长链接方式*/
        connection.setRequestProperty("Connection", "Keep-Alive");

        /*微信推送*/
        BufferedReader reader = new BufferedReader(new InputStreamReader(url1.openStream()));

        /*获取返回值*/
        String result = reader.readLine();
        System.out.println(result);
    }

}
