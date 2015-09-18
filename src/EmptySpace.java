import java.awt.Color;
import java.awt.Graphics;

public class EmptySpace extends GameObject {

	public EmptySpace(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move(GameBoard board) {
		// TODO Auto-generated method stub

	}
	public void paint(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
