package com.amp.utils;

import com.amp.constant.CommonConstant;
import com.amp.enums.ResultCodeEnum;
import com.amp.exception.AmpException;

import jodd.util.ArraysUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.*;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wyw
 * @date 2021/10/26 17:24
 * @description
 */
@Slf4j
public class AmpFileUtils  {

    public static long IMAGE_FILE_SIZE_LIMIT = 1024 * 1024 * 2;     //图片文件大小 限制2M


    /**
     * 图片的content-type
     */
    public static Map<String, String> CONTENT_TYPE = new HashMap();

    static {
        CONTENT_TYPE.put("jpeg", "image/jpeg");
        CONTENT_TYPE.put("jpg", "image/jpeg");
        CONTENT_TYPE.put("png", "image/png");
        CONTENT_TYPE.put("gif", "image/gif");
        CONTENT_TYPE.put("bmp", "image/bmp");
        CONTENT_TYPE.put("wbmp", "image/wbmp");
    }

    public static void checkFileSize(long fileSize, long limit) {
        if (0 > fileSize) {
            throw new AmpException(ResultCodeEnum.DOC_FILE_EMPTY);
        }

        if (fileSize > limit) {
            throw new AmpException(ResultCodeEnum.DOC_FILE_OVERSIZE);
        }
    }

    /**
     * 上传文件夹时获取文件的相对路径
     *
     * @param path 路径
     * @return 相对路径
     */
    public static String getRelativePath(String path) {
        return path.substring(0, path.lastIndexOf(CommonConstant.PATH_SEPARATOR) + 1);
    }

    /**
     * 获取上传文件时第一个"/"之前的路径
     * @param path
     * @return
     */
    public static String getFirstRelativePath(String path) {
        return path.substring(0, path.indexOf(CommonConstant.PATH_SEPARATOR) + 1);
    }

    /**
     * 获取文件的父级路径
     *
     * @param path 文件路径
     * @return 文件的父级路径
     */
    public static String getParentPath(String path) {
        path = path.substring(0, path.length() - 1);
        return path.substring(0, path.lastIndexOf(CommonConstant.PATH_SEPARATOR) + 1);
    }

    /**
     * 获取下级路径
     *
     * @param userFilePath 用户文件路径
     * @param userFileName 用户文件名
     * @return 下级路径
     */
    public static String getNextPath(String userFilePath, String userFileName) {
        String path = String.format("%s/%s/", userFilePath, userFileName);
        path = path.replaceAll("//", "/");
        return path;
    }




    /**
     * 获取文件名(不包含扩展名)
     *
     * @param fileName 文件名
     * @return 不包含扩展名的文件名
     */

    public static String getFileNameNoExtendName(String fileName) {
        if (fileName.contains(CommonConstant.EXTEND_SEPARATOR)) {
            return fileName.substring(0, fileName.lastIndexOf(CommonConstant.EXTEND_SEPARATOR));
        } else {
            return fileName;
        }
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名
     * @return 扩展名
     */
    public static String getFileExtendName(String fileName) {
        if (fileName.contains(CommonConstant.EXTEND_SEPARATOR)) {
            return fileName.substring(fileName.lastIndexOf(CommonConstant.EXTEND_SEPARATOR) + 1);
        } else {
            return "";
        }
    }

    /**
     * 获取完整文件名
     *
     * @param fileName      文件名不包含扩展名
     * @param extensionName 扩展名
     * @return 全文件名
     */
    public static String getFileFullName(String fileName, String extensionName) {
        if (StringUtils.isBlank(extensionName)){
            return fileName;
        }
        return  String.format("%s%s%s",fileName,CommonConstant.EXTEND_SEPARATOR,extensionName);
    }



    /**
     * 获取新的文件名称
     *
     * @param fileName
     * @return
     */
    public static String getNewName(String fileName) {
        Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+");
        Pattern pattern2 = Pattern.compile("^[-\\+]?[\\d]*$");
        Matcher matcher = pattern.matcher(fileName);
        Integer number = 0;
        while (matcher.find()) {
            String str = matcher.group();
            if (StringUtils.isNotEmpty(str)) {
                if (pattern2.matcher(str).matches()) {
                    fileName = fileName.substring(0, fileName.lastIndexOf("("));
                    number = Integer.valueOf(str);
                }
            }
        }
        if (number == 0) {
            fileName = String.format("%s(1)", fileName);
        } else {
            fileName = String.format("%s(%d)", fileName, (number + 1));
        }
        return fileName;
    }

    /**
     * 保存文件方法
     *
     * @param in
     * @param outPath
     * @throws Exception
     */
    public static void saveFile(InputStream in, String outPath) throws Exception {
        OutputStream osm = new FileOutputStream(outPath);
        IOUtils.copy(in, osm);
    }

    /**
     * 前端下载文件方法
     *
     * @param filePath
     * @param response
     */
    public static void downLoadFile(String filePath, HttpServletResponse response) {
        File file = new File(filePath);
        // 将文件写入输入流
        try (
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fileInputStream);
                OutputStream os = response.getOutputStream();
                BufferedOutputStream bs = new BufferedOutputStream(os)
        ) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                bs.write(buffer, 0, len);
            }
        } catch (IOException e) {
            log.error("从系统服务器下载文件失败:", e);
        }
    }


    /**
     * 根据文件的全路径名判断文件是否存在
     *
     * @param file
     * @return
     */
    public static boolean fileExists(String file) {
        boolean fileExists = false;
        Path path = Paths.get(file);
        fileExists = Files.exists(path, LinkOption.NOFOLLOW_LINKS);
        return fileExists;
    }






    /**
     * 支持图片类型
     */
    public static final String[] SUPPORT_IMAGE_TYPE = {"jpg", "jpeg", "png", "gif", "bmp", "wbmp"};

    /**
     * 是否是支持的图片文件
     *
     * @param fileExtName
     * @return
     */
    public static boolean isSupportImage(String fileExtName) {
        return ArraysUtil.contains(SUPPORT_IMAGE_TYPE, fileExtName);
    }

    /**
     * 关闭 MappedByteBuffer流
     *
     * @param buffer
     */
    public static void clean(final Object buffer) {
        AccessController.doPrivileged(new PrivilegedAction() {
            @Override
            public Object run() {
                try {
                    Method getCleanerMethod = buffer.getClass().getMethod("cleaner");
                    getCleanerMethod.setAccessible(true);
                    sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(buffer, new Object[0]);
                    cleaner.clean();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    /**
     * 计算文件MD5
     *
     * @param path 文件路径
     * @return MD5
     */
    public static String sparkMD5(String path) {
        String md5 = "";
        File file = new File(path);
        try(FileInputStream fis = new FileInputStream(file)){
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            BigInteger bigInt = new BigInteger(1, md.digest());
            md5 = bigInt.toString(16);
        } catch (NoSuchAlgorithmException | IOException e) {
            log.error("计算文件MD5错误",e);
        }
        return md5;
    }
}
