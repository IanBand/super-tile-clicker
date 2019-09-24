
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

//game
import game.Game;
//menus
import menus.MenuBar;


//player
import player.SettingsManager;
//ui
import ui.GameTimer;
import ui.GameUI;


class SuperTileClicker extends JFrame{
    private static final long serialVersionUID = -7910618806087098109L;
    
    private GameTimer timer;
    private Game game;
    // ^replace... private GameManager gameManager;
    private SettingsManager settingsManager;
    //private StatsManager statsManager;
    private GameUI gameUI;
    private MenuBar menuBar;

    private int cellSize = 20;
    private int dimW, dimH;



    public SuperTileClicker() throws IOException {
        //also need StatsManager here
        settingsManager = new SettingsManager();
        timer = new GameTimer();

        game = new Game(settingsManager.getSettings());
        game.add(timer);
        game.add(this);

        gameUI = new GameUI(game);
        
        
        
        

        

        menuBar = new MenuBar(settingsManager, this);



        
        updateWindowDimentions();

        getContentPane().setBackground(new Color(185, 184, 185));
        setTitle("Super Tile Clicker");
        add(gameUI);
        add(timer, BorderLayout.SOUTH);
        setJMenuBar(menuBar);
        
        pack();
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    //ALLA DIS HAS TO BE REDONE ERRY TIME THE BOARD SIZE CHANGES, ALSO A REPAINT TOO.
    //board_ui.repaint(); //repaint whenever the board size changes...
    //current dimensions are based on a board size of 20 by 20, 
    //the equation should be something like windowWidth = boardWidth * 20 + 20 or ww = bw * 21
    //height is the same but account for the timer, which is about boardHeight * 20 + 35 to account for the jlabel
    public void updateWindowDimentions(){ //need better name?

        dimW = cellSize * settingsManager.getSettings().width + 16;
        dimH = cellSize * settingsManager.getSettings().height + 77;

        setPreferredSize(new Dimension(dimW , dimH)); //(width, height)
        setMinimumSize(new Dimension(dimW , dimH));
        gameUI.repaint();
    }
    
    
    public static void main(String[] args) throws IOException {
        new SuperTileClicker();
    }

}