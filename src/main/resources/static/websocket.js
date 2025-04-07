/* websocket.js */

let stompClient = null;

/**
 * Connect to the server's WebSocket endpoint at /ws using SockJS + STOMP.
 * Then subscribe to the relevant topics for real-time updates.
 */
function connectWebSocket() {
    console.log("Attempting to connect to WebSocket...");
    // 1. Create a SockJS client that points to /ws (from WebSocketConfig)
    let socket = new SockJS('/ws');
    console.log("SockJS instance created");

    // 2. Create a STOMP client over the SockJS connection
    stompClient = Stomp.over(socket);
    
    // Debug: Enable STOMP debug logging
    // stompClient.debug = function(str) {
    //     console.log("STOMP: ", str);
    // };

    console.log("Attempting STOMP connection...");
    // 3. Connect with empty headers (or add authentication if needed)
    stompClient.connect({}, onConnected, onError);
}

/**
 * Called when we have a successful connection.
 * Subscribe to /topic/board for game state, and /topic/scoreboard for leaderboard updates.
 */
function onConnected(frame) {
    console.log("Connected via STOMP: ", frame);

    // Subscribe to board updates
    console.log("Subscribing to /topic/board...");
    stompClient.subscribe('/topic/board', function(message) {
        // console.log("Received board update:", message);
        let boardState = JSON.parse(message.body);
        // console.log("Parsed board state:", boardState);
        handleBoard(boardState);
    });

    // Subscribe to scoreboard updates
    console.log("Subscribing to /topic/scoreboard...");
    stompClient.subscribe('/topic/scoreboard', function(message) {
        // console.log("Received scoreboard update:", message);
        let scoreboard = JSON.parse(message.body);
        // console.log("Parsed scoreboard:", scoreboard);
        handleScoreboard(scoreboard);
    });
    
    // Automatically start the game when connection is established
    console.log("Connection established, starting game...");
    startGame();
}

/**
 * Called if there is an error with the connection or during handshake.
 */
function onError(error) {
    console.error("STOMP connection error:", error);
}

/**
 * Gracefully disconnect from the STOMP endpoint if desired (e.g., page unload).
 */
function disconnectWebSocket() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected from STOMP WebSocket");
}

/* ============================
 * Methods to send messages
 * ============================ */

/**
 * Add a BFS snake (example). The server expects { "type": "BFS" }
 * on /app/addSnake
 */
function addBfsSnake() {
    if (!stompClient) return;
    stompClient.send('/app/addSnake', {}, JSON.stringify({ type: 'BFS' }));
}

/**
 * Remove a snake by ID. The server expects { "snakeId": 3 }
 * on /app/removeSnake
 */
function removeSnake(snakeId) {
    if (!stompClient) return;
    stompClient.send('/app/removeSnake', {}, JSON.stringify({ snakeId: snakeId }));
}

/**
 * Control a snake. 
 * { "snakeId": 7 } on /app/controlSnake
 */
function controlSnake(snakeId) {
    if (!stompClient) return;
    stompClient.send('/app/controlSnake', {}, JSON.stringify({ snakeId: snakeId }));
}

/**
 * Release control of the currently controlled snake.
 */
function releaseControl() {
    if (!stompClient) return;
    stompClient.send('/app/releaseControl', {}, JSON.stringify({}));
}

/**
 * Set the direction for the controlled snake, e.g. "LEFT", "RIGHT", "UP", "DOWN".
 * { "direction": "LEFT" }
 */
function setUserDirection(direction) {
    if (!stompClient) return;
    stompClient.send('/app/setDirection', {}, JSON.stringify({ direction: direction }));
}

/**
 * Start the game loop
 */
function startGame() {
    if (!stompClient) {
        console.error("Cannot start game: STOMP client not connected");
        return;
    }
    console.log("Starting game from client");
    stompClient.send("/app/startGame", {}, JSON.stringify({}));
    console.log("Start game message sent");
}

/* ============================
 * Helpers to handle messages
 * ============================ */

/**
 * Example function to render the board from an incoming boardState.
 */
function handleBoard(boardState) {
    // e.g. boardState might contain:
    // {
    //   "snakes": [ { "snakeId": 1, "name": "...", "bodyPositions": [...], "direction": "RIGHT" } ],
    //   "foodPositions": [ ... ],
    //   "gameMode": "ADD_AS_YOU_GO",
    //   "roundInProgress": false
    // }
    // console.log("Received board state:", boardState);
    renderBoard(boardState);
}

/**
 * Example function to render the scoreboard from an incoming scoreboard array.
 */
function handleScoreboard(scoreboard) {
    // e.g. scoreboard is an array of objects: [ { "snakeId": 2, "length": 5, "name": "Sneaky" }, ... ]
    // console.log("Received scoreboard:", scoreboard);
    renderScoreboard(scoreboard);
}

// Connect WebSocket when page loads
window.addEventListener('load', connectWebSocket);
