package com.orange.jzchi.jzframework.tool;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;


public final class FormatConvert {
    private FormatConvert() {
        // this is just a helper class.
    }

    public static CharSequence StringToByteMsg(CharSequence cs){
        StringBuffer sb = new StringBuffer();
        int x=0;
        for (int i=0;i<cs.length();i++){
            x++;
            sb.append(cs.charAt(i));
            if (x==2){
                x=0;
                sb.append(" ");
            }
        }
        return sb;
    }

    // byte转char
    public static char[] getChars (byte[] bytes) {
        Charset cs = Charset.forName ("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate (bytes.length);
        bb.put (bytes);
        bb.flip ();
        CharBuffer cb = cs.decode (bb);

        return cb.array();
    }

    //ASCII中的16進位符號轉換成byte
    public static byte[] StringHexToByte(CharSequence cs){
        byte[] bytes = new byte[cs.length()/2];
        for (int i=0;i<(cs.length()/2);i++)
            bytes[i] = (byte) Integer.parseInt(cs.toString().substring(2*i,2*i+2),16);
        return bytes;
    }
    public static byte[] StringHexToByte(StringBuilder sb){
        byte[] bytes = new byte[sb.length()/2];
        for (int i=0;i<(sb.length()/2);i++)
            bytes[i] = (byte) Integer.parseInt(sb.toString().substring(2*i,2*i+2),16);
        return bytes;
    }


    public static String bytesToHex(byte[] hashInBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
    public static byte[] InttoByte(int Num, int ByteSize) {
        byte[] bytes = ByteBuffer.allocate(ByteSize).putInt(Num).array();
        for (byte b : bytes) {
            System.out.format("0x%x ", b);
        }
        return bytes;
    }

    public static int ByteArray2Int(byte[] b){
        int value=0;
        for(int i=3;i>-1;i--){
            value+=(b[i]&0x000000ff)<<(8*(4-1-i));
        }
        return value;
    }

    public static String convertASCII(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        String str = new String(byteArray);
        return str;
    }

    public static byte[] StringToASCIIbyte(String data){
        byte[] B = new byte[data.length()];
        for (int i = 0; i < data.length(); i++) {
            B[i]= (byte) data.charAt(i);
        }
        return B;
    }


    public static String DisplayTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("kk:mm:ss.SS");
        SimpleDateFormat formatter_D = new SimpleDateFormat("yyyy-hh:mm:ss");
        String date = formatter.format(new java.util.Date());
        return date;
    }

    public static byte[] getBooleanArray(byte b) {
        byte[] array = new byte[8];
        for (int i = 7; i >= 0; i--) {
            array[i] = (byte)(b & 1);
            b = (byte) (b >> 1);
        }
        return array;
    }

    public static int twoByteToInt(byte h,byte l){
        int H = h;
        if(l<0){
            H = h+1;
        }
        return (H << 8) + l;
    }
}
