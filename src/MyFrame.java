package maria;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.BoxLayout;

import java.io.File;
import java.io.IOException;

public class MyFrame extends JFrame implements Runnable {

	private ArrayList<Background> Backgrounds = new ArrayList<>();
	private Background Backgroundnow = null;
	private Mario mario;

	private Clip backgroundClip;

	// Диалоговое окно для проигрыша
	private JDialog gameOverDialog;

	public MyFrame() {
		this.setTitle("É½Õ¯ÂíÀï°Â");
		this.setSize(900, 600);
		this.setLocationRelativeTo(null);
		Staticvalues.init();
		Backgroundnow = new Background(0, false);
		for (int i = 1; i <= 3; i++) {
			this.Backgrounds.add(new Background(i, i == 3 ? true : false));
		}
		this.Backgroundnow = Backgrounds.get(0);
		mario = new Mario(0, 480);
		mario.setBackground(Backgroundnow);
		mario.setMyFrame(this); // Устанавливаем ссылку на MyFrame
		this.repaint();
		this.addKeyListener(new key());
		Thread thread = new Thread(this);
		thread.start();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		// Создаем диалоговое окно "Игра окончена"
		createGameOverDialog();
		playBackgroundSound("sounds/background.wav");
	}

	@Override
	public void paint(Graphics graphics) {
		BufferedImage bufferedImage = new BufferedImage(900, 600,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics graphics2 = bufferedImage.getGraphics();
		graphics2.drawImage(this.Backgroundnow.getBackgroundImage(), 0, 0, this);
		
		java.util.Iterator<MoveEnemy> iterator2 = this.Backgroundnow.enemy
				.iterator();
		while(iterator2.hasNext())
		{
			MoveEnemy moveenemy = iterator2.next();
			graphics2.drawImage(moveenemy.getImage(), moveenemy.getX(), moveenemy.getY(), this);
		}
	
		java.util.Iterator<Enemy> iterator = this.Backgroundnow.obstraction
				.iterator();
		while (iterator.hasNext())
		{
			Enemy obstraction = iterator.next();
			graphics2.drawImage(obstraction.getImage(), obstraction.getX(),
					obstraction.getY(), this);
		}
       if(Backgroundnow.Turtle != null)
       {
	   graphics2.drawImage(Backgroundnow.Turtle.image,Backgroundnow.Turtle.x,Backgroundnow.Turtle.y,this);
       }
		graphics2.drawImage(mario.getImage(), mario.getX(), mario.getY(), this);
		graphics.drawImage(bufferedImage, 0, 0, this);
	}

	class key implements KeyListener {

		private boolean jumpSoundPlayed = false;
		private boolean isJumping = false; // Новый флаг для отслеживания состояния прыжка
		// Флаг для контроля состояния меню
		private JDialog menuDialog; // Диалоговое окно для меню

		public key() {
			// Инициализация меню
			menuDialog = new JDialog(MyFrame.this, "Меню", true);
			menuDialog.setSize(300, 200);
			menuDialog.setLayout(new BoxLayout(menuDialog.getContentPane(), BoxLayout.Y_AXIS));
			menuDialog.setLocationRelativeTo(MyFrame.this);

			JButton continueButton = new JButton("Продолжить игру");			continueButton.addActionListener(e -> {
				setGamePaused(false); // Снимаем все объекты игры с паузы
				menuDialog.setVisible(false);
				resumeBackgroundMusic(); // Возобновляем музыку
			});

			JButton exitButton = new JButton("Выход из игры");
			exitButton.addActionListener(e -> System.exit(0));

			menuDialog.add(continueButton);
			menuDialog.add(exitButton);
		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (isPaused) {
				return; // Если игра на паузе, игнорируем нажатия клавиш
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				MyFrame.this.mario.rightMove();
			}
			if (e.getKeyCode() == 37) {
				MyFrame.this.mario.leftMove();
			}
			if (e.getKeyCode() == 32 && !jumpSoundPlayed && !isJumping) { // Проверяем, не в прыжке ли персонаж
				MyFrame.this.mario.jump();
				playSound("sounds/jump.wav");
				jumpSoundPlayed = true;
				isJumping = true; // Устанавливаем флаг прыжка
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // Обработка нажатия Esc
				// Переключаем состояние паузы для всех объектов игры
				setGamePaused(!isPaused);
				
				if (isPaused) {
					pauseBackgroundMusic(); // Останавливаем музыку
					menuDialog.setVisible(true); // Показываем меню, если игра на паузе
				} else {
					resumeBackgroundMusic(); // Возобновляем музыку
					menuDialog.setVisible(false); // Скрываем меню, если игра продолжается
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == 39) {
				MyFrame.this.mario.rightstop();
			}
			if (e.getKeyCode() == 37) {
				MyFrame.this.mario.leftstop();
			}
			if (e.getKeyCode() == 32) {
				jumpSoundPlayed = false;
			}
		}

		public void setJumping(boolean jumping) { // Метод для сброса состояния прыжка
			this.isJumping = jumping;
		}
	}
	public static void main(String[] args) {
		new MyFrame();
	}

	private boolean isPaused = false; // Флаг для отслеживания состояния паузы

	@Override
	public void run() {
		while (true) {
			if (isPaused) {
				try {
					Thread.sleep(50); // Ждем, пока игра на паузе
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}

			this.repaint();
			try {
				Thread.sleep(50);
				if (mario.getX() >= 840) {
					this.Backgroundnow = Backgrounds.get(this.Backgroundnow.getSort());
					mario.setBackground(Backgroundnow);
					mario.setX(0);
					mario.setY(480);
				}
				// Сбрасываем флаг прыжка, если персонаж завершил прыжок
				if (mario.isOnGround()) {
					((key) this.getKeyListeners()[0]).setJumping(false);
				}
			} catch (InterruptedException e) {
			 e.printStackTrace();
			}
		}
	}
	
	private void playSound(String soundFilePath) {
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFilePath).getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
	        e.printStackTrace();
	    }
	}

	private void playBackgroundSound(String soundFilePath) {
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFilePath).getAbsoluteFile());
	        backgroundClip = AudioSystem.getClip();
	        backgroundClip.open(audioInputStream);
	        backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
	    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
	        e.printStackTrace();
	    }
	}
	private void pauseBackgroundMusic() {
	    try {
	        if (backgroundClip != null && backgroundClip.isRunning()) {
	            backgroundClip.stop();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	private void resumeBackgroundMusic() {
	    try {
	        if (backgroundClip != null && !backgroundClip.isRunning()) {
	            backgroundClip.start();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	// Метод для установки паузы для всех объектов игры
	private void setGamePaused(boolean paused) {
	    try {
	        // Устанавливаем флаг паузы для основного потока
	        isPaused = paused;
	        
	        // Передаем состояние паузы персонажу
	        if (mario != null) {
	            mario.setPaused(paused);
	        }
	        
	        // Передаем состояние паузы черепахе
	        if (Backgroundnow != null && Backgroundnow.Turtle != null) {
	            Backgroundnow.Turtle.setPaused(paused);
	        }
	        
	        // Передаем состояние паузы движущимся врагам
	        if (Backgroundnow != null && Backgroundnow.enemy != null) {
	            for (Object enemy : Backgroundnow.enemy) {
	                if (enemy instanceof MoveEnemy) {
	                    ((MoveEnemy) enemy).setPaused(paused);
	                }
	            }
	        }
	        
	        // Управление музыкой
	        if (paused) {
	            pauseBackgroundMusic();
	        } else {
	            resumeBackgroundMusic();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}	// Метод для перезапуска игры с пересозданием всех потоков
	private void restartGame() {
	    try {
	        // Останавливаем всех врагов и персонажа
	        setGamePaused(true);
	        
	        // Останавливаем старые потоки
	        if (mario != null && mario.thread != null) {
	            try {
	                mario.thread.interrupt();
	            } catch (Exception ex) {
	                // Игнорируем ошибки при остановке потоков
	            }
	        }
	        
	        // Останавливаем музыку полностью (закрываем старый клип)
	        if (backgroundClip != null) {
	            try {
	                backgroundClip.stop();
	                backgroundClip.close();
	                backgroundClip = null;
	            } catch (Exception ex) {
	                // Игнорируем ошибки при закрытии аудио
	                backgroundClip = null;
	            }
	        }
	        
	        // Останавливаем врагов и другие потоки
	        if (Backgroundnow != null && Backgroundnow.enemy != null) {
	            for (Object enemy : Backgroundnow.enemy) {
	                if (enemy instanceof MoveEnemy) {                    MoveEnemy moveEnemy = (MoveEnemy) enemy;
	                    if (moveEnemy.getThread() != null) {
	                        try {
	                            moveEnemy.getThread().interrupt();
	                        } catch (Exception ex) {
	                            // Игнорируем ошибки
	                        }
	                    }
	                }
	            }
	        }
	        
	        // Останавливаем черепаху, если она есть
	        if (Backgroundnow != null && Backgroundnow.Turtle != null) {
	            if (Backgroundnow.Turtle.thread != null) {
	                try {
	                    Backgroundnow.Turtle.thread.interrupt();
	                } catch (Exception ex) {
	                    // Игнорируем ошибки
	                }
	            }
	        }
	        
	        // Полностью пересоздаем игровую сцену
	        Staticvalues.init(); // Перезагружаем статические значения
	        
	        // Пересоздаем первый фон
	        Backgroundnow = new Background(0, false);
	        
	        // Пересоздаем все фоны
	        Backgrounds.clear();
	        for (int i = 1; i <= 3; i++) {
	            this.Backgrounds.add(new Background(i, i == 3 ? true : false));
	        }
	        
	        // Создаем нового персонажа
	        mario = new Mario(0, 480);
	        mario.setBackground(Backgroundnow);
	        mario.setMyFrame(this); // Устанавливаем ссылку на MyFrame
	        mario.die = true; // Сбрасываем флаг смерти персонажа
	        
	        // Запускаем музыку заново
	        playBackgroundSound("sounds/background.wav");
	        
	        // Убираем паузу
	        setGamePaused(false);
	        
	        // Перерисовываем игру
	        this.repaint();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}// Метод для отображения меню проигрыша
	public void gameOver() {
	    // Останавливаем все процессы игры
	    setGamePaused(true);
	    
	    // Останавливаем музыку
	    pauseBackgroundMusic();
	    
	    // Показываем диалоговое окно проигрыша
	    gameOverDialog.setLocationRelativeTo(this); // Центрируем диалог
	    gameOverDialog.setVisible(true);
	}
	// Метод для создания диалога "Игра окончена"
	private void createGameOverDialog() {
	    gameOverDialog = new JDialog(this, "Игра окончена", true);
	    gameOverDialog.setSize(300, 150);
	    gameOverDialog.setLayout(new BoxLayout(gameOverDialog.getContentPane(), BoxLayout.Y_AXIS));
	    gameOverDialog.setLocationRelativeTo(this);
	    
	    // Предотвращаем закрытие диалога по нажатию на крестик
	    gameOverDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

	    JButton restartButton = new JButton("Играть заново");
	    restartButton.addActionListener(e -> {
	        gameOverDialog.setVisible(false);
	        restartGame();
	    });

	    JButton exitButton = new JButton("Выход из игры");
	    exitButton.addActionListener(e -> System.exit(0));

	    gameOverDialog.add(restartButton);
	    gameOverDialog.add(exitButton);
	}
	// Убран метод для отображения экрана проигрыша
}
