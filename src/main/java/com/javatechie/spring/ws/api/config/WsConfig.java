package com.javatechie.spring.ws.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //websocket 서버 사용 설정
public class WsConfig implements WebSocketMessageBrokerConfigurer{

	@Override
	//엔드 포인트 등록, withSockJS() 이용 시 브라우저 Websocket 지원하지 않을 경우 fallback 옵션 활성화
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/javatechie").withSockJS(); //클라이언트 측에서 socket 생성할 때, 여기에 정의한 문자열로 생성해야 통신이 가능, stomp websocket의 연결 endpoint
	}
	
	@Override
	//메시지 라우팅할 때 사용하는 브로커 구성
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// "/topic"로 시작하는 주제를가진 메시지를 라우팅해서 해당 주제의 모든 클라이언트에게 메시지
		registry.enableSimpleBroker("/topic");
		// "/app"으로 시작하는 메시지만 메시지 핸들러로 라우팅한다고 정의, 클라이언트가 서버로 메시지를 보낼 때 붙여야 하는 url prefix
		registry.setApplicationDestinationPrefixes("/app");
	}
}
