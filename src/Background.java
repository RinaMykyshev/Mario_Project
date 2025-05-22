import java.awt.Image;
import java.util.ArrayList;

public class Background {
	private boolean isOver = false;
	private static final int GROUND_Y = 540;
    private static final int BLOCK_TYPE = 9;
    private static final int PIPE_TYPE = 6;

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
		if (isLastLevel) {
			backgroundImage = Staticvalues.end;
		} else {
			backgroundImage = Staticvalues.bgImage;
		}
		if (sort == 1) {
			for (int i = 0; i < 15; i++) {
				this.obstraction.add(new Enemy(i * 60, 540, 9));
			}

			this.obstraction.add(new Enemy(120, 360, 4));
			this.obstraction.add(new Enemy(300, 360, 0));
			this.obstraction.add(new Enemy(360, 360, 4));
			this.obstraction.add(new Enemy(420, 360, 0));
			this.obstraction.add(new Enemy(480, 360, 4));
			this.obstraction.add(new Enemy(540, 360, 0));
			this.obstraction.add(new Enemy(420, 180, 0));

			this.enemy.add(new MoveEnemy(690, 540, true, 2, 420, 540, this));

<<<<<<< HEAD
			// Верхняя часть трубы
            this.obstraction.add(new Enemy(660, 480, 6));
            this.obstraction.add(new Enemy(720, 480, 5));
            // Нижняя часть трубы (убираем с земли)
            // this.obstraction.add(new Enemy(660, 540, 6)); // УДАЛЕНО
            // this.obstraction.add(new Enemy(720, 540, 5)); // УДАЛЕНО
=======
			this.obstraction.add(new Enemy(660, 540, 6));
			this.obstraction.add(new Enemy(720, 540, 5));
			this.obstraction.add(new Enemy(660, 480, 8));
			this.obstraction.add(new Enemy(720, 480, 7));
>>>>>>> 5beaa6b2937da85637c9c75c5a5244d9dfd06447

			this.enemy.add(new MoveEnemy(600, 480, true, 1, this));
		}
		if (sort == 2) {
			for (int i = 0; i < 15; i++) {
				if (i != 10) {
<<<<<<< HEAD
					this.obstraction.add(GameObjectFactory.createEnemy(i * 60, 540, 9));
				}
			}
			// Исправлено: труба теперь состоит из двух частей и не торчит из земли
            // Башня трубы (левая)
            this.obstraction.add(new Enemy(660, 480, 6)); // нижняя часть
            this.obstraction.add(new Enemy(660, 420, 6)); // верхняя часть
            // Башня трубы (правая)
            this.obstraction.add(new Enemy(720, 480, 5)); // нижняя часть
            this.obstraction.add(new Enemy(720, 420, 5)); // верхняя часть

            this.obstraction.add(new Enemy(240, 480, 6));
            this.obstraction.add(new Enemy(300, 480, 5));
            this.obstraction.add(new Enemy(240, 420, 6));
            this.obstraction.add(new Enemy(300, 420, 5));
            this.obstraction.add(new Enemy(240, 360, 8));
            this.obstraction.add(new Enemy(300, 360, 7));

            // Удаляем нижние части трубы с земли
            // this.obstraction.add(new Enemy(60, 540, 6)); // УДАЛЕНО
            // this.obstraction.add(new Enemy(200, 540, 5)); // УДАЛЕНО
            // this.obstraction.add(new Enemy(240, 540, 6)); // УДАЛЕНО
            // this.obstraction.add(new Enemy(300, 540, 5)); // УДАЛЕНО

            this.obstraction.add(new Enemy(480, 360, 4));
            this.obstraction.add(new Enemy(540, 360, 0));
            this.obstraction.add(new Enemy(420, 180, 0));

            this.enemy.add(new MoveEnemy(90, 540, true, 2, 350, 540, this));
            this.enemy.add(new MoveEnemy(270, 540, true, 2, 300, 400, this));
            Turtle = new Turtle(680, 480);
=======
					this.obstraction.add(new Enemy(i * 60, 540, 9));
				}
			}
			this.obstraction.add(new Enemy(60, 540, 6));
			this.obstraction.add(new Enemy(120, 540, 5));
			this.obstraction.add(new Enemy(60, 480, 6));
			this.obstraction.add(new Enemy(120, 480, 5));
			this.obstraction.add(new Enemy(60, 420, 8));
			this.obstraction.add(new Enemy(120, 420, 7));

			this.obstraction.add(new Enemy(240, 540, 6));
			this.obstraction.add(new Enemy(300, 540, 5));
			this.obstraction.add(new Enemy(240, 480, 6));
			this.obstraction.add(new Enemy(300, 480, 5));
			this.obstraction.add(new Enemy(240, 420, 6));
			this.obstraction.add(new Enemy(300, 420, 5));
			this.obstraction.add(new Enemy(240, 360, 8));
			this.obstraction.add(new Enemy(300, 360, 7));

			this.obstraction.add(new Enemy(480, 360, 4));
			this.obstraction.add(new Enemy(540, 360, 0));
			this.obstraction.add(new Enemy(420, 180, 0));

			this.enemy.add(new MoveEnemy(90, 540, true, 2, 350, 540, this));
			this.enemy.add(new MoveEnemy(270, 540, true, 2, 300, 400, this));
			Turtle = new Turtle(680, 480);
>>>>>>> 5beaa6b2937da85637c9c75c5a5244d9dfd06447
		}
		if (sort == 3) {
			for (int i = 0; i < 15; i++) {
				this.obstraction.add(new Enemy(i * 60, 540, 9));
			}
			this.obstraction.add(new Enemy(550, 180, 11));
			this.obstraction.add(new Enemy(520, 480, 2));
		}
	}

	public void reset() {
<<<<<<< HEAD
        enemy.clear();
        obstraction.clear();
        removedenemy.clear();
        remove.clear();
        Turtle = null;

        if (sort == 1) {
            for (int i = 0; i < 15; i++) {
                this.obstraction.add(new Enemy(i * 60, GROUND_Y, BLOCK_TYPE));
            }
            this.obstraction.add(new Enemy(120, 360, 4));
            this.obstraction.add(new Enemy(300, 360, 0));
            this.obstraction.add(new Enemy(360, 360, 4));
            this.obstraction.add(new Enemy(420, 360, 0));
            this.obstraction.add(new Enemy(480, 360, 4));
            this.obstraction.add(new Enemy(540, 360, 0));
            this.obstraction.add(new Enemy(420, 180, 0));

            this.enemy.add(new MoveEnemy(690, 540, true, 2, 420, 540, this));

            // Верхняя часть трубы
            this.obstraction.add(new Enemy(660, 480, 6));
            this.obstraction.add(new Enemy(720, 480, 5));
            // Нижняя часть трубы (убираем с земли)
            // this.obstraction.add(new Enemy(660, 540, 6)); // УДАЛЕНО
            // this.obstraction.add(new Enemy(720, 540, 5)); // УДАЛЕНО

            this.enemy.add(new MoveEnemy(600, 480, true, 1, this));
        }
        if (sort == 2) {
            for (int i = 0; i < 15; i++) {
                if (i != 10) {
                    this.obstraction.add(GameObjectFactory.createEnemy(i * 60, 540, 9));
                }
            }
            // Исправлено: труба теперь состоит из двух частей и не торчит из земли
            // Башня трубы (левая)
            this.obstraction.add(new Enemy(660, 480, 6)); // нижняя часть
            this.obstraction.add(new Enemy(660, 420, 6)); // верхняя часть
            // Башня трубы (правая)
            this.obstraction.add(new Enemy(720, 480, 5)); // нижняя часть
            this.obstraction.add(new Enemy(720, 420, 5)); // верхняя часть

            this.obstraction.add(new Enemy(240, 480, 6));
            this.obstraction.add(new Enemy(300, 480, 5));
            this.obstraction.add(new Enemy(240, 420, 6));
            this.obstraction.add(new Enemy(300, 420, 5));
            this.obstraction.add(new Enemy(240, 360, 8));
            this.obstraction.add(new Enemy(300, 360, 7));

            // Удаляем нижние части трубы с земли
            // this.obstraction.add(new Enemy(60, 540, 6)); // УДАЛЕНО
            // this.obstraction.add(new Enemy(200, 540, 5)); // УДАЛЕНО
            // this.obstraction.add(new Enemy(240, 540, 6)); // УДАЛЕНО
            // this.obstraction.add(new Enemy(300, 540, 5)); // УДАЛЕНО

            this.obstraction.add(new Enemy(480, 360, 4));
            this.obstraction.add(new Enemy(540, 360, 0));
            this.obstraction.add(new Enemy(420, 180, 0));

            this.enemy.add(new MoveEnemy(90, 540, true, 2, 350, 540, this));
            this.enemy.add(new MoveEnemy(270, 540, true, 2, 300, 400, this));
            Turtle = new Turtle(680, 480);
        }
        if (sort == 3) {
            for (int i = 0; i < 15; i++) {
                this.obstraction.add(new Enemy(i * 60, GROUND_Y, BLOCK_TYPE));
            }
            this.obstraction.add(new Enemy(550, 180, 11));
            this.obstraction.add(new Enemy(520, 480, 2));
        }
    }
=======
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
>>>>>>> 5beaa6b2937da85637c9c75c5a5244d9dfd06447
}
