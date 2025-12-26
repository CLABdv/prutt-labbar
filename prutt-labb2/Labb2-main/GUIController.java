import java.awt.event.*;

public class GUIController implements Controller {
    GUIView guiView;
    Model model;
    
    public GUIController(Model m, GUIView gV) {
        // note:
        // these two work completely different things in the GUI than the text based version
        guiView = gV;
        model = m;
        getPlayerMove();
        getWallMove();
    }

    public void getPlayerMove() {
        // we connect the board square buttons to doing stuff here
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                final int javahoorayi = i;
                final int javahoorayj = j;
                guiView.boardSquares[2*i][2*j].addActionListener((ActionEvent e) -> {model.makeMove(javahoorayi, javahoorayj); guiView.showBoard(model);});
            }
        }
    }
    

    public void getWallMove() {
        // we connect the board square buttons to doing stuff here
        
        // vertical walls
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 8; j++) {
                final int javahoorayi = i;
                final int javahoorayj = j;
                guiView.boardSquares[2*i][2*j+1].addActionListener((ActionEvent e) -> {model.placeWall(javahoorayi, javahoorayj, true); guiView.showBoard(model);});
            }
        }

        // horizontal walls
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 9; j++){
                final int javahoorayi = i;
                final int javahoorayj = j;
                guiView.boardSquares[2*i+1][2*j].addActionListener((ActionEvent e) -> {model.placeWall(javahoorayi, javahoorayj, false); guiView.showBoard(model);});
            }
        }
    }

    public void performMove() {
        // unused for GUI
    }

    public void actionPerformed(ActionEvent e) {
        // also unused for GUI
    }

}
