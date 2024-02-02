package com.maishibing.decorator.example02;


import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * 具体装饰者类-对文件内容进行加密和解密
 * @author spikeCong
 * @date 2022/9/27
 **/
public class EncryptionDataDecorator extends DataLoaderDecorator {

    public EncryptionDataDecorator(DataLoader dataLoader) {
        super(dataLoader);
    }

    @Override
    public String read() {
        return decode(super.read());
    }

    @Override
    public void write(String data) {
        super.write(encode(data));
    }

    //加密操作
    public String encode(String data){

        try {
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] bytes = data.getBytes("utf-8");

            String result = encoder.encodeToString(bytes);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //解密操作
    public String decode(String data){
        try {
            Base64.Decoder decode = Base64.getDecoder();
            String result = new String(decode.decode(data),"utf-8");

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




}
