package com.shls.webSocket.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//这里是一个类注解，告诉虚拟机该类被注解为一个WebSocket端点
@ServerEndpoint("/webSocketToUserService/{sendUserId}")
@Component
public class WebSocketToUserService {
    /** 静态工具类中注入 RedisTemplate*/
    private static RedisTemplate redisTemplate;
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        WebSocketToUserService.redisTemplate = redisTemplate;
    }

    private static Logger logger = Logger.getLogger(WebSocketToUserService.class);
    public static String webSocketService = "WebSocket_Record";

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    public static ConcurrentHashMap<String, WebSocketToUserService> webSocketSet = new ConcurrentHashMap<String, WebSocketToUserService>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //当前发消息的人员编号
    private String sendUserId = null;

    @OnOpen
    public void onOpen(@PathParam(value = "sendUserId") String sendUserId, Session session, EndpointConfig config) {
        System.out.println("加入用户userId="+sendUserId);
        this.sendUserId = sendUserId;//接收到发送消息的人员编号
        this.session = session;
        webSocketSet.put(sendUserId, this);//加入map中
        /*redisTemplate.opsForHash().put("WebSocket_Service", "1", new UserMessage("离线列表", "这是一条content内容"));
        UserMessage user = (UserMessage) redisTemplate.opsForHash().get("WebSocket_Service", "1");
        System.out.println(user.getType() + " " + user.getContent());*/

        //发送在线人员
        try {
            sendMessageToAll(new UserMessage("在线人员列表", onlineUserIds()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
     * 这里是方法注解，在接收到客户端的消息时触发
     *
     * content:{"message":1212, "receiverUserId":12138}
     */
    @OnMessage
    public void sendMessage(@PathParam(value = "sendUserId") String sendUserId, String content){
        JSONObject json = JSON.parseObject(content);
        String receiverUserId = (String) json.get("receiverUserId");
        String message = (String) json.get("message");
        try {
            if(sendUserId.equals(receiverUserId)) {
                //不支持发给自己
                System.out.println("发送->userId：" + sendUserId + ", Error:不支持自己发送信息给自己");
                webSocketSet.get(sendUserId).send(new UserMessage("系统消息","Error:不支持自己发送信息给自己").toJSONString());
            } else if (webSocketSet.get(receiverUserId) != null) {
                //判断接收用户是否是当前发消息的用户
                System.out.println("发送userId：" + sendUserId + "->" + receiverUserId);
                webSocketSet.get(receiverUserId).send(new UserMessage(sendUserId,message).toJSONString());
                //保存记录
                redisTemplate.opsForHash().put(webSocketService, sendUserId + "->" + receiverUserId + ":" + System.currentTimeMillis(),
                        new UserMessage(sendUserId, receiverUserId, "record", message, System.currentTimeMillis()).toJSONString());
            } else {
                //返回一条消息，告诉用户发送者不存在
                System.out.println("发送->userId：" + sendUserId + ", Error:用户ID " + receiverUserId + " 不上线");
                webSocketSet.get(sendUserId).send(new UserMessage("系统消息","Error:用户ID " + receiverUserId + " 不上线").toJSONString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(CloseReason reason) {
        if (sendUserId != null) {
            System.out.println("关闭->userId="+sendUserId);
            webSocketSet.remove(sendUserId);  //从set中删除
        }
        if(reason != null) {
            System.out.println("webSocket连接关闭,原因是：" + reason.getReasonPhrase() + "code:" + reason.getCloseCode());
        }

        //发送在线人员
        try {
            sendMessageToAll(new UserMessage("在线人员列表", onlineUserIds()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }


    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     */
    public void send(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    //从set中删除
    public void close(String userId){
        if (userId != null) {
            WebSocketToUserService webSocketService = webSocketSet.get(userId);
            if(webSocketService != null) {
                try {
                    //关闭这个webSocket
                    webSocketService.session.close();
                    //从set中删除
                    webSocketSet.remove(userId);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    //向所有在线用户发送消息
    public static void sendMessageToAll(UserMessage userMessage) throws IOException {
        for (WebSocketToUserService wss : WebSocketToUserService.webSocketSet.values()){
            wss.send(userMessage.toJSONString());
        }
    }

    //在线人员
    public String onlineUserIds(){
        StringBuilder builder = new StringBuilder();
        Set<String> keys=webSocketSet.keySet();
        Iterator<String> iterator1=keys.iterator();
        while (iterator1.hasNext()){
            builder.append(iterator1.next() +";");
        }

        if(builder.toString().length() > 0){
            return builder.substring(0, builder.length());
        }

        return builder.toString();
    }

}
