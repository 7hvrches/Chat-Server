package com.example.chat.controller;

import com.example.chat.dto.ChatMessage;
import com.example.chat.redis.RedisPublisher;
import com.example.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 *  @MessageMapping 메세지 발행 처리
 *  클라이언트가 채팅방 입장 시 Redis 채팅방에서 대화가 가능하도록 리스너를 연동하는 enterChatRoom 메서드 설정
 *  채팅방에 발행된 메시지는 서로 다른 서버에 공유하기 위해 redis의 채팅방으로 발행합니다
 */
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }
        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
    }
}