// Game logic and AI implementation
// scripts.js

// Grab references to HTML elements
const gameBoardEl = document.getElementById('game-board');
const scoreboardEl = document.getElementById('scoreboard');
const startGameBtn = document.getElementById('start-game');
const endGameBtn = document.getElementById('end-game');

// On page load (or after DOM is ready), attach a click event
startGameBtn.addEventListener('click', () => {
    // As a demo, let's just add a BFS snake
    // so we can see something appear in the boardState logs
    addBfsSnake();
});

endGameBtn.addEventListener('click', () => {
    disconnectWebSocket();
});

/**
 * The 'renderBoard' function is called from websocket.js's onConnected(...) subscription
 * whenever we receive a new boardState broadcast from the server.
 *
 * boardState structure typically looks like:
 * {
 *   "snakes": [
 *     { "snakeId": 1, "name": "...", "bodyPositions": [...], "direction": "RIGHT" },
 *     ...
 *   ],
 *   "foodPositions": [
 *     { "x": 5, "y": 10 },
 *     ...
 *   ],
 *   "gameMode": "ADD_AS_YOU_GO",
 *   "roundInProgress": false
 * }
 */
function renderBoard(boardState) {
    // console.log("Received board state:", boardState);

    // Build some basic HTML to display the snake info
    let snakesHtml = '';
    if (boardState.snakes && boardState.snakes.length > 0) {
        boardState.snakes.forEach(snake => {
            const length = snake.bodyPositions ? snake.bodyPositions.length : 0;
            snakesHtml += `<div>
                <strong>Snake #${snake.snakeId}</strong> (name: ${snake.name})<br/>
                Length: ${length}, Direction: ${snake.direction}
            </div>`;
        });
    } else {
        snakesHtml = '<div>No snakes on board.</div>';
    }

    // Show the food positions
    let foodHtml = '';
    if (boardState.foodPositions && boardState.foodPositions.length > 0) {
        foodHtml = '<div>Food Positions:</div>';
        boardState.foodPositions.forEach(pos => {
            foodHtml += `<div>- (${pos.x}, ${pos.y})</div>`;
        });
    } else {
        foodHtml = '<div>No food yet.</div>';
    }

    // Game mode & round info
    const modeInfo = `<div>Game Mode: ${boardState.gameMode}</div>
                      <div>Round In Progress: ${boardState.roundInProgress}</div>`;

    // Put it all together
    gameBoardEl.innerHTML = `
        <h2>Current Board State</h2>
        ${modeInfo}
        <hr/>
        <h3>Snakes</h3>
        ${snakesHtml}
        <hr/>
        <h3>Food</h3>
        ${foodHtml}
    `;
}

/**
 * The 'renderScoreboard' function is called from websocket.js's onConnected(...) subscription
 * whenever we receive a scoreboard broadcast from the server.
 *
 * scoreboard might be an array like:
 * [
 *   { "snakeId": 2, "length": 5, "name": "Sneaky" },
 *   { "snakeId": 3, "length": 4, "name": "Python" }
 * ]
 */
function renderScoreboard(scoreboard) {
    // console.log("Received scoreboard:", scoreboard);

    if (!scoreboard || scoreboard.length === 0) {
        scoreboardEl.innerHTML = `<h2>Scoreboard</h2><div>No data yet.</div>`;
        return;
    }

    let sbHtml = `<h2>Scoreboard</h2>`;
    scoreboard.forEach(entry => {
        sbHtml += `<div>
            Snake #${entry.snakeId} - ${entry.name}, length: ${entry.length}
        </div>`;
    });

    scoreboardEl.innerHTML = sbHtml;
}

// Expose these functions globally so websocket.js can call them
window.renderBoard = renderBoard;
window.renderScoreboard = renderScoreboard;
