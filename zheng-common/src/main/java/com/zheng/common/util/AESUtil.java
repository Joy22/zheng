package com.zheng.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES加解密工具类
 * @author Joy
 * @date 2018/2/28
 */
public class AESUtil {

    private static final String ENCODE_RULES = "zheng";

    /**
     * 加密
     * 1.构造密钥生成器
     * 2.根据encodeRules规则初始化密钥生成器
     * 3.产生密钥
     * 4.创建和初始化密码器
     * 5.内容加密
     * 6.返回字符串
     * @param context
     * @return
     */
    public static String aesEncode(String context){
        try {
            // 1.构造密钥生成器，指定位AES算法，不区分大小写
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            // 2.根据encodeRules规则初始化密钥生成器
            // 生成一个128位的随机源，根据传入的字节数组
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(ENCODE_RULES.getBytes());
            keyGenerator.init(128, random);
            // 3.产生原始对称密钥
            SecretKey originKey = keyGenerator.generateKey();
            // 4.获得原始对称密钥的字节数组
            byte[] raw = originKey.getEncoded();
            // 5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            // 6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 7.初始化密码生成器，第一个参数为加密（Encrypt_mode）或者解密（Decrypt_mode）操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 8.获取加密内容的字节数组（这里要设置为utf-8）不然内容如果有中文和英文混合中文就会解密为乱码
            byte[] byteEncode = context.getBytes("utf-8");
            // 9.根据密码器的初始化方式--加密：将数据加密
            byte[] byteAES = cipher.doFinal(byteEncode);
            // 10.将加密后的数据转换为字符串
            String aesEncode = new String(new BASE64Encoder().encode(byteAES));
            // 11.将字符串返回
            return aesEncode;
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        // 如果有错返回null
        return null;
    }

    /**
     * 解密
     * 1.同加密1-4步
     * 2.将加密后的字符串反纺成byte[]数组
     * 3.将加密内容解密
     * @param context
     * @return
     */
    public static String aesDecode(String context){
        try {
            // 1.构造密钥生成器，指定为AES算法，不区分大小写
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            // 2.根据encodeRules规则初始化密码生成器
            // 生成一个128位的随机源，根据传入的字节数组
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(ENCODE_RULES.getBytes());
            keyGenerator.init(128, random);
            // 3.产生原始对称密钥
            SecretKey originalKey = keyGenerator.generateKey();
            // 4.获得原始对称密钥的字节数组
            byte[] raw = originalKey.getEncoded();
            // 5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            // 6.根据指定算法AES生成AES密钥
            Cipher cipher = Cipher.getInstance("AES");
            // 7.初始化密码器，第一个参数为加密（Encrypt_mode）或者解密（Decrypt_mode）操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 8.将加密并解码后的内容解码成字节数组
            byte[] byteContent = new BASE64Decoder().decodeBuffer(context);
            // 解密
            byte[] byteDecode = cipher.doFinal(byteContent);
            String aseDecode = new String(byteDecode, "utf-8");
            return aseDecode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        // 如果有错返回null
        return null;
    }

    public static void main(String[] args) {
        System.out.println(AESUtil.aesEncode("12345678"));
    }
}
