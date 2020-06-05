public class Player {

    private String name;
    private int position;

    private final String color;
    private final String resetColor;

    public Player(String name, String color, String resetColor) {
        this.name = name;
        this.position = 1;

        this.color = color;
        this.resetColor = resetColor;
    }

    public String getName() {
        return color + name + resetColor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getColoredString(String string) {
        return color + string + resetColor;
    }

}
