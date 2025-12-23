import java.util.Arrays;

/**
 * BoardState representerar ett "snapshot" av hela spelbrädet vid ett givet tillfälle.
 * Klassen är "immutable" i den meningen att alla fält är final,
 * men nya BoardState kan skapas som kopior med förändringar.
 */
public final class BoardState {
    // Storleken på brädet (antal rutor i en rad/kolumn), t.ex. 9 för ett 9x9-bräde.
    public final int size;

    // Tvådimensionell matris som visar vilka rutor som är tomma eller innehåller en spelare:
    // 0 = tom, 1 = spelare 1, 2 = spelare 2.
    public final int[][] cells;

    // Horisontella väggar: hWalls[r][c] = true om det finns en horisontell vägg mellan rad r och r+1,
    // i kolumn c. Har storlek (size-1) x size.
    public final boolean[][] hWalls;

    // Vertikala väggar: vWalls[r][c] = true om det finns en vertikal vägg mellan kolumn c och c+1,
    // i rad r. Har storlek size x (size-1).
    public final boolean[][] vWalls;

    // Position för spelare 1: rad (p1r), kolumn (p1c).
    public final int p1r, p1c;

    // Position för spelare 2: rad (p2r), kolumn (p2c).
    public final int p2r, p2c;

    // Hur många väggar spelare 1 respektive spelare 2 har kvar att placera.
    public final int p1WallsLeft, p2WallsLeft;

    /**
     * Skapar ett nytt BoardState med givna parametrar.
     * Alla fält måste skickas in och sparas oförändrade (immutable design).
     */
    public BoardState(int size, int[][] cells, boolean[][] hWalls, boolean[][] vWalls,
                      int p1r, int p1c, int p2r, int p2c, int p1WallsLeft, int p2WallsLeft) {
        this.size = size;
        this.cells = cells;
        this.hWalls = hWalls;
        this.vWalls = vWalls;
        this.p1r = p1r; this.p1c = p1c; this.p2r = p2r; this.p2c = p2c;
        this.p1WallsLeft = p1WallsLeft; this.p2WallsLeft = p2WallsLeft;
    }

    /**
     * Skapar ett nytt spelbräde i startläge.
     * Spelare 1 placeras på första raden (mitten),
     * spelare 2 placeras på sista raden (mitten).
     * Alla väggar är från början tomma.
     *
     * @param size storleken på brädet (t.ex. 9 för 9x9).
     * @param wallsPerPlayer hur många väggar varje spelare får från början.
     */
    public static BoardState initial(int size, int wallsPerPlayer) {
        int[][] cells = new int[size][size];

        // Startpositioner i mitten av första och sista raden
        int p1r = 0, p1c = size/2;
        int p2r = size-1, p2c = size/2;
        cells[p1r][p1c] = 1;
        cells[p2r][p2c] = 2;

        // Inga väggar vid start
        boolean[][] h = new boolean[size-1][size];
        boolean[][] v = new boolean[size][size-1];

        return new BoardState(size, cells, h, v, p1r,p1c,p2r,p2c,wallsPerPlayer,wallsPerPlayer);
    }

    /**
     * Skapar en ny kopia av brädet (deep copy).
     * Används för att kunna göra drag utan att förändra det gamla BoardState.
     *
     * Parametrar som skickas in kan vara null – då används värdena från det nuvarande BoardState.
     * Parametrar som inte är null ersätter motsvarande värden.
     *
     * @param newCells    nytt cells-arr (eller null för att behålla det gamla)
     * @param newH        nytt hWalls-arr (eller null)
     * @param newV        nytt vWalls-arr (eller null)
     * @param newP1r      ny rad för spelare 1 (eller null)
     * @param newP1c      ny kolumn för spelare 1 (eller null)
     * @param newP2r      ny rad för spelare 2 (eller null)
     * @param newP2c      ny kolumn för spelare 2 (eller null)
     * @param newP1Walls  nytt antal väggar kvar för spelare 1 (eller null)
     * @param newP2Walls  nytt antal väggar kvar för spelare 2 (eller null)
     *
     * @return en ny BoardState med uppdaterade värden.
     */
    public BoardState copyWith(int[][] newCells, boolean[][] newH, boolean[][] newV,
                               Integer newP1r, Integer newP1c, Integer newP2r, Integer newP2c,
                               Integer newP1Walls, Integer newP2Walls) {

        // Kopiera cells (antingen nytt arr eller befintligt)
        int[][] c = new int[size][size];
        for (int i=0;i<size;i++)
            System.arraycopy(newCells!=null?newCells[i]:this.cells[i],0,c[i],0,size);

        // Kopiera horisontella väggar
        boolean[][] hh = new boolean[size-1][size];
        for (int i=0;i<size-1;i++)
            System.arraycopy(newH!=null?newH[i]:this.hWalls[i],0,hh[i],0,size);

        // Kopiera vertikala väggar
        boolean[][] vv = new boolean[size][size-1];
        for (int i=0;i<size;i++)
            System.arraycopy(newV!=null?newV[i]:this.vWalls[i],0,vv[i],0,size-1);

        // Returnera nytt BoardState där null-parametrar ersätts med nuvarande värden
        return new BoardState(size, c, hh, vv,
                newP1r!=null?newP1r:this.p1r, newP1c!=null?newP1c:this.p1c,
                newP2r!=null?newP2r:this.p2r, newP2c!=null?newP2c:this.p2c,
                newP1Walls!=null?newP1Walls:this.p1WallsLeft, newP2Walls!=null?newP2Walls:this.p2WallsLeft);
    }
}


