package menus;

import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import player.SettingsManager;
import menus.SettingsUI;

public class MenuBar extends JMenuBar {

    /* GAME MENU */
    private JMenu gameMenu;
    private JMenuItem settingsMenuItem;
    private JMenuItem restartMenuItem;
    //solver submenu goes here

    private JMenu replayMenu;
    private JMenu helpMenu;
    private JMenu versusMenu;

    class MenuActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
          System.out.println("Selected: " + e.getActionCommand());
        }
      }
    public MenuBar(SettingsManager settingsManeger /* also need stats manager and game manager(Game)*/) {

        /* GAME MENU */
        gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);
        /* SETTINGS SUBMENU */
        settingsMenuItem = new JMenuItem("Settings", KeyEvent.VK_S);
        settingsMenuItem.addActionListener(new MenuActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                new SettingsUI(settingsManeger);
            }
        });
        gameMenu.add(settingsMenuItem);

        restartMenuItem = new JMenuItem("Restart Game", KeyEvent.VK_R);


        //JMenu customGameMenu = new JMenu("Custom");

        replayMenu = new JMenu("Replay");
        replayMenu.setMnemonic(KeyEvent.VK_R);

        

        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        versusMenu = new JMenu("Versus");
        versusMenu.setMnemonic(KeyEvent.VK_V);

        //add(replayMenu);
        add(gameMenu);
        //add(helpMenu);
        //add(versusMenu);

    }
}