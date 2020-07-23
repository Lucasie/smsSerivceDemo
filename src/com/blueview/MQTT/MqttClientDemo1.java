package com.blueview.MQTT;
import com.blueview.JDBC.GetUserInfo;

import org.eclipse.paho.client.mqttv3.*;

import java.math.BigInteger;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class MqttClientDemo1 {

    // 配置信息
    private static String HOST = "tcp://blueview.hk";
//    private static String HOST = "tcp://192.168.99.228";
//    private static String HOST = "tcp://kxbobo.com";
    private static String ID = "jdbc测试";
//    private static String PASSWORD = "Public";
    private static int QOS = 1;
    private static String TOPICS = "iot/server";
    private static String statCode ;
    private static String gateway;
    private static double status;
    static MqttClient mq = null;


    /**
     * 控制主题推送
     * @param tops  网关10进制
     * @param data  16进制数据
     * @throws MqttException
     */
    static void publishMessage(String tops, String data) throws MqttException {
        MqttMessage message = new MqttMessage();
        message.setQos(1);
        message.setPayload(HexUtils.toByteArray(data));
        MqttTopic topic = mq.getTopic(tops);
        topic.publish(message);

    }

    public static void main(String[] args) throws MqttException {

        // 添加options信息
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
//        options.setUserName("blueview");
//        options.setPassword(PASSWORD.toCharArray());
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);
        options.setAutomaticReconnect(true);

        // 实例化客户端
        mq = new MqttClient(HOST,ID);
        try {
            // 回调函数
            mq.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable throwable) {
                    System.out.println("连接已断开");
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    // 添加时间
                    SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
                    Date d = new Date();
    //                System.out.println(" Info from the server : " + mqttMessage.getPayload().toString().getBytes("utf-8"));
                    String str = HexUtils.toHexString(mqttMessage.getPayload());

                    // 过滤针头不是AABBCC和设备类型是00的消息
                   if ("aabbcc".equals(str.substring(0,6)) && !"00".equals(str.substring(28,30))){
                       System.out.println("Info from the server time: " + date.format(d) + ":" + str);
                       DataParser dt = new DataParser(str);
                       System.out.println("------------------------");
                       System.out.println("设备类型:" + dt.deviceType());
                       LinkedHashMap<String, String> parser = dt.parser(str);

//                    // 网关节点信息组合
                       String gatewayInfo = new BigInteger(parser.get("gateway"), 16).toString(10) + "%";

//                       ArrayList<String> user = new GetUserInfo(gatewayInfo).getUser();

                       //   电流报警
                       if (parser.containsKey("19")) {
                           statCode = new BigInteger(parser.get("19"), 16).toString(10);
                           status = (Double.valueOf(statCode)) / 10;
//                           gateway = new BigInteger(parser.get("gateway"), 16).toString(10) + parser.get("adder");
//                           System.out.println(gateway);
                           ArrayList<String> user = new GetUserInfo(gatewayInfo).getUser();
                           sendSms.gatewaySMS(user.get(0), user.get(1), parser.get("adder"), status);
                       }

                       //   烟雾报警
                       if (parser.containsKey("20")) {
                           ArrayList<String> user = new GetUserInfo(gatewayInfo).getUser();
                           new SendChatPush().chatPush20();
                           sendSms.smockAlarm(user.get(0), user.get(1), parser.get("adder"));
                       }

                       //   温度报警
                       if (parser.containsKey("1a")) {
                           ArrayList<String> user = new GetUserInfo(gatewayInfo).getUser();
                           String t = new BigInteger(parser.get("1a").substring(2, 4),16).toString(10);
                           sendSms.tempAlarm(user.get(0), parser.get("adder"), user.get(1), t);
//                            new SendChatPush().chatPush1a();
                           System.out.println("温度值为:" + t);
                       }

                       //   倾斜传感器
                       if (parser.containsKey("25")) {
                           ArrayList<String> user = new GetUserInfo(gatewayInfo).getUser();
                           String t = parser.get("25").substring(2, 4);
                           String s1 = new BigInteger(t, 16).toString(10);
                           sendSms.tiltAlarm(user.get(0), parser.get("adder"), user.get(1), s1);
//                            new SendChatPush().chatPush25(s1);
                       }

                       //   门磁传感器
                        if (parser.containsKey("21")){
                            ArrayList<String> user = new GetUserInfo(gatewayInfo).getUser();
                            String distance = parser.get("21");
                            System.out.println(distance);

                        }

                   }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    System.out.println(iMqttDeliveryToken.isComplete());
                }
            });

            mq.connect(options);
            mq.subscribe(TOPICS, 1);
        }catch (Exception e){
            System.out.println(e);;
        }

    }
}