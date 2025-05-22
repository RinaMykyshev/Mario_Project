import java.awt.Image;

public class GameObjectFactory {

    // Создание Марио
    public static Mario createMario(int x, int y) {
        return new Mario(x, y);
    }

    // Создание обычного врага (Enemy)
    public static Enemy createEnemy(int x, int y, int type) {
        return new Enemy(x, y, type);
    }

    // Создание движущегося врага (MoveEnemy)
    public static MoveEnemy createMoveEnemy(int x, int y, boolean isLeftOrUp, int type, Background background) {
        return new MoveEnemy(x, y, isLeftOrUp, type, background);
    }

    // Создание черепахи (Turtle)
    public static Turtle createTurtle(int x, int y) {
        return new Turtle(x, y);
    }
}