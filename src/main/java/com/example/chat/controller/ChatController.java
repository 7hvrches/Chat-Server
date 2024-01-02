package com.example.chat.controller;

import com.example.chat.repository.ChatRoomRepository;
import com.example.chat.service.ChatService;
import com.example.chat.service.JwtTokenProvider;
import com.example.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * @MessageMapping 메세지 발행 처리
 */

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;

    /**
     * WebSocket 클라이언트 요청 메시지 핸들러
     * "/pub/chat/message"로 들어오는 메시징을 처리한다.
     * /pub: 메시지를 발행하는 작업
     * /chat/message: 특정 채널이나 주제를 가리킴
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @Header("token") String token) {
        String nickname = jwtTokenProvider.getUserNameFromJwt(token);
        // 로그인 회원 정보로 대화명 설정
        message.setSender(nickname);
        // 채팅방 인원수 세팅
        message.setUserCount(chatRoomRepository.getUserCount(message.getRoomId()));
        // Websocket에 발행된 메시지를 redis로 발행(publish)
        chatService.sendChatMessage(message);
    }
}