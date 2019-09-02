package ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import game.Game;
//import src.game.GameState;

//gives the user quick options to launch a new game, save the replay, and to see their updated stats
//will have basically the same functionality as the menu.
public class ResultsWindow extends JFrame{
    

    //private static final long serialVersionUID = 1L;

    public ResultsWindow(Game game) {
        setResizable(false);
        setPreferredSize(new Dimension(300, 300)); //(width, height)

        getContentPane().setLayout(new FlowLayout());
        switch(game.getState()){
            default:
                //pregame or ingame
                break;
            case Lost:
                setTitle("Game Lost");
                getContentPane().add(new JLabel("Oh No, You Lost!"));
                


                break;
            case Won:
                setTitle("Game Won");
                getContentPane().add(new JLabel("Hey, You Won!"));
                getContentPane().add(new JLabel("\nTime: "));
                break;
        }
        //on exit (top right), start a new game
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                //if(not quit button){
                    game.newGame();
                //}
            }
        });

        
        //play again button
        JButton playAgain = new JButton("Play Again");
        playAgain.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                game.newGame();
                dispose();
            }          
        });
        getContentPane().add(playAgain);

        //exit entire game button "quit"
        JButton quit = new JButton("Quit");
        quit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                
                game.mainWindow.dispose();
                dispose();
            }          
        });
        getContentPane().add(quit);


        //save replay button 
        JButton replay = new JButton("Save Replay");
        replay.addActionListener(new ActionListener(){
            private boolean saved = false;
            public void actionPerformed(ActionEvent e) {
                if(!saved){
                    //save replay

                    saved = true;
                }
            }          
        });
        getContentPane().add(replay);

        pack();
        setLocationRelativeTo(game.mainWindow);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}


 /*        
JFrame resultsWindow = new JFrame("Oh No, You Lost");
resultsWindow.setResizable(true);
resultsWindow.setVisible(true);
resultsWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 */