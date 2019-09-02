package ui;


import game.Game;
import board.Constants;

import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
//ohter listeners
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
//mouse listeners
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;


public class GameUI extends JPanel implements Constants{

    private static final long serialVersionUID = 1L;
    private Game game;

    //mouse memory
    private int mouse_cell_x_index = -1;
    private int mouse_cell_y_index = -1;
    private boolean mouseUp = true;

    //graphics memory
    private int num_columns;
    private int num_rows;
    private int cell_size = 15; //TODO: replace with image error arrays (or algorithm) for each axis
    private int image_size; //stores the original image sizes, used when resizing images
    private final int num_images = 15; //might change idk prob not tho
    private Image[] img;  //https://stackoverflow.com/questions/5895829/resizing-image-in-java

    int x_offset = 0; //TODO: MAKE THIS WORK GOOD.
        //x_offset needs to be included in all drawImage x values and for calculating the boundaries of tiles, and needs to be calculated based
        //off of the screen size and board size.

    public GameUI(Game game_){

        //set local vars
        this.game = game_;
        num_rows = game.getRows();
        num_columns = game.getColumns();

        //load images 
        img = new Image[num_images];
        for (int i = 0; i < num_images; i++) {
            String path = "src/main/assets/" + i + ".png"; //todo: have images be optionally user defined 
            img[i] = (new ImageIcon(path)).getImage();
        }
        //define image size
        image_size = img[0].getHeight(this);//assumes all images are square and are the same size. TODO: write a check for this, if check fails, warn the user and use default textures

        //create transparent image --https://docs.oracle.com/javase/tutorial/2d/images/drawimage.html

        //set default window size
        //setPreferredSize(new Dimension( num_columns * image_size, num_rows * image_size)); //TODO: reexamine when status bar(s) are added

        //bind mouse click listener
        addMouseListener(new MinesweeperMouseHandler());

        //bind mouse motion listener
        addMouseMotionListener(new MinesweeperMouseMotionHandler());

        //bind resize listener
        addComponentListener(new ResizeHandler());
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=Render Logic=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Override  
    public void paint(Graphics g) {
        super.paintComponent(g);
    
        //draw background
        g.setColor(new Color(185, 184, 185));
        g.fillRect(0,0, g.getClipBounds().width, g.getClipBounds().height);
        
        
        //TODO:logic needed for determining how to draw based on game state & cell contents
        
        for (int i = 0; i < num_columns; i++) {
            for (int j = 0; j < num_rows; j++) {
                int x1 = (i * cell_size) + x_offset;
                int y1 = (j * cell_size);
                int x2 = ((i + 1) * cell_size) + x_offset;
                int y2 = ((j + 1) * cell_size);


                
                //logic is good enough for now, need to redo for flaggs
                if(!game.getCell(i,j).revealed){  
                    g.drawImage(img[COVER], 
                    x1, y1, x2, y2,               //destination x1,y1,x2,y2
                    0, 0, image_size, image_size, //src x1,y1,x2,y2
                    null); //can also be 'this'
                }
                else{
                    g.drawImage(img[game.getCell(i,j).value], 
                    x1, y1, x2, y2,               //destination x1,y1,x2,y2
                    0, 0, image_size, image_size, //src x1,y1,x2,y2
                    null); //can also be 'this'
                }

                switch(game.getState()){
                    case Pregame:
                        //in the future, allow for flags?
        
                        //display unrevealed as unrevealed
                    case InProgress:
        
                        //display unrevealed numbers as unrevealed
        
                        //display revealed numbers as numbers
        
                        //display unrevealed mines as unrevealed
                        break;
                    case Won:
                        //display revealed numbers as numbers
        
                        //display unrevealed mines as revealed
                        break;
                    case Lost:
                        //display revealed mine as false hit mine
        
                        //display incorrect flags
        
                        //display unrevealed mines as revealed
                        if(game.getCell(i,j).value == MINE){
                            g.drawImage(img[MINE], 
                            x1, y1, x2, y2,               //destination x1,y1,x2,y2
                            0, 0, image_size, image_size, //src x1,y1,x2,y2
                            null); //can also be 'this'
                        }
        
                        //display unrevealed numbers as unrevealed
        
                        //display revealed numbers as numbers
                        break;
                    default:
                        //unused case, just display place value for now

                        int place_index = 0;
                        switch(game.getCell(i,j).place){
                            case Center:
                                place_index = 0;
                                break;
                            case North:
                                place_index = 1;
                                break;
                            case NorthEast:
                                place_index = 2;
                                break;
                            case East:
                                place_index = 3;
                                break;
                            case SouthEast:
                                place_index = 4;
                                break;
                            case South:
                                place_index = 5;
                                break;
                            case SouthWest:
                                place_index = 6;
                                break;
                            case West:
                                place_index = 7;
                                break;
                            case NorthWest:
                                place_index = 8;
                                break;
                        }
                
                        g.drawImage(img[place_index], 
                        x1, y1, x2, y2,               //destination x1,y1,x2,y2
                        0, 0, image_size, image_size, //src x1,y1,x2,y2
                        null); //can also be 'this'
                        }
            }
        }
        //draw image(s) on mouse coordinate

        //if mouse oob then do nothing
        if(mouse_cell_x_index == -1 || mouse_cell_y_index == -1) return;
        int px1 = (mouse_cell_x_index * cell_size) + x_offset;
        int py1 = (mouse_cell_y_index * cell_size);
        int px2 = ((mouse_cell_x_index + 1) * cell_size) + x_offset;
        int py2 = ((mouse_cell_y_index + 1) * cell_size);


        //if mouse down & covered then draw '0' tile over tile
        if(!mouseUp && !game.getCell(mouse_cell_x_index, mouse_cell_y_index).revealed){
            g.drawImage(img[0], 
            px1, py1, px2, py2,
            0, 0, image_size, image_size,
            null);
            return;
        }
        //if covered then draw highlight over tile
        if(!game.getCell(mouse_cell_x_index, mouse_cell_y_index).revealed){
            g.drawImage(img[HIGHLIGHT], 
            px1, py1, px2, py2,
            0, 0, image_size, image_size,
            null);
        }
        //if not covered then do nothing
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=Mouse Handling=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private class MinesweeperMouseHandler extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e){
            int x =  (e.getX() - x_offset) / cell_size; // I might not need this if its kept track of by the graphics..?
            int y =  e.getY() / cell_size;

            mouseUp = true;
            
            if(x >= num_columns || y >= num_rows || e.getX() - x_offset < 0){
                //System.out.println("mouse released oob");
                repaint();
                return;
            }
            //System.out.println("mouse released on cell: (" + x + "," + y + ")"); 
            game.leftClick(x, y);
            repaint();
            
        }
        @Override
        public void mousePressed(MouseEvent e) {
            int x =  (e.getX() - x_offset) / cell_size;
            int y =  e.getY() / cell_size;

            mouseUp = false;
            repaint();
            //check for clicking a tile thats beyond the board
            if(x >= num_columns || y >= num_rows || e.getX() - x_offset < 0){
                // System.out.println("mouse pressed oob");
                return;
            }
            //System.out.println("mouse pressed on cell: (" + x + "," + y + ")");
        }
        @Override
        public void mouseExited(MouseEvent e){
            mouse_cell_x_index = -1;
            mouse_cell_y_index = -1;
            repaint();
        }
    }
    private class MinesweeperMouseMotionHandler extends MouseMotionAdapter {
        // is there a reason to differentiate between mouseMoved and mouseDragged?
        @Override
        public void mouseMoved(MouseEvent e){
            motionLogic(e);
        }
        @Override
        public void mouseDragged(MouseEvent e){
            motionLogic(e);
        }
        private void motionLogic(MouseEvent e){
            int x =  (e.getX() - x_offset) / cell_size;
            int y =  e.getY() / cell_size;




            boolean changedTiles = false;
            if(x >= num_columns || y >= num_rows || e.getX() - x_offset < 0){
                mouse_cell_y_index = -1;
                mouse_cell_x_index = -1;
                repaint();
                return;
            }
            if(x != mouse_cell_x_index){
                mouse_cell_x_index = x;
                changedTiles = true;
            }
            if(y != mouse_cell_y_index){
                mouse_cell_y_index = y;
                changedTiles = true;
            }
            if(changedTiles){
                //System.out.println("mouse dragged over cell: (" + x + "," + y + ")");
                //System.out.println(e.getButton());
                repaint();
            } 
        }
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=Resize Handling=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private class ResizeHandler extends ComponentAdapter {
        //@Override
        public void componentResized(ComponentEvent e) {
            
            Dimension window = e.getComponent().getSize();

            //calculate new cell_size based on num_rows, num_columns, and current window size
            cell_size = Math.min((int) window.getWidth()  / num_columns , 
                                 (int) window.getHeight() / num_rows  );
            //calculate new x_offset
            x_offset = ((int)window.getWidth() - (num_columns * cell_size)) / 2;
            //image error arrays...
            int xErr = (int) window.getWidth() % num_columns;
            int yErr = (int) window.getHeight() % num_rows;

            //System.out.print("x error: " + xErr + ", ");
            //System.out.print("y error: " + yErr + "\n");






            //distribute this error over all images, adding 1 to some 
            
        }
          
    }


}