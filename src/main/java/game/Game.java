package game;

import board.MinesweeperBoard;
import board.Cell;
import ui.GameTimer;
import menus.ResultsWindow;

import java.util.Random;

import player.GameSettings;

import javax.swing.JFrame;

public class Game{
    //TODO: middleclick logic goes in this class as well
    private GameState state = GameState.Pregame;
    //make gameState accessor

    //private Replay replay; TODO: make dis

    private GameTimer timer; //time is in ms
    public JFrame mainWindow;

    private int seed;
    private GameSettings settings;

    private MinesweeperBoard board;

    public Game(GameSettings s){

        this.settings = s;
        Random rng = new Random();
        int rand = rng.nextInt();  //this is the random salt for the seed.
        seed = rand + s.height + s.width;
        board = new MinesweeperBoard(seed, s.height, s.width, s.mines);
    }
    public void resetGame(){ //same settings, new seed

        Random rng = new Random();
        int rand = rng.nextInt();  //this is the random salt for the seed.
        seed = rand + settings.height + settings.width;
        board = new MinesweeperBoard(seed, settings.height, settings.width, settings.mines);
        state = GameState.Pregame;

        //destroy old timer, create new timer, or just clear timer
        timer.reset();
    }
    public void restartGame(){ //same settings, same seed, need different pregame logic
        board = new MinesweeperBoard(seed, settings.height, settings.width, settings.mines);

        state = GameState.Pregame; //need new pregame logic, dont want to use 'board.uncoverFirstTile(x, y)' when uncovering first tile

        //destroy old timer, create new timer, or just clear timer
        timer.reset();

        //need to 
    }
    public void newGame(GameSettings s){

        //same as construstor
        this.settings = s;
        Random rng = new Random();
        int rand = rng.nextInt();  //this is the random salt for the seed.
        seed = rand + s.height + s.width;
        board = new MinesweeperBoard(seed, s.height, s.width, s.mines);

        //destroy old timer, create new timer, or just clear timer
        timer.reset();
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

    public void leftClick(int x, int y){
        switch(state){
            case Pregame:
                state = GameState.InProgress;
                board.uncoverFirstTile(x, y);
                //start timer
                timer.startTimer();
                this.checkGameEndConditions(x, y);
                //record replay
                break;
            case InProgress:
                board.uncoverTile(x, y);
                this.checkGameEndConditions(x, y);
                //record replay
                
                break;
            default:
                //do nothing I guess
                //maybe reset or instantly start a new game?
        }
    }
    private void checkGameEndConditions(int x, int y){
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
    }
    public void rightClick(int x, int y){
        //flag stuff
    }
    public void middleClick(int x, int y){
        //middle click logic
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