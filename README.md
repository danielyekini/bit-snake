# Snake AI Game

A Java-based Snake game featuring AI-controlled snakes using different pathfinding algorithms (BFS and A*). The game supports both single-player and tournament modes, with real-time WebSocket updates for multiplayer viewing.

## Features

- **Multiple AI Algorithms**: Watch snakes controlled by different pathfinding algorithms compete
  - Breadth-First Search (BFS)
  - A* Pathfinding
- **Game Modes**:
  - Add-as-you-go: Continuous play with dynamic snake addition
  - Tournament mode: All snakes start together, last snake standing wins
- **Real-time Updates**: WebSocket-based live game state broadcasting
- **Interactive Control**: Take control of any snake using arrow keys
- **Scoreboard**: Real-time tracking of snake lengths and rankings

## Technical Architecture

The project is built using:
- Java 21
- Spring Boot 3.2.3
- WebSocket for real-time communication
- Maven for dependency management

### Core Components

- **GameService**: Central orchestrator managing game state and rules
- **Board**: Game environment managing snake positions and collisions
- **Snake**: Abstract base class for AI-controlled snakes
- **SnakeFactory**: Creates different types of AI snakes
- **WebSocket Controller**: Handles real-time game state updates

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/snakeai.git
cd bit-snake
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

4. Open your browser and navigate to:
```
http://localhost:8080
```

## Game Controls

- **Arrow Keys**: Control your snake (when in control mode)
- **Space**: Start/Stop the game
- **R**: Reset the board
- **1-9**: Take control of snake with corresponding ID

## Development

### Project Structure

```
src/main/java/com/example/
├── domain/
│   ├── enums/          # Game enums (Direction, GameMode, etc.)
│   ├── model/          # Core game models
│   │   └── snakeai/    # Snake implementations
│   └── service/        # Game services
├── routing/
│   ├── controller/     # WebSocket and REST controllers
│   ├── dto/           # Data Transfer Objects
│   └── events/        # Game events
└── SnakeAiApplication.java
```

### Adding New Snake Types

1. Create a new class extending `Snake`
2. Implement the `getNextDirection` method with your pathfinding algorithm
3. Add the new type to the `SnakeType` enum
4. Update `SnakeFactory` to create instances of your new snake type

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Inspired by classic Snake games
- Pathfinding algorithms based on standard computer science implementations
- Spring Boot for the robust backend framework 