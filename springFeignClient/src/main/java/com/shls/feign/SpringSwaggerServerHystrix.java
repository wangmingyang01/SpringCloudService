package com.shls.feign;

import com.shls.feign.vo.AssetPackage;
import com.shls.feign.vo.User;
import com.shls.feign.vo.order.WordOrderAttachmentVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SpringSwaggerServerHystrix implements SpringSwaggerServer {
    Logger logger = LoggerFactory.getLogger(SpringSwaggerServerHystrix.class);

    @Override
    public String feignServer(String name) {
        logger.error("无法访问服务: feignServer");
        return "无法访问服务: feignServer";
    }

    @Override
    public Object object(int id) {
        logger.error("无法访问服务: object");
        return "无法访问服务: object";
    }

    @Override
    public User getUser(User user) {
        logger.error("无法访问服务: user");
        return null;
    }

    @Override
    public Object sendAssetPackage(AssetPackage assetPackage) {
        logger.error("无法访问服务: sendAssetPackage");
        return null;
    }

    @Override
    public Object uploadWordOrderAttachment(WordOrderAttachmentVo vo) {
        logger.error("无法访问服务: uploadWordOrderAttachment");
        return "无法访问服务: uploadWordOrderAttachment";
    }

}
