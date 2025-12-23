import java.util.*;
 
/**
 * QuoridorEngine innehåller all spel-logik för Quoridor.
 * Den är stateless: alla metoder tar in ett BoardState som argument
 * och returnerar antingen en ny BoardState (vid förändring) eller ett booleskt svar.
 *
 * Designidén är att spelmotorn inte själv lagrar något tillstånd,
 * utan all information finns i BoardState. Detta gör den lättare att testa och återanvända.
 */
public final class QuoridorEngine {
    // Privat konstruktor: vi vill inte att någon ska skapa objekt av QuoridorEngine.
    private QuoridorEngine() {}

    /**
     * Kollar om en förflyttning är giltig för en spelare.
     * @param s aktuellt bräde
     * @param player spelare (1 eller 2)
     * @param toR ny rad dit spelaren vill flytta
     * @param toC ny kolumn dit spelaren vill flytta
     * @return true om draget är tillåtet, annars false
     */
    public static boolean isValidMove(BoardState s, int player, int toR, int toC) {
        // Kontrollera att målrutan är inom brädets gränser
        if (toR<0 || toR>=s.size || toC<0 || toC>=s.size)
            return false;

        // Hämta spelarens nuvarande position
        int fromR = (player==1)?s.p1r:s.p2r;
        int fromC = (player==1)?s.p1c:s.p2c;

        // Mål måste vara tom
        if (s.cells[toR][toC]!=0)
            return false;

        // Differens mellan start och mål
        int dr = toR - fromR;
        int dc = toC - fromC;

        // Fall 1: enkel förflyttning (ett steg upp, ner, vänster, höger)
        if (Math.abs(dr)+Math.abs(dc)==1) {
            return !isBlocked(s, fromR, fromC, toR, toC);
        }

        // Fall 2: hopp över motståndaren
        if (Math.abs(dr)+Math.abs(dc)==2) {
            int midR = (fromR+toR)/2;
            int midC = (fromC+toC)/2;
            int opp = (player==1)?2:1;

            // Det måste stå en motståndare i mitten
            if (s.cells[midR][midC]!=opp)
                return false;

            // Både vägen till mitten och från mitten till mål måste vara fria
            return !isBlocked(s, fromR, fromC, midR, midC) && !isBlocked(s, midR, midC, toR, toC);
        }

        // Övriga förflyttningar är ogiltiga
        return false;
    }

    /**
     * Utför en förflyttning och returnerar en ny BoardState.
     * Om draget inte är giltigt returneras det gamla brädet oförändrat.
     *
     * @param s aktuellt bräde
     * @param player spelare (1 eller 2)
     * @param toR ny rad dit spelaren vill flytta
     * @param toC ny kolumn dit spelaren vill flytta
     * @return BoardState nytt uppdaterade bräde
     */
    public static BoardState applyMove(BoardState s, int player, int toR, int toC) {
        if (!isValidMove(s, player, toR, toC)) return s;

        // Kopiera celler
        int[][] newCells = new int[s.size][s.size];
        for (int i=0; i<s.size; i++)
            System.arraycopy(s.cells[i],0,newCells[i],0,s.size);

        // Ta bort spelaren från den gamla positionen
        if (player==1) newCells[s.p1r][s.p1c]=0;
        else newCells[s.p2r][s.p2c]=0;

        // Placera spelaren på den nya positionen
        newCells[toR][toC]=player;

        // Returnera nytt BoardState med uppdaterad position
        if (player==1)
            return s.copyWith(newCells,null,null,toR,toC,null,null,null,null);
        else
            return s.copyWith(newCells,null,null,null,null,toR,toC,null,null);
    }


    //////

    /**
     * Kollar om en vägg kan placeras på en viss plats.
     * Tar hänsyn till överlappning och angränsande väggar med samma riktning.
     *
     * @param i radindex
     * @param j kolumnindex
     * @param vertical true om väggen är vertikal, false om horisontell
     */

    public static boolean canPlaceWall(BoardState s, int i, int j, boolean vertical) {
    // top-left index för en hel vägg måste ligga i 0..size-2 (eftersom väggen består av två segment)
    if (i < 0 || i >= s.size-1 || j < 0 || j >= s.size-1) return false;

    if (vertical) {
        // vertikal vägg täcker vWalls[i][j] och vWalls[i+1][j]
        // 1) Overlapp
        if (s.vWalls[i][j] || s.vWalls[i+1][j]) return false;

        // 2) Undvik "intilliggande" vertikala väggar som rör vid någon av segmenten (vänster/höger)
        if (j > 0 && (s.vWalls[i][j-1] || s.vWalls[i+1][j-1])) return false;
        if (j < s.size-2 && (s.vWalls[i][j+1] || s.vWalls[i+1][j+1])) return false;

        // 3) Korsning: en full horisontell vägg på samma plats får inte finnas
        //    En horisontell hel vägg här skulle vara hWalls[i][j] && hWalls[i][j+1]
        if (s.hWalls[i][j] && s.hWalls[i][j+1]) return false;

        return true;
    } else {
        // horisontell vägg täcker hWalls[i][j] och hWalls[i][j+1]
        // 1) Overlapp
        if (s.hWalls[i][j] || s.hWalls[i][j+1]) return false;

        // 2) Undvik intilliggande horisontella väggar (upp/ner)
        if (i > 0 && (s.hWalls[i-1][j] || s.hWalls[i-1][j+1])) return false;
        if (i < s.size-2 && (s.hWalls[i+1][j] || s.hWalls[i+1][j+1])) return false;

        // 3) Korsning: full vertikal vägg på samma plats får inte finnas
        //    En vertikal hel vägg här skulle vara vWalls[i][j] && vWalls[i+1][j]
        if (s.vWalls[i][j] && s.vWalls[i+1][j]) return false;

        return true;
    }
}

    /**
     * Lägger till en vägg (om det är tillåtet) och returnerar nytt BoardState.
     * Kontrollerar också att båda spelarna fortfarande har en möjlig väg till mållinjen.
     *
     * @param i radindex
     * @param j kolumnindex
     * @param vertical true om väggen är vertikal, false om horisontell
     */
    
public static BoardState applyWall(BoardState s, int player, int i, int j, boolean vertical) {
    if (!canPlaceWall(s, i, j, vertical)) return s;

    // Kopiera vägg-arrayer
    boolean[][] newH = new boolean[s.size-1][s.size];
    boolean[][] newV = new boolean[s.size][s.size-1];
    for (int r = 0; r < s.size-1; r++) System.arraycopy(s.hWalls[r], 0, newH[r], 0, s.size);
    for (int r = 0; r < s.size; r++)     System.arraycopy(s.vWalls[r], 0, newV[r], 0, s.size-1);

    // Sätt båda segmenten för den nya väggen
    if (vertical) {
        newV[i][j]     = true;
        newV[i+1][j]   = true;
    } else {
        newH[i][j]     = true;
        newH[i][j+1]   = true;
    }

    // Minska spelarens väggantal
    Integer p1Walls = null, p2Walls = null;
    if (player == 1) p1Walls = s.p1WallsLeft - 1;
    else              p2Walls = s.p2WallsLeft - 1;

    BoardState candidate = s.copyWith(null, newH, newV, null, null, null, null, p1Walls, p2Walls);

    // Kontrollera att båda spelare fortfarande kan nå sin mållinje
    if (!pathExists(candidate, 1) || !pathExists(candidate, 2))
        return s; // ogiltigt (stänger av spelbarheten), återgå till original

    return candidate;
}
    
  
    /**
     * Hjälpmetod: avgör om en rörelse mellan två rutor är blockerad av en vägg.
     */
    private static boolean isBlocked(BoardState s, int r1, int c1, int r2, int c2) {
        if (r1==r2) { // rörelse i sidled
            int minC = Math.min(c1,c2);
            return s.vWalls[r1][minC];
        } else if (c1==c2) { // rörelse upp/ner
            int minR = Math.min(r1,r2);
            return s.hWalls[minR][c1];
        }
        return false; // diagonala drag hanteras inte
    }

    /**
     * Hjälpmetod: kollar om en spelare fortfarande kan nå målraden.
     * Implementerad som en BFS (bredden-först-sökning).
     */
    private static boolean pathExists(BoardState s, int player) {
        int sr = player==1? s.p1r : s.p2r; // start-rad
        int sc = player==1? s.p1c : s.p2c; // start-kolumn
        int targetRow = player==1? s.size-1 : 0; // målraden
        boolean[][] visited = new boolean[s.size][s.size];

        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.add(new int[]{sr,sc});
        visited[sr][sc]=true;

        int[] dr = {-1,1,0,0}; // möjliga rörelser (radförändringar)
        int[] dc = {0,0,-1,1}; // möjliga rörelser (kolumnförändringar)

        while(!q.isEmpty()){
            int[] p = q.removeFirst();
            int r=p[0], c=p[1];

            // Om vi nått målraden är det OK
            if (r==targetRow) return true;

            // Kolla alla grannar
            for (int k=0; k<4; k++){
                int nr=r+dr[k], nc=c+dc[k];
                if (nr<0||nr>=s.size||nc<0||nc>=s.size) continue;
                if (visited[nr][nc]) continue;
                if (s.cells[nr][nc]!=0 && s.cells[nr][nc]!=(player)) continue;
                if (isBlocked(s,r,c,nr,nc)) continue;
                visited[nr][nc]=true;
                q.add(new int[]{nr,nc});
            }
        }
        return false; // ingen väg fanns
    }

    /**
     * Kollar om någon spelare har vunnit.
     * Spelare 1 vinner om den når sista raden,
     * spelare 2 vinner om den når första raden.
     */
    public static boolean hasWinner(BoardState s) {
        if (s.p1r==s.size-1) return true;
        if (s.p2r==0) return true;
        return false;
    }
}

