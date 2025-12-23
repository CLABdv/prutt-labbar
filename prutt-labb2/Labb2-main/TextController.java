import java.util.Scanner;
// should probably make it such that base state (Model and View) is required by Controller (or a superclass thingy, idk i dont use java)
// and not variables only for TextController, but this doesnt seem to be required so
// the following solution is instead used,
public class TextController implements Controller{
    public Model model;
    public View view;
    private final Scanner s = new Scanner(System.in);
    
    TextController(Model m, View v) {
        model = m;
        view = v;
    }
    public void getPlayerMove() {
        int row=-1;
        int col=-1;
        while(true)
        {
            System.out.println("Enter VALID space seperated \"row col\\n\" you wish to move to:");
            try {
                row = Integer.parseInt(s.next())-1;
                col = Integer.parseInt(s.next())-1;
                if (model.makeMove(row, col)) {
                    break;
                }
                else {
                    System.out.println("Invalid move.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input.");
            }
        }
        view.showBoard(model);
        if (model.victor() != 0) {
            view.victoryScreen(model.victor());
        }
    }

    public void getWallMove() {
        boolean isVertical = false;
        int row=-1;
        int col=-1;
        while(true)
        {
            System.out.println("Do you want to place a vertical wall (y/n)?");
            try {
                String ans = s.next().toLowerCase();
                if (ans.equals("y"))
                {
                    isVertical = true;
                    break;
                }
                else if (ans.equals("n"))
                {
                    break;
                }
                // else wrong, keep looping
            } catch (Exception e) {
                System.out.println("Enter $ans \\in {y,n}$");
            }
        }
        while(true)
        {
            System.out.println("Enter top left corner of wall");
            try {
                row = Integer.parseInt(s.next())-1;
                col = Integer.parseInt(s.next())-1;
            } catch (Exception e) {
                System.out.println("Invalid input.");
            }
            if (model.placeWall(row, col, isVertical)){
                break;
            }
            else {
                System.out.println("Invalid wall placement.");
            }
        }
        view.showBoard(model);
    }

    public void performMove() {
        while(true)
        {
            System.out.println("type 'm' if you want to move player, 'w' if you want to place wall");
            try {
                String ans = s.next().toLowerCase();
                if (ans.equals("m"))
                {
                    getPlayerMove();
                    break;
                }
                else if (ans.equals("w"))
                {
                    getWallMove();
                    break;
                }
                // else wrong, keep looping
            } catch (Exception e) {
                System.out.println("Enter $ans \\in {y,n}$");
            }
        }
    }
}
