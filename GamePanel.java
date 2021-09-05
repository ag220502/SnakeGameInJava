import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener 
{
	//***********Variable Declaration****************
	//For Width Of Screen
	static final int SCREEN_WIDTH= 1000;
	//For Height Of Screen
	static final int SCREEN_HEIGHT= 700;
	//For size of pixels of screen
	static final int UNIT_SIZE= 20 ;
	static final int GAME_UNITS= (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE; 
	//For movement of Snake
	Timer timer;
	static final int DELAY = 65;
	//For Length of Snake
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	//For No. Of Default Body Parts
	int bodyParts = 6;
	//For Numbers of Apple Eaten
	int applesEaten;
	//For Position of Apple
	int appleX;
	int appleY;
	//For Direction Of Snake
	char direction='R';
	//For checking whether snake is moving or not
	boolean running= false;
	//For Creating random values
	Random random;
	
	//***********Constructor****************
	GamePanel()
	{
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		//Calling method to start New Game
		startGame();
	}
	//Method for Starting New Game
	public void startGame()
	{
		//Calling method to place an apple
		newApple();
		//Keeping the snake to move by default 
		running = true;
		//Starting Timer to move by the speed declared
		timer = new Timer(DELAY,this);
		timer.start();
	}
	//Method For Drawing Components
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//Calling Method to make body of snake
		draw(g);
	}
	//Method to draw Body Of Snake
	public void draw(Graphics g)
	{
		//If the Snake Is Running
		if(running)
		{
			
			//for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++)
			//{
				//g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				//g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			//}
			
			//Drawing apple 
			//Giving color to apple
			g.setColor(Color.red);
			//FIlling the color so that it is visible
			g.fillOval(appleX,appleY, UNIT_SIZE, UNIT_SIZE);
			
			//For Body Parts Of Snake if the snake is moving
			for(int i=0;i<bodyParts;i++)
			{
				//For the Face Of Snake
				if(i==0)
				{
					//Giving color to face of Snake
					g.setColor(Color.green);
					//Filling the color so that face is visible
					g.fillRect(x[i], y[i], UNIT_SIZE,UNIT_SIZE);
				}
				else
				{
					//Giving color to body part of Snake
					g.setColor(Color.BLUE);
					//Filling the color so that body part is visible
					g.fillRect(x[i], y[i], UNIT_SIZE,UNIT_SIZE);
				}
			}
			//For displaying the Score 
			//Giving color to face of Text
			g.setColor(Color.GRAY);
			//Font Style for Text
			g.setFont(new Font("Ink Free",Font.BOLD,40));
			
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score : "+applesEaten,(SCREEN_WIDTH - metrics.stringWidth("Score : "+applesEaten))/2, g.getFont().getSize());
		}
		//If Game is Not Running Game is Over
		else
		{
			gameOver(g);
		}	
	}
	public void newApple()
	{
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	public void move() 
	{
		for(int i=bodyParts;i>0;i--)
		{
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		switch(direction)
		{
		case 'U' :
			y[0] = y[0]-UNIT_SIZE;
			break;
		case 'D' :
			y[0] = y[0]+UNIT_SIZE;
			break;
		case 'L' :
			x[0] = x[0]-UNIT_SIZE;
			break;
		case 'R' :
			x[0] = x[0]+UNIT_SIZE;
			break;
		}
	}
	public void checkApple()
	{
		if((x[0] == appleX) && (y[0] == appleY))
		{
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	public void checkCollision()
	{
		//check if head collides with body
		for(int i=bodyParts;i>0;i--)
		{
			if((x[0] == x[i]) && (y[0] == y[i]))
			{
				running = false;
			}
		}
		//check if head touches left border 
		if(x[0]<0)
		{
			running = false;
		}
		//check if head touches right border 
		if(x[0]>SCREEN_WIDTH)
		{
			running = false;
		}
		//check if head touches top border 
		if(y[0]<0)
		{
			running = false;
		}
		//check if head touches right border 
		if(y[0]>SCREEN_HEIGHT)
		{
			running = false;
		}
		if(!running)
		{
			timer.stop();
		}
	}
	public void gameOver(Graphics g)
	{
		//Game Over
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over",(SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}
	public void actionPerformed(ActionEvent e) {
		if(running)
		{
			move();
			checkApple();
			checkCollision();
		}
		repaint();
	}
	public class MyKeyAdapter extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R')
				{
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L')
				{
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D')
				{
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U')
				{
					direction = 'D';
				}
				break;
			}
		}
	}
}
