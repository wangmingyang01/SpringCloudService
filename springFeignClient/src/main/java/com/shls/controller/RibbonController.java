package com.shls.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.shls.feign.BgyInterfaceServiceCaller;
import com.shls.feign.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.jws.soap.SOAPBinding;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户端实现负载均衡
 */
@RestController
@RequestMapping("/ribbon")
public class RibbonController {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "GetSwaggerHystrix")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Object GetSwagger(@PathVariable Integer id){
        // 将原来的ip:port的形式，改成注册到Eureka Server上的应用名即可
        String str = restTemplate.getForObject("http://springSwagger/swagger/test?id={1}", String.class, id);

        //示例
        /*ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://springSwagger/swagger/test?id={1}", String.class, id);
        str = responseEntity.getBody();*/

        //返回body是一个User对象类型
        /*ResponseEntity<User> responseEntity = restTemplate.getForEntity("http://springSwagger/swagger/test?id={1}", User.class, id);
        User user = responseEntity.getBody();*/

        /*Map<String, String> params = new HashMap<>();
        params.put("id", "123");
        ResponseEntity<User> responseEntity = restTemplate.getForEntity("http://springSwagger/swagger/test?id={id}", User.class, params);*/

        /*UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://springSwagger/swagger/test?id={id}").build()
                .expand("123").encode();
        URI url = uriComponents.toUri();
        ResponseEntity<User> responseEntity = restTemplate.getForEntity(url, User.class);*/

        return str;
    }

    /**
     * rabbon熔断返回
     */
    public String GetSwaggerHystrix(Integer id){
        return "GetSwagger 方法出现熔断";
    }

    //服务间调用复杂对象（ribbon）
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Object PostSwagger(@PathVariable Integer id){
        MultiValueMap<String, Object> entity = new LinkedMultiValueMap<>();
        entity.add("id", id);
        entity.add("name", "许三多");
        //集合List<Score> scores
        entity.add("scores[0].course","语文");
        entity.add("scores[0].num",90);
        entity.add("scores[1].course","数学");
        entity.add("scores[1].num",100);

        String str = restTemplate.postForObject("http://springSwagger/swagger/ribbon/user", entity, String.class);

        /*User user = new User(1, "wang");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://ServiceName/addUser", user, String.class);*/

        //String str = restTemplate.postForObject("http://ServiceName/addUser", user, String.class);

        //URI uri = restTemplate.postForLocation("http://ServiceName/addUser", user);

        return str;
    }

}
