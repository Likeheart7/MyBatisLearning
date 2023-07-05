package org.apache.chenx.example01;

import java.util.HashMap;

// 用于leetcode 代码测试
public class TestApplication {
    public static void main(String[] args) {
        String[] strs = new String[]{"flower","flow","flight"};
        if (strs.length <= 0)
            System.out.println("");
        int cnt = 1;
        for (; ; ) {
            String substring = strs[0].substring(0, cnt);
            for (String str : strs) {
                if (str.length() < cnt || !str.startsWith(substring)) {
                    System.out.println(strs[0].substring(0, cnt - 1));
                    return;
                }
            }
            cnt ++;
        }
    }
}
