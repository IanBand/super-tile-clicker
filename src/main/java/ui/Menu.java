package ui;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class Menu extends JMenuBar{

    public Menu(/* settings and game obj...? */){
        JMenu replayMenu = new JMenu("Replay");
        replayMenu.setMnemonic(KeyEvent.VK_R);

        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        JMenu versusMenu = new JMenu("Versus");
        versusMenu.setMnemonic(KeyEvent.VK_V);

        add(replayMenu);
        add(gameMenu);
        add(helpMenu);
        add(versusMenu);

    }
}