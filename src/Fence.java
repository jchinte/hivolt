import java.awt.Color;
import java.awt.Graphics;

public class Fence extends GameObject {

	public Fence(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move(GameBoard board) {
		// TODO Auto-generated method stub

	}
	public void paint(Graphics g){
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
