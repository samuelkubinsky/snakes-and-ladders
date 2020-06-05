import java.util.HashMap;

public class Field {

    private final int rows;
    private final int columns;
    private final int numberOfTiles;
    private final Tile[] array;

    public Field(int rows, int columns, HashMap<Integer, Integer> snakesAndLadders) {
        this.rows = rows;
        this.columns = columns;
        this.numberOfTiles = rows * columns;
        this.array = new Tile[numberOfTiles];

        generate(snakesAndLadders);
    }

    private void generate(HashMap<Integer, Integer> snakesAndLadders) {
        for (int i = 0; i < numberOfTiles; i++) {
            Integer additionalMove = snakesAndLadders.get(i);

            if (additionalMove == null) {
                additionalMove = 0;
            }

            array[i] = new Tile(i + 1, additionalMove);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getNumberOfTiles() {
        return numberOfTiles;
    }

    public Tile[] getArray() {
        return array;
    }

}
