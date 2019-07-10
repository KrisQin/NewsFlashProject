package com.blockadm.common.utils;
import android.os.Build;
import android.support.annotation.RequiresApi;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * Created by hp on 2019/1/7.
 * */

public class Base64Utils {

    private static Base64Utils utils = null;

    private Base64Utils(){

    }

    /**
     * 机能概要:单利 ，懒汉模式
     * @return
     */
    public static Base64Utils getInstance(){
        if(utils == null){
            synchronized (Base64Utils.class) {
                if(utils == null ){
                    utils = new Base64Utils();
                }
            }
        }
        return utils;
    }
    /**
     * 机能概要:获取文件的大小
     * @param inFile 文件
     * @return 文件的大小
     */
    public int getFileSize(File inFile){
        InputStream in = null;

        try {
            in = new FileInputStream(inFile);
            //文件长度
            int len = in.available();
            return len;
        }catch (Exception e) {
            // TODO: handle exception
        }finally{
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return -1;
    }
    BASE64Encoder base64Encoder = new BASE64Encoder();
    /**
    将文件转化为base64
     * @return
     * @throws Exception
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String file2Base64(File inFile){

        //将文件转化为字节码
        byte [] bytes = copyFile2Byte(inFile);
        if(bytes == null){
            return null;
        }

        //base64,将字节码转化为base64的字符串
        String result = base64Encoder.encode(bytes);
        return result;
    }

    /**
     * 机能概要:将文件转化为字节码
     * @param inFile
     * @return
     */
    private byte [] copyFile2Byte(File inFile){
        InputStream in = null;

        try {
            in = new FileInputStream(inFile);
            //文件长度
            int len = in.available();

            //定义数组
            byte [] bytes = new byte[len];

            //读取到数组里面
            in.read(bytes);
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally{
            try {
                if(in != null){
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    BASE64Decoder base64Decoder = new BASE64Decoder();
    /**
     * @param strBase64 base64 编码的文件
     * @param outFile 输出的目标文件地址
     * @return
     * @throws IOException
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean base64ToFile(String strBase64, File outFile){
        try {
            // 解码，然后将字节转换为文件
            byte[] bytes = base64Decoder.decodeBuffer(strBase64); // 将字符串转换为byte数组
            return copyByte2File(bytes,outFile);
        } catch (Exception ioe) {
            ioe.printStackTrace();
            return false;
        }
    }
    /**
     * 机能概要:将字节码转化为文件
     * @param bytes
     * @param file
     */
    private boolean copyByte2File(byte [] bytes,File file){
        FileOutputStream  out = null;
        try {
            //转化为输入流
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);

            //写出文件
            byte[] buffer = new byte[1024];

            out = new FileOutputStream(file);

            //写文件
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len); // 文件写操作
            }
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                if(out != null){
                    out.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }


}

