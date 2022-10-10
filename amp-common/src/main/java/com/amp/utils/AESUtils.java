package com.amp.utils;


import com.amp.exception.AmpException;
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
 * ASE 256 加密工具类
 *
 * @ClassName AESUtils
 * @Author Emi.Wu
 * @Date 2021-11-10 15:15
 * @Version 1.0
 */
public class AESUtils {


    /**
     * 编码格式
     */
    private final static String CHARSET = "utf-8";

    /**
     * 文件分享参数 盐
     */
    public final static String SALT_SHARE_PARAM = "share_user_file_id";

    /**
     * 企业团队邀请链接参数 盐
     */
    public final static String SALT_TEAM_INVITE_PARAM = "team_id";

    /**
     * 生成密钥
     *
     * @param salt 盐 编码规则
     * @return 密钥
     * @author Emil.Wu
     * @date 2021-11-12 10:33
     * @version 1.0
     */
    private static SecretKey getSecretKey(String salt) throws NoSuchAlgorithmException {
        // 1.构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        // 2.根据salt规则初始化密钥生成器
        // 生成一个128位的随机源,根据传入的字节数组
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(salt.getBytes());
        keygen.init(128, random);
        // 3.产生原始对称密钥
        SecretKey originalKey = keygen.generateKey();
        // 4.获得原始对称密钥的字节数组
        byte[] raw = originalKey.getEncoded();
        // 5.根据字节数组生成AES密钥
        return new SecretKeySpec(raw, "AES");
    }

    /**
     * ASE 加密具体过程
     * 加密失败，返回null，根据具体业务抛出具体的异常
     *
     * @param salt    盐 编码规则
     * @param content 明文
     * @return 密文
     * @author Emil.Wu
     * @date 2021-11-12 9:59
     * @version 1.0
     */
    private static String encrypt(String salt, String content) {
        try {
            // 1.获取密钥
            SecretKey key = getSecretKey(salt);
            // 2.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 3.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 4.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byteEncode = content.getBytes(CHARSET);
            // 5.根据密码器的初始化方式--加密：将数据加密
            byte[] bytes = cipher.doFinal(byteEncode);
            String encode = new BASE64Encoder().encode(bytes);
            return encode;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                InvalidKeyException | IllegalBlockSizeException |
                BadPaddingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ASE解密具体过程
     * 解密失败，返回null，根据具体业务抛出具体的异常
     *
     * @param salt    盐 编码规则
     * @param content 密文
     * @return 明文
     * @author Emil.Wu
     * @date 2021-11-12 10:01
     * @version 1.0
     */
    private static String decrypt(String salt, String content) {
        try {
            // 1.获取密钥
            SecretKey key = getSecretKey(salt);
            // 2.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 3.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 4.将加密并编码后的内容解码成字节数组
            byte[] byteContent = new BASE64Decoder().decodeBuffer(content);
            // 5.解密
            byte[] bytes = cipher.doFinal(byteContent);
            return new String(bytes, CHARSET);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IOException
                | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     *
     * @param salt    盐 编码规则
     * @param content 明文
     * @return 密文
     * @author Emil.Wu
     * @date 2021-11-12 9:47
     * @version 1.0
     */
    public static String encryptASE(String salt, String content) {
        checkParam(salt, content);
        return encrypt(salt, content);
    }

    /**
     * 解密
     *
     * @param salt    盐 编码规则
     * @param content 密文
     * @return 明文
     * @author Emil.Wu
     * @date 2021-11-12 9:47
     * @version 1.0
     */
    public static String decryptASE(String salt, String content) {
        checkParam(salt, content);
        return decrypt(salt, content);
    }

    /**
     * 参数校验
     *
     * @param salt    盐 编码规则
     * @param content 密文
     * @author Emil.Wu
     * @date 2021-11-12 11:00
     * @version 1.0
     */
    private static void checkParam(String salt, String content) {
        if (StringUtils.isBlank(salt)) {
            throw new AmpException("AES 盐[salt]参数为空 ");
        }
        if (StringUtils.isBlank(content)) {
            throw new AmpException("AES 操作文本[content]参数为空 ");
        }
    }

    public static void main(String[] args) {
        String secretKey = SALT_SHARE_PARAM;
        String context = "346328746832428746";
        System.out.println("需要加密的内容: " + context);
        String passwedContext = encryptASE(secretKey, context);
        System.out.println("密文: " + passwedContext);
        System.out.println("明文: " + decryptASE(secretKey, passwedContext));
    }


}
