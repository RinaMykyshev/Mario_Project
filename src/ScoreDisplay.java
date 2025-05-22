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
        // draw(g); // не вызываем здесь, только через декоратор
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Score: " + currentScore, 10, 20);
    }
} 