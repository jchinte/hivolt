import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	public GameBoard board;
	public GamePanel(){
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		board = new GameBoard();
		add(board);
		//addKeyListener(board);
	}
}