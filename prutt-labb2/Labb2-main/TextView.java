public class TextView implements View{
    private final char hWall = '-';
    private final char vWall = '|';
    private final char noWall = ' ';
    private final char p1 = '1';
    private final char p2 = '2';
    private final char boardSquare = '∘'; 
    private final char corner = 'x';
    public TextView(){}
    
    public void showBoard(Model model) {
        char currentBoard[][] = new char[17][17]; 
        // empty board to start with
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                if (i%2 == 0 && j%2 == 0) {
                    currentBoard[i][j]=boardSquare;
                }
                else if (i%2 == 1 && j%2 == 1) {
                    currentBoard[i][j]=corner;
                }
                else {
                    currentBoard[i][j]=noWall;
                }
            }
        }
        currentBoard[2*model.boardState.p1r][2*model.boardState.p1c] = p1;
        currentBoard[2*model.boardState.p2r][2*model.boardState.p2c] = p2;
        
        // vertical walls
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 8; j++){
                if (model.boardState.vWalls[i][j]) {
                    currentBoard[2*i][2*j+1]=vWall;
                }
            }
        }

        // horizontal walls
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 9; j++){
                if (model.boardState.hWalls[i][j]) {
                    currentBoard[2*i+1][2*j]=hWall;
                }
            }
        }
        
        for (int i = 0; i < 17; i++){
            if (i%2 == 0) {
                System.out.print((i/2+1) + "   ");
            }
            else {
                System.out.print("  "+(i/2+1) + " ");
            }
            for (int j = 0; j < 17; j++){
                System.out.print(currentBoard[i][j]);                
            }
            System.out.println();
        }
        // wall numberings
        System.out.print("    ");
        for (int i= 1; i < 9; i++) {
            System.out.print(" "+i);
        }
        System.out.println();

        // tile numberings
        System.out.print("    ");
        for (int i= 1; i < 9; i++) {
            System.out.print(i+" ");
        }
        System.out.println("9");
        
        System.out.println("\nCurrently player "+ (model.currentPlayer==0?"one's":"two's")+" turn.");        
        System.out.println("Player one has "+model.boardState.p1WallsLeft+" walls left.");
        System.out.println("Player two has "+model.boardState.p2WallsLeft+" walls left.");
    }

    public void victoryScreen(int winner) {
        System.out.println("\nPlayer "+ ((winner==1)?"one":"two")+" is victorious.");
    }
    public void startingScreen(Model model) {
        System.out.println("Welcome to Quoridor.");
        showBoard(model);
    }
}
