package battleship;

import java.io.*;
import java.awt.*;
import javax.sound.sampled.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;

import static battleship.battleShip.frame1;

public class battleShip extends JFrame implements Runnable {
    static final int WINDOW_WIDTH = 1366;
    static final int WINDOW_HEIGHT = 768;

    final int TOP_BORDER = 160;
    final int YTITLE = 25;
    boolean animateFirstTime = true;
    int xsize = -1;
    int ysize = -1;
    Image image;
    Graphics2D g;

    boolean player1Turn;
    final static int NUM_ROWS = 11;
    final static int NUM_COLUMNS = 10;  
    ship board[][] = new ship[NUM_ROWS][NUM_COLUMNS];

    sound explosion = null;
    sound bgSound = null;
    sound Nuke = null;
    sound sonar = null;
    
    boolean selected;
    boolean P1 = true;
    boolean AIactive = false;
    boolean placingShips;
    int numShips;
    boolean pause;
    int currentRow;
    int currentCol;
    boolean shipPlace1;
    boolean shipPlace2;
    int timecount;
    boolean shipPlace3;
    boolean placeNewShip;
    boolean selectDir;
    int count;
    int lastMap;
    boolean selected2;
    int number;

    int playerWinsNum;
    int computerWinsNum;
    int nukeCount;
    boolean nuke;
    boolean shipActive1;
    boolean shipActive2;
    boolean shipActive3;
    
    boolean aiShipActive1;
    boolean aiShipActive2;
    boolean aiShipActive3;
    
    boolean aiActive;
    boolean illegalPlacement;
    
    boolean nukeClicked;
    
     Image gameBackground;
     Image nukeIcon;
     Image startIcon;    
     Image unHelp;
     Image Help;
     Image unStart;
     Image Start;

     boolean startMenu = true;
     boolean gameOver;

     final int UP = 0;
     final int DOWN = 1;
     final int LEFT = 2;
     final int RIGHT = 3;

     int mouseX; 
     int mouseY;

    int mostRecentRowHit;
    int mostRecentColumnHit;
    boolean firstHit;


    boolean playerWin;
    boolean computerWin;
//    
//    boolean player1Turn = true;
//    boolean player2Turn = false;
//   
//    int player1Score;
//    int player2Score;

//    boolean buyProp;
//    enum buy{
//        YES, NO
//    }
//    buy Prop;


   

static battleShip frame1;
    public static void main(String[] args) {
        frame1 = new battleShip();
        frame1.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setVisible(true);
    }

    public battleShip() {

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

 if (e.BUTTON1 == e.getButton()) {
if(gameOver)
            return;
   if(startMenu)
                    {
                        if (mouseX >484 && mouseX < 840
                            &&mouseY > 540 && mouseY < 642)
                        {
                                startMenu=false;
                                pause=true;
                                
                        }
                         if (mouseX >484 && mouseX < 840
                            &&mouseY > 740 && mouseY < 842)
                        {
                                startMenu=false;
                                return;
                        }
                        
                    }


 if(selectDir){
//Calculate the width and height of each board square.
                    int ydelta = getHeight2()/NUM_ROWS;
                    int xdelta = getWidth2()/NUM_COLUMNS;

                    int xpos = e.getX();
                    int ypos = e.getY();
                    
//Check to make sure we have selected within the space of the board.
                    if (xpos < getX(0) || xpos > getX(getWidth2()) ||
                        ypos < getY(0) || ypos > getY(getHeight2()))
                        return;
                    
//Determine which column has been selected.
                    int zcol = 0;
                    boolean keepGoing = true;
                    while (keepGoing)
                    {
                        if (xpos < getX((zcol+1)*xdelta))
                        {
                            keepGoing = false;
                        }   
                        else
                            zcol++;
                    }
                    int zrow = 0;
                    keepGoing = true;
                    while (keepGoing)
                    {
                        if (ypos < getY((zrow+1)*ydelta))
                        {
                            keepGoing = false;
                        }   
                        else
                            zrow++;
                    }
                if(!pause)
                {    
                  if(placingShips)
                  {
                    if(zcol >=0 && zrow < NUM_ROWS && zrow>5 && zcol< NUM_COLUMNS && board[zrow][zcol]==null)
                    {
                  //  numShips++;
                    
                    if(numShips == 1 && shipPlace1)
                    {
                         board[zrow][zcol] = new ship(1);
                         placeNewShip = false;
                         shipPlace1=false;
                         selectDir=false;
                         currentRow = zrow;
                         currentCol = zcol;
                    }
                    if(numShips == 2 && shipPlace2)
                    {
                        board[zrow][zcol] = new ship(2);
                        placeNewShip = false;
                        shipPlace2=false;
                        selectDir=false;
                        currentRow = zrow;
                        currentCol = zcol;
                    }                    
                     if(numShips == 3 && shipPlace3)
                     {

                         board[zrow][zcol] = new ship(3);
                         placeNewShip = false;
                         shipPlace3=false;
                         selectDir=false;
                         currentRow = zrow;
                         currentCol = zcol;
                     }                    
                    
                     if(numShips >3)
                     {
                        placingShips = false;
                     }
                    }
                  }
                  else if (!placingShips) 
                  {
                    
                      if(nuke && nukeCount==0 && zrow < 5)
                     {
                         nuke = false;
                         nukeCount++;
                         if(board[zrow][zcol] != null && zrow  <5 && zcol >=0 && zrow < NUM_ROWS && zcol< NUM_COLUMNS)
                         {
                             if(board[zrow][zcol].getShipNum() >= 100)
                            {
                              board[zrow][zcol].setColor(Color.red);
                              explosion = new sound("explosion.wav"); 
                              aiActive = true;
                            }      
                         }
                          else if(zrow  <5 && zcol >=0 && zrow < NUM_ROWS && zcol< NUM_COLUMNS)
                          {
                              board[zrow][zcol] = new ship(count);
                              board[zrow][zcol].setColor(Color.WHITE);
                              count++;
                              aiActive = true;
                          }
 /////////////////////////////////////////////////
                         if(board[zrow][zcol+1] != null && zrow  <5 && zcol+1 >=0 && zrow < NUM_ROWS && zcol+1< NUM_COLUMNS)
                         {
                             if(board[zrow][zcol+1].getShipNum() >= 100)
                            {
                              board[zrow][zcol+1].setColor(Color.red);
                              explosion = new sound("explosion.wav"); 
                              aiActive = true;
                            }      
                         }
                          else if(zrow  <5 && zcol+1 >=0 && zrow < NUM_ROWS && zcol+1< NUM_COLUMNS)
                          {
                              board[zrow][zcol+1] = new ship(count);
                              board[zrow][zcol+1].setColor(Color.WHITE);
                              count++;
                              aiActive = true;
                          }
  /////////////////////////////////////////////
                         if(board[zrow+1][zcol] != null && zrow+1<5 && zcol >=0 && zrow+1 < NUM_ROWS && zcol< NUM_COLUMNS)
                         {
                             if(board[zrow+1][zcol].getShipNum() >= 100)
                            {
                              board[zrow+1][zcol].setColor(Color.red);
                              explosion = new sound("explosion.wav"); 
                              aiActive = true;
                            }      
                         }
                          else if(zrow+1  <5 && zcol >=0 && zrow+1 < NUM_ROWS && zcol< NUM_COLUMNS)
                          {
                              board[zrow+1][zcol] = new ship(count);
                              board[zrow+1][zcol].setColor(Color.WHITE);
                              count++;
                              aiActive = true;
                          }
 ///////////////////////////////////////////
                         if(board[zrow-1][zcol] != null && zrow-1  <5 && zcol >=0 && zrow < NUM_ROWS && zcol< NUM_COLUMNS)
                         {
                             if(board[zrow-1][zcol].getShipNum() >= 100)
                            {
                              board[zrow-1][zcol].setColor(Color.red);
                              explosion = new sound("explosion.wav"); 
                              aiActive = true;
                            }      
                         }
                          else if(zrow-1  <5 && zcol >=0 && zrow-1 < NUM_ROWS && zcol< NUM_COLUMNS)
                          {
                              board[zrow-1][zcol] = new ship(count);
                              board[zrow-1][zcol].setColor(Color.WHITE);
                              count++;
                              aiActive = true;
                          }
//////////////////////////////////////////////////////
                         if(board[zrow][zcol-1] != null && zrow  <5 && zcol-1 >=0 && zrow < NUM_ROWS && zcol-1< NUM_COLUMNS)
                         {
                             if(board[zrow][zcol-1].getShipNum() >= 100)
                            {
                              board[zrow][zcol-1].setColor(Color.red);
                              explosion = new sound("explosion.wav"); 
                              aiActive = true;
                            }      
                         }
                          else if(zrow  <5 && zcol-1 >=0 && zrow-1 < NUM_ROWS && zcol-1< NUM_COLUMNS)
                          {
                              board[zrow][zcol-1] = new ship(count);
                              board[zrow][zcol-1].setColor(Color.WHITE);
                              count++;
                              aiActive = true;
                          }
                      Nuke = new sound("Nuke.wav");
                     }
 /////////////////////////////////////////////////////////////  no nuke        

                      if(board[zrow][zcol] != null && board[zrow][zcol].getColor() != Color.red && zrow  <5 && zcol >=0 && zrow < NUM_ROWS && zcol< NUM_COLUMNS)
                      {
                          if(board[zrow][zcol].getShipNum() >= 100)
                          {
                              board[zrow][zcol].setColor(Color.red);
                              explosion = new sound("explosion.wav"); 
                              aiActive = true;
                          }            
                      }
                      else if(board[zrow][zcol] == null && zrow  <5 && zcol >=0 && zrow < NUM_ROWS && zcol< NUM_COLUMNS)
                          {
                              board[zrow][zcol] = new ship(count);
                              board[zrow][zcol].setColor(Color.WHITE);
                              count++;
                              aiActive = true;
                          }
                    if(zrow == 5 && zcol == 0) 
                    {
                        nuke = true;
                    }
                  }
                }
}

                if (e.BUTTON3 == e.getButton()) {
                    //right button
                    reset();
                }
                repaint();
      }
            }
        });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
mouseX=e.getX();
   	mouseY=e.getY();
        repaint();
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {
                    mouseX = e.getX();
                    mouseY = e.getY();
          if(startMenu)
                    {
                        if (mouseX >484 && mouseX < 840
                            &&mouseY > 540 && mouseY < 642)
                        {
                                selected=true;
                               
                        }
                        else
                          selected=false;
                         if (mouseX >484 && mouseX < 840
                            &&mouseY > 740 && mouseY < 842)
                        {
                                selected2=true;
                                
                        }
                        else
                          selected2=false;
                    }

        repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                selectDir=true;
	if (e.VK_P == e.getKeyCode()) 
{
	pause = !pause;
}

if (e.VK_ESCAPE == e.getKeyCode()) 
        {
            reset();
        }
        


                 
     if (e.VK_UP == e.getKeyCode()) 
                 {
                     if(numShips==1 && !placeNewShip)
                     {
                       if(currentRow-1 >=0 && currentCol >=0 && currentRow-1 < NUM_ROWS && currentRow-1>5 && currentCol< NUM_COLUMNS && board[currentRow-1][currentCol] ==null)
                       {
                           board[currentRow-1][currentCol] = new ship(1);
                           numShips++; 
                           shipActive1 = true;
                           shipPlace2 = true;
                           placeNewShip = true;
                           illegalPlacement = false;
                           return;
                       }
                       else
                           illegalPlacement = true;
                     }
                     if(numShips==2 && !placeNewShip)
                     {
                       if(currentRow-2 >=0 && currentCol >=0 && currentRow-2 < NUM_ROWS && currentRow-2>5 && currentCol< NUM_COLUMNS && board[currentRow-2][currentCol] ==null && board[currentRow-1][currentCol] ==null)
                       {
                           board[currentRow-1][currentCol] = new ship(2);
                           board[currentRow-2][currentCol] = new ship(2);
                           numShips++; 
                           shipActive2 = true;
                           shipPlace2 = false;
                           shipPlace3=true;
                           placeNewShip = true;
                           illegalPlacement = false;
                           return;
                       }
                       else
                           illegalPlacement = true;
                     }
                     if(numShips==3 && !placeNewShip)
                     {
                       if(currentRow-3 >=0 && currentCol >=0 && currentRow-3 < NUM_ROWS && currentRow-3>5 && currentCol< NUM_COLUMNS 
                          && board[currentRow-3][currentCol] ==null && board[currentRow-2][currentCol] ==null && board[currentRow-1][currentCol] ==null)
                       {
                           board[currentRow-1][currentCol] = new ship(3);
                           board[currentRow-2][currentCol] = new ship(3);
                           board[currentRow-3][currentCol] = new ship(3);
                           numShips++; 
                           shipPlace3=false;
                           shipActive3 = true;
                           placeNewShip = true;
                           illegalPlacement = false;
                       }
                       else
                           illegalPlacement = true;
                     }
                     if(numShips >3)
                     {
                         placingShips = false;
                     }
                         
                 }
     ////////////////////////////////////////////////////////////////////
                 else if (e.VK_DOWN == e.getKeyCode()) 
                 { 
                     if(numShips==1 && !placeNewShip)
                     {
                       if(currentRow+1 >=0 && currentCol >=0 && currentRow+1 < NUM_ROWS && currentRow+1>5 && currentCol< NUM_COLUMNS && board[currentRow+1][currentCol] ==null)
                       {
                           board[currentRow+1][currentCol] = new ship(1);
                           numShips++; 
                           shipActive1 = true;
                           shipPlace2 = true;
                           placeNewShip = true;
                           illegalPlacement = false;
                           return;
                       }
                       else
                           illegalPlacement = true;
                     }
                     if(numShips==2 && !placeNewShip)
                     {
                       if(currentRow+2 >=0 && currentCol >=0 && currentRow+2 < NUM_ROWS && currentRow+2>5 && currentCol< NUM_COLUMNS && board[currentRow+2][currentCol] ==null  && board[currentRow+1][currentCol] ==null)
                       {
                           board[currentRow+1][currentCol] = new ship(2);
                           board[currentRow+2][currentCol] = new ship(2);
                           numShips++; 
                           shipActive2 = true;
                           shipPlace2 = false;
                           shipPlace3=true;
                           placeNewShip = true;
                           illegalPlacement = false;
                           return;
                       }
                       else
                           illegalPlacement = true;
                     }
                     if(numShips==3 && !placeNewShip)
                     {
                       if(currentRow+3 >=0 && currentCol >=0 && currentRow+3 < NUM_ROWS && currentRow+3>5 && currentCol< NUM_COLUMNS 
                         && board[currentRow+3][currentCol] ==null && board[currentRow+2][currentCol] ==null && board[currentRow+1][currentCol] ==null)
                       {
                           board[currentRow+1][currentCol] = new ship(3);
                           board[currentRow+2][currentCol] = new ship(3);
                           board[currentRow+3][currentCol] = new ship(3);
                           numShips++; 
                           shipPlace3=false;
                           shipActive3 = true;
                           placeNewShip = true;
                           illegalPlacement = false;
                       }
                        else
                           illegalPlacement = true;
                     }
                     if(numShips >3)
                     {
                         placingShips = false;
                     }
                 }  
////////////////////////////////////////////////////////////////////////////////////////////////                 
                 else if (e.VK_LEFT == e.getKeyCode()) 
                 {  
                     if(numShips==1 && !placeNewShip)
                     {
                       if(currentRow >=0 && currentCol-1 >=0 && currentRow < NUM_ROWS && currentRow>5 && currentCol-1< NUM_COLUMNS&& board[currentRow][currentCol-1] ==null)
                       {
                           board[currentRow][currentCol-1] = new ship(1);
                           numShips++; 
                           shipActive1 = true;
                           shipPlace2 = true;
                           placeNewShip = true;
                           illegalPlacement = false;
                           return;
                       }
                       else
                           illegalPlacement = true;
                     }
                     if(numShips==2 && !placeNewShip)
                     {
                       if(currentRow >=0 && currentCol-2 >=0 && currentRow < NUM_ROWS && currentRow>5 && currentCol-2< NUM_COLUMNS && board[currentRow][currentCol-2] ==null && board[currentRow][currentCol-1] ==null)
                       {
                           board[currentRow][currentCol-1] = new ship(2);
                           board[currentRow][currentCol-2] = new ship(2);
                           numShips++; 
                           shipActive2 = true;
                           shipPlace2 = false;
                           shipPlace3=true;
                           placeNewShip = true;
                           illegalPlacement = false;
                           return;
                       }
                       else
                           illegalPlacement = true;
                     }
                     if(numShips==3 && !placeNewShip)
                     {
                       if(currentRow >=0 && currentCol-3 >=0 && currentRow < NUM_ROWS && currentRow>5 && currentCol-3< NUM_COLUMNS 
                        && board[currentRow][currentCol-3] ==null && board[currentRow][currentCol-2] ==null && board[currentRow][currentCol-1] ==null)
                       {
                           board[currentRow][currentCol-1] = new ship(3);
                           board[currentRow][currentCol-2] = new ship(3);
                           board[currentRow][currentCol-3] = new ship(3);
                           numShips++; 
                           shipPlace3=false;
                           shipActive3 = true;
                           placeNewShip = true;
                           illegalPlacement = false;
                       }
                        else
                           illegalPlacement = true;
                     }
                     if(numShips >3)
                     {
                         placingShips = false;
                     }
                 
                 } 
 //////////////////////////////////////////////////////////////////////////////////////////////////                
                 else if (e.VK_RIGHT == e.getKeyCode()) 
                 {
                   if(numShips==1 && !placeNewShip)
                     {
                       if(currentRow >=0 && currentCol+1 >=0 && currentRow < NUM_ROWS && currentRow>5 && currentCol+1< NUM_COLUMNS && board[currentRow][currentCol+1] ==null)
                       {
                           board[currentRow][currentCol+1] = new ship(1);
                           numShips++; 
                           shipActive1 = true;
                           shipPlace2 = true;
                           placeNewShip = true;
                           illegalPlacement = false;
                           return;
                       }
                       else
                           illegalPlacement = true;
                     }
                     if(numShips==2 && !placeNewShip)
                     {
                       if(currentRow >=0 && currentCol+2 >=0 && currentRow < NUM_ROWS && currentRow>5 && currentCol+2< NUM_COLUMNS && board[currentRow][currentCol+2] ==null && board[currentRow][currentCol+1] ==null)
                       {
                           board[currentRow][currentCol+1] = new ship(2);
                           board[currentRow][currentCol+2] = new ship(2);
                           numShips++; 
                           shipActive2 = true;
                           shipPlace2 = false;
                           shipPlace3=true;
                           placeNewShip = true;
                           illegalPlacement = false;
                           return;
                       }
                       else
                           illegalPlacement = true;
                     }
                     if(numShips==3 && !placeNewShip)
                     {
                       if(currentRow >=0 && currentCol+3 >=0 && currentRow < NUM_ROWS && currentRow>5 && currentCol+3< NUM_COLUMNS 
                               && board[currentRow][currentCol+3] ==null && board[currentRow][currentCol+2]==null && board[currentRow][currentCol+1]==null)
                       {
                           board[currentRow][currentCol+1] = new ship(3);
                           board[currentRow][currentCol+2] = new ship(3);
                           board[currentRow][currentCol+3] = new ship(3);
                           numShips++; 
                           shipPlace3=false;
                           shipActive3 = true;
                           placeNewShip = true;
                           illegalPlacement = false;
                       }
                        else
                           illegalPlacement = true;
                     }
                     if(numShips >3)
                     {
                         placingShips = false;
                     }
                 }

                    
                
                repaint();
            }


        });
        init();
        start();
    }




    Thread relaxer;
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }
    public void paint(Graphics gOld) {

        if (image == null || xsize != getSize().width || ysize != getSize().height) {
            xsize = getSize().width;
            ysize = getSize().height;
            image = createImage(xsize, ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
//fill background
        
        g.setColor(Color.black);
        g.fillRect(0, 0, xsize, ysize);

        int x[] = {getX(0), getX(getWidth2()), getX(getWidth2()), getX(0), getX(0)};
        int y[] = {getY(0), getY(0), getY(getHeight2()), getY(getHeight2()), getY(0)};
//fill border
        g.setColor(Color.white);
        g.fillPolygon(x, y, 4);
// draw border
        g.setColor(Color.red);
        g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }
        

	if(pause)
        {
            g.setFont(new Font("Impact",Font.BOLD,75) );
            g.setColor(Color.white);
            g.drawString("Welcome to Battleship!",getX(180),getY(0));
             g.setColor(Color.black);
            g.setFont(new Font("Impact",Font.BOLD,55) );
            g.drawString("Rules and How to Play:",getX(200),getY(50));
            g.setFont(new Font("Impact",Font.PLAIN,35) );
            g.drawString("Click on the bottom board to place your three ships",getX(215),getY(100));
            g.drawString("Use the arrow keys to choose a direction for each ship",getX(215),getY(150));
            g.drawString("After placing your ships, click on the top screen to attack",getX(215),getY(200));
            g.drawString("If you hit a ship, a red circle will appear; if you miss, a white will appear",getX(215),getY(250));
            g.drawString("To use a nuke click on the nuke icon in the middle of the two boards",getX(215),getY(300));
            g.drawString("Tip: You can see which ships remain at the top right of the game screen",getX(215),getY(350));
            g.drawString("'Esc' to reset the game",getX(215),getY(400));
            g.drawString("Press 'P' to return to the game",getX(215),getY(450));
            
                 
            gOld.drawImage(image, 0, 0, null);
            return;
        }



//Calculate the width and height of each board square.
        int ydelta = getHeight2()/NUM_ROWS;
        int xdelta = (getWidth2()/NUM_COLUMNS);
        
        for (int zi = 0;zi<NUM_ROWS;zi++)
        {
            for (int zx = 0;zx<NUM_COLUMNS;zx++)
            {
                    g.setColor(Color.blue);
                    g.fillRect(getX(zx*xdelta), getY(zi*ydelta), xdelta, ydelta);            
            }
        }
        
        
 
        

//Draw the piece.        
        for (int zi = 0;zi<NUM_ROWS;zi++)
        {
            for (int zx = 0;zx<NUM_COLUMNS;zx++)
            {
                if (board[zi][zx] != null && zi > 5)
                {
                  g.setColor(board[zi][zx].getColor());
                    g.fillRect(getX(zx*xdelta), getY(zi*ydelta), xdelta, ydelta);
                    if(board[zi][zx].getShipNum()<=3)
                    {
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("IMPACT",Font.PLAIN,25) );
                    g.drawString(""+board[zi][zx].getShipNum(),getX(zx*xdelta+50),getY(zi*ydelta+50));
                    }

                }
               

            }
        }
         g.setFont(new Font("Lucidia Handwriting",Font.PLAIN,12) );
        for (int zi = 0;zi<NUM_ROWS;zi++)
        {
            for (int zx = 0;zx<NUM_COLUMNS;zx++)
            {
                if (board[zi][zx] != null && zi < 5)
                {
                    
                    g.setColor(board[zi][zx].getColor());
                    g.fillOval(getX(zx*xdelta), getY(zi*ydelta), xdelta, ydelta);
                }
               

            }
        }
        g.setColor(Color.black);
        for (int index = NUM_ROWS; index>=0;index--)
        {
            g.fillRect(getX(index*xdelta), getY(5*ydelta), xdelta, ydelta);
        }    
        

       //draw grid
        g.setColor(Color.black);
        for (int zi = 1;zi<NUM_ROWS;zi++)
        {
            g.drawLine(getX(0),getY(zi*ydelta),
                    getX(getWidth2()),getY(zi*ydelta));
        }
        
        for (int zi = 1;zi<NUM_COLUMNS;zi++)
        {
            g.drawLine(getX(zi*xdelta),getY(0),
                    getX(zi*xdelta),getY(getHeight2()));
        }

//draw nuke icon
        //draw background picture
          g.drawImage(gameBackground,getX(0),getY(0)-TOP_BORDER,this);
        
        
        
        
        g.setFont(new Font("QUARTZ MS",Font.PLAIN,15) );
        g.setColor(Color.white);
        g.drawString("Number of Player Wins: " + playerWinsNum,650,180);
        g.drawString("Number of Computer Wins: " + computerWinsNum,900,180);
        g.drawString("Player Ships Remaining:",800,40);
        if(shipActive1)
        {
            g.setColor(Color.white);
            g.drawString("Ship 1",800,55);
            g.setColor(Color.GRAY);
            g.fillRect(800, 60, 15,15 );
            g.fillRect(820, 60, 15,15 );
        }
        if(shipActive2)
        {
            g.setColor(Color.white);
            g.drawString("Ship 2",800,95);
            g.setColor(Color.GRAY);
            g.fillRect(800, 100, 15,15 );
            g.fillRect(820, 100, 15,15 );
            g.fillRect(840, 100, 15,15 );
        }
        if(shipActive3)
        {
            g.setColor(Color.white);
            g.drawString("Ship 3",800,135);
            g.setColor(Color.GRAY);
            g.fillRect(800, 140, 15,15 );
            g.fillRect(820, 140, 15,15 );
            g.fillRect(840, 140, 15,15 );
            g.fillRect(860, 140, 15,15 );
        }
        
        g.setColor(Color.white);
        g.drawString("Computer Ships Remaining:",1000,40);
        
        if(aiShipActive1)
        {
            g.setColor(Color.white);
            g.drawString("Ship 1",1000,55);
            g.setColor(Color.GRAY);
            g.fillRect(1000, 60, 15,15 );
            g.fillRect(1020, 60, 15,15 );
        }
        if(aiShipActive2)
        {
            g.setColor(Color.white);
            g.drawString("Ship 2",1000,95);
            g.setColor(Color.GRAY);
            g.fillRect(1000, 100, 15,15 );
            g.fillRect(1020, 100, 15,15 );
            g.fillRect(1040, 100, 15,15 );
        }
        if(aiShipActive3)
        {
            g.setColor(Color.white);
            g.drawString("Ship 3",1000,135);
            g.setColor(Color.GRAY);
            g.fillRect(1000, 140, 15,15 );
            g.fillRect(1020, 140, 15,15 );
            g.fillRect(1040, 140, 15,15 );
            g.fillRect(1060, 140, 15,15 );
        }
       if (startMenu)
        {
            g.setColor(Color.black);

            g.fillRect(getX(0), 0, 15000, 11000);

            g.drawImage(startIcon,getX(0),0,this);
           if (selected != true)
           {
            g.drawImage(unHelp,getX(WINDOW_WIDTH/2 - 115), getY(WINDOW_HEIGHT/2 - 165),this);
           }
            if (selected == true)
            {
               g.drawImage(Help,getX(WINDOW_WIDTH/2 - 115), getY(WINDOW_HEIGHT/2 - 165),this);  
            }
            if(!selected2)
            g.drawImage(unStart,getX(WINDOW_WIDTH/2 - 115), getY(WINDOW_HEIGHT/2 + 35),  this);
            else if(selected2)
            g.drawImage(Start,getX(WINDOW_WIDTH/2 - 115), getY(WINDOW_HEIGHT/2 + 35),  this); 
            
           gOld.drawImage(image, 0, 0, null);
           return;
        }      


        g.setColor(Color.white);
        g.drawString("press 'P' to pause",getX(610),40);
        g.drawString("and access help menu",getX(610),60);
        
      if(illegalPlacement)
      { 
        g.setColor(Color.red);
        g.setFont(new Font("Lucidia Handwriting",Font.PLAIN,55) );
        g.drawString("ILLEGAL PLACEMET",getX(220),640);
      }
      g.setFont(new Font("Lucidia Handwriting",Font.PLAIN,12) );
        
 if(gameOver)
        {
            for (int zi = 0;zi<NUM_ROWS;zi++)
            {
                for (int zx = 0;zx<NUM_COLUMNS;zx++)
                {
                    if(board[zi][zx] != null && board[zi][zx].getColor() != Color.red &&        board[zi][zx].getShipNum() >= 100)
                    {
                        board[zi][zx].setColor(Color.gray);
                    }
                }
            }

            if (playerWin)
        {
            Color wingreen = new Color (10, 148, 13);
            g.setColor(wingreen);
            g.setColor(Color.GREEN);
            g.setFont(new Font("QUARTZ MS",Font.PLAIN,285) );
            g.drawString("VICTORY",25,650);
        }
        
        if (computerWin)
        {
            Color losered = new Color (198, 0, 0);
            g.setColor(losered);
            g.setFont(new Font("QUARTZ MS",Font.PLAIN,285) );
            g.drawString("DEFEAT",75,650);
        }
        }
        g.setFont(new Font("Lucidia Handwriting",Font.PLAIN,12) );
	
        
      if(nukeCount == 0)    
      g.drawImage(nukeIcon,getX(25),573,this);



gOld.drawImage(image, 0, 0, null);
    }

////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = 0.1;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
/////////////////////////////////////////////////////////////////////////
    public void reset() {
        if(playerWin)
            playerWinsNum++;
        else if(computerWin)
            computerWinsNum++;
        
         selected =false;
         selected2 = false;
         placingShips=true;
         shipPlace1= true;
         shipPlace2= false;
         shipPlace3= false;
         selected =false;
         placeNewShip= true;
         numShips=1;
         selectDir= true;
         pause = false;
         count = 5;
         timecount =0;
         illegalPlacement = false;
         nuke= false;
         nukeCount=0;
         
         shipActive1 = false;
         shipActive2 = false;
         shipActive3 = false;
         
         aiShipActive1 = true;
         aiShipActive2 = true;
         aiShipActive3 = true;
         aiActive = false;
         
         playerWin = false;
         computerWin = false;

         gameOver = false;
         mostRecentRowHit = 0;
         mostRecentColumnHit = 0;
         firstHit = true;

        
         for (int zi = 0;zi<NUM_ROWS;zi++)
         {
             for (int zx = 0;zx<NUM_COLUMNS;zx++)
             {
                 board[zi][zx] = null;
             }
         }
///// drawing ai ships
         
     int temprow;
         int tempcol;
         boolean loop = true;
         int tempShipNum = 0;
         int UP = 0;
         int DOWN = 1;
         int LEFT = 2;
         int RIGHT = 3;
         while (loop)
         {
              
                  if(tempShipNum == 0)
                  {
                      boolean loop2 = true;
                      while(loop2)
                      {
                          temprow = (int) (Math.random() * NUM_ROWS);
                          tempcol = (int) (Math.random() * NUM_COLUMNS);
                          int random = (int) (Math.random() * 4);
                            if(temprow < 5)
                            {
                                if (random == LEFT)
                                {
                                    if(tempcol != 0)
                                    {
                                        board[temprow][tempcol] = new ship(100);
                                        board[temprow][tempcol-1] = new ship(100);
                                        loop2 = false;
                                        tempShipNum++;
                                    }
                                }
                                if (random == RIGHT)
                                {
                                    if(tempcol != NUM_COLUMNS && tempcol != 9)                        
                                    {
                                        board[temprow][tempcol] = new ship(100);
                                        board[temprow][tempcol+1] = new ship(100);
                                        loop2 = false;
                                        tempShipNum++;
                                    }
                                }
                                if (random == UP)
                                {
                                    if(temprow != 0)
                                    {
                                        board[temprow][tempcol] = new ship(100);
                                        board[temprow-1][tempcol] = new ship(100);
                                        loop2 = false;
                                        tempShipNum++;
                                    }
                                }
                                if (random == DOWN)
                                {
                                    if(temprow < 4)
                                    {
                                        board[temprow][tempcol] = new ship(100);
                                        board[temprow+1][tempcol] = new ship(100);
                                        loop2 = false;
                                        tempShipNum++;
                                    }
                                }
                            }
                      }
                      
                      
                  }
                  
                  if(tempShipNum == 1)
                  {
                      boolean loop2 = true;
                      while(loop2)
                      {
                            temprow = (int) (Math.random() * NUM_ROWS);
                            tempcol = (int) (Math.random() * NUM_COLUMNS);
                            int random = (int) (Math.random() * 4);
                          if(temprow < 5)
                          {
                              if (random == LEFT)
                              {
                                  if(tempcol > 1 && board[temprow][tempcol] == null &&  board[temprow][tempcol-1] == null && board[temprow][tempcol-2] == null)
                                  {
                                      board[temprow][tempcol] = new ship(101);
                                      board[temprow][tempcol-1] = new ship(101);
                                      board[temprow][tempcol-2] = new ship(101);
                                      loop2 = false;
                                      tempShipNum++;
                                  }
                              }
                              if (random == RIGHT)
                              {
                                  if(tempcol < 8 && board[temprow][tempcol] == null &&  board[temprow][tempcol+1] == null && board[temprow][tempcol+2] == null)                        
                                  {
                                      board[temprow][tempcol] = new ship(101);
                                      board[temprow][tempcol+1] = new ship(101);
                                      board[temprow][tempcol+2] = new ship(101);
                                      loop2 = false;
                                      tempShipNum++;
                                  }
                              }
                              if (random == UP)
                              {
                                  if(temprow > 1 && board[temprow][tempcol] == null &&  board[temprow-1][tempcol] == null && board[temprow-2][tempcol] == null)
                                  {
                                      board[temprow][tempcol] = new ship(101);
                                      board[temprow-1][tempcol] = new ship(101);
                                      board[temprow-2][tempcol] = new ship(101);
                                      loop2 = false;
                                      tempShipNum++;
                                  }
                              }
                              if (random == DOWN)
                              {
                                  if(temprow < 3 && board[temprow][tempcol] == null &&  board[temprow+2][tempcol] == null && board[temprow+2][tempcol] == null)
                                  {
                                      board[temprow][tempcol] = new ship(101);
                                      board[temprow+1][tempcol] = new ship(101);
                                      board[temprow+2][tempcol] = new ship(101);
                                      loop2 = false;
                                      tempShipNum++;
                                  }
                              }
                          }
                      }
                  }
                  
                  if(tempShipNum == 2)
                  {
                      boolean loop2 = true;
                      while(loop2)
                      {
                            temprow = (int) (Math.random() * NUM_ROWS);
                            tempcol = (int) (Math.random() * NUM_COLUMNS);
                            int random = (int) (Math.random() * 4);
                          if(temprow < 5)
                          {
                              if (random == LEFT)
                              {
                                  if(tempcol > 2 && board[temprow][tempcol] == null && board[temprow][tempcol-1] == null && board[temprow][tempcol-2] == null && board[temprow][tempcol-3] == null)
                                  {
                                      board[temprow][tempcol] = new ship(102);
                                      board[temprow][tempcol-1] = new ship(102);
                                      board[temprow][tempcol-2] = new ship(102);
                                      board[temprow][tempcol-3] = new ship(102);
                                      loop2 = false;
                                  }
                              }
                              if (random == RIGHT)
                              {
                                  if(tempcol < 7 && board[temprow][tempcol] == null && board[temprow][tempcol+1] == null && board[temprow][tempcol+2] == null && board[temprow][tempcol+3] == null)                        
                                  {
                                      board[temprow][tempcol] = new ship(102);
                                      board[temprow][tempcol+1] = new ship(102);
                                      board[temprow][tempcol+2] = new ship(102);
                                      board[temprow][tempcol+3] = new ship(102);
                                      loop2 = false;
                                  }
                              }
                              if (random == UP)
                              {
                                  if(temprow > 2 && board[temprow][tempcol] == null &&  board[temprow-1][tempcol] == null && board[temprow-2][tempcol] == null && board[temprow-3][tempcol] == null)
                                  {
                                      board[temprow][tempcol] = new ship(102);
                                      board[temprow-1][tempcol] = new ship(102);
                                      board[temprow-2][tempcol] = new ship(102);
                                      board[temprow-3][tempcol] = new ship(102);
                                      loop2 = false;
                                  }
                              }
                              if (random == DOWN)
                              {
                                  if(temprow < 2 && board[temprow][tempcol] == null &&  board[temprow+1][tempcol] == null && board[temprow+2][tempcol] == null && board[temprow+3][tempcol] == null)
                                  {
                                      board[temprow][tempcol] = new ship(102);
                                      board[temprow+1][tempcol] = new ship(102);
                                      board[temprow+2][tempcol] = new ship(102);
                                      board[temprow+3][tempcol] = new ship(102);
                                      loop2 = false;
                                  }
                              }
                          }
                      }
                  }
                  loop = false;
                  
              
         }


    }
    
/////////////////////////////////////////////////////////////////////////
    public void animate() {

        if (animateFirstTime) {
            animateFirstTime = false;
            if (xsize != getSize().width || ysize != getSize().height) {
                xsize = getSize().width;
                ysize = getSize().height;
            }
             gameBackground = Toolkit.getDefaultToolkit().getImage("./perhaps.jpg");
            startIcon= Toolkit.getDefaultToolkit().getImage("./battleshiplogo.png");
            nukeIcon = Toolkit.getDefaultToolkit().getImage("./nukeIcon.jpg");
            unHelp = Toolkit.getDefaultToolkit().getImage("./unHelp.png");
            Help= Toolkit.getDefaultToolkit().getImage("./Help.png");
            Start = Toolkit.getDefaultToolkit().getImage("./Start.png");
            unStart = Toolkit.getDefaultToolkit().getImage("./unStart.png");

            reset();
   
        }
if(number == 0 &&!pause && !startMenu)
        {
            sonar = new sound("sonar.wav");
            number++;
        }   


        if (!pause && !startMenu && sonar.donePlaying)
            {
        	if (timecount% 100 == 99)
                {
                sonar = new sound("sonar.wav"); 
        	}
            }
                if(!shipActive1 && !shipActive2 && !shipActive3 && !placingShips)
                {
                    gameOver = true;
                    computerWin = true;
                }
                if(!aiShipActive1 && !aiShipActive2 && !aiShipActive3)
                {
                    gameOver = true;
                    playerWin = true;
                }

if (gameOver)
                {
                    return;
                }
        
/*enemy hit*/   for(int shipNum = 0; shipNum  <= 2;shipNum++)
                {
                    int hits = 0;
                    for (int zi = 0;zi<NUM_ROWS;zi++)
                     {
                         for (int zx = 0;zx<NUM_COLUMNS;zx++)
                         {
                             if(board[zi][zx] != null && board[zi][zx].getColor() == Color.red && board[zi][zx].getColor() != Color.white && board[zi][zx].getShipNum() == 100+shipNum)
                             {
                                 hits++;
                                 if (shipNum == 0 && hits == 2)
                                 {
                                     aiShipActive1 = false;
                                 }
                                 else if(shipNum == 1 && hits == 3)
                                 {
                                     aiShipActive2 = false;
                                 }
                                 else if(shipNum == 2 && hits == 4)
                                 {
                                     aiShipActive3 = false;
                                 }
                             }
                         }
                     }
                }
        
/*player hit*/  for(int shipNum = 1; shipNum  <= 3;shipNum++)
                {
                    int hits = 0;
                    for (int zi = 0;zi<NUM_ROWS;zi++)
                     {
                         for (int zx = 0;zx<NUM_COLUMNS;zx++)
                         {
                             if(board[zi][zx] != null && board[zi][zx].getColor() == Color.red && board[zi][zx].getColor() != Color.white && board[zi][zx].getShipNum() == shipNum)
                             {
                                 hits++;
                                 if (shipNum == 1 && hits == 2)
                                 {
                                     shipActive1 = false;
                                 }
                                 else if(shipNum == 2 && hits == 3)
                                 {
                                     shipActive2 = false;
                                 }
                                 else if(shipNum == 3 && hits == 4)
                                 {
                                     shipActive3 = false;
                                 }
                             }
                         }
                     }
                }


                if (aiActive/* && canMove*/)
                {
                    if(!firstHit)
                    {
if(!aiShipActive1 && !aiShipActive2 && !aiShipActive3)
                            return;
                        
if(board[mostRecentRowHit][mostRecentColumnHit].getShipNum() == 1 && shipActive1)
                        {
                            for (int zi = 0;zi<NUM_ROWS;zi++)
                            {
                                for (int zx = 0;zx<NUM_COLUMNS;zx++)
                                {
                                    if(board[zi][zx] != null && board[zi][zx].getShipNum() ==  1 && board[zi][zx].getColor() != Color.red)
                                    {
                                        board[zi][zx].setColor(Color.red);
                                        explosion = new sound("explosion.wav"); 
                                        aiActive = false;
                                        return;
                                    }
                                }
                            }
                        }

                        else if(board[mostRecentRowHit][mostRecentColumnHit].getShipNum() == 2 && shipActive2)
                        {

                            for (int zi = 0;zi<NUM_ROWS;zi++)
                            {
                                for (int zx = 0;zx<NUM_COLUMNS;zx++)
                                {
                                    if(board[zi][zx] != null && board[zi][zx].getShipNum() ==  2 && board[zi][zx].getColor() != Color.red)
                                    {
                                        board[zi][zx].setColor(Color.red);
                                        explosion = new sound("explosion.wav"); 
                                        aiActive = false;
                                        return;
                                    }
                                }
                            }
                        
                        }
                        
                        else if(board[mostRecentRowHit][mostRecentColumnHit].getShipNum() == 3 && shipActive3)
                        {

                            for (int zi = 0;zi<NUM_ROWS;zi++)
                            {
                                for (int zx = 0;zx<NUM_COLUMNS;zx++)
                                {
                                    if(board[zi][zx] != null && board[zi][zx].getShipNum() ==  3 && board[zi][zx].getColor() != Color.red)
                                    {
                                        board[zi][zx].setColor(Color.red);
                                        explosion = new sound("explosion.wav"); 
                                        aiActive = false;
                                        return;
                                    }
                                }
                            }
                        
                        }   
                    }           
                    
                    
                    int row;
                    int col;
                    boolean loop = true;
                    while(loop)
                    {
                        row = (int) (Math.random() * NUM_ROWS);
                        col = (int) (Math.random() * NUM_COLUMNS);
                        if(row > 5 && col < NUM_COLUMNS)
                        {
                           if(board[row][col] != null && board[row][col].getColor() != Color.white && board[row][col].getColor() != Color.red)
                           {
                               board[row][col].setColor(Color.red);
                               aiActive = false;
//                               canMove = false;
                               loop = false;
                               mostRecentRowHit = row;
                               mostRecentColumnHit = col;
                               explosion = new sound("explosion.wav"); 
                               
                               if(firstHit)
                                   firstHit = false;
                           }
                           else if(board[row][col] == null)
                           {
                               board[row][col] = new ship(count);
                               aiActive = false;
//                               canMove = false;
                               loop = false;

                           }
                            
                        }
                    }
                    
                    
                    
                }

                


                 timecount++;



    }
////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }
/////////////////////////////////////////////////////////////////////////
    public int getX(int x) {
        return (x);
    }
    public int getY(int y) {
        return (y + TOP_BORDER + YTITLE);
    }
    public int getWidth2() {
        return (xsize - getX(0));
    }
    public int getHeight2() {
        return (ysize - getY(0));
    }
}

class sound implements Runnable {
	Thread myThread;
	File soundFile;
	public boolean donePlaying = false;
	sound(String _name)
	{
    	soundFile = new File(_name);
    	myThread = new Thread(this);
    	myThread.start();
	}
	public void run()
	{
    	try {
    	AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
    	AudioFormat format = ais.getFormat();
	//	System.out.println("Format: " + format);
    	DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
    	SourceDataLine source = (SourceDataLine) AudioSystem.getLine(info);
    	source.open(format);
    	source.start();
    	int read = 0;
    	byte[] audioData = new byte[16384];
    	while (read > -1){
        	read = ais.read(audioData,0,audioData.length);
        	if (read >= 0) {
            	source.write(audioData,0,read);
        	}
    	}
    	donePlaying = true;
    	source.drain();
    	source.close();
    	}
    	catch (Exception exc) {
        	System.out.println("error: " + exc.getMessage());
        	exc.printStackTrace();
    	}
	}
}





