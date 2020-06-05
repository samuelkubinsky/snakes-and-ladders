public class Tile {

    private final int tileNumber;
    private final int additionalMove;

    public Tile(int tileNumber, int additionalMove) {
        this.tileNumber = tileNumber;
        this.additionalMove = additionalMove;
    }

    public int getAdditionalMove() {
        return additionalMove;
    }

    public int getTileNumber() {
        return tileNumber;
    }
}
