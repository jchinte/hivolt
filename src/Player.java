import java.awt.Color;
import java.awt.Graphics;

public class Player extends GameObject {
	private GameBoard gameBoard;
	private Color c;
	public Player(int x, int y, GameBoard gameBoard) {
		super(x, y);
		this.gameBoard = gameBoard; 
		c = Color.blue;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move(GameBoard board) {
		// TODO Auto-generated method stub

	}
	public void moveTo(int x, int y){
		gameBoard.move();
		fadeOut();
		gameBoard.swap(gameGetX(),gameGetY(),x, y);
		fadeIn();
		gameBoard.stop();
		gameBoard.repaint();
	}
	private void fadeOut() {
		// TODO Auto-generated method stub
		//flash to white
		System.err.println("fade out");
		while (c.getRGB()!=Color.WHITE.getRGB()){
			c = new Color((256-c.getRed())/2 + c.getRed(),
					(256-c.getGreen())/2 + c.getGreen(),
					(256-c.getBlue())/2 + c.getBlue());
			this.paintImmediately(this.getVisibleRect());
			repaint();
			System.err.println("color = " + c.toString());
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//fade out
		while (c.getRGB()!=Color.BLACK.getRGB()){
			c = new Color((int)(c.getRed()*0.8), (int)(c.getGreen()*0.8), (int)(c.getBlue()*0.8));
			this.paintImmediately(this.getVisibleRect());
			repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}

	private void fadeIn() {
		System.err.println("fade in");
		//get to white
		while (c.getRGB()!=Color.WHITE.getRGB()){
			c = new Color((int)(c.getRed()+(259-c.getRed())*0.2), 
					(int)(c.getGreen()+(259-c.getGreen())*0.2), 
					(int)(c.getBlue()+(259-c.getBlue())*0.2));
			this.paintImmediately(this.getVisibleRect());
			repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}		
		//get to blue
		c = Color.blue;
		repaint();
	}

	public void paint(Graphics g){
		g.setColor(c);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
}
