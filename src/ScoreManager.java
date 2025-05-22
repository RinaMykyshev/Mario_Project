import java.util.ArrayList;
import java.util.List;

public class ScoreManager {
    private static ScoreManager instance;  //статический экземпляр для синглтон
    private int score = 0;
    private List<ScoreObserver> observers = new ArrayList<>();  // список набл

    private ScoreManager() {}

    public static ScoreManager getInstance() {     // получить единственный экземпляр
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

    private void notifyObservers() {   //уводемляет всех наблюдателей
        for (ScoreObserver observer : observers) { // каждого уводемляет
            observer.onScoreChanged(score);  // обновляет
        }
    }

    public void resetScore() {
        score = 0;
        notifyObservers();
    }

}


