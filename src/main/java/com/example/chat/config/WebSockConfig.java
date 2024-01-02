package com.example.chat.config;

import com.example.chat.controller.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 *  pub/sub 메시징 구현을 위해 메시지를 발행하는 요청의 prefix 는 /pub 로 시작하도록 설정
 *  메시지를 구독하는 요청의 prefix 는 /sub 로 시작하도록 설정
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker //Stomp 사용을 위한 어노테이션
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //메시지 브로커가 /sub으로 시작하는 주제를 허용
        config.enableSimpleBroker("/sub");
        // /pub으로 시작하는 메시지가 @MessageMapping으로 라우팅되도록 설정
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // /ws-stomp 엔드포인트를 통해 websocket 연결을 활성화
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*")
                .withSockJS(); //sock.js를 통하여 낮은 버전의 브라우저에서도 websocket이 동작할 수 있게 합니다.
    }

    //StompHandler가 websocket 앞단에서 token을 체크할 수 있도록 인터셉터로 설정
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}
