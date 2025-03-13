package com.example.snakeai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple message broker on a destination prefix /topic 
        // for broadcasting to clients, and /queue for point-to-point if needed
        config.enableSimpleBroker("/topic", "/queue");
        // Prefix used by clients for sending messages to server
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // The endpoint clients will connect to, e.g. ws://localhost:8080/snake-websocket
        registry.addEndpoint("/snake-websocket").setAllowedOriginPatterns("*").withSockJS();
    }
}

