
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import  java.util.Random;
import  javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH=600;
    static final int SCREEN_HEIGHT=600;

    /*size of game objects*/
    static final int UNIT_SIZE=25;

    /*elements that fit on the screen*/
    static final int GAME_UNITS= (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;

    /*timer*/
    static final int DELAY=75;

    /*matrices that will have the coordinates where the snake will move through*/
    final int x[]= new int[GAME_UNITS]; //body parts
    final int y[] = new int[GAME_UNITS]; // has all the coordinates

    int bodyParts =6;
    int applesEaten;
    int appleX; // random apples
    int appleY; // positioning of the apples
    char direction = 'R'; // start of movement
    boolean running = false;
    Timer timer;
    Random random;
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        startGame();
    }

    /* Methods to be used*/

    //start game
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }

    // paint component
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    // draw
    public void draw(Graphics g){
        //form a kind of grid
        for (int i = 0; i <SCREEN_HEIGHT/UNIT_SIZE ; i++) {
            //draw the lines
            g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
            g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
        }
        g.setColor(Color.red);
        g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);

        //drawing the head and body

        for (int i = 0; i < bodyParts; i++) {
            if (i==0){
                g.setColor(Color.green);
                g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
            }
            else{
                g.setColor((new Color(45,180,0)));
                g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
            }
        }
    }

    //Generate apples randomly
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleX = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }
    //move
    public void move(){
        for (int i = bodyParts; i >0 ; i--) {
            x[i]= x[i-1];
            y[i]= y[i-1];
        }

        switch (direction){
            case 'U':
                y[0]= y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0]= y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0]= x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0]= x[0] + UNIT_SIZE;
                break;
        }
    }

    // points
    public void checkApple(){

    }

    public void checkCollisions(){
        //checks if head collides with bosy
        for (int i = bodyParts; i >0 ; i--) {
            if((x[0]== x[i])&& (y[0]==y[i])){
                running=false;
            }
        }
        //check if head touches left border
        if(x[0]<0){
            running=false;
        }

        //check if head touches right border
        if(x[0]> SCREEN_WIDTH){
            running=false;
        }
        //check if head touches top border
        if(y[0]<0){
            running=false;
        }
        //check if head touches bottom border
        if(x[0]> SCREEN_HEIGHT){
            running=false;
        }
        if(!running){
            timer.stop();
        }
    }

    public void gameOver(Graphics g){

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public class myKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){

            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
