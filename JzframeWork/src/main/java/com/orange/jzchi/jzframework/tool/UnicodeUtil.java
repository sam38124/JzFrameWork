package com.orange.jzchi.jzframework.tool;

import android.util.Log;

public class UnicodeUtil {

    /**
     * 将utf-8的汉字转换成unicode格式汉字码
     * @param str
     * @return
     */

    public static String stringToUnicode(String str) {
        str = (str == null ? "" : str);
        String tmp;
        StringBuffer sb = new StringBuffer(1000);
        char c;
        int i, j;
        sb.setLength(0);
        for (i = 0; i < str.length(); i++)
        {
            c = str.charAt(i);
            sb.append("\\\\u");
            j = (c >>>8); //取出高8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);
            j = (c & 0xFF); //取出低8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);

        }
        return (new String(sb));
    }

    /**
     * 将unicode的汉字码转换成utf-8格式的汉字
     * @param unicode
     * @return
     */
    public static String unicodeToString(String unicode) {
        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {

//        System.out.println(hex[i].length());
            if(hex[i].length()>4){string.append(hex[i].substring(4));
            }
            int data=Integer.parseInt(hex[i].substring(0,4), 16);
            // 追加成string
            string.append((char) data);
        }
        Log.e("hex", ""+hex.length);
        return (hex.length<=1) ? unicode:string.toString();
    }

    public static void main(String[] args) {
        String str = "你好吗？ How are you";
        //String test2 = "0xu4f600xu597d0xu54170xuff1f0xu200xu480xu6f0xu770xu200xu610xu720xu650xu200xu790xu6f0xu75";
        String unicode = stringToUnicode(str);
        String string = unicodeToString(unicode);
        System.out.println("转换成unicode格式的:\n"+unicode);
        System.out.println("转换成汉字UTF-8格式的:\n"+string);
    }

}