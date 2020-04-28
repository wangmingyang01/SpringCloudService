package com.shls.feign;

import com.shls.feign.processor.FormData;
import com.shls.feign.processor.MultipartAndUrlencodedFormContentProcessor;
import com.shls.feign.vo.AssetPackage;
import com.shls.feign.vo.User;
import com.shls.feign.vo.order.WordOrderAttachmentVo;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;

/**
 * 服务间相互调用,以项目springSwagger为服务提供方
 */
@Service
@FeignClient(value = "springSwagger"
        /*,fallback = SpringSwaggerServerHystrix.class
        ,url = "127.0.0.1:8080"*/
        /*,configuration = {SpringSwaggerServer.ConfigurationFeign.class}*/)
public interface SpringSwaggerServer {

    @RequestMapping(value = "/swagger/feignServer", method = RequestMethod.POST)
    String feignServer(@RequestParam("name") String name);

    //返回是Object类型（return不能返回String类型）
    @RequestMapping(value = "/swagger/object", method = RequestMethod.GET)
    Object object(@RequestParam(value = "id")int id);

    //传递实体类,返回实体类
    @RequestMapping(value = "/swagger/feign/user", method = RequestMethod.POST)
    User getUser(@RequestBody User user);

    @RequestMapping(value = "/swagger/sendAssetPackage", method = RequestMethod.POST)
    Object sendAssetPackage(@RequestBody AssetPackage assetPackage);

    //表单里带附件带参数的传递
    @RequestMapping(value = "/swagger/uploadWordOrderAttachment", method = RequestMethod.POST)
    Object uploadWordOrderAttachment(@RequestBody WordOrderAttachmentVo vo);

    @Component
    class ConfigurationFeign {
        @Autowired
        ObjectFactory<HttpMessageConverters> messageConverters;

        @Bean
        public Encoder feignFormEncoder() {
            return new Encoder() {
                @Override
                public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
                    System.out.println(">>>:" + bodyType);
                    if (bodyType == MultipartFile.class) {
                        //目前适用于表单类工单(带附件) uploadWordOrderInfo
                        new SpringFormEncoder().encode(object, bodyType, template);
                    } else if (bodyType == WordOrderAttachmentVo.class) {
                        //附件类工单上传
                        Charset charset = Charset.forName("UTF-8");

                        /*WordOrderAttachmentVo vo = (WordOrderAttachmentVo) object;
                        //附件
                        Map<String, Object> filesData = new HashMap<>();
                        //参数
                        Map<String, Object> paramsData = new HashMap<>();
                        paramsData.put("orderId", vo.getOrderId());

                        for (int i=0; (vo.getAttachmentList()!= null && i<vo.getAttachmentList().size()); i++) {
                            MultipartFile file = vo.getAttachmentList().get(i).getFile();
                            filesData.putAll(singletonMap(file.getName(), ((Object) file)));
                            paramsData.put("attachmentList["+ i +"].type", vo.getAttachmentList().get(i).getType());
                        }

                        for (int i=0; (vo.getTradingNotesList()!= null && i<vo.getTradingNotesList().size()); i++) {
                            MultipartFile file = vo.getTradingNotesList().get(i).getFile();
                            filesData.putAll(singletonMap(file.getName(), ((Object) file)));
                            paramsData.put("tradingNotesList["+ i +"].type", vo.getTradingNotesList().get(i).getType());
                            paramsData.put("tradingNotesList["+ i +"].tradingNotesId", vo.getTradingNotesList().get(i).getTradingNotesId());
                        }*/

                        try {
                            FormData formData = new FormData(object, true);
                            MultipartAndUrlencodedFormContentProcessor processor = new MultipartAndUrlencodedFormContentProcessor(new Encoder.Default());
                            processor.process(template, charset, formData.getFilesData(), formData.getParamsData());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        //默认的
                        new SpringEncoder(messageConverters).encode(object, bodyType, template);
                    }
                }
            };
        }
    }
}
