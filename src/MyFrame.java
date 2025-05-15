import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyFrame extends JFrame implements Runnable, ScoreObserver {

	private ArrayList<Background> Backgrounds = new ArrayList<>();
	private Background Backgroundnow = null;
	private Mario mario;

	private Clip backgroundClip;

	private JDialog gameOverDialog;
	private JDialog gameWinDialog;
	private JLabel scoreLabel;
	private JPanel gamePanel;

	public MyFrame() {
		this.setTitle("Super Mario");
		this.setSize(900, 600);
		this.setLocationRelativeTo(null);

		this.setLayout(new BorderLayout());
		javax.swing.JLayeredPane layeredPane = new javax.swing.JLayeredPane();
		this.add(layeredPane, BorderLayout.CENTER);
		layeredPane.setPreferredSize(new java.awt.Dimension(900, 600));

		gamePanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				paintGame(g);
			}
		};
		gamePanel.setBounds(0, 0, 900, 600);
		gamePanel.setOpaque(true);
		layeredPane.add(gamePanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

		JPanel scorePanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(new Color(0, 0, 0, 128));
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		scorePanel.setOpaque(false);
		scorePanel.setBounds(10, 10, 120, 40);
		scorePanel.setLayout(new BorderLayout());
		
		scoreLabel = new JLabel("Score: 0");
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
		scoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		
		scorePanel.add(scoreLabel, BorderLayout.CENTER);
		layeredPane.add(scorePanel, javax.swing.JLayeredPane.POPUP_LAYER);
		
		ScoreManager.getInstance().addObserver(this);
		
		Staticvalues.init();
		Backgroundnow = new Background(0, false);
		for (int i = 1; i <= 3; i++) {
			this.Backgrounds.add(new Background(i, i == 3 ? true : false));
		}
		this.Backgroundnow = Backgrounds.get(0);
		mario = new Mario(0, 480);
		mario.setBackground(Backgroundnow);
		mario.setMyFrame(this);
		this.repaint();
		this.addKeyListener(new key());
		Thread thread = new Thread(this);
		thread.start();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);

		createGameOverDialog();
		createGameWinDialog();
		playBackgroundSound("sounds/background.wav");
	}

	private void paintGame(Graphics graphics) {
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

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (gamePanel != null) {
			gamePanel.repaint();
		}
	}

	class key implements KeyListener {

		private boolean jumpSoundPlayed = false;
		private boolean isJumping = false;

		private JDialog menuDialog;

		public key() {

			menuDialog = new JDialog(MyFrame.this, "Меню", true);
			menuDialog.setSize(300, 200);
			menuDialog.setLayout(new BoxLayout(menuDialog.getContentPane(), BoxLayout.Y_AXIS));
			menuDialog.setLocationRelativeTo(MyFrame.this);

			JButton continueButton = new JButton("Продолжить игру");			continueButton.addActionListener(e -> {
				setGamePaused(false);
				menuDialog.setVisible(false);
				resumeBackgroundMusic();
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
				return;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				MyFrame.this.mario.rightMove();
			}
			if (e.getKeyCode() == 37) {
				MyFrame.this.mario.leftMove();
			}
			if (e.getKeyCode() == 32 && !jumpSoundPlayed && !isJumping) {
				MyFrame.this.mario.jump();
				playSound("sounds/jump.wav");
				jumpSoundPlayed = true;
				isJumping = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

				setGamePaused(!isPaused);

				if (isPaused) {
					pauseBackgroundMusic();
					menuDialog.setVisible(true);
				} else {
					resumeBackgroundMusic();
					menuDialog.setVisible(false);
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

		public void setJumping(boolean jumping) {
			this.isJumping = jumping;
		}
	}
	public static void main(String[] args) {
		new MyFrame();
	}

	private boolean isPaused = false;

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

			this.repaint();
			try {
				Thread.sleep(50);
				if (Backgroundnow.isFlag() && mario.getX() >= 690) {
					gameWin();
				} else if (mario.getX() >= 840) {
					this.Backgroundnow = Backgrounds.get(this.Backgroundnow.getSort());
					mario.setBackground(Backgroundnow);
					mario.setX(0);
					mario.setY(480);
				}

				if (mario.isOnSurface()) {
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
	private void setGamePaused(boolean paused) {
	    try {

	        isPaused = paused;

	        if (mario != null) {
	            mario.setPaused(paused);
	        }

	        if (Backgroundnow != null && Backgroundnow.Turtle != null) {
	            Backgroundnow.Turtle.setPaused(paused);
	        }


	        if (Backgroundnow != null && Backgroundnow.enemy != null) {
	            for (Object enemy : Backgroundnow.enemy) {
	                if (enemy instanceof MoveEnemy) {
	                    ((MoveEnemy) enemy).setPaused(paused);
	                }
	            }
	        }
	        if (paused) {
	            pauseBackgroundMusic();
	        } else {
	            resumeBackgroundMusic();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	private void restartGame() {
		try {
			setGamePaused(true);

			if (Backgroundnow != null) {
				Backgroundnow.enemy.clear();
				Backgroundnow.obstraction.clear();
				Backgroundnow.removedenemy.clear();
				Backgroundnow.remove.clear();
				Backgroundnow.Turtle = null;
			}

			if (backgroundClip != null) {
				backgroundClip.stop();
				backgroundClip.close();
				backgroundClip = null;
			}


			ScoreManager.getInstance().resetScore();

			Staticvalues.init();

			Backgrounds.clear();
			for (int i = 1; i <= 3; i++) {
				this.Backgrounds.add(new Background(i, i == 3));
			}
			Backgroundnow = Backgrounds.get(0);

			mario = new Mario(0, 480);
			mario.setBackground(Backgroundnow);
			mario.setMyFrame(this);

			playBackgroundSound("sounds/background.wav");

			setGamePaused(false);


			this.repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void gameOver() {
	    setGamePaused(true);

	    pauseBackgroundMusic();

	    gameOverDialog.setLocationRelativeTo(this);
	    gameOverDialog.setVisible(true);
	}
	private void createGameOverDialog() {
	    gameOverDialog = new JDialog(this, "Игра окончена", true);
	    gameOverDialog.setSize(300, 150);
	    gameOverDialog.setLayout(new BoxLayout(gameOverDialog.getContentPane(), BoxLayout.Y_AXIS));
	    gameOverDialog.setLocationRelativeTo(this);

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
	private void createGameWinDialog() {
	    gameWinDialog = new JDialog(this, "Победа!", true);
	    gameWinDialog.setSize(300, 150);
	    gameWinDialog.setLayout(new BoxLayout(gameWinDialog.getContentPane(), BoxLayout.Y_AXIS));
	    gameWinDialog.setLocationRelativeTo(this);

	    gameWinDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

	    JButton restartButton = new JButton("Играть заново");
	    restartButton.addActionListener(e -> {
	        gameWinDialog.setVisible(false);
	        restartGame();
	    });

	    JButton exitButton = new JButton("Выход из игры");
	    exitButton.addActionListener(e -> System.exit(0));

	    gameWinDialog.add(restartButton);
	    gameWinDialog.add(exitButton);
	}
	public void gameWin() {
	    setGamePaused(true);
	    pauseBackgroundMusic();
	    playSound("sounds/victory.wav");
	    gameWinDialog.setLocationRelativeTo(this);
	    gameWinDialog.setVisible(true);
	}

	@Override
	public void onScoreChanged(int newScore) {
		scoreLabel.setText("Score: " + newScore);
	}
}
