import java.awt.Image;

public class GameObjectFactory {

    public static Mario createMario(int x, int y) {
        return new Mario(x, y);
    }

    public static Enemy createEnemy(int x, int y, int type) {
        return new Enemy(x, y, type);
    }

    public static MoveEnemy createMoveEnemy(int x, int y, boolean isLeftOrUp, int type, Background background) {
        return new MoveEnemy(x, y, isLeftOrUp, type, background);
    }

    public static Turtle createTurtle(int x, int y) {
        return new Turtle(x, y);
    }
}