package com.chat.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


/**
 * @EnableWebSocketMessageBroker : bật tính năng websocket + message broker sử dụng STOMP
 * @Configuration : báo cho Spring đây là class cấu hình
 *
 * */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        //Mở Enpoint

        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry brokerRegistry){
        brokerRegistry.enableSimpleBroker("/topic");// bước 1 :subcribe /topic
        brokerRegistry.setApplicationDestinationPrefixes("/app");// /app
    }
}
