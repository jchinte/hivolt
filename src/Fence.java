import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

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
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.YELLOW);
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.addRenderingHints(rh);
		double w = getWidth();
		double h = getHeight();
		for (int  n = 0; n < 4; n++){
			double x = w/4.0*n;
			double y = h/4.0*n;
			Line2D line = new Line2D.Double(0,y,x,0);
			g2d.draw(line);
			line = new Line2D.Double (x, getHeight(), getWidth(), y);
			g2d.draw(line);
			line = new Line2D.Double(0, getHeight()-y, x, getHeight());
			g2d.draw(line);
			line = new Line2D.Double(x, 0, getWidth(), getHeight()-y);
			g2d.draw(line);
		}
	}
}
