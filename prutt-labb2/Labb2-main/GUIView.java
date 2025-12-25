import javax.swing.*;
import java.awt.*;

public class GUIView extends JFrame implements View {
    public JButton[][] boardSquares;
    public JTextArea gameInfo;

    public GUIView(Model model) {
        setTitle("Quoridor-ish");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLayout(new BorderLayout());

        gameInfo = new JTextArea("this text should never be seen\n\n\n\n");
        gameInfo.setEditable(false);
        gameInfo.setLineWrap(true);
        add(gameInfo, BorderLayout.NORTH);
        JPanel boardPanel = new JPanel(new GridBagLayout());
        boardSquares = new JButton[17][17];
        GridBagConstraints gbc = new GridBagConstraints();

        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                boardSquares[i][j] = new JButton();
                gbc.gridx = j;
                gbc.gridy = i;
                if (i % 2 == 0 && j % 2 == 0) {
                    boardSquares[i][j].setPreferredSize(new Dimension(50,50));
                } else if (i % 2 == 0) {
                    boardSquares[i][j].setPreferredSize(new Dimension(10,50));
                } else if (j % 2 == 0) {
                    boardSquares[i][j].setPreferredSize(new Dimension(50,10));
                } 
                else { 
                    boardSquares[i][j].setPreferredSize(new Dimension(10,10));
                    boardSquares[i][j].setBackground(Color.WHITE);;
                    boardSquares[i][j].setEnabled(false);
                }
                boardPanel.add(boardSquares[i][j], gbc);
                }
            }
        add(boardPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        add(controlPanel, BorderLayout.SOUTH);

        pack();
        setMinimumSize(new Dimension(500, 550));
        setLocationRelativeTo(null);
        showBoard(model);
        startingScreen(model);
        setVisible(true);
    }

    public void showBoard(Model model) {
        // logic is basically a copy paste of textview
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                boardSquares[i][j].setBackground(Color.WHITE);;
            }
        }

        boardSquares[2*model.boardState.p1r][2*model.boardState.p1c].setBackground(Color.BLUE);
        boardSquares[2*model.boardState.p2r][2*model.boardState.p2c].setBackground(Color.MAGENTA);
       
        // vertical walls
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 8; j++){
                if (model.boardState.vWalls[i][j]) {
                    boardSquares[2*i][2*j+1].setBackground(Color.BLACK);;
                }
            }
        }

        // horizontal walls
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 9; j++){
                if (model.boardState.hWalls[i][j]) {
                    boardSquares[2*i+1][2*j].setBackground(Color.BLACK);;
                }
            }
        }
        if (model.victor() == 0) {
            gameInfo.setText("\nCurrently player "+ (model.currentPlayer==0?"one's":"two's")+" turn." +
                "Player one has "+model.boardState.p1WallsLeft+" walls left." +
                "Player two has "+model.boardState.p2WallsLeft+" walls left.");
        }
        else {
            victoryScreen(model.victor());
        }
    }

    public void victoryScreen(int winner) {
        gameInfo.setText("Player "+ (winner==1?"one":"two")+" is victorious.");
    }

    public void startingScreen(Model model) {
        gameInfo.setText(" Welcome to Quoridor.\n Rules: You may place a wall or move your " +
            "player to an adjacent square during your turn. \n Blue is starting player. " +
            "Selected wall is top left corner of placed wall.");
    }

}
