package player;


import java.io.File;  // Import the File class

class StatsManager {

    //completely fuckd, need to rewrite

    private int begWon = 0;
    private int begLost = 0;

    private int intWon = 0;
    private int intLost = 0;

    private int expWon = 0;
    private int expLost = 0;

    public StatsManager(){
        //read from stats file

        //if file not found, create one and use default settings
    }


    public void reportWin(int time, int mode){
        switch(mode){
            case 0:
                begWon++;
                break;
            case 1:
                intWon++;
                break;
            case 2:
                expWon++;
                break;
            default:
                break;
        }
    }
    public void reprotLoss(int mode){
        switch(mode){
            case 0:
                begLost++;
                break;
            case 1:
                intLost++;
                break;
            case 2:
                expLost++;
                break;
            default:
                break;
        }
    }

}