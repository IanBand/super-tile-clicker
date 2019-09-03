package player;

public class SaveObj{

    public Mode mode;
    public boolean flags;
    public boolean questions;
    public int customHeight;
    public int customWidth;
    public int customMines;

    public SaveObj(Mode mode, boolean flags, boolean questions, int customHeight, int customWidth, int customMines){
        this.mode = mode;
        this.flags = flags;
        this.questions = questions;
        this.customHeight = customHeight;
        this.customWidth = customWidth;
        this.customMines = customMines;
    }
    @Override
    public String toString() {
        return new StringBuilder().append("SaveFile{")
        .append("mode: ").append(mode)
        .append(",flags?: ").append(flags)
        .append(",questions?: ").append(questions)
        .append(",custom-height:").append(customHeight)
        .append(",custom-width:").append(customWidth)
        .append(",custom-mines:").append(customMines)
        .append("}").toString();
    }
}