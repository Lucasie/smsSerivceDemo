package com.blueview.MQTT;
import com.blueview.JDBC.GetUserInfo;
import org.apache.commons.codec.binary.Hex;
import org.eclipse.paho.client.mqttv3.*;
import javax.lang.model.element.NestingKind;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class MqttClientDemo {

    // 配置信息
    private static String HOST = "tcp://blueview.hk";
//    private static String HOST = "tcp://192.168.99.228";
//    private static String HOST = "tcp://kxbobo.com";
    private static String ID = "Java客户端测试";
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
                    System.out.println("Info from the server time: " + date.format(d) + ":" + str);
                    DataParser dt = new DataParser(str);
                    System.out.println("------------------------");
                    System.out.println("设备类型:" + dt.deviceType());
                    LinkedHashMap<String, String> parser = dt.parser(str);

                    if ( str.indexOf("000311c3e8872ed2") >= 8){
                        if (parser.containsKey("13")) {
                            statCode = new BigInteger(parser.get("13"), 16).toString(10);
                            int statues = Integer.parseInt(statCode);
                            System.out.println("状态码:" + status);
                            gateway = new BigInteger(parser.get("gateway"), 16).toString(10) + parser.get("adder");
                            new SendChatPush().chatPush13((int) status); // 微信
                            sendSms.dianLiu("zhangdong", "18543129521", "展箱-1", statues);


                        }

                        // 获取指定错误码和短地址信息
                        if (parser.containsKey("19")) {
                            statCode = new BigInteger(parser.get("19"), 16).toString(10);
                            status = (Double.valueOf(statCode)) / 10;
                            gateway = new BigInteger(parser.get("gateway"), 16).toString(10) + parser.get("adder");
                            System.out.println(gateway);
                            sendSms.gatewaySMS("zhangdong", "18543129521", "展箱-1", status);
                            new SendChatPush().chatPush19("zhangdong", status, "展箱-1");

                            // 数据库查询
                            //                    ArrayList<String> user = new GetUserInfo(gateway).getUser();
                            //                    if (user != null && user.size() == 3) {
                            //                        String name = user.get(0);
                            //                        String phone = user.get(1);
                            //                        String adder = user.get(2);
                            //                        System.out.println("手机号为:"+phone);
                            //                        new SendChatPush().chatPush19(name, status, adder); // 微信
                            //                        if (phone != null) {

                        }

                        if (parser.containsKey("20")) {
                            new SendChatPush().chatPush20();
                            sendSms.smockAlarm("zhangdong", "18543129521", "展箱-1");
                            publishMessage("863958047600338","11223308A20600005270be8f");
                        }

                        if (parser.containsKey("1a")) {
                            String t = parser.get("1a").substring(2, 4);
                            sendSms.tempAlarm("zhangdong", "展箱-1", "18543129521", t);
                            new SendChatPush().chatPush1a();
                            System.out.println("温度值为:" + t);
                            publishMessage("863958047600338","11223308A20600005270be8f");
                        }

                        // 倾斜报警
                        if (parser.containsKey("25")) {
                            String t = parser.get("25").substring(2, 4);
                            String s1 = new BigInteger(t, 16).toString(10);  // 倾斜
                            System.out.println(s1);
                            //                    System.out.println(Key +":"+ new BigInteger(Value,16).toString(10));
//                            sendSms.tiltAlarm("zhangdong", "展箱-1", "18543129521", s1);
                            sendSms.tiltAlarm("zhangdong", "展箱-1", "18543129521", s1);
                            new SendChatPush().chatPush25(s1);
                            publishMessage("863958047600338","11223308A20600005270be8f");
                        }

                        // 侵水报警
                        if (parser.containsKey("46")) {
                            // 触发直接报警
                            System.out.println("侵水报警");
                        }


                        for (Map.Entry<String, String> item : parser.entrySet()) {
                            String Key = item.getKey();
                            String Value = item.getValue();
                            //                    System.out.println(Key +":"+ new BigInteger(Value,16).toString(10));
                            System.out.println(Key + ":" + Value);

                        }

                        System.out.println("---------------------------------------------------------------------------------");

                    }
                    // 燕山触控屏
                    if(str.indexOf("311c3e885ce6b") >=8) {
                        for (Map.Entry<String, String> item : parser.entrySet()) {
                            String Key = item.getKey();
                            String Value = item.getValue();
                            System.out.println(Key + ":" + Value);
                        }
                        if (parser.containsKey("81") && parser.containsKey("80")){
                            String light = parser.get("81");
                            String room = parser.get("80");
                            System.out.println(light +":" + room);
                            publishMessage("863958047571380","1122331cA206"+light+"18e6 1ded 399c f419 9144 edc5 71f1 9136 5e48 f7b3 e682 a65b".replace(" ",""));
                            System.out.println("调光完成");
                        }


                    }
                    // 触控屏测试
                    if(str.indexOf("311c3e885cd32") >=8) {
                        for (Map.Entry<String, String> item : parser.entrySet()) {
                            String Key = item.getKey();
                            String Value = item.getValue();
                            System.out.println(Key + ":" + Value);
                        }
                        if (parser.containsKey("81") && parser.containsKey("80")){
//                            String light = new BigInteger(parser.get("81"),16).toString(10) ;
                            String light = parser.get("81") ;
                            String room = parser.get("80");
                            System.out.println(light +":" + room);
                            publishMessage("863958047509810","11223320A206"+light+"1408c7f8863070a047429bdd8b32956c7a9d5378afe7");
//                            publishMessage("863958047571349","11223326A206"+light+"21e0 7378 394a fd8e e257 5d34 cd61 b88a 3857 9daa 53f7 6fdd 23b3  c3fb dc22 a5b1  7334".replace(" ",""));
                            // 1-7 3/4号网关 863958047571349:3    863958047571372:4
//                            publishMessage("863958047571349","11223326A206"+light+"21e07378394afd8ee2575d34cd61b88a38579daa53f76fdd23b3c3fbdc22a5b17334".replace(" ",""));
//                            Thread.sleep(1000);
//                            publishMessage("863958047571372","11223326A206"+light+"899c132e8d4535722a1689e2dba07f96f34de2d89f873e6c2403f8532e4cfc09575c".replace(" ",""));
//                            Thread.sleep(1000);
//                            publishMessage("863958047571349","11223326A206"+light+"cd74f69090154c5c81d99aab3f4ff5c7591cd3144ff6f4002cb7c7a11e35f3e952f3".replace(" ",""));
//                            Thread.sleep(1000);
//                            publishMessage("863958047571372","11223326A206"+light+"d69c5eec8778e7459ed655f262ed11d5d440c9c6b59190c370d27c3ce571f53aa4c7".replace(" ",""));
//                            Thread.sleep(1000);
//                            publishMessage("863958047571349","11223326A206"+light+"b8c6944f43ce08df92ee7d0636aaac3f32dbc626bf083c18aa48903eb144dc50f4f1".replace(" ",""));
//                            Thread.sleep(1000);
//                            publishMessage("863958047571372","11223326A206"+light+"fd6628ed2d292cae614be8bc8c7f8fccc412730ade31d74e3ce59a28ded948d0941a".replace(" ",""));
//                            Thread.sleep(1000);
//                            publishMessage("863958047571349","1122331AA206"+light+"e7b4896df717449a9499eecb263706edc80dea123630".replace(" ",""));

//
//                            System.out.println(String.valueOf(Integer.parseInt(light) * 100));

//                            String nums = Integer.toHexString( Integer.valueOf(new BigInteger(light,16).toString(10)) * 10);
//                            System.out.println("16进制："+nums );
//                            if (nums.length() == 1){
//                                publishMessage("863958047571349","11223307A10C0E000"+nums+"01A2");
//                            }
//                            if (nums.length() == 2){
//                                publishMessage("863958047571349","11223307A10C0E00"+nums+"01A2");
//
//                            }
//                            if (nums.length() == 3){
//                                publishMessage("863958047571349","11223307A10C0E0"+nums+"01A2");
//
//                            }


//                            publishMessage("863958047571349","11223307A10C0E"+hexStr+"01A2");
                            System.out.println("调光完成,当前亮度为:" + new BigInteger(light,16).toString(10));


                    }

                    // 唐经理展箱
                    if (str.indexOf("311c3e885cdaf") >= 8){
//                        System.out.println("开始发送16进制");
//                        publishMessage("863958047509935","11223308A20600005378956c");
//                        System.out.println("主题发送完毕");


                        if (parser.containsKey("13")) {
                            statCode = new BigInteger(parser.get("13"), 16).toString(10);
                            int statues = Integer.parseInt(statCode);
                            System.out.println("状态码:" + status);
                            gateway = new BigInteger(parser.get("gateway"), 16).toString(10) + parser.get("adder");
//                            new SendChatPush().chatPush13((int) status); // 微信
                            sendSms.dianLiu("zhangdong", "15281085057", "展箱-1", statues);

                        }

                        // 获取指定错误码和短地址信息
                        if (parser.containsKey("19")) {
                            statCode = new BigInteger(parser.get("19"), 16).toString(10);
                            status = (Double.valueOf(statCode)) / 10;
                            gateway = new BigInteger(parser.get("gateway"), 16).toString(10) + parser.get("adder");
                            System.out.println(gateway);
                            sendSms.gatewaySMS("tangjin", "15281085057", "展箱-1", status);
//                            new SendChatPush().chatPush19("tangjin", status, "展箱-1");

                            // 数据库查询
                            //                    ArrayList<String> user = new GetUserInfo(gateway).getUser();
                            //                    if (user != null && user.size() == 3) {
                            //                        String name = user.get(0);
                            //                        String phone = user.get(1);
                            //                        String adder = user.get(2);
                            //                        System.out.println("手机号为:"+phone);
                            //                        new SendChatPush().chatPush19(name, status, adder); // 微信
                            //                        if (phone != null) {

                        }

                        if (parser.containsKey("20")) {
                            new SendChatPush().chatPush20();
                            sendSms.smockAlarm("tangjin", "15281085057", "展箱-1");
                        }

                        if (parser.containsKey("1a")) {
                            String t = parser.get("1a").substring(2, 4);
                            sendSms.tempAlarm("tangjin", "展箱-1", "15281085057", t);
//                            new SendChatPush().chatPush1a();
                            System.out.println("温度值为:" + t);
                        }

                        // 倾斜报警
                        if (parser.containsKey("25")) {
                            String t = parser.get("25").substring(2, 4);
                            String s1 = new BigInteger(t, 16).toString(10);  // 倾斜
                            System.out.println(s1);
                            //                    System.out.println(Key +":"+ new BigInteger(Value,16).toString(10));
                            sendSms.tiltAlarm("tangjin", "展箱-1", "15281085057", s1);
//                            new SendChatPush().chatPush25(s1);
                        }

                        // 侵水报警
                        if (parser.containsKey("46")) {
                            // 触发直接报警
                            System.out.println("侵水报警");
                        }

                        for (Map.Entry<String, String> item : parser.entrySet()) {
                            String Key = item.getKey();
                            String Value = item.getValue();
                            //                    System.out.println(Key +":"+ new BigInteger(Value,16).toString(10));
                            System.out.println(Key + ":" + Value);

                        }
                        System.out.println("---------------------------------------------------------------------------------");
                    }
                } }

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