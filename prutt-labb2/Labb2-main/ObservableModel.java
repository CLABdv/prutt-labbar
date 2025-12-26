import java.util.ArrayList;
public class ObservableModel extends Model implements MyObservable {
    private ArrayList<MyObserver> observerlist = new ArrayList<>();

    public void addObserver(MyObserver o) {
        observerlist.add(o);
    }

    public void removeObserver(MyObserver o) {
        observerlist.remove(o);
    }

    public void notifyObservers() {
        for (MyObserver observer : observerlist) {
            observer.update();
        }
    }
    
    public boolean placeWall(int row, int col, boolean isVertical) {
        boolean tmp = super.placeWall(row, col, isVertical);
        notifyObservers();
        return tmp;
    }

    
    public boolean makeMove(int row, int col) {
        boolean tmp = super.makeMove(row, col);
        notifyObservers();
        return tmp;
    }
}
