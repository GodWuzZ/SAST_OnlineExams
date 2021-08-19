package sast.onlineexams.component;

import cn.hutool.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author sherman
 * @create 2021-08-10 9:26
 * @description websocket实现类
 */
@Component
@ServerEndpoint("/ws/{username}")
public class WebSocket {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocket.class);

    private Session session;
    private static CopyOnWriteArraySet<WebSocket> webSockets =new CopyOnWriteArraySet<>();
    private static Map<String,Session> sessionPool = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username")String username) {
        this.session = session;
        webSockets.add(this);
        if(username==null)
            username="undefined";
        sessionPool.put(username, session);
        LOGGER.info(username + "[websocket消息]有新的连接，总数为:" + webSockets.size());
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);
        LOGGER.info("[websocket消息]连接断开，总数为:" + webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {
        LOGGER.info("[websocket消息]收到客户端消息:"+message);
    }

    // 此为广播消息
    public void sendAllMessage(String message,Object data) {
        JSONObject result=new JSONObject();
        Map<String,Object>map = new HashMap<>();
        map.put("message",message);
        map.put("data",data);
        result.putAll(map);
        for(WebSocket webSocket : webSockets) {
            LOGGER.info("[websocket消息]广播消息:"+message);
            try {
                webSocket.session.getAsyncRemote().sendText(result.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息
    public void sendOneMessage(String username, String message) {
        JSONObject result=new JSONObject();
        result.putOnce("message",message);
        LOGGER.info("[websocket消息]单点消息:"+message);
        Session session = sessionPool.get(username);
        if (session != null) {
            try {
                session.getAsyncRemote().sendText(result.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
