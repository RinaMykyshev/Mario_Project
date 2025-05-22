import java.awt.Image;

public class Turtle implements Runnable {
    public int x;
    public int y;
    public Image image;
    public int speed = 5;
    public int type = 2;
    Thread thread;

    public Turtle(int x, int y) {
        this.x = x;
        this.y = y;
        image = Staticvalues.turtel.get(0);
        thread = new Thread(this);
        thread.start();
    }

    public boolean a = false;
    private boolean isPaused = false;

    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    @Override
    public void run() {
        while (true) {
            if (isPaused) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            if (type == 1)
            {
                if (a == false) {
                    image = Staticvalues.turtel.get(0);
                    a = true;
                } else {
                    image = Staticvalues.turtel.get(1);
                    a = false;
                }
                x -= speed;
            }
            if (type == 2)
            {
                if (a == false) {
                    image = Staticvalues.turtel.get(2);
                    a = true;
                } else {
                    image = Staticvalues.turtel.get(3);
                    a = false;
                }
                x += speed;
            }
            if (type == 3)
            {
                image = Staticvalues.turtel.get(4);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                type = 1;
            }

            if (x <= 660) {
                type = 2;
            }
            if (x >= 840) {
                type = 1;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
