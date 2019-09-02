package player;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;



public class SettingsManager {

    /*modes: 
    0: beginner
    1: intermediate
    2: expert
    3: custom
    else error
    */

    //default settings
    private Mode mode = Mode.BEGINNER;
    public Mode getMode(){return mode;}
    

    private Boolean flags = true;
    public boolean getFlags(){return flags;}
    public void setFlags(boolean f){flags = f;}

    private Boolean questions = true;
    public boolean getQuestions(){return questions;}

    private int customHeight = 50;
    private int customWidth = 70;
    private int customMines = 700;

    private String path = "C:/Users/Bundo/Desktop/code/ms-java/data/settings.dat";

    private boolean saving = true; //determines if the settings will be saved to a file when updated. settings should autosave when modified

    //... no matter what the settings are, a user should always be able to read the height, width, and num mines in a standard way

    public SettingsManager() throws IOException{
        PrintWriter saveFile = null;
        try {
            saveFile = new PrintWriter(path, "r");
            /*
            saveFile = new PrintWriter(path, "r");
            JSONObject jo = new JSONObject();

            //read settings, assumes file is correct format and length
            flags = saveFile.readBoolean(); // flags
            questions = saveFile.readBoolean(); // questions
            mode = Mode.values()[saveFile.readInt()]; //mode
            customHeight = saveFile.readInt(); //custom height
            customWidth =  saveFile.readInt(); //custom width
            customMines =  saveFile.readInt(); //custom mines

            //close file
            saveFile.close();
            */
        } 
        catch(FileNotFoundException e){ //catch file not found exception
            //if file not found, try and create
            System.out.println("save file not found, creating new save file");

            try{ //try and create new save file

                saveFile = new PrintWriter(path, "rw");
                /*
                

                //write default settings
                saveFile.writeBoolean(flags);
                saveFile.writeBoolean(questions);
                saveFile.writeInt(mode.ordinal());
                saveFile.writeInt(customHeight);
                saveFile.writeInt(customWidth);
                saveFile.writeInt(customMines);

                //close file
                saveFile.close();
                */
            }
            catch(IOException e_){ //creating new save file failed
                System.out.println(e_.getMessage());
                saving = false;
            }
        }
        catch(IOException e){ //catch all other exceptions
            //else, if its another exception, just give up and dont create a save
            System.out.println(e.getMessage());
            saving = false;
        }
        finally {
            if (saveFile != null) {
                saveFile.close();
            }
        }
    }
    public void setCustomHeight(int newHeight){
        if(newHeight * customWidth - 9 >= customMines){
            customHeight = newHeight;
        }
        else{
            //notify user of invalid custom settings
        }
    }
    public void setCustomWidth(int newWidth){
        if(customHeight * newWidth - 9 >= customMines){
            customWidth = newWidth;
        }
        else{
            //notify user of invalid custom settings
        }
    }
    public void setCustomMines(int newMines){
        if(customHeight * customWidth - 9 >= newMines){
            customMines = newMines;
        }
        else{
            //notify user of invalid custom settings
        }
    }
    public Settings getSettings(){//returns settings based on mode

        Settings s = new Settings();
        s.mode = mode;

        switch(mode){
            case BEGINNER: //beginner settings defined here
                s.height = 9;
                s.width = 9;
                s.mines = 10;
                break;
            case INTERMEDIATE: //intermediate settings defined here
                s.height = 16;
                s.width = 16;
                s.mines = 40;
                break;
            case ADVANCED: //advanced settings defined here
                s.height = 16;
                s.width = 30;
                s.mines = 99;
                break;
            case CUSTOM:
                s.height = customHeight;
                s.width = customWidth;
                s.mines = customMines;
                break;
        }
        return s;
    }


    public void setMode(Mode m){mode = m;}
}
