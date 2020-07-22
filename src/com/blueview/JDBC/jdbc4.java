package com.blueview.JDBC;

import java.util.List;

public class jdbc4 {

    public static void main(String[] args) {

        UserInfoFindAll list = new UserInfoFindAll();
        List<UserInfo> list1 = list.findAll();
//        System.out.println(list1);
//        System.out.println(list1.size());
        for (UserInfo userInfo : list1) {
            System.out.println(userInfo);
        }
    }
}
