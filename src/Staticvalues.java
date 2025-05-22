import java.awt.Image;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;

public class Staticvalues {
    public static ArrayList<Image> mariao = new ArrayList<>();
    public static Image start = null;
    public static Image end = null;
    public static Image bgImage = null;
    public static ArrayList<Image> flower = new ArrayList<>();
    public static ArrayList<Image> trangel = new ArrayList<>();
    public static ArrayList<Image> turtel = new ArrayList<>();
    public static ArrayList<Image> obstruction = new ArrayList<>();
    public static Image die = null;

    public static void init() {
        try {
            for (int i = 1; i <= 10; i++) {  // загружаем 10 изображений марио
                Image image = ImageIO.read(new File("images//" + i + ".gif"));
                mariao.add(image);  // добавляем список
            }
            end = ImageIO.read(new File("images//firststageend.gif"));
            bgImage = ImageIO.read(new File("images//firststage.gif")); // загружаем фон изображение
            for (int i = 1; i <= 5; i++) {
                if (i <= 2) {
                    Image image = ImageIO.read(new File("images//flower" + i + ".gif"));
                    flower.add(image);
                }
                if(i <= 3)
                {
                    Image image = ImageIO.read(new File("images//triangle" + i +".gif")); //крокодил
                    trangel.add(image);
                }
                Image image = ImageIO.read(new File("images//Turtle" + i +".gif"));  //тасбака
                turtel.add(image);
            }
            for(int i = 1;i <= 12;i++)
            {
                Image image = ImageIO.read(new File("images//ob" + i + ".gif")); //блоки
                obstruction.add(image);  //добавляет
            }
            die = ImageIO.read(new File("images//over.gif"));  //изображение смерти
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
