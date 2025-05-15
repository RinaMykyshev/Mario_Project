import java.awt.Image;
import java.util.ArrayList;

public class Background {
    private static final int GROUND_Y = 540;
    private static final int BRICK_Y = 360;
    private static final int PIPE_TYPE = 6;
    private static final int BLOCK_TYPE = 9;
    private static final int COIN_BLOCK_TYPE = 4;
    private static final int BRICK_TYPE = 0;

    private boolean isOver = false;
    public Image backgroundImage = null;
    private int sort;
    private boolean isLastLevel;
    ArrayList<MoveEnemy> enemy = new ArrayList<>();
    ArrayList<Enemy> obstraction = new ArrayList<>();
    ArrayList<Enemy> removedenemy = new ArrayList<>();
    ArrayList<MoveEnemy> remove = new ArrayList<>();
    Turtle Turtle = null;

    public Background(int sort, boolean isLastLevel) {
        this.sort = sort;
        this.isLastLevel = isLastLevel;
        this.backgroundImage = isLastLevel ? Staticvalues.end : Staticvalues.bgImage;

        switch (sort) {
            case 1: initLevel1(); break;
            case 2: initLevel2(); break;
            case 3: initLevel3(); break;
        }
    }

    private void initLevel1() {

        for (int i = 0; i < 15; i++) {
            this.obstraction.add(new Enemy(i * 60, GROUND_Y, BLOCK_TYPE));
        }

        this.obstraction.add(new Enemy(120, BRICK_Y, COIN_BLOCK_TYPE));
        this.obstraction.add(new Enemy(300, BRICK_Y, BRICK_TYPE));
        this.obstraction.add(new Enemy(360, BRICK_Y, COIN_BLOCK_TYPE));
        this.obstraction.add(new Enemy(420, BRICK_Y, BRICK_TYPE));
        this.obstraction.add(new Enemy(480, BRICK_Y, COIN_BLOCK_TYPE));
        this.obstraction.add(new Enemy(540, BRICK_Y, BRICK_TYPE));
        this.obstraction.add(new Enemy(420, 180, BRICK_TYPE));

        this.enemy.add(new MoveEnemy(690, GROUND_Y, true, 2, 420, GROUND_Y, this));


        this.obstraction.add(new Enemy(660, GROUND_Y, PIPE_TYPE));
        this.obstraction.add(new Enemy(720, GROUND_Y, 5));
        this.obstraction.add(new Enemy(660, 480, 8));
        this.obstraction.add(new Enemy(720, 480, 7));

        this.enemy.add(new MoveEnemy(600, 480, true, 1, this));
    }

    private void initLevel2() {
        for (int i = 0; i < 15; i++) {
            if (i != 10) {
                this.obstraction.add(new Enemy(i * 60, GROUND_Y, BLOCK_TYPE));
            }
        }

        addPipeStructure(60, GROUND_Y);
        this.obstraction.add(new Enemy(60, 420, 8));
        this.obstraction.add(new Enemy(120, 420, 7));


        addPipeStructure(240, GROUND_Y);
        this.obstraction.add(new Enemy(240, 360, 8));
        this.obstraction.add(new Enemy(300, 360, 7));


        this.obstraction.add(new Enemy(480, BRICK_Y, COIN_BLOCK_TYPE));
        this.obstraction.add(new Enemy(540, BRICK_Y, BRICK_TYPE));
        this.obstraction.add(new Enemy(420, 180, BRICK_TYPE));

        this.enemy.add(new MoveEnemy(90, GROUND_Y, true, 2, 350, GROUND_Y, this));
        this.enemy.add(new MoveEnemy(270, GROUND_Y, true, 2, 300, 400, this));
        Turtle = new Turtle(680, 480);
    }

    private void initLevel3() {
        for (int i = 0; i < 15; i++) {
            this.obstraction.add(new Enemy(i * 60, GROUND_Y, BLOCK_TYPE));
        }
        this.obstraction.add(new Enemy(550, 180, 11));
        this.obstraction.add(new Enemy(520, 480, 2));
    }

    private void addPipeStructure(int x, int baseY) {
        this.obstraction.add(new Enemy(x, baseY, PIPE_TYPE));
        this.obstraction.add(new Enemy(x + 60, baseY, 5));
        this.obstraction.add(new Enemy(x, baseY - 60, PIPE_TYPE));
        this.obstraction.add(new Enemy(x + 60, baseY - 60, 5));
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isFlag() {
        return isLastLevel;
    }

    public void setFlag(boolean isLastLevel) {
        this.isLastLevel = isLastLevel;
    }

    public ArrayList<MoveEnemy> getEnemy() {
        return enemy;
    }

    public void setEnemy(ArrayList<MoveEnemy> enemy) {
        this.enemy = enemy;
    }

    public ArrayList<Enemy> getObstraction() {
        return obstraction;
    }

    public void setObstraction(ArrayList<Enemy> obstraction) {
        this.obstraction = obstraction;
    }

    public ArrayList<Enemy> getRemovedenemy() {
        return removedenemy;
    }

    public void setRemovedenemy(ArrayList<Enemy> removedenemy) {
        this.removedenemy = removedenemy;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean isOver) {
        this.isOver = isOver;
    }

    public void reset() {
        enemy.clear();
        obstraction.clear();
        removedenemy.clear();
        remove.clear();
        Turtle = null;

        if (sort == 1) {
            for (int i = 0; i < 15; i++) {
                this.obstraction.add(new Enemy(i * 60, GROUND_Y, BLOCK_TYPE));
            }
        }
    }
}