package com.example.routing.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Client can subscribe to /topic/...
        config.enableSimpleBroker("/topic");
        // Client can send messages to /app/...
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Client connects here for the WebSocket handshake
        registry.addEndpoint("/ws")
        .setAllowedOrigins("http://localhost:8080")
        .withSockJS();
    }
}

