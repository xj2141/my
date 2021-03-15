package com.example.demo.utils;

import java.util.Random;

//生成账号密码
public class GenerateUtil {

    public static String generateUserName() {
        Random r = new Random();
        int len = r.nextInt(6) + 5;//设定用户名长度
        // 26个小写字母+10个数字
        final int maxNum = 36;
        int i; // 生成的随机数
        int count = 0; // 生成的用户名的长度
        char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer username = new StringBuffer("");
        while (count < len) {
            i = r.nextInt(maxNum);
            username.append(str[i]);
            count++;
        }
        return username.toString();
    }

    public static String generatePassword() {
        Random r = new Random();
        int pwd_len = r.nextInt(10) + 6;//设定密码长度
        // 26*2个字母+10个数字
        final int maxNum = 62;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        while (count < pwd_len) {
            i = r.nextInt(maxNum);
            pwd.append(str[i]);
            count++;

        }
        return pwd.toString();
    }
}
