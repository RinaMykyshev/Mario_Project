import java.awt.Image;

public class MoveEnemy implements Runnable {
	private int x = 0;
	private int y = 0;
	private int startx;
	private int starty;
	private int type;
	private Image image;
	private int imagetype = 0;
	private boolean isleftorup = true;
	private int upmax = 0;
	private int downmax = 0;
	private Thread thread;
	private Background Background;
	private boolean isPaused = false;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getStartx() {
		return startx;
	}

	public void setStartx(int startx) {
		this.startx = startx;
	}

	public int getStarty() {
		return starty;
	}

	public void setStarty(int starty) {
		this.starty = starty;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public boolean isIsleftorup() {
		return isleftorup;
	}

	public void setIsleftorup(boolean isleftorup) {
		this.isleftorup = isleftorup;
	}

	public int getUpmax() {
		return upmax;
	}

	public void setUpmax(int upmax) {
		this.upmax = upmax;
	}

	public int getDownmax() {
		return downmax;
	}

	public void setDownmax(int downmax) {
		this.downmax = downmax;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public Background getBackground() {
		return Background;
	}

	public void setBackground(Background Background) {
		this.Background = Background;
	}

	public void setPaused(boolean paused) {
		this.isPaused = paused;
	}

	public void dead() {
		this.image = Staticvalues.trangel.get(2);
		this.Background.enemy.remove(this);
		this.Background.remove.add(this);
	}

	@Override
	public void run() {
		while (true) {
			if (isPaused) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}

			if (type == 1) {
				if (this.isleftorup) {
					this.x -= 3;
				} else {
					this.x += 3;
				}

				imagetype = (imagetype == 0) ? 1 : 0;
				this.image = Staticvalues.trangel.get(imagetype);

				for (int i = 0; i < this.Background.obstraction.size(); i++) {
					Enemy ob = Background.obstraction.get(i);

					if (ob.getX() == this.x + 60 &&
<<<<<<< HEAD
						(ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)) {
=======
							(ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)) {
>>>>>>> 5beaa6b2937da85637c9c75c5a5244d9dfd06447
						isleftorup = true;
					}

					if (ob.getX() == this.x - 60 &&
<<<<<<< HEAD
						(ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)) {
=======
							(ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)) {
>>>>>>> 5beaa6b2937da85637c9c75c5a5244d9dfd06447
						isleftorup = false;
					}
				}

				if (x < 0) {
					isleftorup = !isleftorup;
				}
			}

			if (type == 2) {
				if (this.isleftorup) {
					this.y -= 3;
				} else {
					this.y += 3;
				}

				imagetype = (imagetype == 0) ? 1 : 0;
				this.image = Staticvalues.flower.get(imagetype);

				if (this.isleftorup && this.y <= this.upmax) {
					this.isleftorup = false;
				}
				if (!this.isleftorup && this.y >= this.downmax) {
					this.isleftorup = true;
				}
			}

			try {
				Thread.sleep(50);
			} catch (Exception e) {
			}
		}
	}

	public MoveEnemy(int x, int y, boolean isleft, int type, Background Background) {
		this.x = x;
		this.y = y;
		this.isleftorup = isleft;
		this.type = type;
		this.Background = Background;
		if (type == 1) {
			this.image = Staticvalues.trangel.get(0);
		}
		thread = new Thread(this);
		thread.start();
	}

	public MoveEnemy(int x, int y, boolean isup, int type, int upmax,
					 int downmax, Background Background) {
		this.x = x;
		this.y = y;
		this.isleftorup = isup;
		this.type = type;
		this.upmax = upmax;
		this.downmax = downmax;
		this.Background = Background;
		if (type == 2) {
			this.image = Staticvalues.flower.get(0);
		}
		thread = new Thread(this);
		thread.start();
	}
}
