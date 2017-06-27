package com.springboot.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


@Controller
public class FileUploadController {
    //访问路径为：http://127.0.0.1:8080/file
    @RequestMapping("/file")
    public String file(){
        return "/file";
    }
    
    @RequestMapping("/mutifile")
    public String mutifile(){
        return "/mutifile";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file")MultipartFile file){
        if(!file.isEmpty()){
            try {
            	System.out.println("文件名称："+file.getOriginalFilename());
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();
            }catch(FileNotFoundException e) {
                e.printStackTrace();
                return "上传失败,"+e.getMessage();
            }catch (IOException e) {
                e.printStackTrace();
                return "上传失败,"+e.getMessage();
            }

            return "上传成功";

        }else{

            return "上传失败，因为文件是空的.";

        }
    }

    /**

     * 多文件具体上传时间，主要是使用了MultipartHttpServletRequest和MultipartFile

     * @param request

     * @return

     */

    @RequestMapping(value="/batch/upload", method=RequestMethod.POST) 
    public @ResponseBody 
    String handleFileUpload(HttpServletRequest request){ 
        List<MultipartFile> files =((MultipartHttpServletRequest)request).getFiles("file"); 
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i =0; i< files.size(); ++i) { 
            file = files.get(i); 
            if (!file.isEmpty()) { 
                try { 
                    byte[] bytes = file.getBytes(); 
                    stream = 
                            new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename()))); 
                    stream.write(bytes); 
                    stream.close(); 
                } catch (Exception e) { 
                    stream =  null;
                    return "You failed to upload " + i + " =>" + e.getMessage(); 
                } 
            } else { 
                return "You failed to upload " + i + " becausethe file was empty."; 
            } 
        } 
        return "upload successful"; 

    } 
}