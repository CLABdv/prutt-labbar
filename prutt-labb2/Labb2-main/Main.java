public class Main {
    public static void main(String[] args) {
        if (args.length == 1 && args[0] == "text") { // textview
            Model model = new Model();
            View textView = new TextView();
            Controller textController = new TextController(model, textView);
            
            textView.startingScreen(model);
            while(!QuoridorEngine.hasWinner(model.boardState)) {
                textController.performMove();            
            }
        }
        else if (args.length == 1 && args[0] == "x2") { // observer
            ObservableModel myObservable = new ObservableModel();
            MyView myView = new MyView(myObservable);
            myObservable.addObserver(myView);
            myObservable.notifyObservers();
            
            myObservable.makeMove(1, 4); // note that the view is automatically updated
        }
        else { // guiview
            Model model = new Model();
            GUIView view = new GUIView(model);
            GUIController _ = new GUIController(model, view);
        }
    }
}