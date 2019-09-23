
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

//game
import game.Game;
//menus
import menus.MenuBar;


import menus.SettingsUI;

//player
import player.SettingsManager;
//ui
import ui.GameTimer;
import ui.GameUI;


class Main{
    // game has a board ui instance...? these can all just be declaired in Main I guess
    
    public static void main(String[] args) throws IOException {
        
        SettingsManager settingsManager = new SettingsManager();

        GameTimer timer = new GameTimer();
        //game settings will be read from file, if none found, create and use defaults
        
        Game game = new Game(settingsManager.getSettings());
        game.add(timer);
        
        GameUI board_ui = new GameUI(game);
        MenuBar menu= new MenuBar(settingsManager);
        
        JFrame mainWindow = new JFrame("Super Tile Clicker");
        mainWindow.getContentPane().setBackground(new Color(185, 184, 185));


        //ALLA DIS HAS TO BE REDONE ERRY TIME THE BOARD SIZE CHANGES, ALSO A REPAINT TOO.
        //board_ui.repaint(); //repaint whenever the board size changes...
        //current dimensions are based on a board size of 20 by 20, 
        //the equation should be something like windowWidth = boardWidth * 20 + 20 or ww = bw * 21
        //height is the same but account for the timer, which is about boardHeight * 20 + 35 to account for the jlabel
        int cellSize = 20;
        int dimW = cellSize * settingsManager.getSettings().width + 16;
        int dimH = cellSize * settingsManager.getSettings().height + 77;
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