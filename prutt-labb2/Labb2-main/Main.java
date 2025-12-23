public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        View textView = new TextView();
        Controller textController = new TextController(model, textView);
        
        textView.startingScreen(model);
        while(!QuoridorEngine.hasWinner(model.boardState)) {
            textController.performMove();            
        }
    }
}