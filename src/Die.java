import java.util.Random;

public class Die {

    private final Random random = new Random();

    private final int min = 1;
    private final int max = 6;

    public int roll() {
        return random.nextInt(max - min + 1) + min;
    }

}
