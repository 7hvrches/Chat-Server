package com.example.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    //입장, 채팅, 퇴장
    public enum MessageType {
        ENTER, TALK, QUIT
    }

    private MessageType type; //메시지 타입
    private String roomId; //방번호
    private String sender; //메시지 보낸 사람
    private String message; //메시지
    private long userCount; //채팅방 인원수, 채팅방 내에서 메시지가 전달될 때 인원수 갱신시 사용

    public ChatMessage() {

    }

    @Builder
    public ChatMessage(MessageType type, String roomId, String sender, String message) {
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
    }
}
