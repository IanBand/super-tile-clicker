package game;

import board.MinesweeperBoard;
import board.Cell;
import ui.GameTimer;
import ui.ResultsWindow;

import java.util.Random;

import javax.swing.JFrame;

public class Game{
    //TODO: middleclick logic goes in this class as well
    private GameState state = GameState.Pregame;
    //make gameState accessor

    //private Replay replay; TODO: make dis

    private GameTimer timer; //time is in ms
    public JFrame mainWindow;

    private int seed;
    private int numMines;
    private int height;
    private int width;


    private MinesweeperBoard board;

    public Game(int height_, int width_, int numMines_){


        Random rng = new Random();
        int rand = rng.nextInt();  //this is the random salt for the seed.
        seed = rand + height_ + width_ + numMines_;
        numMines = numMines_;
        width = width_;
        height = height_;
        board = new MinesweeperBoard(seed, height_, width_, numMines_);
    }
    public void add(GameTimer timer_){
        timer = timer_;
    }
    public void add(JFrame mainWindow_){
        mainWindow = mainWindow_;
    }
    public String getTime(){
        return "buh";
    }
    public void newGame(/* Settings settings */){ //newGame() just uses the same settings for now

        //new seed
        Random rng = new Random();
        int rand = rng.nextInt();  //this is the random salt for the seed.
        seed = rand + height + width + numMines;

        
        state = GameState.Pregame;
        board = new MinesweeperBoard(seed, height, width, numMines);

        //destroy old timer, create new timer, or just clear timer
        timer.reset();
    }
    public void leftClick(int x, int y){
        switch(state){
            case Pregame:
                state = GameState.InProgress;
                board.uncoverFirstTile(x, y);
                //start timer
                timer.startTimer();
                //record replay
                break;
            case InProgress:
                board.uncoverTile(x, y);
                //record replay
                if(board.getBoard()[x][y].value == 9){
                    state = GameState.Lost;
                    //end timer
                    timer.endTimer();
                    //summon losing window
                    new ResultsWindow(this);
                }
                if(board.gameWon()){
                    state = GameState.Won;
                    //end timer
                    timer.endTimer();
                    //summon winning window
                    new ResultsWindow(this);
                }
                
                break;
            default:
                //do nothing I guess
                //maybe reset or instantly start a new game?
        }
    }
    public void rightClick(int x, int y){
        //flag stuff
    }
    public Cell getCell(int x, int y){
        return board.getBoard()[x][y];
    }
    public int getRows(){
        return board.getRows();
    }
    public int getColumns(){
        return board.getColumns();
    }
    public GameState getState(){
        return state;
    }
    

    
}