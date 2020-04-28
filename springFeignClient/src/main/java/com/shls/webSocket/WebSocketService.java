package com.shls.webSocket;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

//这里是一个类注解，告诉虚拟机该类被注解为一个WebSocket端点
@ServerEndpoint("/webSocketService/{downloadCode}")
@Component
public class WebSocketService {
    private static Logger logger = Logger.getLogger(WebSocketService.class);

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    public static ConcurrentHashMap<String, WebSocketService> webSocketSet = new ConcurrentHashMap<String, WebSocketService>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //当前发消息的人员编号
    private String downloadCode = null;

    @OnOpen
    public void onOpen(@PathParam(value = "downloadCode") String downloadCode, Session session, EndpointConfig config) {
        System.out.println("加入用户userId="+downloadCode);
        this.downloadCode = downloadCode;//接收到发送消息的人员编号
        this.session = session;
        webSocketSet.put(downloadCode, this);//加入map中
    }


    //这里是方法注解，在接收到客户端的消息时触发
    @OnMessage
    public void sendMessage(@PathParam(value = "downloadCode") String downloadCode, String clientMessage){
        //判断接收用户是否是当前发消息的用户
        if (webSocketSet.get(downloadCode) != null) {
            try {
                System.out.println("发送->userId：" + downloadCode);
                webSocketSet.get(downloadCode).send(clientMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClose
    public void onClose(CloseReason reason) {
        if (downloadCode != null) {
            System.out.println("关闭->userId="+downloadCode);
            webSocketSet.remove(downloadCode);  //从set中删除
        }
        if(reason != null) {
            System.out.println("webSocket连接关闭,原因是：" + reason.getReasonPhrase() + "code:" + reason.getCloseCode());
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
            WebSocketService webSocketService = webSocketSet.get(userId);
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

    //向某个webSocket发送消息
    public static void sendToMessage(String downloadCode, int code, String type, String content) throws IOException {
        WebSocketService wss = webSocketSet.get(downloadCode);
        if(wss != null) {
            Message message = new Message(code, type, content);
            wss.send(JSONObject.toJSONString(message));
        }
    }

}
