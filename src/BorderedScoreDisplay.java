import java.awt.Color;
import java.awt.Graphics;

public class BorderedScoreDisplay implements ScoreDisplayComponent {
    private final ScoreDisplayComponent component;

    public BorderedScoreDisplay(ScoreDisplayComponent component) {
        this.component = component;
    }

    @Override
    public void draw(Graphics g) {
        // Сначала рисуем основной компонент
        component.draw(g);
        // Затем рисуем рамку
        g.setColor(Color.YELLOW);
        g.drawRect(5, 5, 120, 25);
    }
} 