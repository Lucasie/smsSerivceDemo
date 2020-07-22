package com.blueview.MQTT;

import java.io.IOException;

public class testMain {

    public static void main(String[] args) throws IOException {
//        new SendChatPush().chatPush19("admin",125.0);

//        new SendChatPush().chatPush20();
//        new SendChatPush().chatPush19("zhangdong",19.0);
//        new SendChatPush().chatPush13(2);
//        new sendSms().smockAlarm("zhangdong","18980739868","China");
//        sendSms.smockAlarm("zhangdong","18980739868","China");
//        sendSms.dianLiu("张东","18980739868","展箱",2);
        sendSms.gatewaySMS("zhangdong","18980739868","展箱",1.0);
    }
}
