package com.shls.controller;

import com.shls.webSocket.WebSocketService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.naming.ServiceUnavailableException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.shls.webSocket.WebSocketService.sendToMessage;

@RestController
@RequestMapping("/webSocket")
public class WebSocketController {

    @RequestMapping(value = "/test/{code}", method = RequestMethod.GET)
    @ResponseBody
    public Object object(@PathVariable String code){
        ConcurrentHashMap<String, WebSocketService> webSocketSet = WebSocketService.webSocketSet;
        try {
            WebSocketService wss = webSocketSet.get(code);
            for (int i=1;i<5;i++) {
                wss.send("加载进度："+i);
            }

            wss.close(code);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "success";
    }

    @RequestMapping(value = "/downloadZip/{downloadCode}", method = RequestMethod.GET)
    public Object downloadZipFile(@PathVariable String downloadCode,HttpServletResponse response) throws IOException, InterruptedException, ServiceUnavailableException {

        //下载
        /*response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
        response.setHeader("Content-Disposition","attachment; filename=\"download.zip\"");

        List<String> fileNames = Arrays.asList("springBoot-pay.zip","springcloud.zip","springcode.zip");
        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

        for(String fileName : fileNames) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOutputStream.putNextEntry(zipEntry);
            FileInputStream inputStream = new FileInputStream("/home/wang/下载/"+fileName);
            IOUtils.copy(inputStream,zipOutputStream);
            inputStream.close();
            wss.send(fileName + "  压缩完成");
        }

        zipOutputStream.closeEntry();
        zipOutputStream.close();
        wss.send("下载完成");*/



        //byte
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
        response.setHeader("Content-Disposition","attachment; filename=\"download.zip\"");

        List<String> fileNames = Arrays.asList("rocketchat_2.15.5_amd64.deb","fescar-server-0.4.1.zip");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

        //查找总大小
        double allLen = 0;
        for(String fileName : fileNames) {
            FileInputStream inputStream = new FileInputStream("/home/wang/Downloads/"+fileName);
            byte[] byt = StreamUtils.copyToByteArray(inputStream);
            allLen += byt.length;
        }

        DecimalFormat df = new DecimalFormat("0.000000");
        double nowLen = 0;
        sendToMessage(downloadCode,200,"message","开始下载");
        for(String fileName : fileNames) {
            if("应收款债权清单.xlsx".equals(fileName)){
                //sendToMessage(downloadCode,404,"message","下载出错了");
                throw new ServiceUnavailableException("服务器出错了");
            }
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipEntry.setUnixMode(644);//解决linux乱码
            zipOutputStream.putNextEntry(zipEntry);
            FileInputStream inputStream = new FileInputStream("/home/wang/Downloads/"+fileName);
            byte[] byt = StreamUtils.copyToByteArray(inputStream);
            InputStream is = new ByteArrayInputStream(byt);
            byte[] buffer = new byte[20480];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                zipOutputStream.write(buffer, 0, len);
                nowLen += len;
                sendToMessage(downloadCode,200,"progress", df.format(nowLen/allLen));
            }
            is.close();
            inputStream.close();


            sendToMessage(downloadCode,200,"message",fileName + "  压缩完成");
        }
        zipOutputStream.close();
        sendToMessage(downloadCode,200,"message","下载完成");

        /*FileInputStream in=null;
        ServletOutputStream out2=null;
        try
        {
            wss.send("开始下载");
            response.setHeader("content-disposition","attachment; filename=d.zip");
            in=new FileInputStream("/home/wang/下载/springBoot-pay.zip");
            out2=response.getOutputStream();
            double allLen = new File("/home/wang/下载/springBoot-pay.zip").length();
            double now = 0l;
            int len = 0;
            byte[] buffer = new byte[2048];
            DecimalFormat df = new DecimalFormat("0.000000");
            while ((len = in.read(buffer)) > 0) {
                out2.write(buffer,0,len);
                now += len;
                Thread.sleep(10);
                wss.send("progess:"+ df.format(now/allLen));
            }
            in.close();
            out2.flush();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(in!=null)
                in.close();
            if(out2 !=null)
                out2.close();
        }
        wss.send("下载结束");*/

        return "success";
    }
}
