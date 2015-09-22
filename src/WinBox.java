import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;

import javax.swing.JLabel;

/**
 * 
 */

/**
 * @author user
 *
 */
public class WinBox extends GameObject {

	
	public WinBox(int x, int y){
		super(x, y);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.getAllFonts();
		Font f = new Font("Garamond", Font.BOLD|Font.ITALIC, 11);
		JLabel lab = new JLabel("You won");
		lab.setFont(f);
		add(lab);
	}
	/* (non-Javadoc)
	 * @see GameObject#move(GameBoard)
	 */
	@Override
	public void move(GameBoard board) {
		// TODO Auto-generated method stub

	}
	public void paint(Graphics g){
		Font f = new Font("Garamond", Font.BOLD|Font.ITALIC, 11);
		
	}
}
