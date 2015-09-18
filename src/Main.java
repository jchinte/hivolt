import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame f = new JFrame("HiVolt");
		f.setSize(600,400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GamePanel gp = new GamePanel();
		f.add(gp);
		f.addKeyListener(gp.board);
		f.setVisible(true);
		// TODO Auto-generated method stub

	}

}
