package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.domain.model.Board;
import com.example.domain.service.GameService;
import com.example.domain.service.SnakeFactory;

@SpringBootApplication
public class SnakeGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnakeGameApplication.class, args);
    }

    // 1) Define a Board bean
    @Bean
    public Board board() {
        // If your Board constructor needs width/height or other config, pass it here
        return new Board(); 
    }

    // 2) Define a SnakeFactory bean
    @Bean
    public SnakeFactory snakeFactory() {
        return new SnakeFactory();
    }

    // 3) Define a GameService bean, injecting the Board and SnakeFactory beans
    @Bean
    public GameService gameService(Board board, SnakeFactory snakeFactory) {
        return new GameService(board, snakeFactory);
    }
}
