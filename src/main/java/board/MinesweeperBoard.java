//https://stackoverflow.com/questions/380046/how-can-i-best-apply-oop-principles-to-games-and-other-input-driven-gui-apps
//https://code.tutsplus.com/tutorials/build-a-minesweeper-game-within-200-lines-of-code--active-8578
package board;

import java.util.Random;

public class MinesweeperBoard implements Constants{

    private Cell field[][];
    private int columns;
    private int rows;
    private int numMines;
    private int seed;
    private int numTilesRevealed = 0;
    private int totalTiles;
    private int numSafeTiles;
    private boolean gameOver = false; //?????? shouldnt need to keep track of this...

    private int minesToPush = 0; //used in pushAwayMines() to count the number of mines that need to be pushed
    private int adjMines = 0;    //used in placeNumbers() to count the number of adjacent mines
    private boolean rerollTarget = false; //used in pushAwayMines() to indicate if a target is a neighbor of initial click

    public MinesweeperBoard(int seed_, int rows_, int columns_, int numMines_){
        //set dimensions ect
        columns = columns_;
        rows = rows_;
        numMines = numMines_;
        totalTiles = columns * rows;
        numSafeTiles = totalTiles - numMines;
        seed = seed_;

        //System.out.println("seed: " + seed + ", height: " + columns_ + ", width: " + rows_ + ", numMines: " + numMines_);

        //instantiate fielld and Cells
        int rightmost = columns - 1;
        int bottom = rows - 1;
        int top = 0;
        int leftmost = 0;

        field = new Cell[columns][rows];

        for(int i = 0; i < columns; i++){
            for(int j = 0; j < rows; j++){
                field[i][j] = new Cell();
                // determine cells place
                if      (i == rightmost && j == top)    field[i][j].place = Cardinal.NorthEast;
                else if (i == leftmost  && j == top)    field[i][j].place = Cardinal.NorthWest;
                else if (i == rightmost && j == bottom) field[i][j].place = Cardinal.SouthEast;
                else if (i == leftmost  && j == bottom) field[i][j].place = Cardinal.SouthWest;
                else if (i == leftmost)                 field[i][j].place = Cardinal.West;
                else if (i == rightmost)                field[i][j].place = Cardinal.East;
                else if (j == top)                      field[i][j].place = Cardinal.North;
                else if (j == bottom)                   field[i][j].place = Cardinal.South;
                else                                    field[i][j].place = Cardinal.Center;
            }
        }

        //place mines
        Random rng = new Random(seed);
        int minesLeft = numMines;
        int targetX, targetY;
        while(minesLeft > 0 ){
            targetX = rng.nextInt(columns);
            targetY = rng.nextInt(rows);
            //System.out.println("target: " + "(" + targetX + "," + targetY + ")");
            if(field[targetX][targetY].value != MINE){
                field[targetX][targetY].value = MINE;
                minesLeft--;
            }
        }

        Area boardArea = new Area(0,0,columns, rows);
        //place numbers on entire board
        placeNumbers(boardArea); //this is broken af lol it writes all the tile values to zero
    }
    public void uncoverFirstTile(int x, int y){
        //System.out.println("first click: ( " + x + " , " + y + " )");
        pushAwayMines(x,y);
        uncoverTile(x,y);
    }
    public void flagTile(int x, int y, boolean allow_questions){
        field[x][y].cycleFlag(allow_questions);
    }
    public int[][] getKnownBoard(){ //needless
        int[][] board = new int[columns][rows];
        for(int i = 0; i < columns; i++){
            for(int j = 0; j < rows; j++){
                if(field[i][j].revealed){
                    board[i][j] = field[i][j].value;
                }
                else{
                    board[i][j] = COVER; 
                } //TODO: account for MINEHIT, flaggs, ect
            }
        }
        return board;
    }
    public Cell[][] getBoard(){
        return field;
    }
    public int getColumns() {
        return columns;
    }
    public int getRows() {
        return rows;
    }
    public boolean gameWon(){
        //will add to this later..?
        return numTilesRevealed == numSafeTiles;
    }

    public void uncoverTile(int x, int y){
        
        //check if tile is revealed
        if(field[x][y].revealed){
            return;
        }
        //reveal tile
        field[x][y].revealed = true;
        numTilesRevealed++;

        //check lose condition
        if(field[x][y].value == MINE){
            gameOver = true;
            return;
        }

        //check win condition
        if(gameWon()){
            return;
        }

        //if tile is a zero, recurse over all neighbors
        if(field[x][y].value == 0){
            
            //if no flag or question
            //CellComparison comp = (xc , yc) ->  field[xc][yc].flaggedState != noMark; //this is broken, fix later 
            CellComparison comp = (xc , yc) -> true;
            NeighborOperation nebOp = (xn , yn) -> uncoverTile(xn, yn);

            evalNeighbors(x, y, nebOp, comp);
        }
    }

    private void pushAwayMines(int x, int y){

        //count number of mines in x,y and surrounding tiles, clear them if they are mines
        if(field[x][y].value == MINE){
            field[x][y].value = 0;
            minesToPush++;
        }
        CellComparison comp = (xc , yc) ->  field[xc][yc].value == MINE;
        NeighborOperation nebOp = (xn , yn) -> {
            field[xn][yn].value = 0;
            minesToPush++;
        };
        evalNeighbors(x, y, nebOp, comp);


        Random rng = new Random(seed + 1);
        while(minesToPush > 0){
            //place mine somehwere on the board excluding the restricted area
            int targetX, targetY;
            targetX = rng.nextInt(columns);
            targetY = rng.nextInt(rows);

            //if target is neighbor of click (xTargNei and yTargNei represent all the neighbors of target)
            CellComparison comp2 = (xTargNei , yTargNei) ->  xTargNei == targetX && yTargNei == targetY;

            //mark that new target is needed
            NeighborOperation nebOp2 = (a , b) -> rerollTarget = true;
            evalNeighbors( x, y, nebOp2, comp2);

            //check if target is same as first click
            if(x == targetX && y == targetY){
                rerollTarget = true;
            }

            //System.out.println("target: " + "(" + targetX + "," + targetY + ")");
            if(field[targetX][targetY].value != MINE && !rerollTarget){
                field[targetX][targetY].value = MINE;
                minesToPush--;
                rerollTarget = false;
            }

            //place numbers over destination area
            placeNumbers(getAffectedArea(targetX, targetY, 2));
        }

        //place numbers over restricted area
        placeNumbers(getAffectedArea(x, y, 3));
    }
    private Area getAffectedArea(int x, int y, int radius){
        //retreives the bounding coordinates of a square with area (2* radius + 1)^2 centered around (x,y)
        //accounts for edge coordinates, only gives areas that are contained in the board

        //the logic is probably wrong and will cause a lot of array index oob errors until I get it right
        int x1 = Math.max(x - radius , 0);
        int y1 = Math.max(y - radius , 0);

        int x2 = Math.min(x + radius , columns - 1);
        int y2 = Math.min(y + radius , rows - 1);

        Area destArea = new Area(x1,y1,x2,y2);

        return destArea;
    }

    private void placeNumbers(Area a){
        //if a neighbor is a mine
        CellComparison comp = (x , y) ->  field[x][y].value == MINE;

        //add 1 to tile value
        NeighborOperation nebOp = (x , y) -> adjMines++;

        for (int i = a.x1; i < a.x2; i++){
            for(int j = a.y1; j < a.y2; j++){
                //if not mine, then count neighbors
                if(field[i][j].value == MINE) continue;
                    
                adjMines = 0;
                evalNeighbors(i, j, nebOp, comp); //eval neighbors not working properly...?
                field[i][j].value = adjMines;

                
            }
        }
    }

    //https://www.javaworld.com/article/3319078/functional-programming-for-java-developers-part-2.html
    //https://hackernoon.com/finally-functional-programming-in-java-ad4d388fb92e
    @FunctionalInterface 
    interface NeighborOperation {
        void operateOn(int x, int y); // operateOn is just the prototype for the lambda fucntion that will be defined later
        //other functions will be able have arguments with the type NeighborOperation
    }

    @FunctionalInterface 
    interface CellComparison {
        boolean compareTo(int x, int y);
    }
    private void evalNeighbors( int x, int y, NeighborOperation n, CellComparison c){
        switch(field[x][y].place){
            case Center:
                if(c.compareTo(x + 1, y))     n.operateOn(x + 1, y);        //check East
                if(c.compareTo(x + 1, y + 1)) n.operateOn(x + 1, y + 1);    //check SouthEast
                if(c.compareTo(x , y + 1))    n.operateOn(x , y + 1);       //check South
                if(c.compareTo(x - 1, y + 1)) n.operateOn(x - 1, y + 1);    //check SouthWest
                if(c.compareTo(x - 1, y))     n.operateOn(x - 1, y);        //check West
                if(c.compareTo(x - 1, y - 1)) n.operateOn(x - 1, y - 1);    //check NorthWest
                if(c.compareTo(x , y - 1))    n.operateOn(x , y - 1);       //check North
                if(c.compareTo(x + 1, y - 1)) n.operateOn(x + 1, y - 1);    //check NorthEast
                break;
            case North:
                if(c.compareTo(x + 1, y))     n.operateOn(x + 1, y);        //check East
                if(c.compareTo(x + 1, y + 1)) n.operateOn(x + 1, y + 1);    //check SouthEast
                if(c.compareTo(x , y + 1))    n.operateOn(x , y + 1);       //check South
                if(c.compareTo(x - 1, y + 1)) n.operateOn(x - 1, y + 1);    //check SouthWest
                if(c.compareTo(x - 1, y))     n.operateOn(x - 1, y);        //check West
                break;
            case NorthEast:
                if(c.compareTo(x , y + 1))    n.operateOn(x , y + 1);       //check South
                if(c.compareTo(x - 1, y + 1)) n.operateOn(x - 1, y + 1);    //check SouthWest
                if(c.compareTo(x - 1, y))     n.operateOn(x - 1, y);        //check West
                break;
            case East:
                if(c.compareTo(x , y + 1))    n.operateOn(x , y + 1);       //check South
                if(c.compareTo(x - 1, y + 1)) n.operateOn(x - 1, y + 1);    //check SouthWest
                if(c.compareTo(x - 1, y))     n.operateOn(x - 1, y);        //check West
                if(c.compareTo(x - 1, y - 1)) n.operateOn(x - 1, y - 1);    //check NorthWest
                if(c.compareTo(x , y - 1))    n.operateOn(x , y - 1);       //check North
                break;
            case SouthEast:
                if(c.compareTo(x - 1, y))     n.operateOn(x - 1, y);        //check West
                if(c.compareTo(x - 1, y - 1)) n.operateOn(x - 1, y - 1);    //check NorthWest
                if(c.compareTo(x , y - 1))    n.operateOn(x , y - 1);       //check North
                break;
            case South:
                if(c.compareTo(x + 1, y))     n.operateOn(x + 1, y);        //check East
                if(c.compareTo(x - 1, y))     n.operateOn(x - 1, y);        //check West
                if(c.compareTo(x - 1, y - 1)) n.operateOn(x - 1, y - 1);    //check NorthWest
                if(c.compareTo(x , y - 1))    n.operateOn(x , y - 1);       //check North
                if(c.compareTo(x + 1, y - 1)) n.operateOn(x + 1, y - 1);    //check NorthEast
                break;
            case SouthWest:
                if(c.compareTo(x + 1, y))     n.operateOn(x + 1, y);        //check East
                if(c.compareTo(x , y - 1))    n.operateOn(x , y - 1);       //check North
                if(c.compareTo(x + 1, y - 1)) n.operateOn(x + 1, y - 1);    //check NorthEast
                break;
            case West:
                if(c.compareTo(x + 1, y))     n.operateOn(x + 1, y);        //check East
                if(c.compareTo(x + 1, y + 1)) n.operateOn(x + 1, y + 1);    //check SouthEast
                if(c.compareTo(x , y + 1))    n.operateOn(x , y + 1);       //check South
                if(c.compareTo(x , y - 1))    n.operateOn(x , y - 1);       //check North
                if(c.compareTo(x + 1, y - 1)) n.operateOn(x + 1, y - 1);    //check NorthEast
                break;
            case NorthWest:
                if(c.compareTo(x + 1, y))     n.operateOn(x + 1, y);        //check East
                if(c.compareTo(x + 1, y + 1)) n.operateOn(x + 1, y + 1);    //check SouthEast
                if(c.compareTo(x , y + 1))    n.operateOn(x , y + 1);       //check South
                break;
        }
    }
    
    @Override //for testing/debug purposes
    public String toString () {
        String val = "";
        for(int i = 0; i < columns; i++){
            for(int j = 0; j < rows; j++){
                //val += field[i][j].value;
                if(field[i][j].revealed){
                    val += field[i][j].value + " ";
                }
                else{
                    val += "? "; // -1 is a placeholder for unrevealed
                }
            }
            val += "\n";
        }
        return val;
    }
}