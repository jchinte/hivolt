import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

/**
 * 
 */

/**
 * @author user
 *
 */
public class WinBox extends GameObject {

	JLabel winText = new JLabel("");
	JLabel movesText = new JLabel("Moves: ");
	JLabel mhoCountText = new JLabel("Mhos: ");
	GameBoard gameBoard;
	
	public WinBox(GameBoard gb, int x, int y){
		super(x, y);
		gameBoard = gb;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.getAllFonts();
		Font f = new Font("Garamond", Font.BOLD, 19);
		winText.setFont(f);
		movesText.setFont(f);
		mhoCountText.setFont(f);
		add(movesText);
		add(mhoCountText);
		add(winText);
	}
	/* (non-Javadoc)
	 * @see GameObject#move(GameBoard)
	 */
	@Override
	public void move(GameBoard board) {
		// TODO Auto-generated method stub

	}
	public void paint(Graphics g){
		super.paint(g);
		movesText.setText("Moves: " + gameBoard.getNumMoves()+"\n");
		mhoCountText.setText("Mhos: "+gameBoard.getNumMhos()+"\n");
		if (gameBoard.isGameOver()){
			if (gameBoard.getNumMhos()>0){
				winText.setText("You Died!");
			}
			else {
				winText.setText("You won!");
			}
		}
		Font f = new Font("Garamond", Font.BOLD|Font.ITALIC, 11);
		
	}
}
