import java.util.ArrayList;
import java.util.List;

public class ScoreManager {
    private static ScoreManager instance;
    private int score = 0;
    private List<ScoreObserver> observers = new ArrayList<>();

    private ScoreManager() {}

    public static ScoreManager getInstance() {
        if (instance == null) {
            instance = new ScoreManager();
        }
        return instance;
    }

    public void addObserver(ScoreObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ScoreObserver observer) {
        observers.remove(observer);
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        score += points;
        notifyObservers();
    }

    private void notifyObservers() {
        for (ScoreObserver observer : observers) {
            observer.onScoreChanged(score);
        }
    }

    public void resetScore() {
        score = 0;
        notifyObservers();
    }
} 