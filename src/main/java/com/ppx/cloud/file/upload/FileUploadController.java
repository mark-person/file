package com.ppx.cloud.file.upload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ppx.cloud.auth.common.AuthContext;
import com.ppx.cloud.common.controller.ControllerReturn;
import com.ppx.cloud.common.util.ApplicationUtils;

import net.coobird.thumbnailator.Thumbnails;

@Controller
public class FileUploadController {

    private DateFormat df = new SimpleDateFormat("yyyyMMdd");
    
    private final String UPLOAD_PATH = "upload_file/";
    
    private final String SHOW_PATH = "show/";
    
    private final String PROD_PATH = "prod/";
    
    @PostMapping @ResponseBody
    public Map<String, Object> showImgUpload(@RequestParam("file") MultipartFile[] file) throws Exception {
        List<String> returnList = new ArrayList<String>();
       
        if (file == null || file.length == 0) {
            return ControllerReturn.fail(601, "file == null || file.length == 0");
        }
        
        for (int i = 0; i < file.length; i++) {
            String fileName = file[i].getOriginalFilename();
            if (StringUtils.isEmpty(fileName)) {
                continue;
            }
            byte[] bytes = file[i].getBytes();
            String path = getShowImgPath(fileName);
            String savePath = ApplicationUtils.JAR_HOME + UPLOAD_PATH + path;

            try (FileOutputStream fileOutputStream = new FileOutputStream(new File(savePath));
                    BufferedOutputStream buffStream = new BufferedOutputStream(fileOutputStream);) {
                buffStream.write(bytes);
            }
            returnList.add(path);
        }
        return ControllerReturn.ok(returnList);
    }

    private String getShowImgPath(String fileName) {
        int merId = AuthContext.getLoginAccount().getMerId();
        String path = merId + "/" + SHOW_PATH + df.format(new Date());
        File pathFile = new File(ApplicationUtils.JAR_HOME + UPLOAD_PATH + path);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        String ext = fileName.substring(fileName.lastIndexOf("."));
        String imgFileName = UUID.randomUUID().toString().replaceAll("-", "") + ext;
        return path + "/" + imgFileName;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @PostMapping @ResponseBody
    public Map<String, Object> prodImgUpload(@RequestParam("file") MultipartFile[] file) throws Exception {
        List<String> returnList = new ArrayList<String>();
       
        if (file == null || file.length == 0) {
            return ControllerReturn.fail(601, "file == null || file.length == 0");
        }

        for (int i = 0; i < file.length; i++) {
            String fileName = file[i].getOriginalFilename();
            if (StringUtils.isEmpty(fileName)) {
                continue;
            }
            byte[] bytes = file[i].getBytes();
            String path = getProdImgPath(fileName);
            String savePath = ApplicationUtils.JAR_HOME + UPLOAD_PATH + path;

            try (FileOutputStream fileOutputStream = new FileOutputStream(new File(savePath));
                    BufferedOutputStream buffStream = new BufferedOutputStream(fileOutputStream);) {
                buffStream.write(bytes);
                Thumbnails.of(savePath).size(240, 240).toFile(savePath + "_240.jpg");
            }
            returnList.add(path);
        }
        return ControllerReturn.ok(returnList);
    }

    private String getProdImgPath(String fileName) {
        int merId = AuthContext.getLoginAccount().getMerId();
        String path = merId + "/" + PROD_PATH + df.format(new Date());
        File pathFile = new File(ApplicationUtils.JAR_HOME + UPLOAD_PATH + path);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        String ext = fileName.substring(fileName.lastIndexOf("."));
        String imgFileName = UUID.randomUUID().toString().replaceAll("-", "") + ext;
        return path + "/" + imgFileName;
    }
    
    
    
    
    
    
    
    
    

   
}