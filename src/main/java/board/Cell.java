package board;
public class Cell {
    public boolean revealed = false;
    public int value = 0;
    public Cardinal place;
    public int flaggedState = 0;//make enum?
    
    public void cycleFlag(boolean allow_questions){ // toggles flag or cycles through flag -> question -> none if flags are enabled in game
        if(allow_questions){
            flaggedState = (flaggedState + 1) % 3;
        }
        else{
            flaggedState = (flaggedState + 1) % 2;
        }
    }
}