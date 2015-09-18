import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author John Chinte
 *
 */
public abstract class GameObject extends JPanel {
	private int x;
	private int y;
	public GameObject(int x, int y){
		this.x = x;
		this.y = y;
	}
	public abstract void move(GameBoard board);
	public void paint(Graphics g){
		super.paint(g);
	}
	public int gameGetX(){
		return x;
	}
	public int gameGetY(){
		return y;
	}
	public void gameSetLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
}
