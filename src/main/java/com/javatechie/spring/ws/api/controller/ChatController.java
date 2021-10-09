package com.javatechie.spring.ws.api.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.javatechie.spring.ws.api.model.ChatMessage;

//클라이언트에게 메시지 수신한 다음, 다른 클라이언트에게 브로드캐스트
@Controller
public class ChatController {

	@MessageMapping("/chat.register") // 어노테이션 발행하는 경로, **님이 입장하셨습니다라는 채팅을 참여자 전체에게
	@SendTo("/topic/public") // 구독 경로, SendTo는 1:n으로 메시지 뿌릴 때 사용하는 구조이며 보통 경로가 "/topic", SendToUser는 1:1 경로는
								// "/queue"
	// @Payload: websocket에서 전송하는 데이터를 받기 위한 어노테이션
	// SendTo로 토픽 구독한 사람 전원에게 메시지 보낼 수 있지만 메소드 converAndSend 메소드를 사용하면 @payload로 넘어온
	// 메시지 내부의 값을 토픽으로 활용
	public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		// 누가 보냈는지 정보 담기
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

		System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆>" + headerAccessor);
		return chatMessage;
	}

	@MessageMapping("/chat.send") // chat.send로 참여한 사람들에게 입력한 메시지를 전송하는 거라고 보면 됨
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {

		System.out.println("♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥>" + chatMessage);
		return chatMessage;

	}

}
