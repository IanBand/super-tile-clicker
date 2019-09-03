package player;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.util.HashMap;


//import org.json.simple.JSONObject;
//import com.google.code.gson.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;



public class SettingsManager {

    //default settings
    private SaveObj settings = new SaveObj(Mode.INTERMEDIATE, false, false, 40, 20, 120);

    private String path = "C:/Users/Bundo/Desktop/code/ms-java/settings.json";
    private boolean saving = true; //determines if the settings will be saved to a file when updated. settings should autosave when modified

    //... no matter what the settings are, a user should always be able to read the height, width, and num mines in a standard way

    
    public SettingsManager() throws IOException{


        try{
            JsonReader jsonReader = new JsonReader(new FileReader(path));
            

            
            //saveFile.close();

        } 
        catch(FileNotFoundException e){ //catch file not found exception
            //if file not found, try and create
            System.out.println("save file not found, creating new save file");

            try{ //try and create new save file
                FileWriter newSaveFile = new FileWriter(path);

                Gson gson = new Gson();
                newSaveFile.write(gson.toJson(settings));

                newSaveFile.flush();
                newSaveFile.close();            }
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
        }
    }


    public void setCustomHeight(int newHeight){
        if(newHeight * settings.customWidth - 9 >= settings.customMines){
            settings.customHeight = newHeight;
        }
        else{
            //notify user of invalid custom settings
        }
    }
    public void setCustomWidth(int newWidth){
        if(settings.customHeight * newWidth - 9 >= settings.customMines){
            settings.customWidth = newWidth;
        }
        else{
            //notify user of invalid custom settings
        }
    }
    public void setCustomMines(int newMines){
        if(settings.customHeight * settings.customWidth - 9 >= newMines){
            settings.customMines = newMines;
        }
        else{
            //notify user of invalid custom settings
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
