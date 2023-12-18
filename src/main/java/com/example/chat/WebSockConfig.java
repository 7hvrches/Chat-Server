package com.example.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/*
    WebSocketHandler 를 이용하여 Websocket 을 활성화하기 위한 Config
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSocket //Websocket 활성화
public class WebSockConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //endpoint 를 /ws/chat 으로 하고, 도메인이 다른 서버에서도 접속 가능하도록 *를 추가
        registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*"); //Websocket의 경우 별개의 프로토콜이므로 HTTP가 아닌 ws로 시작하는 주소 체계를 갖는다
    }
}
