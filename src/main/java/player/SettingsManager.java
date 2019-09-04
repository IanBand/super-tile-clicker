package player;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class SettingsManager {

    //default settings
    private SaveObj settings = new SaveObj(Mode.INTERMEDIATE, false, false, 40, 20, 120);

    private String path = "C:/Users/Bundo/Desktop/code/ms-java/settings.dat";
    private boolean saving = true; //determines if the settings will be saved to a file when updated. settings should autosave when modified

    //... no matter what the settings are, a user should always be able to read the height, width, and num mines in a standard way

    
    public SettingsManager() throws IOException{
        RandomAccessFile saveFile = null;
        try {
            saveFile = new RandomAccessFile(path, "r");
            System.out.println("loading settings file");
        

            settings.flags = saveFile.readBoolean(); // flags
            settings.questions = saveFile.readBoolean(); // questions
            settings.mode = Mode.values()[saveFile.readInt()]; //mode
            settings.customHeight = saveFile.readInt(); //custom height
            settings.customWidth =  saveFile.readInt(); //custom width
            settings.customMines =  saveFile.readInt(); //custom mines
            //close file
            saveFile.close();
            
        } 
        catch(FileNotFoundException e){ //catch file not found exception
            //if file not found, try and create
            System.out.println("settings file not found, creating new settings file");

            try{ //try and create new save file

                saveFile = new RandomAccessFile(path, "rw");
                
                
                //write default settings
                saveFile.writeBoolean(settings.flags);
                saveFile.writeBoolean(settings.questions);
                saveFile.writeInt(settings.mode.ordinal());
                saveFile.writeInt(settings.customHeight);
                saveFile.writeInt(settings.customWidth);
                saveFile.writeInt(settings.customMines);
                //close file
                saveFile.close();
                
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


    public Boolean setCustomHeight(int newHeight){
        if(newHeight * settings.customWidth - 9 >= settings.customMines){
            settings.customHeight = newHeight;
            return true;
        }
        else{
            //notify user of invalid custom settings
            return false;
        }
    }
    public Boolean setCustomWidth(int newWidth){
        if(settings.customHeight * newWidth - 9 >= settings.customMines){
            settings.customWidth = newWidth;
            return true;
        }
        else{
            //notify user of invalid custom settings
            return false;
        }
    }
    public Boolean setCustomMines(int newMines){
        if(settings.customHeight * settings.customWidth - 9 >= newMines){
            settings.customMines = newMines;
            return true;
        }
        else{
            //notify user of invalid custom settings
            return false;
        }
    }
    public GameSettings getSettings(){//returns settings based on mode

        GameSettings s = new GameSettings();
        s.mode = settings.mode;

        switch(settings.mode){
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
                s.height = settings.customHeight;
                s.width = settings.customWidth;
                s.mines = settings.customMines;
                break;
        }
        return s;
    }


    public void setMode(Mode m){settings.mode = m;}
}
