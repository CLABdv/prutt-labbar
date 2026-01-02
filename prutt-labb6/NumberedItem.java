import java.util.function.*;

public class NumberedItem<T> implements Comparable<NumberedItem<T>>{
    final int index;
    final T t;

    NumberedItem(int index, T t) {
        this.t=t;
        if (index < 0) {
            this.index = 0;
        }
        else {
            this.index = index;
        }
    }

    public int compareTo(NumberedItem<T> t2) {
        return this.index - t2.index;
    }
    
    public String toString() {
        return "Index: " + index + " Value: " + t;
    }

    public <R> NumberedItem<R> mapValue(Function<T, R> f) {
        return new NumberedItem<>(index, f.apply(t));
    }
}
