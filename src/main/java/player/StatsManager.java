package player;


import java.io.File;  // Import the File class

class StatsManager {

    private StatsObj stats = new StatsObj(); 

    public StatsManager(){
        //read from stats file

        //if file not found, create one and use default settings
    }


    public void reportWin(int time, Mode mode){
        switch(mode){
            case BEGINNER:
                stats.begWon++;
                break;
            case INTERMEDIATE:
                stats.intWon++;
                break;
            case ADVANCED:
                stats.expWon++;
                break;
            default:
            //idk if I want to deal with keeping track of custom results
                break;
        }
    }
    public void reprotLoss(Mode mode){
        switch(mode){
            case BEGINNER:
                stats.begLost++;
                break;
            case INTERMEDIATE:
                stats.intLost++;
                break;
            case ADVANCED:
                stats.expLost++;
                break;
            default:
            //idk if I want to deal with keeping track of custom results
                break;
        }
    }

    public double getWinRate(Mode mode){
        switch(mode){
            case BEGINNER:
            return stats.begWon / (stats.begLost + stats.begWon);
            case INTERMEDIATE:
            return stats.intWon / (stats.intLost + stats.intWon);
            case ADVANCED: 
            return stats.expWon / (stats.expLost + stats.expWon);
            default:
            return 0.0;
        }
    }

}