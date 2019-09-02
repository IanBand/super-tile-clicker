package ui;

import javax.swing.JLabel;
import java.util.Timer; 
import java.util.TimerTask; 
import java.util.Date;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameTimer extends JLabel{
    
    private boolean displayMinutes = false;
    private long startTime;
    private Timer timer;
    private int elapsedTime = 0; //time in 10ms incriments

    public GameTimer(){
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                displayMinutes = !displayMinutes;
                if(timer == null){
                    redrawZeroTime();
                }
            }
        });
        redrawZeroTime();
    }
    public void startTimer(/*long startTime_*/){
        //startTime = startTime_;
        timer = new Timer();
        timer.schedule(new IncTime(), 10, 10); //clock incriments every 10 ms
    }
    public void endTimer(){
        timer.cancel();
        //timer.purge(); //not needed
    }
    public void reset(){
        elapsedTime = 0;
        redrawZeroTime();
    }
    private class IncTime extends TimerTask {
        public void run(){
            elapsedTime += 1;
            if(displayMinutes){
                //convert elapsedTime to hour:minute:second:miliseconds format
                int hour = (elapsedTime / 360000);
                int minute = (elapsedTime / 6000) % 60;
                int second = (elapsedTime / 100) % 60;
                int tenMs = elapsedTime % 100;

                //ternary operator
                String displayHour = ((hour < 10 ) ? "0" + hour : "" + hour);
                String displayMinute = ((minute < 10 ) ? "0" + minute : "" + minute);
                String displaySecond = ((second < 10 ) ? "0" + second : "" + second);
                String displayTenMs = ((tenMs < 10 ) ? "0" + tenMs : "" + tenMs);

                setText(displayHour + ":" + displayMinute + ":" + displaySecond + ":" + displayTenMs);
            }
            else{
                //convert elapsedTime to fixed point format
                int second = elapsedTime / 100;
                int tenMs = elapsedTime % 100;

                String displaySecond = ((second < 10 ) ? "0" + second : "" + second);
                String displayTenMs = ((tenMs < 10 ) ? "0" + tenMs : "" + tenMs);

                setText(displaySecond + "." + displayTenMs);
            }
        }
    }

    private void redrawZeroTime(){
        if(displayMinutes){
            setText("00:00:00:00");
        }
        else{
            setText("00.00");
        }
    }

    public String getTimeInMinutes(){
        return "00:00:00";
    }
    public String getTimeInSeconds(){
        return "000.00";
    }
}