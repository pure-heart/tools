package com.jhly.fileoperation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author jiang
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "文件上传下载，导入导出模块";
    }

    @RequestMapping("file")
    public String file() {
        return "/file";
    }

    /**
     * 实现文件上传
     */
    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("fileName") MultipartFile file) {
        if (file.isEmpty()) {
            return "false";
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);

        String path = "D:/test";
        File dest = new File(path + "/" + fileName);
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(dest);
            return "true";
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        }
    }

    /**
     * 获取multifile.html页面
     */
    @RequestMapping("multifile")
    public String multifile() {
        return "/multifile";
    }

    /**
     * 实现多文件上传
     */
    @ResponseBody
    @RequestMapping(value = "multifileUpload", method = RequestMethod.POST)
    public String multifileUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("fileName");

        if (files.isEmpty()) {
            return "false";
        }

        String path = "F:/test";

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            int size = (int) file.getSize();
            System.out.println(fileName + "-->" + size);

            if (file.isEmpty()) {
                return "false";
            } else {
                File dest = new File(path + "/" + fileName);
                //判断文件父目录是否存在
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdir();
                }
                try {
                    file.transferTo(dest);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return "false";
                }
            }
        }
        return "true";
    }

    @RequestMapping("/download")
    public String downLoad(HttpServletResponse response) throws UnsupportedEncodingException {
        String filename = "评分步骤详解.docx";
        String filePath = "D:/test";
        File file = new File(filePath + "/" + filename);
        //判断文件父目录是否存在
        if (file.exists()) {
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(filename, "UTF-8"));
            byte[] buffer = new byte[1024];
            //文件输入流
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            //输出流
            OutputStream os = null;
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----------file download---" + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    @RequestMapping("/transfer")
    public String transfer(HttpServletResponse response){
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName= backup.zip");
        String content = "Hello, Txt World! my name is 江航！！!!";
        try {
            OutputStream os = response.getOutputStream();
            os.write(content.getBytes(StandardCharsets.UTF_8));
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取流文件
     * @return
     * @throws IOException
     */
    @RequestMapping("getFIle")
    @ResponseBody
    public String getFIle(@RequestParam("fileName") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();

        InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader br = new BufferedReader(isr);

        // 设置一个接收的String
        StringBuffer strBuf = new StringBuffer();
        String line = null;
        while ((line = br.readLine()) != null) {
            strBuf.append(line);
        }
        String str= strBuf.toString();
        br.close();
        isr.close();
        inputStream.close();
        return str;
    }


}
