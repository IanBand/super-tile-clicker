
import java.awt.EventQueue;//?
import java.io.IOException;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import java.awt.BorderLayout;


//game
import game.Game;

//ui
import ui.GameTimer;
import ui.GameUI;
import ui.Menu;
import ui.ResultsWindow; //wont need here...?

//player
import player.SettingsManager;


class Main{
    // game has a board ui instance...? these can all just be declaired in Main I
    // guess
    public static void main(String[] args) throws IOException {
        
        SettingsManager settingsManager = new SettingsManager();

        GameTimer timer = new GameTimer();
        //game settings will be read from file, if none found, create and use defaults
        int defaultHeight = 25;
        int defaultWidth = 40;
        int defaultMines = 350;
        Game game = new Game(defaultHeight, defaultWidth, defaultMines); 
        game.add(timer);
        
        GameUI board_ui = new GameUI(game);
        Menu menu = new Menu();
        
        //board_ui.repaint(); //repaint whenever the board size changes...


        JFrame mainWindow = new JFrame("Super Tile Clicker");
        mainWindow.getContentPane().setBackground(new Color(185, 184, 185));

        //current dimensions are based on a board size of 20 by 20, 
        //the equation should be something like windowWidth = boardWidth * 20 + 20 or ww = bw * 21
        //height is the same but account for the timer, which is about boardHeight * 20 + 35 to account for the jlabel
        int cellSize = 20;


        int dimW = cellSize * defaultWidth + 16;
        int dimH = cellSize * defaultHeight + 77;
        mainWindow.setPreferredSize(new Dimension(dimW , dimH)); //(width, height)
        mainWindow.setMinimumSize(new Dimension(dimW , dimH));
        mainWindow.add(board_ui);
        mainWindow.add(timer, BorderLayout.SOUTH);
        mainWindow.setJMenuBar(menu);
        
        mainWindow.pack();
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setResizable(true);
        mainWindow.setVisible(true);
        
        
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game.add(mainWindow);

    }

}