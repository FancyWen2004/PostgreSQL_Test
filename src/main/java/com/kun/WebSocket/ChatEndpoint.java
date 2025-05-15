package com.kun.WebSocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

@ServerEndpoint("/chat")
@Component
public class ChatEndpoint {

    // 处理WebSocket连接
    @OnOpen
    public void onOpen() {
        // 连接建立时的逻辑
    }
    // 处理WebSocket消息
    @OnMessage
    public void onMessage(String message) {
        // 处理接收到的消息
    }
    // 处理WebSocket关闭
    @OnClose
    public void onClose() {
        // 连接关闭时的逻辑
    }
}
