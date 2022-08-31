package com.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.entity.R;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${upload.base-path}")
    String BASE_PATH;

    @PostMapping("upload")
    // 参数名必须和上传的name一致
    public R<Object> upload(MultipartFile[] file) {
        File dir = new File(BASE_PATH);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        ArrayList<MultipartFile> list = new ArrayList<>(file.length);

        Collections.addAll(list, file);

        ArrayList<String> sList = new ArrayList<>(file.length);

        list.forEach(f -> {
            String s = uploadFile(f);
            sList.add(s);
        });

        return R.SUCCESS(sList);
    }

    public String uploadFile(MultipartFile file) {
        String name = file.getOriginalFilename();
        String ext = name.substring(file.getOriginalFilename().lastIndexOf("."));
        String uuid = UUID.randomUUID().toString() + ext;
        String path = BASE_PATH + uuid;

        try {
            file.transferTo(new File(path));
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @GetMapping("download")
    public void download(HttpServletResponse response, String path) {
        try (FileInputStream is = new FileInputStream(new File(path));
                ServletOutputStream os = response.getOutputStream();) {

            response.setContentType("image/gif");
            outputFile(is, os);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void outputFile(FileInputStream is, ServletOutputStream os) {
        int len = 0;
        byte[] b = new byte[1024];

        try {
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
