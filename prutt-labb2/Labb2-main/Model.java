public class Model {
    public BoardState boardState;
    public int currentPlayer = 0; // 0 is 1, 1 is 2
    public Model() {
        boardState = BoardState.initial(9, 10);
    }
    
    // returns false if move was illegal, make move if legal
    // updates state
    public boolean makeMove(int row, int col) {
        if (QuoridorEngine.isValidMove(boardState, currentPlayer+1, row, col))
        {
            boardState = QuoridorEngine.applyMove(boardState, currentPlayer+1, row, col);
            currentPlayer ^=1;
            return true;
        }
        return false;
    }

    // note that the provided implementation is incorrect
    // i wasted over 30 minutes reading my own code before realising :)
    // the only reason the time wasted was this short was because i had heard
    // certain rumours about the implementations in some of the assignments in this course.
    // please fix for future course versions (this applies to several other assignments too)
    public boolean placeWall(int row, int col, boolean isVertical) {
        if (QuoridorEngine.canPlaceWall(boardState, row, col, isVertical))
        {
            System.out.println("Wall is placeable.");
            BoardState tmp = QuoridorEngine.applyWall(boardState, currentPlayer+1, row, col, isVertical);
            // this is whacky but it will work, canPlaceWall does not check 
            // that the players can reach the finish line (aka the name is a misnomer)
            // applyWall returns the same object if we couldnt place the wall.
            if (tmp == boardState)
            {
                return false;
            }
            currentPlayer ^=1;
            boardState = tmp;
            return true;
        }
        System.out.println("Wall was not placeable at "+row+","+col);
        return false;
    }

    public int victor(){
        if (QuoridorEngine.hasWinner(boardState))
        {
            if(boardState.p2c == 0)
                return 2;
            return 1;
        }
        return 0;
    }
}
