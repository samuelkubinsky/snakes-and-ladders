import java.util.Scanner;

public class ConsoleUI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[93m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private final Field field;
    private final Player playerOne = new Player("Player 1", ANSI_BLUE, ANSI_RESET);
    private final Player playerTwo = new Player("Player 2", ANSI_YELLOW, ANSI_RESET);
    private final Player[] players = new Player[]{playerOne, playerTwo};
    private final Die die = new Die();
    private final Scanner scanner = new Scanner(System.in);

    private GameState gameState = GameState.PLAYING;
    private int roundCount = 0;

    public ConsoleUI(Field field) {
        this.field = field;
    }

    public void play() {
        whoGoesFirst();

        while (gameState == GameState.PLAYING) {
            Player playerOnTurn = players[getPlayerNumberOnTurn()];

            printSeparator();
            printField();
            printPlayers();
            rollDie(playerOnTurn);
            additionalMove(playerOnTurn);
            checkIfWon(playerOnTurn);

            roundCount++;
        }
    }

    private void whoGoesFirst() {
        System.out.println("\nPlayer who roll the larger number goes first!");
        int playerOneRoll = 0;
        int playerTwoRoll = 0;

        while (playerOneRoll == playerTwoRoll) {
            System.out.println("\n" + playerOne.getName() + "'s turn!");
            System.out.println("Press ENTER to roll a die");
            scanner.nextLine();
            playerOneRoll = die.roll();
            System.out.println(playerOne.getName() + " rolled " + playerOneRoll);

            System.out.println("\n" + playerTwo.getName() + "'s turn!");
            System.out.println("Press ENTER to roll a die");
            scanner.nextLine();
            playerTwoRoll = die.roll();
            System.out.println(playerTwo.getName() + " rolled " + playerTwoRoll);
        }

        if (playerTwoRoll > playerOneRoll) {
            roundCount++;
        }
    }

    private void printSeparator() {
        System.out.println("\n-------------------------------------------------\n");
    }

    private void printField() {
        int rows = field.getRows();
        int columns = field.getColumns();
        int numberOfTiles = field.getNumberOfTiles();
        Tile[] array = field.getArray();

        boolean isRowCountEven = (rows % 2 == 0);

        for (int row = 0; row < rows; row++) {
            boolean isActualRowEven = (row % 2 == 0);

            for (int column = 0; column < columns; column++) {
                int tileNumber;

                // get actual tile number (zig-zag)
                if ((isRowCountEven && isActualRowEven) || (!isRowCountEven && !isActualRowEven)) {
                    tileNumber = numberOfTiles - (columns * row) - column;
                } else {
                    tileNumber = numberOfTiles - ((columns * row) + columns - 1) + column;
                }

                // printing snake, ladder, tile number or players
                Tile tile = array[tileNumber - 1];

                if (tile.getAdditionalMove() > 0) {
                    System.out.print(ANSI_GREEN + "+" + tile.getAdditionalMove() + ANSI_RESET);
                } else if (tile.getAdditionalMove() < 0) {
                    System.out.print(ANSI_RED + tile.getAdditionalMove() + ANSI_RESET);
                } else if (playerOne.getPosition() == tileNumber || playerTwo.getPosition() == tileNumber) {
                    for (Player player: players) {
                        if (player.getPosition() == tileNumber) {
                            if ((rows - (row + 1)) % 2 == 0) {
                                System.out.print(player.getColoredString(">"));
                            } else {
                                System.out.print(player.getColoredString("<"));
                            }
                        }
                    }
                } else {
                    System.out.print(tile.getTileNumber());
                }

                System.out.print("\t");
            }

            System.out.println();
        }
    }

    private void printPlayers() {
        System.out.println();

        for (Player player: players) {
            System.out.println(player.getName() + " is on position: " + player.getPosition() );
        }

        System.out.println();
    }

    private void rollDie(Player playerOnTurn) {
        System.out.println(playerOnTurn.getName() + "'s turn!");
        System.out.println("Press ENTER to roll a die");
        scanner.nextLine();

        int roll = die.roll();
        movePlayer(playerOnTurn, roll);

        System.out.println(
                playerOnTurn.getName() + " rolled '" + roll +
                "' and went to position '" + playerOnTurn.getPosition() + "'"
        );
    }

    private void additionalMove(Player playerOnTurn) {
        int moveBy = getSnakeOrLadder(playerOnTurn);
        movePlayer(playerOnTurn, moveBy);

        if (moveBy > 0) {
            System.out.println(
                    playerOnTurn.getName() + " stepped on ladder and climbed to position: " +
                    playerOnTurn.getPosition()
            );
        } else if (moveBy < 0) {
            System.out.println(
                    playerOnTurn.getName() + " stepped on snake and fell to position: " +
                    playerOnTurn.getPosition()
            );
        }
    }

    private int getSnakeOrLadder(Player playerOnTurn) {
        int numberOfTiles = field.getNumberOfTiles();
        int playerPosition = playerOnTurn.getPosition();
        Tile[] array = field.getArray();

        for (int i = 0; i < numberOfTiles; i++) {
            Tile tile = array[i];

            if (tile.getTileNumber() == playerPosition) {
                return tile.getAdditionalMove();
            }
        }

        return 0;
    }

    private void movePlayer(Player playerOnTurn, int moveBy) {
        int newPosition = playerOnTurn.getPosition() + moveBy;

        if (newPosition > field.getNumberOfTiles()) {
            System.out.println("Number is too large");
            return;
        }

        playerOnTurn.setPosition(newPosition);
    }

    private void checkIfWon(Player playerOnTurn) {
        if (playerOnTurn.getPosition() == field.getNumberOfTiles()) {
            gameState = GameState.SOLVED;
            System.out.println("\n" + playerOnTurn.getName() + " has won!");
        }
    }

    private int getPlayerNumberOnTurn() {
        return roundCount % 2;
    }

}
