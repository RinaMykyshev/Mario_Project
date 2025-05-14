import java.awt.Image;

public class Mario implements Runnable {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	private int x;
	private int y;
	private String status;
	private Image image;
	public int xmove = 0;
	public int ymove = 0;
	private int p = 0;
	private int a = 1;
	public int time = 0;
	public Thread thread = null;
	private Background Background = null;

	public Background getBackground() {
		return Background;
	}

	public void setBackground(Background Background) {
		this.Background = Background;
	}

	public int getscore() {
		return score;
	}

	public void setscore(int score) {
		this.score = score;
	}

	public int getLift() {
		return lift;
	}

	public void setLift(int lift) {
		this.lift = lift;
	}

	private int score;
	private int lift;

	public Mario(int x, int y) {
		this.x = x;
		this.y = y;
		image = Staticvalues.mariao.get(0);
		this.score = 0;
		this.lift = 3;
		this.status = "right-standing";
		thread = new Thread(this);
		thread.start();
	}

	private boolean isPaused = false;

	public void setPaused(boolean paused) {
		this.isPaused = paused;
	}

	public void leftMove() {
		if (isPaused) return;
		xmove = -5;
		this.status = "left-moving";
	}

	public void rightMove() {
		if (isPaused) return;
		xmove = 5;
		this.status = "right-moving";
	}

	public void leftstop() {
		if (isPaused) return;
		xmove = 0;
		this.status = "left-standing";
	}

	public void rightstop() {
		if (isPaused) return;
		xmove = 0;
		this.status = "right-standing";
	}

	private boolean isOnGround = true;

	public boolean isOnGround() {
		return this.y >= 480;
	}

	public boolean isOnSurface() {
		if (this.y + 60 >= 480) {
			System.out.println("On ground: y = " + y);
			return true;
		}
		for (Enemy ob : Background.obstraction) {
			System.out.println("Checking block: y = " + ob.getY() + ", x = " + ob.getX());
			if (this.y + 60 >= ob.getY() && this.y + 60 <= ob.getY() + 10 &&
					this.x + 50 > ob.getX() &&
					this.x < ob.getX() + 50) {
				System.out.println("On block: Mario y = " + y + ", block y = " + ob.getY() + ", block x = " + ob.getX() + ", Mario x = " + x);
				return true;
			}
		}
		System.out.println("Not on surface: y = " + y);
		return false;
	}

	public void jump() {
		if (isPaused) return;
		if ((this.status.indexOf("jumping") == -1) && isOnSurface()) {
			if (this.status.indexOf("left") != -1) {
				this.status = "left-jumping";
			} else {
				this.status = "right-jumping";
			}
			ymove = -10;
			time = 18;
			System.out.println("Jump initiated: status = " + status + ", y = " + y + ", isOnSurface = " + isOnSurface());
		} else {
			System.out.println("Jump blocked: status = " + status + ", isOnSurface = " + isOnSurface());
		}
	}

	public void down() {
		if (this.status.indexOf("left") != -1) {
			this.status = "left-jumping";
		} else {
			this.status = "right-jumping";
		}
		ymove = 10;
	}

	public boolean die = true;
	public void dead() {
		this.image = Staticvalues.die;
		isPaused = true;
		die = false;
		if (myFrame != null) {
			new Thread(() -> {
				try {
					Thread.sleep(500);
					myFrame.gameOver();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}
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

			isOnGround = (this.y >= 480);

			if (!die) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				boolean canleft = true;
				boolean canright = true;
				boolean jumb = false;
				if (Background.Turtle != null
						&& Background.Turtle.y == this.y + 60
						&& (Background.Turtle.x + 60 > this.x && Background.Turtle.x - 60 < this.x)) {
					Background.Turtle.type = 3;
					this.time = 10;
					this.ymove = -5;
				}
				if (Background.Turtle != null
						&& Background.Turtle.x + 50 > this.x
						&& Background.Turtle.x - 50 < this.x
						&& Background.Turtle.y + 50 > this.y
						&& Background.Turtle.y - 50 < this.y) {
					this.dead();
				}
				for (int i = 0; i < Background.obstraction.size(); i++) {
					Enemy ob = Background.obstraction.get(i);
					if ((ob.getX() == this.x + 50)
							&& (ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)) {
						canright = false;
					}
					if ((ob.getX() == this.x - 50)
							&& (ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)) {
						canleft = false;
					}
					if (ob.getY() == this.y + 50 && ob.getX() + 50 > this.x
							&& ob.getX() - 50 < this.x) {
						jumb = true;
					}
					if (ob.getY() == this.y - 60
							&& (ob.getX() + 50 > this.x && ob.getX() - 50 < this.x)) {
						if (ob.getType() == 0) {
							this.Background.obstraction.remove(ob);
							this.Background.removedenemy.add(ob);
						}
						if (ob.getType() == 4 || ob.getType() == 3 && time > 0) {
							ob.setType(2);
							ob.setImage();
						}
						time = 0;
						ymove = 0;
						System.out.println("Hit block: setting time = 0, ymove = 0, status = " + status + ", y = " + y);
					}
				}
				if (jumb && time == 0) {
					if (this.status.indexOf("left") != -1) {
						if (xmove != 0) {
							status = "left-moving";
						} else {
							status = "left-standing";
						}
					} else {
						if (xmove != 0) {
							status = "right-moving";
						} else {
							status = "right-standing";
						}
					}
					ymove = 0;
					System.out.println("Jump ended: status = " + status + ", y = " + y + ", ymove = " + ymove);
				} else {
					if (time != 0) {
						time -= 1;
					} else {
						this.down();
					}
					y += ymove;
				}

				if (canleft && xmove < 0 || canright && xmove > 0) {
					if (x < 0) {
						x = 0;
					} else {
						x += xmove;
					}
				}
				int temp = 0;
				if (this.status.indexOf("left") != -1) {
					temp += 5;
				}
				if (this.status.indexOf("moving") != -1) {
					temp += p;
					if (a % 4 == 1) {
						p++;
						if (p == 4) {
							p = 0;
						}
					}
				}
				if (this.status.indexOf("jumping") != -1) {
					temp += 4;
				}
				this.image = Staticvalues.mariao.get(temp);
				a++;
				for (int i = 0; i < this.Background.enemy.size(); i++) {
					MoveEnemy e = (MoveEnemy) this.Background.enemy.get(i);
					if (e.getX() + 50 > this.x && e.getX() - 50 < this.x
							&& e.getY() + 50 > this.y && e.getY() - 50 < this.y) {
						this.dead();
					}
					if (e.getY() == this.y + 60
							&& (e.getX() + 60 > this.x && e.getX() - 60 < this.x)) {
						if (e.getType() == 1) {
							e.dead();
							this.time = 10;
							this.ymove = -5;
						} else if (e.getType() == 2) {
							this.dead();
						}
					}
				}
				try {
					Thread.sleep(50);
					if (a == 100) {
						a = 0;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private MyFrame myFrame;

	public void setMyFrame(MyFrame myFrame) {
		this.myFrame = myFrame;
	}

	public void reset() {
		this.die = true;
		this.x = 0;
		this.y = 480;
		this.status = "right-standing";
		this.image = Staticvalues.mariao.get(0);
		this.isPaused = false;
		this.xmove = 0;
		this.ymove = 0;
	}
}