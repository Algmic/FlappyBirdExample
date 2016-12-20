package flappy.bird.example;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author micla1676
 */


public class FlappyBird extends JComponent{

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
    Rectangle bird = new Rectangle(100, 300, 50, 50);
    //create top pipes
    Rectangle[] topPipes = new Rectangle[5];
    //create bottom pipes
    Rectangle[] bottomPipes = new Rectangle[5];   
    
    //create an integer for gap between top and bottom
    int pipeGap = 150;
    //create an integer to store the distance between pipes
    int pipeSpacing = 200;
    //width of a single pipe
    int pipeWidth = 100;
    //the height of a pipe
    int pipeHeight = HEIGHT - 50;
    //minimum distance from edge
    int minDistance = 200;
    
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
        g.setColor(skyColour);
        //draw the sky background
        g.fillRect(0,0,WIDTH,HEIGHT);
        
        //draw the pipes
        g.setColor(Color.GREEN);
        for(int i = 0; i < topPipes.length; i++){
            g.fillRect(topPipes[i].x, topPipes[i].y, topPipes[i].width, topPipes[i].height);
            g.fillRect(bottomPipes[i].x, bottomPipes[i].y, bottomPipes[i].width, bottomPipes[i].height);
        }
        

        //draw the bird
        g.setColor(Color.YELLOW);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);
        
        
        
        // GAME DRAWING ENDS HERE
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
        
        Random randGen = new Random();
        
        //set up the pipes
        for(int i = 0; i < topPipes.length; i++){
            //generating a random y position
            int pipeY = randGen.nextInt(HEIGHT - (2 * minDistance))+minDistance;
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
        JFrame frame = new JFrame("My Game");
       
        // creates an instance of my game
        FlappyBird game = new FlappyBird();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        // adds the game to the window
        frame.add(game);
         
        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        
        // starts my game loop
        game.run();
    }
}
