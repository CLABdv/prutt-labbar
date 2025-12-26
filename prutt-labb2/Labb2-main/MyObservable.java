public interface MyObservable {
    void addObserver(MyObserver o);
    void removeObserver(MyObserver o);
    void notifyObservers();
}
