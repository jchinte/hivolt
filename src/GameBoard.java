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
	private GridBagConstraints getConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx =1.0;
		c.weighty = 1.0;		
		return c;
	}
	public GameBoard(){
		super();
		gameOver = false;
		moving = false;
		layout = new GridBagLayout();
		setLayout(layout);
		gameObjects = new GameObject[12][12];
		
		makeFence();



		mhos = new ArrayList<GameObject>();
		Random rng = new Random();

		for (int mhoNum=0; mhoNum<12;mhoNum++){
			//this algorithm will take a while!
			int x = rng.nextInt(10)+1;
			int y = rng.nextInt(10)+1;
			while (gameObjects[x][y]!=null){
				x = rng.nextInt(10)+1;
				y = rng.nextInt(10)+1;
			}

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

			player = new Player(x,y, this);
			gameObjects[x][y] = player;
		}

		makeEmptys();
		addGameObjects();
	}
	/**
	 * Fill the rest of the board with empty game objects.
	 * @param c
	 */
	private void makeEmptys() {
		for (int i=1;i<11;i++){
			for(int j=1;j<11;j++){
				if (gameObjects[i][j]==null){
					gameObjects[i][j] = new EmptySpace(i,j);
				}
			}
		}
	}
	/**
	 * add all the game objects to the grid.
	 * @param c
	 */
	private void addGameObjects() {
		//add to grid
		for (int i=0; i<12; i++){
			for (int j=0; j<12; j++){
				add(gameObjects[i][j], gameObjects[i][j].getConstraints());   //add the object to (i,j) in the grid.
			}
		}
	}
	private void makeFence() {
		for (int i=0;i<12;i++){
			gameObjects[i][0] = new Fence(i,0);
		}
		for (int j=1;j<11;j++){
			gameObjects[0][j] = new Fence(0,j);
			gameObjects[11][j] = new Fence(11,j);
		}
		for (int i=0;i<12;i++){
			gameObjects[i][11] = new Fence(i,11);
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
			gameObjects[x][y] = new Fence(x,y);
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
				newX = player.getGameX();
				newY = player.getGameY()-1;
			}
			else if (arg0.getKeyCode()==KeyEvent.VK_X){
				newX = player.getGameX();
				newY = player.getGameY()+1;
			}
			else if (arg0.getKeyCode()==KeyEvent.VK_A){
				newX = player.getGameX()-1;
				newY = player.getGameY();
			}
			else if (arg0.getKeyCode()==KeyEvent.VK_D){
				newX = player.getGameX()+1;
				newY = player.getGameY();
			}
			else if (arg0.getKeyCode()==KeyEvent.VK_Q){
				newX = player.getGameX()-1;
				newY = player.getGameY()-1;
			}
			else if (arg0.getKeyCode()==KeyEvent.VK_E){
				newX = player.getGameX()+1;
				newY = player.getGameY()-1;
			}
			else if (arg0.getKeyCode()==KeyEvent.VK_Z){
				newX = player.getGameX()-1;
				newY = player.getGameY()+1;
			}
			else if (arg0.getKeyCode()==KeyEvent.VK_C){
				newX = player.getGameX()+1;
				newY = player.getGameY()+1;
			}
			else if (arg0.getKeyCode()==KeyEvent.VK_S){
				newX = player.getGameX();
				newY = player.getGameY();
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
			int diffX = player.getGameX() - mho.getGameX();
			int diffY = player.getGameY() - mho.getGameY();
			
			
			int newX = mho.getGameX()+((diffX==0)?0:diffX/Math.abs(diffX));
			/*
			 * int newX 
			 */
			
			int newY = mho.getGameY()+((diffY==0)?0:diffY/Math.abs(diffY));
			if (boardPlayer(newX, newY)){
				System.out.println("You Died!");
				removefromGame(player);
				swap(mho.getGameX(), mho.getGameY(), newX, newY);
				gameOver = true;
				return;
			}
			if (boardEmpty(newX, newY)){
				swap(mho.getGameX(), mho.getGameY(), newX, newY);
				continue;
			}
			if (Math.abs(diffX)>=Math.abs(diffY)){
				newX = mho.getGameX()+((diffX==0)?0:diffX/Math.abs(diffX));
				newY = mho.getGameY();
				if (boardPlayer(newX, newY)){
					System.out.println("You Died!");
					return;
				}
				if (boardEmpty(newX, newY)){
					swap(mho.getGameX(), mho.getGameY(), newX, newY);
					continue;
				}				
			}
			if (Math.abs(diffX)<=Math.abs(diffY)){
				newX = mho.getGameX();
				newY = mho.getGameY()+((diffY==0)?0:diffY/Math.abs(diffY));
				if (boardPlayer(newX, newY)){
					System.out.println("You Died!");
					return;
				}
				if (boardEmpty(newX, newY)){
					swap(mho.getGameX(), mho.getGameY(), newX, newY);
					continue;
				}				
			}
			newX = mho.getGameX()+((diffX==0)?0:diffX/Math.abs(diffX));
			newY = mho.getGameY()+((diffY==0)?0:diffY/Math.abs(diffY));
			if (boardFence(newX, newY)){
				removefromGame(mho);
				iterator.remove();
				continue;
			}
			if (Math.abs(diffX)>=Math.abs(diffY)){
				newX = mho.getGameX()+((diffX==0)?0:diffX/Math.abs(diffX));
				newY = mho.getGameY();
				if (boardFence(newX, newY)){
					removefromGame(mho);
					iterator.remove();
					continue;
				}				
			}
			if (Math.abs(diffX)<=Math.abs(diffY)){
				newX = mho.getGameX();
				newY = mho.getGameY()+((diffY==0)?0:diffY/Math.abs(diffY));
				if (boardFence(newX, newY)){
					removefromGame(mho);
					iterator.remove();
					continue;
				}
			}
		}

	}
	private void removefromGame(GameObject mho) {
		EmptySpace newEmpty = new EmptySpace(mho.getGameX(), mho.getGameY());
		remove(mho);
		gameObjects[newEmpty.getGameX()][newEmpty.getGameY()] = newEmpty;
		add(newEmpty, newEmpty.getConstraints());
		invalidate();
		validate();
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

		remove(b);
		remove(a);
		add(b, b.getConstraints());
		add(a, a.getConstraints());
		//invalidate();
		validate();
	}
}
