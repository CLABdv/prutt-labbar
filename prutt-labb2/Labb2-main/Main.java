public class Main {
    public static void main(String[] args) {
        if (args.length == 1 && args[0] == "text") {
            Model model = new Model();
            View textView = new TextView();
            Controller textController = new TextController(model, textView);
            
            textView.startingScreen(model);
            while(!QuoridorEngine.hasWinner(model.boardState)) {
                textController.performMove();            
            }
        }
        else {
            Model model = new Model();
            GUIView view = new GUIView(model);
            GUIController _ = new GUIController(model, view);
        }
    }
}