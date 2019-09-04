package menus;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar{

    public MenuBar(/* settings and game obj...? */){
        JMenu replayMenu = new JMenu("Replay");
        replayMenu.setMnemonic(KeyEvent.VK_R);

        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);

        JMenu customGameMenu = new JMenu("Custom");

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        JMenu versusMenu = new JMenu("Versus");
        versusMenu.setMnemonic(KeyEvent.VK_V);

        //add(replayMenu);
        add(gameMenu);
        //add(helpMenu);
        //add(versusMenu);

    }
}