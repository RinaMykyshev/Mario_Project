
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class BackGround {
    private boolean isOver=false;
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
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public ArrayList getEnemy() {
        return enemy;
    }

    public void setEnemy(ArrayList enemy) {
        this.enemy = enemy;
    }

    public ArrayList<enemy> getObstraction() {
        return obstraction;
    }

    public void setObstraction(ArrayList<enemy> obstraction) {
        this.obstraction = obstraction;
    }

    public ArrayList getRemovedenemy() {
        return removedenemy;
    }

    public void setRemovedenemy(ArrayList removedenemy) {
        this.removedenemy = removedenemy;
    }

    public void setOver(boolean isOver) {
        this.isOver = isOver;
    }

    private boolean isDown=false;	public boolean isDown() {
        return isDown;
    }

    public Image backgroundImage = null;
    private int sort;
    private boolean flag;
    ArrayList enemy = new ArrayList<>();
    ArrayList<enemy> obstraction = new ArrayList<>();
    ArrayList<enemy> removedenemy = new ArrayList<>();
    ArrayList<moveenemy> remove = new ArrayList<>();
    trutel trutel = null;

    public backgrond(int sort,boolean flag) {
        this.sort = sort;
        this.flag = flag;
        if(flag)
        {
            backgroundImage = staticvalues.end;
        }
        else {
            backgroundImage = staticvalues.bgImage;
        }
        if(sort == 1)
        {
            for(int i = 0;i < 15;i++)
            {
                this.obstraction.add(new enemy(i*60, 540, 9));
            }

            this.obstraction.add(new enemy(120, 360, 4));
            this.obstraction.add(new enemy(300, 360, 0));
            this.obstraction.add(new enemy(360, 360, 4));
            this.obstraction.add(new enemy(420, 360, 0));
            this.obstraction.add(new enemy(480, 360, 4));
            this.obstraction.add(new enemy(540, 360, 0));
            this.obstraction.add(new enemy(420, 180, 0));

            this.enemy.add(new moveenemy(690,540 , true , 2 , 420 , 540 ,this));

            this.obstraction.add(new enemy(660, 540, 6));
            this.obstraction.add(new enemy(720, 540, 5));
            this.obstraction.add(new enemy(660, 480, 8));
            this.obstraction.add(new enemy(720, 480, 7));

            this.enemy.add(new moveenemy(600, 480, true,1,this));
        }
        if(sort==2){
            for(int i=0;i<15;i++){
                if(i!=10){
                    this.obstraction.add(new enemy(i*60,540,9));
                }
            }
            this.obstraction.add(new enemy(60,540,6));
            this.obstraction.add(new enemy(120,540,5));
            this.obstraction.add(new enemy(60,480,6));
            this.obstraction.add(new enemy(120,480,5));
            this.obstraction.add(new enemy(60,420,8));
            this.obstraction.add(new enemy(120,420,7));


            this.obstraction.add(new enemy(240,540,6));
            this.obstraction.add(new enemy(300,540,5));
            this.obstraction.add(new enemy(240,480,6));
            this.obstraction.add(new enemy(300,480,5));
            this.obstraction.add(new enemy(240,420,6));
            this.obstraction.add(new enemy(300,420,5));
            this.obstraction.add(new enemy(240,360,8));
            this.obstraction.add(new enemy(300,360,7));


            this.obstraction.add(new enemy(480, 360, 4));
            this.obstraction.add(new enemy(540, 360, 0));
            this.obstraction.add(new enemy(420, 180, 0));

            this.enemy.add(new moveenemy(90,540 , true , 2 , 350 , 540 ,this));
            this.enemy.add(new moveenemy(270,540 , true , 2 , 300 , 400 ,this));
            trutel = new trutel(680, 480);

        }
        if(sort==3){
            for(int i=0;i<15;i++){
                this.obstraction.add(new enemy(i*60,540,9));
            }
            this.obstraction.add(new enemy(550,180,11));
            this.obstraction.add(new enemy(520,480,2));


        }
    }
    public boolean isOver() {
        return isOver;
    }

    public void reset()
    {

    }

}