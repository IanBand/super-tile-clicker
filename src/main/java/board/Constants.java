package board;

public interface Constants {
        //Tile values
        static int MINE = 9;
        static int MINEHIT = 10;
        static int COVER = 11;
        static int FLAG = 12;
        static int WRONGFLAG = 13;
        static int HIGHLIGHT = 14;

        //flag values (its not an enum because I want to use the modulo operation)
        static int noMark = 0;
        static int flagged = 1;
        static int questioned = 2;
}

//https://www.journaldev.com/1365/static-keyword-in-java
//these static vars last the lifetime of the program and are shared by all instances of objects that implement Constants