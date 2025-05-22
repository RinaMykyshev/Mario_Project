import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class ScoreDisplay extends JPanel implements ScoreObserver, ScoreDisplayComponent {
    private int currentScore = 0;

    public ScoreDisplay() {
        ScoreManager.getInstance().addObserver(this);
    }

    @Override
    public void onScoreChanged(int newScore) {
        this.currentScore = newScore;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Score: " + currentScore, 10, 20);
    }
<<<<<<< HEAD
}
=======
} 
>>>>>>> 5beaa6b2937da85637c9c75c5a5244d9dfd06447
