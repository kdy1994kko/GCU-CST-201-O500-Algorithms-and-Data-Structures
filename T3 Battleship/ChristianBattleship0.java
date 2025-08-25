import java.util.*;

// Battleship Game Incorporating Christian Values
public class ChristianBattleship0 {

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
        while (!placed) {
            // Variables to store player input and track if valid input is received
            int row = -1;
            int col = -1;
            boolean validInput = false;

            // Loop until valid numeric input is entered
            while (!validInput) {
                try {
                    // Get coordinates from player
                    System.out.print("Enter starting row (0-9): ");
                    row = scanner.nextInt(); // Try reading row as integer
                    System.out.print("Enter starting column (0-9): ");
                    col = scanner.nextInt(); // Try reading column as integer
                    validInput = true; // If successful, exit loop
                } catch (InputMismatchException e) {
                    // Handles non-integer inputs like letters or special characters
                    System.out.println("Invalid input, please type numbers (0-9) only.");
                    scanner.nextLine(); // clear invalid input
                }
            }

            // Check ship type and validate placement
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

            // Loop until player provides valid numeric input
            while (!validInput) {
                try {
                    System.out.print("Enter starting row (0-9): ");
                    row = scanner.nextInt(); // Read row input
                    System.out.print("Enter starting column (0-9): ");
                    col = scanner.nextInt(); // Read column input
                    validInput = true; // Input is valid, exit loop
                } catch (InputMismatchException e) {
                    // Display error and prompt again if input is invalid
                    System.out.println("Invalid input, please type numbers (0-9) only.");
                    scanner.nextLine(); // clear invalid input
                }
            }

            // Input validation
            if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
                System.out.println("Invalid input. Stay focused and try again.");
                System.out.println("\"Be completely humble and gentle; be patient...\" — Ephesians 4:2");
                continue;
            }

            // Check if already targeted
            if (computerBoard[row][col] == HIT || computerBoard[row][col] == MISS) {
                System.out.println("You already targeted this cell. Patience! Try a new one.");
                continue;
            }

            // Determine hit or miss
            if (computerBoard[row][col] == SHIP) {
                computerBoard[row][col] = HIT;
                System.out.println("Hit! You get another turn!");
                System.out.println(
                        "\"Blessed be the Lord my strength, who trains my hands for war, and my fingers for battle.\" — Psalms 144:1");

                // Decrement relevant ship parts
                decrementShipPart(row, col);

                return true; // Allow another shot
            } else {
                computerBoard[row][col] = MISS;
                System.out.println("Miss!");
                System.out.println("\"The righteous falls seven times, and rises again...\" — Proverbs 24:16");
                return false; // Turn over
            }
        }
    }

    // Decrements ship part counters (placeholder logic)
    static void decrementShipPart(int row, int col) {
        // Check Destroyer
        if (destroyerParts > 0 && isPartOfDestroyer(row, col)) {
            destroyerParts--;
            if (destroyerParts == 0)
                System.out.println("Escape Boat Found, First Battleship Eliminated");
        }
        // Check Submarine
        else if (submarineParts > 0 && isPartOfSubmarine(row, col)) {
            submarineParts--;
            if (submarineParts == 0)
                System.out.println("Second Battleship Eliminated");
        }
        // Check Cruiser
        else if (cruiserParts > 0 && isPartOfCruiser(row, col)) {
            cruiserParts--;
            if (cruiserParts == 0)
                System.out.println("Last Battleship Eliminated");
        }
    }

    // Placeholder methods
    static boolean isPartOfDestroyer(int row, int col) {
        return computerBoard[row][col] == HIT;
    }

    static boolean isPartOfSubmarine(int row, int col) {
        return computerBoard[row][col] == HIT;
    }

    static boolean isPartOfCruiser(int row, int col) {
        return computerBoard[row][col] == HIT;
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

                if (playerBoard[row][col] == SHIP) {
                    playerBoard[row][col] = HIT;
                    System.out.println("Computer hits your ship! Stay strong.");
                    return true; // Computer shoots again
                } else {
                    if (playerBoard[row][col] == EMPTY)
                        playerBoard[row][col] = MISS;
                    System.out.println("Computer misses! Your perseverance is rewarded.");
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

/*
 * CLC deleted old lines
 * 303 to 314
 */

/*
 * CLC battleship edits and bug fixes on lines
 * 90 to 101
 * 230 to 298
 * 305
 * 310
 * 315
 * 350
 * 351
 * 357
 * 358
 * 363
 */
