
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class PongSimpleFINALWeb


extends Applet 
implements KeyListener, MouseListener, Runnable{

	//DETERMIN WIDTH AND HEIGHT
	int width, height; 
	
	//SCOREBOARD
	int PlayerScore = 0;
	int CompScore = 0;
	String PlayerScoreString = ""+PlayerScore;
	String CompScoreString = ""+CompScore;

	//DEFINES PADDLE
	//UserPAddle
	int paddlex = 50, paddley = 50; 		
	int paddlewidth = 10, paddleheight = 50; 
	//ComputerPaddle
	
	int paddlex2 = 20, paddley2 = 50; 
	int paddlewidth2 = 10, paddleheight2 = 50; 
	int PaddleHeightTop = paddley2;
	int PaddleHeightBottom = paddley2 + paddleheight2;
	int PaddleHeightMiddle = (PaddleHeightTop + PaddleHeightBottom)/2;

	//DEFINES BALL
	int ballx = 50, bally = 300;
	int ballwidth = 10, ballheight = 10;
	int deltax = 2, deltay = 2;
	
	//GAMESPEED
	int Speed = 20;

	Image backbuffer;
   	Graphics backg;
   	
   	//AI CASE
   	Random AI = new Random();
   	int AIKey;

   	//KEYINP
	boolean leftdown = false;	// flags for smooth response to key presses
	boolean rightdown = false;
	boolean updown = false;
	boolean downdown = false;
	
	private Image img;
	
	//START/RESTART KEY
	boolean Start = false;
	boolean Restart = false;
	Thread t;

	
	public void init(){

		addKeyListener(this);
		addMouseListener(this);
		//addMouseListener (this);
		width = 500;	
      	height = 250;

		paddlex = width - 2 * paddlewidth;

		//CREATE BACKGROUND BUFFER
		backbuffer = createImage( width, height );
      	backg = backbuffer.getGraphics();
		drawToBackbuffer();
		
		//SET BACKGROUND
		
		//THREAD ANIMATION
		t = new Thread(this);	
	}  		
	
	public void update( Graphics g ) {
      		g.drawImage( backbuffer, 0, 0, this );
      		if (CompScore >= 5){
    			g.drawString ("Computer Wins", 200, 50);
    			g.drawString ("Press Space to restart", 182, 70);
    			t.suspend();
    		}
    		if (PlayerScore >= 5){
    			g.drawString("Player Wins", 200, 50);
    			g.drawString ("Press Space to restart", 182, 70);
    			t.suspend();
    		}
      		
   	}
	
	//MOUSE FUNCTIONS(START GAME)!
	public void mouseClicked (MouseEvent Me){
		if(Me.MOUSE_CLICKED == MouseEvent.MOUSE_CLICKED){
			t.start();
		}
	}
	public void mouseEntered(MouseEvent Me) {
	}
	public void mouseExited(MouseEvent Me) {		
	}
	public void mousePressed(MouseEvent Me) {	
	}
	public void mouseReleased(MouseEvent Me) {
	}

   	public void paint( Graphics g ) {
      		update( g );
   	}

   	//DRAWING CODE
	//GAMES DESC/HOLDS SCORE
	public void drawToBackbuffer(){ 
		
		backg.setColor(Color.white);
		backg.fillRect( 0,0, getSize().width, getSize().height);
		backg.drawImage(img, 0, 0, 500, 250, this);

		backg.setColor(Color.blue);
		backg.drawRect(0,0,width-1,height-1);
		backg.fillRect(ballx,bally,ballwidth,ballheight); //draw ball;
		backg.fillRect(paddlex,paddley,paddlewidth,paddleheight);//draw userpaddle
		
		//Draws other Paddle
		backg.fillRect(paddlex2,paddley2,paddlewidth2,paddleheight2);//draw userpaddle
		
		//Draws
		backg.drawString("Player Score:"+PlayerScoreString, 300, 20);
      		backg.drawString("Computer Score:"+CompScoreString, 100, 20);
      		backg.drawString("Click to start", 10, 240);
		repaint();
	}

	
	public void keyPressed(KeyEvent ke){

		switch (ke.getKeyCode()) {

    			case KeyEvent.VK_UP: 
        			updown = true;	
        			//We only change states of flags here	
				break;
    			case KeyEvent.VK_DOWN: 
        			downdown = true;
				break;
    			case KeyEvent.VK_SPACE:
    				Restart = true;
    				PlayerScore = 0;
    				PlayerScoreString = ""+PlayerScore;
    				CompScore = 0; 
    				CompScoreString = ""+CompScore;
    				t.resume();
    			break;
    		}		
	}
	
	public void keyReleased(KeyEvent ke){

		switch (ke.getKeyCode()) {

    			case KeyEvent.VK_UP: 
        			updown = false;		
				break;
    			case KeyEvent.VK_DOWN: 
        			downdown = false;
				break;
    			case KeyEvent.VK_SPACE:
    				Restart = false;
				break;
    		}
	}
	
	
	//OPPONENT CODE -- AI
	public void updateOpponent(){
		
		//ACTUAL GAME AI (BASED ON RANDOM GENERATOR)
		
		switch(AIKey){
		case 0:
			if (bally + ballheight >= paddley2)
			{
				paddley2 += 5;
			}
			if (bally + ballheight <= paddley2+paddleheight2)
			{
				paddley2 += -5;
			}

			break;
		case 1:
			if (bally - ballheight >= paddley2)
			{
				paddley2 += 5;
			}
			if (bally - ballheight <= paddley2+paddleheight2)
			{
				paddley2 += -5;
			}
		case 2:
			if (bally - ballheight >= paddley2)
			{
				paddley2 += 5;
			}
			if (bally + ballheight <= paddley2+paddleheight2)
			{
				paddley2 += -5;
			}
		}
		
		/*//AI_1 -- PERFECT AI IMPOSSIBLE TO BEAT
		if (bally - ballheight >= paddley2)
			{
				paddley2 += 2;
			}
			if (bally + ballheight <= paddley2+paddleheight2)
			{
				paddley2 += -2;
			}*/		

		/*//AI_0 -- AVERAGE SPEED
		if (bally + ballheight >= paddley2)
		{
			paddley2 += 5;
		}
		if (bally + ballheight <= paddley2+paddleheight2)
		{
			paddley2 += -5;
		}
*/		
	}
	
	public void keyTyped(KeyEvent ke){}

	public void updateUserPaddle(){
		
		//CREATES FLAG STATES
		if(updown && paddley>0){
			paddley-=4; 	// move paddle 	
		}
		
		if(downdown && paddley+paddleheight<height){
			paddley+=4;
		}
	}
	
	public void updateBall(){

		//INCREMENTS BY DELTA
		ballx = ballx + deltax;   
		bally = bally + deltay;

		//HIT LEFT
		if(ballx<=0){				//hit left?
			ballx = 250;			//reset to left side
			bally = 125;
			deltax = -deltax;		//change movement direction
			PlayerScore++;
			PlayerScoreString = ""+PlayerScore;
			Speed = 20;
			//INTIMIDATOR
			//paddleheight2 = 50;
			AIKey = AI.nextInt(3);
		}
		
		//HIT RIGHT
		if(ballx + ballwidth >= width){  //hit right
			ballx = 250;
			bally = 125;
			deltax = -deltax;
			CompScore++; 
			CompScoreString = ""+CompScore;
			Speed = 20;
			//INTIMADATOR
			//paddleheight2 = 50;
			AIKey = AI.nextInt(3);
		}
		
		//HIT TOP
		if(bally<=0){			//hit top
			bally = 0;
			deltay = -deltay;
		}
		
		//HIT BOTTOM
		if(bally + ballheight >= height){ //hit bottom
			bally = height-ballheight;
			deltay = -deltay;
		}
		
		//DEFINES COMP IMAPCT
		if (ballx + ballwidth>= paddlex && bally > paddley && bally < paddley+paddleheight){ //Note: The code: "bally > paddly" is made because it helps the actually paddle.
			ballx = paddlex - ballwidth;
			deltax = -deltax;
			//INTIMIDATOR
			//paddleheight2 += 5;
			AIKey = AI.nextInt(3);
			if(Speed >= 6){
				Speed = Speed - 2;
			}
			else{
				Speed = 6;
			}
			//Derived from 'hit right section'.
		}
		
		//DEFINES USER IMPACT
		if(ballx - ballwidth <= paddlex2 && bally > paddley2 && bally < paddley2 + paddleheight2){//hit left?
			ballx = paddlex2 + ballwidth;			//reset to left side
			deltax = -deltax;
			AIKey = AI.nextInt(3);
			if(Speed >= 6){
				Speed = Speed - 2;
			}
			else{
				Speed = 6;
			}
		}
	}

	public void run(){

		//ANIMATION LOOP
		while(t!=null){

			updateBall();
			updateOpponent();
			updateUserPaddle();
			
			drawToBackbuffer();
			try{
				Thread.sleep(Speed);
			}catch(InterruptedException ie){}
		}
	}
}


