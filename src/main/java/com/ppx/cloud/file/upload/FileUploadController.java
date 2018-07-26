package com.ppx.cloud.file.upload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

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

    @PostMapping
    @ResponseBody
    public Map<String, Object> showImgUpload(@RequestParam("file") MultipartFile[] file) throws Exception {
        file[0].transferTo(new File("E:\\upload\\1.txt"));

        Resource rs = new FileUrlResource("E:\\upload\\1.txt");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<Resource> entity = new HttpEntity<>(rs, headers);
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", entity);
        WebClient.create().post().uri("http://localhost:8081/fileUpload/showImgUpload2")
                .contentType(MediaType.MULTIPART_FORM_DATA).body(BodyInserters.fromMultipartData(parts)).retrieve()
                .bodyToMono(String.class).subscribe((out) -> {
                    System.out.println("0000000000:out:" + out);
                });
        System.out.println("111111111");
        // boolean isBackBegin = false;
        //
        List<String> returnList = new ArrayList<String>();
        returnList.add("sssssssss");
        //
        // InputStream inputstream = file[0].getInputStream();
        // FileOutputStream outputstream = new FileOutputStream(new
        // File("E:\\upload\\1.txt"));
        // byte[] buffer = new byte[512];
        // int byteRead = -1;
        // while ((byteRead = (inputstream.read(buffer))) != -1) {
        //
        // outputstream.write(buffer, 0, byteRead);
        // System.out.println("begin.............sleep");
        //
        //
        // if (isBackBegin == false) {
        // isBackBegin = true;
        // Resource rs = new FileUrlResource("E:\\upload\\1.txt");
        // HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        // HttpEntity<Resource> entity = new HttpEntity<>(rs, headers);
        // MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        // parts.add("file", entity);
        // WebClient.create().post()
        // .uri("http://localhost:8081/fileUpload/showImgUpload2")
        // .contentType(MediaType.MULTIPART_FORM_DATA)
        // .body(BodyInserters.fromMultipartData(parts))
        // .retrieve().bodyToMono(String.class)
        // .subscribe((out) -> {
        // System.out.println("0000000000:out:" + out);
        // });
        // }
        //
        // try {
        // Thread.sleep(100);
        // } catch (Exception e) {
        // // TODO: handle exception
        // }
        // System.out.println("end.............sleep");
        // }
        //
        //
        // try {
        // Thread.sleep(3*1000);
        // } catch (Exception e) {
        // // TODO: handle exception
        // }
        //
        // inputstream.close();
        // outputstream.close();

        //
        //
        // // 测试
        //
        // HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        // // HttpEntity<ClassPathResource> entity = new HttpEntity<>(new
        // ClassPathResource("parallel.png"), headers);
        // // builder.addBinaryBody("file", file.getInputStream(),
        // ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
        //
        // Resource rs = new FileUrlResource("E:/upload/parallel.png");
        //
        //
        // //FileUrlResource fur = new FileUrlResource("");
        //
        //
        //
        // InputStreamResource isr = new
        // InputStreamResource(file[0].getInputStream());
        //
        //
        //
        //// File f = new File("E:/upload/parallel.png");
        //// InputStream targetStream = new FileInputStream(f);
        ////
        //
        // HttpEntity<Resource> entity = new HttpEntity<>(rs, headers);
        //
        //
        //
        //
        //
        // MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        //
        // System.out.println("xxxxxxxxxxxxx0000000000000000len:" +
        // file[0].getBytes().length);
        //
        // parts.add("file", entity);
        //
        //
        //
        // WebClient.create().post()
        // .uri("http://localhost:8081/fileUpload/showImgUpload2")
        // .contentType(MediaType.MULTIPART_FORM_DATA)
        // .body(BodyInserters.fromMultipartData(parts))
        // .retrieve().bodyToMono(String.class)
        // .subscribe((out) -> {
        // System.out.println("0000000000:out:" + out);
        // });
        //
        // System.out.println("begin..................:1");
        // try {
        // Thread.sleep(5*1000);
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // System.out.println("end..................:1");
        //
        //
        // /*
        // //遍历并保存文件
        // for(MultipartFile file : multipartfiles){
        // file.transferTo(new File(savePath + file.getOriginalFilename()));
        // }
        // */
        //
        //
        //
        // List<String> returnList = new ArrayList<String>();
        //
        // if (file == null || file.length == 0) {
        // return ControllerReturn.fail(601, "file == null || file.length ==
        // 0");
        // }
        //
        // for (int i = 0; i < file.length; i++) {
        // String fileName = file[i].getOriginalFilename();
        // if (StringUtils.isEmpty(fileName)) {
        // continue;
        // }
        // byte[] bytes = file[i].getBytes();
        // System.out.println("........................bytes.length:" +
        // bytes.length);
        // String path = getShowImgPath(fileName);
        // String savePath = ApplicationUtils.JAR_HOME + UPLOAD_PATH + path;
        //
        // try (FileOutputStream fileOutputStream = new FileOutputStream(new
        // File(savePath));
        // BufferedOutputStream buffStream = new
        // BufferedOutputStream(fileOutputStream);) {
        // buffStream.write(bytes);
        // }
        // returnList.add(path);
        // }
        return ControllerReturn.ok(returnList);
    }

    private String getShowImgPath(String fileName) {
        int merId = 1;// AuthContext.getLoginAccount().getMerId();
        String path = merId + "/" + SHOW_PATH + df.format(new Date());
        File pathFile = new File(ApplicationUtils.JAR_HOME + UPLOAD_PATH + path);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        String ext = fileName.substring(fileName.lastIndexOf("."));
        String imgFileName = UUID.randomUUID().toString().replaceAll("-", "") + ext;
        return path + "/" + imgFileName;
    }

    @PostMapping
    @ResponseBody
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

    // test>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @PostMapping
    @ResponseBody
    public Map<String, Object> showImgUpload2(@RequestParam("file") MultipartFile[] file) throws Exception {
        try {
            Thread.sleep(5*1000);
        } catch (Exception e) {
            // TODO: handle exception
        }
        List<String> returnList = new ArrayList<String>();
        file[0].transferTo(new File("E:\\upload\\2.txt"));

        // InputStream inputstream = file[0].getInputStream();
        // FileOutputStream outputstream = new FileOutputStream(new
        // File("E:\\upload\\2.txt"));
        // byte[] buffer = new byte[256];
        // int byteRead = -1;
        // while ((byteRead = (inputstream.read(buffer))) != -1) {
        //
        // outputstream.write(buffer, 0, byteRead);
        // try {
        // // Thread.sleep(3 * 1000);
        // } catch (Exception e) {
        // // TODO: handle exception
        // }
        // }
        // inputstream.close();
        // outputstream.close();

        // file[0].transferTo(new File("E:\\upload\\2.txt"));
        // System.out.println("sssssssfile:" + file);
        // File f = new File("E:\\upload\\tmp.png");
        // FileWriter fw = new FileWriter(f);

        // DataOutputStream out = new DataOutputStream(new
        // FileOutputStream("E:\\upload\\tmp.png"));
        // out.write(file[0].getBytes());
        // out.close();

        // String savePath = "E:\\upload\\tmp.png";
        //
        // try (FileOutputStream fileOutputStream = new FileOutputStream(new
        // File(savePath));
        // BufferedOutputStream buffStream = new
        // BufferedOutputStream(fileOutputStream);) {
        // buffStream.write(file[0].getBytes());
        // }

        // if (file == null || file.length == 0) {
        // System.out.println("xxxxxxfile:" + file.length);
        // return ControllerReturn.fail(601, "file == null || file.length ==
        // 0");
        // }
        //
        // for (int i = 0; i < file.length; i++) {
        // String fileName = file[i].getOriginalFilename();
        // if (StringUtils.isEmpty(fileName)) {
        // continue;
        // }
        // byte[] bytes = file[i].getBytes();
        // String path = getShowImgPath2(fileName);
        // String savePath = ApplicationUtils.JAR_HOME + UPLOAD_PATH + path;
        //
        // try (FileOutputStream fileOutputStream = new FileOutputStream(new
        // File(savePath));
        // BufferedOutputStream buffStream = new
        // BufferedOutputStream(fileOutputStream);) {
        // buffStream.write(bytes);
        // }
        // returnList.add(path);
        // }
        return ControllerReturn.ok(returnList);
    }

    private String getShowImgPath2(String fileName) {
        int merId = 2;// AuthContext.getLoginAccount().getMerId();
        String path = merId + "/" + SHOW_PATH + df.format(new Date());
        File pathFile = new File(ApplicationUtils.JAR_HOME + UPLOAD_PATH + path);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        String ext = fileName.substring(fileName.lastIndexOf("."));
        String imgFileName = UUID.randomUUID().toString().replaceAll("-", "") + ext;
        return path + "/" + imgFileName;
    }

}