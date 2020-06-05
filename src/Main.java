import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        HashMap<Integer, Integer> snakesAndLadders = new HashMap<>();

        // snakes
        snakesAndLadders.put(11, -10);
        snakesAndLadders.put(13, -3);
        snakesAndLadders.put(16, -13);
        snakesAndLadders.put(30, -12);
        snakesAndLadders.put(34, -13);

        // ladders
        snakesAndLadders.put(2, 13);
        snakesAndLadders.put(4, 2);
        snakesAndLadders.put(14, 10);
        snakesAndLadders.put(17, 2);
        snakesAndLadders.put(20, 11);

        // play
        Field field = new Field(6, 6, snakesAndLadders);
        ConsoleUI console = new ConsoleUI(field);
        console.play();
    }

}
