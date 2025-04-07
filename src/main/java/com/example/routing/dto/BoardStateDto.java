package com.example.routing.dto;

import java.util.List;

import com.example.domain.service.GameService;

/**
 * DTO for broadcasting the entire board state to "/topic/board".
 */
public class BoardStateDto {
    private List<SnakeDto> snakes;
    private List<?> foodPositions;
    private String gameMode;
    private boolean roundInProgress;

    public List<SnakeDto> getSnakes() { return snakes; }
    public void setSnakes(List<SnakeDto> snakes) { this.snakes = snakes; }

    public List<?> getFoodPositions() { return foodPositions; }
    public void setFoodPositions(List<?> foodPositions) { this.foodPositions = foodPositions; }

    public String getGameMode() { return gameMode; }
    public void setGameMode(String gameMode) { this.gameMode = gameMode; }

    public boolean isRoundInProgress() { return roundInProgress; }
    public void setRoundInProgress(boolean roundInProgress) { this.roundInProgress = roundInProgress; }

    /**
     * A static factory method that builds a BoardStateDto from the
     * current state of the GameService (including board, mode, etc.).
     */
    public static BoardStateDto fromGameService(GameService gameService) {
        BoardStateDto dto = new BoardStateDto();

        // 1. Build the SnakeDto list
        dto.setSnakes(
            gameService.getBoard().getSnakes().stream()
                .map(snake -> new SnakeDto(
                        snake.getSnakeId(),
                        snake.getName(),
                        snake.getBody()
                ))
                .toList()
        );

        // 2. Copy food positions
        dto.setFoodPositions(
            List.copyOf(gameService.getBoard().getFoodPositions())
        );

        // 3. Basic info about mode
        dto.setGameMode(gameService.getCurrentMode().toString());
        dto.setRoundInProgress(gameService.isRoundInProgress());

        return dto;
    }
}
