package flappy.bird.example;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author micla1676
 */


public class FlappyBirdOriginal extends JComponent implements KeyListener{

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000)/desiredFPS;
    
    //create sky colour
    Color skyColour = new Color (116,211,242);
    //create bird
    Rectangle bird = new Rectangle(100, 200, 50, 50);
    //make gravity
    int gravity = 1;
    //difference in y
    int dy = 0;
    //jump velocity
    int jumpVelocity = -12;
    int deathCount = 0;
    
    //jump key variable
    boolean jump = false;
    boolean lastJump = false;
    //wait to start
    boolean start = false;
    boolean dead = false;
    
    //create top pipes
    Rectangle[] topPipes = new Rectangle[5];
    //create bottom pipes
    Rectangle[] bottomPipes = new Rectangle[5];   
    
    //create an integer for gap between top and bottom
    int pipeGap = 200;
    //create an integer to store the distance between pipes
    int pipeSpacing = 250;
    //width of a single pipe
    int pipeWidth = 100;
    //the height of a pipe
    int pipeHeight = HEIGHT - 50;
    //minimum distance from edge
    int minDistance = 250;
    
    //speed of the game
    int speed = 6;
    
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g)
    {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);
        
        // GAME DRAWING GOES HERE 
        //change to colour the sky
        g.setColor(Color.BLACK);
        //draw the sky background
        g.fillRect(0,0,WIDTH,HEIGHT);
        
        //draw the pipes
        g.setColor(Color.BLUE);
        for(int i = 0; i < topPipes.length; i++){
            g.fillRect(topPipes[i].x, topPipes[i].y, topPipes[i].width, topPipes[i].height);
            g.fillRect(bottomPipes[i].x, bottomPipes[i].y, bottomPipes[i].width, bottomPipes[i].height);
        }
        

        //draw the bird
        g.setColor(Color.YELLOW);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);
        
        
        
        // GAME DRAWING ENDS HERE
    }
    
    
    public void reset(){
        // keep track of pipe position
        int pipeX = 600;
        
        deathCount++;
        
        //create a random number
        Random randGen = new Random();
        
        //set up the pipes
        for(int i = 0; i < topPipes.length; i++){
            //generating a random y position
            //1 * minDistance
            int pipeY = randGen.nextInt(HEIGHT - (2 * minDistance)) + minDistance;
            bottomPipes[i] = new Rectangle(pipeX,pipeY, pipeWidth, pipeHeight);
            topPipes[i] = new Rectangle(pipeX, pipeY - pipeGap - pipeHeight, pipeWidth, pipeHeight);
            
            //move the pipeX value over
            pipeX = pipeX + pipeWidth + pipeSpacing;
        }
        //reset the bird
        bird.y = 200;
        dy = 0;
        start = false;
        dead = false;
        
        System.out.println("Your death count is " + deathCount);
    }
    
    

    public void setPipe (int pipePosition){
         //create a random number
        Random randGen = new Random();
         //generating a random y position
         int pipeY = randGen.nextInt(HEIGHT - (1 * minDistance))+minDistance;
         
         //generate the new pipe X coordinate
         int pipeX = topPipes [pipePosition].x;
         pipeX = pipeX + (pipeWidth + pipeSpacing)*topPipes.length;
         
          bottomPipes[pipePosition].setBounds(pipeX,pipeY, pipeWidth, pipeHeight);
          topPipes[pipePosition].setBounds(pipeX, pipeY - pipeGap - pipeHeight, pipeWidth, pipeHeight);
        
    }
    
    // The main game loop
    // In here is where all the logic for my game will go
    public void run()
    {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;
        
        // keep track of pipe position
        int pipeX = 600;
        
        //create a random number
        Random randGen = new Random();
        
        //set up the pipes
        for(int i = 0; i < topPipes.length; i++){
            //generating a random y position
            //1 * minDistance
            int pipeY = randGen.nextInt(HEIGHT - (2 * minDistance)) + minDistance;
            bottomPipes[i] = new Rectangle(pipeX,pipeY, pipeWidth, pipeHeight);
            topPipes[i] = new Rectangle(pipeX, pipeY - pipeGap - pipeHeight, pipeWidth, pipeHeight);
            
            //move the pipeX value over
            pipeX = pipeX + pipeWidth + pipeSpacing;
        }
                
                
        // the main game loop section
        // game will end if you set done = false;
        boolean done = false; 
        while(!done)                      
        {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();
            
            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
           
            //pause code if start is false
            if (start){
            //get the pipes moving
           
                if (!dead){
                for(int i = 0; i <topPipes.length; i++){
                topPipes[i].x = topPipes[i].x - speed; 
                bottomPipes[i].x = bottomPipes[i].x - speed; 
                //check if pipe is off the screen
                if(topPipes[i].x + pipeWidth < 0){
                    //move the pipe
                    setPipe(i);
                }
                }
            }
            
            //get the bird to fall
            //apply gravity
            dy = dy + gravity;
            //apply change in y to the bird
            bird.y = bird.y + dy;
            
            //check if bird is offscreen
            if(bird.y < 0 ){
                dead = true;
                
            }
            else if(bird.y + bird.height > HEIGHT){
                dead = true;
                bird.y = HEIGHT - bird.height;
                reset();
            }
            //dif the bird hit a pipe?
            //go thriugh all the pipes
            for (int i = 0; i < topPipes.length; i++){
               //if bird hit one of the top pipes
                if (bird.intersects(topPipes[i])){
                    dead = true;
                }
                //did the bird hit one of the bottom pipes
                else if(bird.intersects(bottomPipes[i])){
                    dead = true;
                }
            }
            
            //make the bird fly
            if(jump && !lastJump && !dead){
                dy = jumpVelocity;
            }
            lastJump = jump;
            }
            // GAME LOGIC ENDS HERE 
            
            // update the drawing (calls paintComponent)
            repaint();
            
            
            
            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if(deltaTime > desiredTime)
            {
                //took too much time, don't wait
            }else
            {
                try
                {
                    Thread.sleep(desiredTime - deltaTime);
                }catch(Exception e){};
            }
        }
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("Fatty Bird by Alex Miclaus");
       
        // creates an instance of my game
        FlappyBirdOriginal game = new FlappyBirdOriginal();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        // adds the game to the window
        frame.add(game);
        
        //add the key listener
        frame.addKeyListener(game);
        
        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        
        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //it's a trap
    }

    @Override
    public void keyPressed(KeyEvent e) {
         int key = e.getKeyCode();
         
         if(key == KeyEvent.VK_SPACE){
            jump = true;  
            start = true;     
    }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_SPACE){
            jump = false;
           
    }
    }
}
