package com.example.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/*
    pub/sub 메시징 구현을 위해 메시지를 발행하는 요청의 prefix 는 /pub 로 시작하도록 설정
    메시지를 구독하는 요청의 prefix 는 /sub 로 시작하도록 설정
 */
@Configuration
@EnableWebSocketMessageBroker //Stomp 사용을 위한 어노테이션
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
