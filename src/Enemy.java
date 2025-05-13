import java.awt.Image;

public class Enemy {
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStarttype() {
		return starttype;
	}

	public void setStarttype(int starttype) {
		this.starttype = starttype;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	private int x; 
	private int y; 
	private int type; 
	private int starttype; 
	private Image image = null;

	public Enemy(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.setImage();
	}

	public void reset() 
	{ 
		this.type = starttype;
		this.setImage();
	}

	public void setImage() 
	{
		image = Staticvalues.obstruction.get(type);
	}

}
