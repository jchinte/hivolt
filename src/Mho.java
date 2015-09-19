import java.awt.Color;
import java.awt.Graphics;

public class Mho extends GameObject {

	public Mho(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move(GameBoard board) {
		// TODO Auto-generated method stub

	}
	public void paint(Graphics g){
		System.err.println("Mho drawn at "+gameGetX()+", "+gameGetY());
		g.setColor(Color.RED);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
