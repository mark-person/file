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

import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ppx.cloud.common.controller.ControllerReturn;
import com.ppx.cloud.grant.common.GrantContext;

import net.coobird.thumbnailator.Thumbnails;

@Controller
public class FileUploadController {

    private DateFormat df = new SimpleDateFormat("yyyyMMdd");
    
    private static String UPLOAD_FILE_PATH = null;
    
    @PostMapping
    @ResponseBody
    public Map<String, Object> prodImgUpload(@RequestParam("file") MultipartFile[] file) throws Exception {
        List<String> returnList = new ArrayList<String>();
        if (UPLOAD_FILE_PATH == null) {
            ApplicationHome home = new ApplicationHome(getClass());
            String jarPath = home.getSource().getParent();
            UPLOAD_FILE_PATH = jarPath + "/upload_file/";
        }
        
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
            String savePath = UPLOAD_FILE_PATH + path;

            try (FileOutputStream fileOutputStream = new FileOutputStream(new File(savePath));
                    BufferedOutputStream buffStream = new BufferedOutputStream(fileOutputStream);) {
                buffStream.write(bytes);
                Thumbnails.of(savePath).size(240, 240).toFile(savePath + "_240.jpg");
            } catch (Exception e) {
                throw e;
            }
            returnList.add(path);
        }
        return ControllerReturn.ok(returnList);
    }

    private String getProdImgPath(String fileName) {
        int merId = GrantContext.getLoginAccount().getMerId();
        String path = merId + "/prod/" + df.format(new Date());
        File pathFile = new File(UPLOAD_FILE_PATH + path);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        String ext = fileName.substring(fileName.lastIndexOf("."));
        String imgFileName = UUID.randomUUID().toString().replaceAll("-", "") + ext;
        return path + "/" + imgFileName;
    }

    // private static Set<String> prodTypeSet = new HashSet<String>();
    //
    // static {
    // prodTypeSet.add("prod");
    // prodTypeSet.add("sku");
    // }
    // /**
    // * product
    // * 路经=merchantId/prod/yyyyMMdd/UUID.ext
    // * 路经=merchantId/yyyyMMdd/UUID.ext
    // * @param file
    // * @param type
    // * @return
    // */
    // @PostMapping @ResponseBody
    // public Map<String, Object> prodSave(@RequestParam("file") MultipartFile[]
    // file, @RequestParam("type") String[] type) {
    // List<String> returnList = new ArrayList<String>();
    //
    // if (file == null || file.length == 0 || file.length != type.length) {
    // return ControllerReturn.ok(returnList);
    // }
    //
    // for (String t : type) {
    // if (!prodTypeSet.contains(t)) {
    // return ControllerReturn.ok(returnList);
    // }
    // }
    //
    // for (int i = 0; i < file.length; i++) {
    // BufferedOutputStream buffStream = null;
    // try {
    // String fileName = file[i].getOriginalFilename();
    // if (StringUtils.isEmpty(fileName)) {
    // continue;
    // }
    // byte[] bytes = file[i].getBytes();
    // String path = getImgPath(fileName, type[i]);
    // String savePath = System.getProperty("file.imgFilePath") + path;
    // buffStream = new BufferedOutputStream(new FileOutputStream(new
    // File(savePath)));
    // buffStream.write(bytes);
    //
    // // 图片压缩 不需要
    // if ("prod".equals(type[i])) {
    // Thumbnails.of(savePath).size(240, 240).toFile(savePath + "_240.jpg");
    // }
    //
    // returnList.add(path);
    // } catch (Exception e) {
    // e.printStackTrace();
    // } finally {
    // if (buffStream != null) {
    // try {
    // buffStream.close();
    // } catch (Exception e) {}
    // }
    // }
    // }
    // return ControllerReturn.ok(returnList);
    // }
    //
    // private String getImgPath(String fileName, String type) {
    // int merchantId = GrantContext.getLoginAccount().getMerchantId();
    // String path = merchantId + "/" + type + "/" + new
    // SimpleDateFormat("yyyyMMdd").format(new Date());
    // File pathFile = new File(System.getProperty("file.imgFilePath") + path);
    // if (!pathFile.exists()) {
    // pathFile.mkdirs();
    // }
    //
    // String ext = fileName.substring(fileName.lastIndexOf("."));
    // String imgFileName = UUID.randomUUID().toString().replaceAll("-", "") +
    // ext;
    // return path + "/" + imgFileName;
    // }
    //
    // // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // private static Set<String> showTypeSet = new HashSet<String>();
    //
    // static {
    // showTypeSet.add("store");
    // showTypeSet.add("swiper");
    // showTypeSet.add("cat");
    // showTypeSet.add("brand");
    // showTypeSet.add("theme");
    // showTypeSet.add("promo");
    // }
    //
    // /**
    // * 路经=merchantId/show/name.ext
    // * show/
    // * store/ swiper/ brand cat promo theme
    // *
    // *
    // * @param file
    // * @return
    // */
    // @PostMapping @ResponseBody
    // public Map<String, Object> showSave(@RequestParam("file") MultipartFile[]
    // file, @RequestParam("type") String[] type) {
    // List<String> returnList = new ArrayList<String>();
    //
    // if (file == null || file.length == 0 || file.length != type.length) {
    // return ControllerReturn.ok(returnList);
    // }
    //
    // for (String t : type) {
    // if (!showTypeSet.contains(t)) {
    // return ControllerReturn.ok(returnList);
    // }
    // }
    //
    //
    //
    // for (int i = 0; i < file.length; i++) {
    // BufferedOutputStream buffStream = null;
    // try {
    // String fileName = file[i].getOriginalFilename();
    // if (StringUtils.isEmpty(fileName)) {
    // continue;
    // }
    // byte[] bytes = file[i].getBytes();
    // String path = getShowPath(fileName, type[i]);
    // buffStream = new BufferedOutputStream(new FileOutputStream(new
    // File(System.getProperty("file.imgFilePath") + path)));
    // buffStream.write(bytes);
    // returnList.add(path);
    // } catch (Exception e) {
    // e.printStackTrace();
    // } finally {
    // if (buffStream != null) {
    // try {
    // buffStream.close();
    // } catch (Exception e) {}
    // }
    // }
    // }
    // return ControllerReturn.ok(returnList);
    // }
    //
    // private String getShowPath(String fileName, String type) {
    // int merchantId = GrantContext.getLoginAccount().getMerchantId();
    // String ext = fileName.substring(fileName.lastIndexOf("."));
    //
    // if (type.equals("swiper") || type.equals("store")) {
    // String path = merchantId + "/show/" + type;
    // File pathFile = new File(System.getProperty("file.imgFilePath") + path);
    // if (!pathFile.exists()) {
    // pathFile.mkdirs();
    // }
    // String imgFileName = UUID.randomUUID().toString().replaceAll("-", "") +
    // ext;
    // return path + "/" + imgFileName;
    // }
    // else {
    // String path = merchantId + "/show";
    // File pathFile = new File(System.getProperty("file.imgFilePath") + path);
    // if (!pathFile.exists()) {
    // pathFile.mkdirs();
    // }
    // return path + "/" + type + ext;
    // }
    // }
}