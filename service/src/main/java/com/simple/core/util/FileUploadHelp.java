package com.project.meetinglive.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.project.meetinglive.core.config.ApplicationConfig;

/**
 * 图片上传帮助类
 * @author hejinguo
 * @version $Id: FileUploadHelp.java, v 0.1 2019年11月20日 下午10:15:45
 */
public class FileUploadHelp {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadHelp.class);

    /**
     * 公用图片上传
     * @param file
     * @return
     */
    public static Map<String, Object> imageUpload(MultipartFile file) throws Exception {
        //返回对象
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("status", 0);
        returnMap.put("message", null);
        returnMap.put("fileUrl", null);
        if (file == null || file.isEmpty()) {
            returnMap.put("message", "图片不能为空!");
            return returnMap;
        }
        //step2:重新生成图片文件名
        String fileName = FileNameRuleHelp.getUserPictureFileName() + "."
                          + FilenameUtils.getExtension(file.getOriginalFilename());
        String filePathBase = FileNameRuleHelp.makeTargetPath();
        String filePath = ApplicationConfig.upload_image_dirPath + filePathBase;
        File dh = new File(filePath);
        if (!dh.exists()) {
            dh.mkdirs();
        }
        //step3:本地服务器生成图片
        File localFile = new File(filePath + fileName);
        try {
            file.transferTo(localFile);
        } catch (Exception e) {
            logger.error("图片上传文件转换异常!", e);
            returnMap.put("message", "图片转换异常,请重新上传!");
            return returnMap;
        }
        //step4:获取文件访问目录
        String fileUrl = ApplicationConfig.upload_image_httpUrl
                         + ApplicationConfig.upload_image_path + filePathBase + fileName;
        returnMap.put("status", 1);
        returnMap.put("message", "图片上传成功!");
        returnMap.put("fileUrl", fileUrl);
        return returnMap;
    }

    /**
     * 公用图片上传
     * @param file
     * @return
     */
    public static Map<String, Object> imageUpload(File file) throws Exception {
        //返回对象
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("status", 0);
        returnMap.put("message", null);
        returnMap.put("fileUrl", null);
        if (file == null || !file.isFile()) {
            returnMap.put("message", "图片不能为空!");
            return returnMap;
        }
        //step2:重新生成图片文件名
        String fileName = file.getName();
        String fileNameType = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newFileName = FileNameRuleHelp.getUserPictureFileName() + "." + fileNameType;
        String filePathBase = FileNameRuleHelp.makeTargetPath();
        String filePath = ApplicationConfig.upload_image_dirPath + filePathBase;
        File dh = new File(filePath);
        if (!dh.exists()) {
            dh.mkdirs();
        }
        //step3:本地服务器生成图片
        File localFile = new File(filePath + newFileName);
        try {
            file.renameTo(localFile);
        } catch (Exception e) {
            logger.error("图片上传文件转换异常!", e);
            returnMap.put("message", "图片转换异常,请重新上传!");
            return returnMap;
        }
        //step4:获取文件访问目录
        String fileUrl = ApplicationConfig.upload_image_httpUrl
                         + ApplicationConfig.upload_image_path + filePathBase + newFileName;
        returnMap.put("status", 1);
        returnMap.put("message", "图片上传成功!");
        returnMap.put("fileUrl", fileUrl);
        return returnMap;
    }

    /**
     * byte转file
     * @param data
    * @throws Exception 
     */
    public static void transImageToFile(File file, byte[] data) {
        //创建输出流 
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(file);
            //写入数据 
            outStream.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭输出流 
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
