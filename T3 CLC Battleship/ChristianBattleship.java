import java.util.*;

// Battleship Game Incorporating Christian Values
public class ChristianBattleship {

    // Board and game constants
    static final int SIZE = 10;
    static final char EMPTY = '-';
    static final char SHIP = 'S';
    static final char HIT = 'H';
    static final char MISS = 'M';

    // Ship part counters (used to track remaining parts of each ship type)
    static int destroyerParts = 4; // Destroyer = 2x2 block (::) shape
    static int cruiserParts = 3; // Cruiser = horizontal (---) shape
    static int submarineParts = 3; // Submarine = diagonal left-to-right (\) shape

    // Player and computer game boards
    static char[][] playerBoard = new char[SIZE][SIZE];
    static char[][] computerBoard = new char[SIZE][SIZE];

    // To track computer’s previous guesses to avoid repeating shots
    static boolean[][] computerGuesses = new boolean[SIZE][SIZE];

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Display introductory Christian message
        System.out.println("Welcome to Christian Battleship!");
        System.out.println("\"Whatever you do, work heartily, as for the Lord and not for men.\" — Colossians 3:23");
        System.out.println("Play fairly, persevere through challenges, and respect your opponent.");

        // Initialize boards with empty cells
        initializeBoard(playerBoard);
        initializeBoard(computerBoard);

        // Place ships for both player and computer
        placePlayerShips();
        placeComputerShips();

        boolean playerTurn = true; // Player starts first

        // Main game loop, continues until someone wins
        while (true) {
            if (playerTurn) {
                // Player’s turn
                System.out.println("\nYour Turn! Stay strong and focused.");
                System.out.println("\"Let us not grow weary of doing good...\" — Galatians 6:9");
                boolean anotherTurn = playerShoot(); // Take a shot
                if (checkVictory(computerBoard)) {
                    // Check if all computer ships are sunk
                    System.out.println("Congratulations! You have won. Remain humble in victory.");
                    System.out.println("\"I can do all things through Christ who strengthens me.\" — Philippians 4:13");
                    break; // Game over
                }
                playerTurn = anotherTurn; // Player gets another turn on hit
            } else {
                // Computer’s turn
                System.out.println("\nComputer's Turn... Stay patient and faithful.");
                System.out.println("\"Blessed is the one who perseveres under trial...\" — James 1:12");
                boolean anotherTurn = computerShoot(); // Computer takes a shot
                if (checkVictory(playerBoard)) {
                    // Check if all player ships are sunk
                    System.out.println("The computer has won. Accept defeat with grace and perseverance.");
                    System.out.println("\"Be strong and courageous.\" — Joshua 1:9");
                    break; // Game over
                }
                playerTurn = !anotherTurn; // Computer continues if it hits
            }
        }
    }

    // Fills board with empty cells
    static void initializeBoard(char[][] board) {
        for (int i = 0; i < SIZE; i++)
            Arrays.fill(board[i], EMPTY);
    }

    // Allow player to place ships manually
    static void placePlayerShips() {
        System.out.println("\nTime to place your ships! Honor the rules and place them carefully.");
        System.out.println("\"Let all things be done decently and in order.\" — 1 Corinthians 14:40");

        // Place each type of ship
        placeShipManually(playerBoard, "Destroyer", 2, 2);
        placeShipManually(playerBoard, "Submarine", 3, 1);
        placeShipManually(playerBoard, "Cruiser", 3, 0);
    }

    // Handles manual ship placement for the player
    static void placeShipManually(char[][] board, String shipName, int length, int type) {
        System.out.println("Placing your " + shipName + ":");
        boolean placed = false;

        // Determine unique ship character based on type
        char shipChar = switch (type) {
            case 0 -> 'C'; // Cruiser = horizontal (---)
            case 1 -> 'S'; // Submarine = diagonal (\)
            case 2 -> 'D'; // Destroyer = 2x2 block (::)
            default -> SHIP;
        };

        while (!placed) {
            int row = -1;
            int col = -1;
            boolean validInput = false;

            // Get and validate numeric input
            while (!validInput) {
                try {
                    System.out.print("Enter starting row (0-9): ");
                    row = scanner.nextInt();
                    System.out.print("Enter starting column (0-9): ");
                    col = scanner.nextInt();
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, please type numbers (0-9) only.");
                    scanner.nextLine(); // Clear invalid input
                }
            }

            if (type == 0) { // Cruiser = horizontal (---)
                if (col + length <= SIZE) {
                    boolean clear = true;
                    for (int i = 0; i < length; i++) {
                        if (board[row][col + i] != EMPTY)
                            clear = false;
                    }
                    if (clear) {
                        for (int i = 0; i < length; i++) {
                            board[row][col + i] = shipChar;
                        }
                        placed = true;
                    }
                }
            } else if (type == 1) { // Submarine = diagonal (\)
                if (row + length <= SIZE && col + length <= SIZE) {
                    boolean clear = true;
                    for (int i = 0; i < length; i++) {
                        if (board[row + i][col + i] != EMPTY)
                            clear = false;
                    }
                    if (clear) {
                        for (int i = 0; i < length; i++) {
                            board[row + i][col + i] = shipChar;
                        }
                        placed = true;
                    }
                }
            } else if (type == 2) { // Destroyer = 2x2 block (::)
                if (row + 1 < SIZE && col + 1 < SIZE) {
                    if (board[row][col] == EMPTY && board[row + 1][col] == EMPTY &&
                        board[row][col + 1] == EMPTY && board[row + 1][col + 1] == EMPTY) {
                        board[row][col] = shipChar;
                        board[row + 1][col] = shipChar;
                        board[row][col + 1] = shipChar;
                        board[row + 1][col + 1] = shipChar;
                        placed = true;
                    }
                }
            }

            if (!placed) {
                System.out.println("Invalid position. Please try again with patience.");
            } else {
                System.out.println(shipName + " placed with care and fairness.");
            }
        }
    }

    // Randomly place computer's ships
    static void placeComputerShips() {
        Random random = new Random();
        // Only 1 Destroyer, 1 Submarine, 1 Cruiser for simplicity
        placeShipRandomly(computerBoard, random, 2, 2);
        placeShipRandomly(computerBoard, random, 3, 1);
        placeShipRandomly(computerBoard, random, 3, 0);
    }

    // Random placement logic for computer ships
    static void placeShipRandomly(char[][] board, Random random, int length, int type) {
        boolean placed = false;
        while (!placed) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);

            // Reuse manual placement logic with random inputs
            if (type == 0) { // Cruiser = horizontal (---) shape
                if (col + length <= SIZE) {
                    boolean clear = true;
                    for (int i = 0; i < length; i++) {
                        if (board[row][col + i] == SHIP)
                            clear = false;
                    }
                    if (clear) {
                        for (int i = 0; i < length; i++) {
                            board[row][col + i] = SHIP;
                        }
                        placed = true;
                    }
                }
            } else if (type == 1) { // Submarine = diagonal left-to-right (\) shape
                if (row + length <= SIZE && col + length <= SIZE) {
                    boolean clear = true;
                    for (int i = 0; i < length; i++) {
                        if (board[row + i][col + i] == SHIP)
                            clear = false;
                    }
                    if (clear) {
                        for (int i = 0; i < length; i++) {
                            board[row + i][col + i] = SHIP;
                        }
                        placed = true;
                    }
                }
            } else if (type == 2) { // Destroyer = 2x2 block (::) shape
                if (row + 1 < SIZE && col + 1 < SIZE) {
                    if (board[row][col] == EMPTY && board[row + 1][col] == EMPTY &&
                            board[row][col + 1] == EMPTY && board[row + 1][col + 1] == EMPTY) {
                        board[row][col] = SHIP;
                        board[row + 1][col] = SHIP;
                        board[row][col + 1] = SHIP;
                        board[row + 1][col + 1] = SHIP;
                        placed = true;
                    }
                }
            }
        }
    }
    // Handles player shooting at the computer’s board
    static boolean playerShoot() {
        while (true) {
            int row = -1;
            int col = -1;
            boolean validInput = false;

            // Improves Input Validation for Player Shots
            while (!validInput) {
                try {
                    System.out.print("Enter starting row (0-9): ");
                    row = scanner.nextInt();
                    System.out.print("Enter starting column (0-9): ");
                    col = scanner.nextInt();
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, please type numbers (0-9) only.");
                    scanner.nextLine();
                }
            }

            // Prevents Out-of-Bounds Errors
            if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
                System.out.println("Invalid input. Try again.");
                continue;
            }

            // Blocks Repeated Shots on Same Cell
            if (computerBoard[row][col] == HIT || computerBoard[row][col] == MISS) {
                System.out.println("Already targeted. Choose a new cell.");
                continue;
            }

            // Maintained Ship Type Before Marking Hits
            if (computerBoard[row][col] == 'D' || computerBoard[row][col] == 'S' || computerBoard[row][col] == 'C') {
                char shipType = computerBoard[row][col]; // get type BEFORE changing to 'H'
                computerBoard[row][col] = HIT;
                System.out.println("Hit! You get another turn!");
                System.out.println("\nEnemy Waters:");
                displayFogBoard(computerBoard);
                decrementShipPart(shipType); // correct call
                return true;
            } else {
                computerBoard[row][col] = MISS;
                System.out.println("Miss!");
                System.out.println("\nEnemy Waters:");
                displayFogBoard(computerBoard);
                return false;//turn over
            }
        }
    }

    // Displays the enemy board, hiding unhit ships
    static void displayFogBoard(char[][] board) {
        System.out.print("   ");
        for (int c = 1; c <= SIZE; c++)
            System.out.print(c + " ");
        System.out.println();
        for (int r = 0; r < SIZE; r++) {
            char rowLabel = (char) ('A' + r);
            System.out.print(rowLabel + "  ");
            for (int c = 0; c < SIZE; c++) {
                char val = board[r][c];
                if (val == HIT || val == MISS) {
                    System.out.print(val + " ");
                } else {
                    System.out.print(EMPTY + " "); // hide ships
                }
            }
            System.out.println();
        }
    }

    // Decrements ship part counters (placeholder logic)
    static void decrementShipPart(char shipType) {
        switch (shipType) {
            case 'D':
                destroyerParts--;
                if (destroyerParts == 0)
                    System.out.println("Destroyer Eliminated");
                break;
            case 'S':
                submarineParts--;
                if (submarineParts == 0)
                    System.out.println("Submarine Eliminated");
                break;
            case 'C':
                cruiserParts--;
                if (cruiserParts == 0)
                    System.out.println("Cruiser Eliminated");
                break;
        }
    }

    // Displays the current state of a board
    static void displayBoard(char[][] board) {
        System.out.print("   ");
        for (int c = 1; c <= SIZE; c++)
            System.out.print(c + " ");
        System.out.println();
        for (int r = 0; r < SIZE; r++) {
            char rowLabel = (char) ('A' + r);
            System.out.print(rowLabel + "  ");
            for (int c = 0; c < SIZE; c++) {
                System.out.print(board[r][c] + " ");
            }
            System.out.println();
        }
    }

    // Computer randomly fires at player's board
    static boolean computerShoot() {
        Random random = new Random();
        while (true) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);

            if (!computerGuesses[row][col]) { // Ensure no repeat shots
                computerGuesses[row][col] = true;
                System.out.println("Computer fires at (" + row + "," + col + ").");

                // Check for ALL ship types, just like in playerShoot()
                if (playerBoard[row][col] == 'D' || playerBoard[row][col] == 'S' || playerBoard[row][col] == 'C') {
                    char shipType = playerBoard[row][col]; // store before replacing
                    playerBoard[row][col] = HIT; // Mark as hit
                    decrementShipPart(shipType); // Reduce correct ship counter
                    System.out.println("Computer hits your ship! Stay strong.");
                    System.out.println("\nYour Board:");
                    displayBoard(playerBoard);
                    return true; // Computer gets another turn
                } else {
                    if (playerBoard[row][col] == EMPTY)
                        playerBoard[row][col] = MISS;
                    System.out.println("Computer misses! Your perseverance is rewarded.");
                    System.out.println("\nYour Board:");
                    displayBoard(playerBoard);
                    return false; // Turn over
                }
            }
        }
    }

    // Checks if all ships on a board have been destroyed
    static boolean checkVictory(char[][] board) {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (board[i][j] == SHIP)
                    return false;
        return true; // No ships left = victory
    }
}


// Cruiser = horizontal (---) shape
// Submarine = diagonal left-to-right (\) shape
// Destroyer = 2x2 block (::) shape