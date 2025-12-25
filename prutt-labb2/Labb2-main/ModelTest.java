import org.junit.Test;
import static org.junit.Assert.*;

public class ModelTest {
    @Test
    public void correctStart() {
        Model model = new Model();
        assertNotNull(model.boardState);
        assertEquals(0, model.currentPlayer);
        assertEquals(4, model.boardState.p1c);
        assertEquals(0, model.boardState.p1r);
        assertEquals(4, model.boardState.p2c);
        assertEquals(8, model.boardState.p2r);
        assertEquals(10, model.boardState.p1WallsLeft);
        assertEquals(10, model.boardState.p2WallsLeft);
    }
                        
    @Test
    public void invalidMove() {
        Model model = new Model();
        assertFalse(model.makeMove(-1,-1));
    }

    @Test
    public void validMove() {
        Model model = new Model();
        assertTrue(model.makeMove(1,4));
    }
    
    // in the real Quoridor this would work
    @Test 
    public void wallsAdjacent(){
        Model model = new Model();
        assertTrue(model.placeWall(0,0, true));
        assertFalse(model.placeWall(0,1, true));
    }
    
    @Test
    public void winners() {
        Model model = new Model();
        assertEquals(0, model.victor());
        BoardState tmp = BoardState.initial(9, 10);
        tmp = new BoardState(tmp.size, tmp.cells, tmp.hWalls, tmp.vWalls, 8, tmp.p1c, tmp.p2r, tmp.p2c, tmp.p1WallsLeft, tmp.p2WallsLeft);
        model.boardState=tmp;
        assertEquals(1,model.victor());
        tmp = new BoardState(tmp.size, tmp.cells, tmp.hWalls, tmp.vWalls, 2, tmp.p1c, 0, tmp.p2c, tmp.p1WallsLeft, tmp.p2WallsLeft);
        model.boardState=tmp;
        assertEquals(2,model.victor());
    }

}
