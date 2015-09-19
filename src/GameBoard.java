import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

public class GameBoard extends JPanel implements KeyListener {
	private GridBagLayout layout;
	private GameObject [][] gameObjects;
	private List<GameObject> mhos;
	private Player player;
	private boolean gameOver;
	
	private boolean moving;
	public GameBoard(){
		super();
		gameOver = false;
		moving = false;
		layout = new GridBagLayout();
		setLayout(layout);
		gameObjects = new GameObject[12][12];
		GridBagConstraints c = new GridBagConstraints();
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx =1.0;
		c.weighty = 1.0;
		for (int i=0;i<12;i++){
			c.gridx = i;
			c.gridy = 0;
			gameObjects[i][0] = new Fence(i,0);
			//add(gameObjects[i][0], c);
		}
		for (int j=1;j<11;j++){
			c.gridx = 0;
			c.gridy = j;
			gameObjects[0][j] = new Fence(0,j);
			add(gameObjects[0][j], c);
			c.gridx = 11;
			gameObjects[11][j] = new Fence(11,j);
			//add(gameObjects[11][j], c);

		}
		for (int i=0;i<12;i++){
			c.gridx = i;
			c.gridy = 11;
			gameObjects[i][11] = new Fence(i,11);
			//add(gameObjects[i][11], c);
		}

		Random rng = new Random();
		for (int fenceNum=0; fenceNum<20;fenceNum++){
			//this algorithm will take a while!
			int x = rng.nextInt(10)+1;
			int y = rng.nextInt(10)+1;
			while (gameObjects[x][y]!=null){
				x = rng.nextInt(10)+1;
				y = rng.nextInt(10)+1;
			}
			c.gridx = x;
			c.gridy = y;
			gameObjects[x][y] = new Fence(x,y);
		}

		mhos = new ArrayList<GameObject>();
		for (int mhoNum=0; mhoNum<12;mhoNum++){
			//this algorithm will take a while!
			int x = rng.nextInt(10)+1;
			int y = rng.nextInt(10)+1;
			while (gameObjects[x][y]!=null){
				x = rng.nextInt(10)+1;
				y = rng.nextInt(10)+1;
			}
			c.gridx = x;
			c.gridy = y;
			gameObjects[x][y] = new Mho(x,y);
			mhos.add(gameObjects[x][y]);
		}
		{
			int x = rng.nextInt(10)+1;
			int y = rng.nextInt(10)+1;
			while (gameObjects[x][y]!=null){
				x = rng.nextInt(10)+1;
				y = rng.nextInt(10)+1;
			}
			c.gridx = x;
			c.gridy = y;
			player = new Player(x,y, this);
			gameObjects[x][y] = player;
		}

		for (int i=1;i<11;i++){
			for(int j=1;j<11;j++){
				if (gameObjects[i][j]==null){
					c.gridx = i;
					c.gridy = j;
					gameObjects[i][j] = new EmptySpace(i,j);
				}
				//add(gameObjects[i][j], c);
			}
		}
		//add to grid
		for (int i=0; i<12; i++){
			for (int j=0; j<12; j++){
				c.gridx = i;
				c.gridy = j;
				add(gameObjects[i][j],c);
			}
		}
	}
	public void paint(Graphics g){
		super.paint(g);
		System.err.println("Width: "+getWidth() + "; height: " + getHeight());
	}
	public void move(){
		moving = true;
	}
	public void stop(){
		moving = false;
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		if (!gameOver && !moving){
			int newX, newY;
			if (arg0.getKeyCode()==KeyEvent.VK_W){
				newX = player.gameGetX();
				newY = player.gameGetY()-1;
			}
			else if (arg0.getKeyCode()==KeyEvent.VK_X){
				newX = player.gameGetX();
				newY = player.gameGetY()+1;
			}
			else if (arg0.getKeyCode()==KeyEvent.VK_A){
				newX = player.gameGetX()-1;
				newY = player.gameGetY();
			}
			else if (arg0.getKeyCode()==KeyEvent.VK_D){
				newX = player.gameGetX()+1;
				newY = player.gameGetY();
			}
			else if (arg0.getKeyCode()==KeyEvent.VK_Q){
				newX = player.gameGetX()-1;
				newY = player.gameGetY()-1;
			}
			else if (arg0.getKeyCode()==KeyEvent.VK_E){
				newX = player.gameGetX()+1;
				newY = player.gameGetY()-1;
			}
			else if (arg0.getKeyCode()==KeyEvent.VK_Z){
				newX = player.gameGetX()-1;
				newY = player.gameGetY()+1;
			}
			else if (arg0.getKeyCode()==KeyEvent.VK_C){
				newX = player.gameGetX()+1;
				newY = player.gameGetY()+1;
			}
			else if (arg0.getKeyCode()==KeyEvent.VK_S){
				newX = player.gameGetX();
				newY = player.gameGetY();
			}
			else if (arg0.getKeyCode()==KeyEvent.VK_J){
				Random rng = new Random();
				newX = rng.nextInt(10)+1;
				newY = rng.nextInt(10)+1;
				while (gameObjects[newX][newY].getClass() == Fence.class){
					newX = rng.nextInt(10)+1;
					newY = rng.nextInt(10)+1;
				}
			}		
			else {
				return;
			}
			if (boardEmpty(newX, newY)||boardPlayer(newX, newY)){
				player.moveTo(newX, newY);
			}
			else {
				System.out.println("You died.");
				gameOver = true;
			}
			if (arg0.getKeyCode()==KeyEvent.VK_J) {
				return;
			}
			moveMhos();
			if (mhos.size()==0){
				System.out.println("You won!");
				gameOver = true;
			}
		}
	}

	private void moveMhos() {
		for (Iterator<GameObject> iterator = mhos.iterator(); iterator.hasNext(); ){
			GameObject mho = iterator.next();
			int diffX = player.gameGetX() - mho.gameGetX();
			int diffY = player.gameGetY() - mho.gameGetY();
			int newX = mho.gameGetX()+((diffX==0)?0:diffX/Math.abs(diffX));
			int newY = mho.gameGetY()+((diffY==0)?0:diffY/Math.abs(diffY));
			if (boardPlayer(newX, newY)){
				System.out.println("You Died!");
				removefromGame(player);
				swap(mho.gameGetX(), mho.gameGetY(), newX, newY);
				gameOver = true;
				return;
			}
			if (boardEmpty(newX, newY)){
				swap(mho.gameGetX(), mho.gameGetY(), newX, newY);
				continue;
			}
			if (Math.abs(diffX)>=Math.abs(diffY)){
				newX = mho.gameGetX()+((diffX==0)?0:diffX/Math.abs(diffX));
				newY = mho.gameGetY();
				if (boardPlayer(newX, newY)){
					System.out.println("You Died!");
					return;
				}
				if (boardEmpty(newX, newY)){
					swap(mho.gameGetX(), mho.gameGetY(), newX, newY);
					continue;
				}				
			}
			if (Math.abs(diffX)<=Math.abs(diffY)){
				newX = mho.gameGetX();
				newY = mho.gameGetY()+((diffY==0)?0:diffY/Math.abs(diffY));
				if (boardPlayer(newX, newY)){
					System.out.println("You Died!");
					return;
				}
				if (boardEmpty(newX, newY)){
					swap(mho.gameGetX(), mho.gameGetY(), newX, newY);
					continue;
				}				
			}
			newX = mho.gameGetX()+((diffX==0)?0:diffX/Math.abs(diffX));
			newY = mho.gameGetY()+((diffY==0)?0:diffY/Math.abs(diffY));
			if (boardFence(newX, newY)){
				removefromGame(mho);
				iterator.remove();
				continue;
			}
			if (Math.abs(diffX)>=Math.abs(diffY)){
				newX = mho.gameGetX()+((diffX==0)?0:diffX/Math.abs(diffX));
				newY = mho.gameGetY();
				if (boardFence(newX, newY)){
					removefromGame(mho);
					iterator.remove();
					continue;
				}				
			}
			if (Math.abs(diffX)<=Math.abs(diffY)){
				newX = mho.gameGetX();
				newY = mho.gameGetY()+((diffY==0)?0:diffY/Math.abs(diffY));
				if (boardFence(newX, newY)){
					removefromGame(mho);
					iterator.remove();
					continue;
				}
			}
		}

	}
	private void removefromGame(GameObject mho) {
		EmptySpace newEmpty = new EmptySpace(mho.gameGetX(), mho.gameGetY());
		remove(mho);
		GridBagConstraints c = new GridBagConstraints();
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.gridx = newEmpty.gameGetX();
		c.gridy = newEmpty.gameGetY();
		c.weightx =1.0;
		c.weighty = 1.0;
		gameObjects[newEmpty.gameGetX()][newEmpty.gameGetY()] = newEmpty;
		add(newEmpty, c);
	}
	private boolean boardEmpty(int newX, int newY) {
		return gameObjects[newX][newY].getClass() == EmptySpace.class;
	}
	private boolean boardPlayer(int newX, int newY) {
		return gameObjects[newX][newY].getClass() == Player.class;
	}
	private boolean boardFence(int newX, int newY) {
		return gameObjects[newX][newY].getClass() == Fence.class ||
				gameObjects[newX][newY].getClass() == Player.class;
	}
	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}
	public void swap(int ax, int ay, int bx, int by){
		GameObject a = gameObjects[ax][ay];
		GameObject b = gameObjects[bx][by];
		a.gameSetLocation(bx, by);
		b.gameSetLocation(ax, ay);
		gameObjects[ax][ay] = b;
		gameObjects[bx][by] = a;
		GridBagConstraints c = new GridBagConstraints();
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.gridx = ax;
		c.gridy = ay;
		c.weightx =1.0;
		c.weighty = 1.0;
		remove(b);
		remove(a);
		add(b, c);
		c.gridx = bx;
		c.gridy = by;
		add(a, c);
		//layout.invalidateLayout(this);
		invalidate();
		validate();
		//paintImmediately(getVisibleRect());
		//repaint();
	}
}
