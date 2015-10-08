import java.awt.Graphics;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author John Chinte
 *
 */
public abstract class GameObject extends JPanel {

	private GridBagConstraints location;
	public GameObject(int x, int y){
		this.location = getGridBagConstraints(x, y);
	}
	public abstract void move(GameBoard board);
	public void paint(Graphics g){
		super.paint(g);
	}
	public GridBagConstraints getConstraints(){
		return location;
	}
	public int getGameX(){
		return location.gridx;
	}
	public int getGameY(){
		return location.gridy;
	}
	public void gameSetLocation(int x, int y){
		this.location.gridx = x;
		this.location.gridy = y;
	}
	GridBagConstraints getGridBagConstraints(int x, int y){
		GridBagConstraints c = new GridBagConstraints();
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.gridx = x;
		c.gridy = y;
		c.weightx =1.0;
		c.weighty = 1.0;
		return c;
	}
}
