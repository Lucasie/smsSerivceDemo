package com.blueview.MQTT;

import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


    /*
        1.网关给服务器发送消息,前28位位固定长度(aabbcc0e000311c3e886f597ad05)
        2.从第29位开始,判断设备类型,参数编号
     */

public class DataParser {

    private String data;

    public DataParser() {
    }

    public DataParser(String data) {
        this.data = data;
    }

    // 设备类型
    /*
        A1: 网关本身
        A2: LED电源控制器
        A3: 可控硅调光设备
        B1: 筒灯广播设备
        C8: 烟雾传感器
        Note: 所有信息均是2字节长度,唯独A1设备下02(SIM卡号信息是8字节)
     */

    // 网关信息
    public String gatewayInfo(){
        if (null == data && data.length() < 28 && data.length() % 2 != 0) {
            return null;
        }
        return data.substring(8,28);
    }

    // 设备类型
    public String deviceType() {
        if (null == data && data.length() < 28 && data.length() % 2 != 0) {
            return null;
        }
        return data.substring(28, 30);
    }

    // 参数解析
    public LinkedHashMap<String, String> parser(String str) {
        if (null == str && str.length() < 28) {
            return null;
        }

        // A2:LED电源控制器
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        String sub = str.substring(30);
        String type = deviceType();
        if ("a2".equals(type) && (sub.length() % 6 == 0)) {
                map = formatParser(sub);
            // A1:网关
        } else if ("a1".equals(type)) {
            String replace = null;
            int index = str.lastIndexOf("02");
            if (index % 2 == 0 && str.substring(index).length() >= 42) {
                //  1.获取02字节的整个长度
                //  02:SIM:卡号
                String sub12 = str.substring(index,18 + index);
                System.out.println(sub12);
                replace = sub.replace(sub12, "");       // 从参数里替换掉SIM号信息
                System.out.println(replace);
                String key = sub12.substring(0, 2);
                String value = sub12.substring(2);
                map.put(key, value);
                // 处理后续
                System.out.println(replace);
                LinkedHashMap<String, String> parserMap = formatParser(replace);

                Iterator iterator = parserMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry  iterNext = (Map.Entry) iterator.next();
                    Object next = iterNext.getKey();
                    map.put(next.toString(), parserMap.get(next));
                }
            } else {
//                String a1 = sub.substring(sub.lastIndexOf("a1") + 2);
                System.out.println(sub);
                map = formatParser(sub);
            }
        // C8烟雾报警器
        }else if ("c8".equals(type) && (sub.length() % 6 == 0)){
                map = formatParser(sub);
        }else if ("a3".equals(type) && (sub.length() % 6 == 0)){
            map = formatParser(sub);
        }else if ("b1".equals(type) && (sub.length() % 6 == 0)){
            map = formatParser(sub);
        }else if ("a4".equals(type) && (sub.length() % 6 == 0)){
            map = formatParser(sub);
        }else if ("c9".equals(type) && (sub.length() % 6 == 0)){
            map = formatParser(sub);
        }else if ("ca".equals(type) && (sub.length() % 6 == 0)){
            map = formatParser(sub);
        }else if ("cc".equals(type) && (sub.length() % 6 == 0)){
            map = formatParser(sub);
        }

        return map;
    }

    // 参数解析
    private LinkedHashMap<String, String> formatParser(String sub) {
        String gateway = gatewayInfo();
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < sub.length() / 6; i++) {
            int step = i * 6;
            String list = sub.substring(step, step + 6);
            map.put("gateway", gateway.substring(0,16));
            map.put("adder", gateway.substring(16));
            map.put(list.substring(0, 2), list.substring(2));
        }
        return map;
    }
}



