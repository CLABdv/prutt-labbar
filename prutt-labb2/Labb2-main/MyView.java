public class MyView extends GUIView implements MyObserver {

    private Model model;
    public MyView(Model m) {
        super(m);
        model = m;
    }

    public void update() {
        showBoard(model);
    }

}
