package com.amp.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class MD5Utils {

    private static final String HEX_NUMS_STR = "0123456789ABCDEF";
    private static final Integer SALT_LENGTH = 12;
    private static MessageDigest MD5 = null;

    /**
     * 将16进制字符串转换成字节数组
     *
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4
                    | HEX_NUMS_STR.indexOf(hexChars[pos + 1]));
        }
        return result;
    }


    /**
     * 将指定byte数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    public static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * 验证口令是否合法
     *
     * @param password
     * @param passwordInDb
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static boolean validPassword(String password, String passwordInDb) {
        //将16进制字符串格式口令转换成字节数组
        byte[] pwdInDb = hexStringToByte(passwordInDb);
        //声明盐变量
        byte[] salt = new byte[SALT_LENGTH];
        //将盐从<a href="http://lib.csdn.net/base/mysql" class='replace_word' title="MySQL知识库" target='_blank' style='color:#df3434; font-weight:bold;'>数据库</a>中保存的口令字节数组中提取出来
        System.arraycopy(pwdInDb, 0, salt, 0, SALT_LENGTH);
        //创建消息摘要对象
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //将盐数据传入消息摘要对象
        md.update(salt);
        //将口令的数据传给消息摘要对象
        try {
            md.update(password.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //生成输入口令的消息摘要
        byte[] digest = md.digest();
        //声明一个保存数据库中口令消息摘要的变量
        byte[] digestInDb = new byte[pwdInDb.length - SALT_LENGTH];
        //取得数据库中口令的消息摘要
        System.arraycopy(pwdInDb, SALT_LENGTH, digestInDb, 0, digestInDb.length);
        //比较根据输入口令生成的消息摘要和数据库中消息摘要是否相同
        if (Arrays.equals(digest, digestInDb)) {
            //口令正确返回口令匹配消息
            return true;
        } else {
            //口令不正确返回口令不匹配消息
            return false;
        }
    }


    /**
     * 获得加密后的16进制形式口令
     *
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getEncryptedPwd(String password) {
        //声明加密后的口令数组变量
        byte[] pwd = null;
        //随机数生成器
        SecureRandom random = new SecureRandom();
        //声明盐数组变量
        byte[] salt = new byte[SALT_LENGTH];
        //将随机数放入盐变量中
        random.nextBytes(salt);

        //声明消息摘要对象
        MessageDigest md = null;
        //创建消息摘要
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //将盐数据传入消息摘要对象
        md.update(salt);
        //将口令的数据传给消息摘要对象
        try {
            md.update(password.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获得消息摘要的字节数组
        byte[] digest = md.digest();

        //因为要在口令的字节数组中存放盐，所以加上盐的字节长度
        pwd = new byte[digest.length + SALT_LENGTH];
        //将盐的字节拷贝到生成的加密口令字节数组的前12个字节，以便在验证口令时取出盐
        System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH);
        //将消息摘要拷贝到加密口令字节数组从第13个字节开始的字节
        System.arraycopy(digest, 0, pwd, SALT_LENGTH, digest.length);
        //将字节数组格式加密后的口令转化为16进制字符串格式的口令
        return byteToHexString(pwd);
    }

    /**
     * 根据账号和密码进行密码加密
     * @param userId
     * @param password
     * @return
     */
    public static String encrypt(String userId, String password){
        String salt = new Md5Hash(userId).toHex();
        return MD5Utils.encryptPassword(salt,password);
    }


    /**
     *  加密
     * @param salt 盐
     * @param password 明文密码
     * @return 密文
     */
    public static String encryptPassword(String salt, String password) {
        return new Md5Hash(new Md5Hash(password,salt), password).toHex();
    }



    /**
     * 获取该输入流的MD5值
     */
    public static String getMD5(InputStream is) throws NoSuchAlgorithmException, IOException {
        StringBuffer md5 = new StringBuffer();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] dataBytes = new byte[1024];

        int nread = 0;
        while ((nread = is.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }
        byte[] mdbytes = md.digest();

        // convert the byte to hex format
        for (int i = 0; i < mdbytes.length; i++) {
            md5.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return md5.toString();
    }

    /**
     * 对一个文件获取md5值
     */
    public static String getFileMd5(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(MD5.digest()));
        } catch (IOException e) {
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * 根据一个字符串和时间戳生成一个唯一hash值
     * @param str 任意字符
     * @return hash值
     */
    public static String generateStrMd5(String str){
        JSONObject json = new JSONObject();
        json.put("str", str);
        json.put("time", System.currentTimeMillis());
        return new Md5Hash(json.toJSONString()).toHex();
    }

    /**
     * 计算字符串的md5值
     * @param target 字符串
     * @return md5 value
     */
    public static String md5(final String target) {
        return DigestUtils.md5Hex(target);
    }

}
