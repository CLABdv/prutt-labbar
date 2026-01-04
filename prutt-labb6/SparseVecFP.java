import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

public class SparseVecFP <E extends Comparable<E>> implements SparseVec<E> {
    private TreeMap<Integer, E> state;
    
    SparseVecFP() {
        state = new TreeMap<>();
    }

    public void add(E elem) {
        try {
            state.put(this.maxIndex()+1, elem); // appends
        } catch (Exception e) {
            state.put(0, elem);
        }
    }

    public void add(int pos, E elem) {
        if(pos >= 0) state.put(pos, elem);
        else throw new IndexOutOfBoundsException();
    }

    // Returns the first index which contains the element. We havent specified that
    // everything is unique, so i do not know why we do not return a list 
    // Also, if everything would be unique we could keep a reverse map
    public int indexOf(E elem) {
        for (Map.Entry<Integer, E> entry : state.entrySet()) {
            if (elem==entry.getValue()) {
                return entry.getKey();
            }
        }
        throw new NoSuchElementException();
    }

    public void removeAt(int pos) {
        state.remove(pos);
    }

    public void removeElem(E elem) {
        state.remove(indexOf(elem));
    }

    public int size() {
        return state.size();
    }

    public int minIndex() {
        return state.firstKey();
    }

    public int maxIndex() {
        return state.lastKey();
    }

    public E get(int pos) {
        return state.get(pos);
    }

    // basically the following in (a bit subpar) haskell
    // unsparse xs = reverse $ u (0, []) xs
    //     where u (_, l) [] = l
    //           u (i, l) ((ix, x):xs) =  u (ix+1, x : replicate (ix - i) 0 ++ l) xs
    public Object[] toArray() {
        Stream<Map.Entry<Integer, E>> sortedStream = state.entrySet().stream(); // should be sorted by key by default
        List<E> denseList = new LinkedList<E>();
        sortedStream.forEach(e -> {
            denseList.addAll(Collections.nCopies(e.getKey() - denseList.size(), null)); // note we compare index vs size i.e. length
            denseList.add(e.getValue());});
        return denseList.toArray();
    }

    // here we throw away the indices
    public List<E> sortedValues() {
        Stream<E> s = state.values().stream();
        return s.sorted().toList();
    }
    
    public String toString() {
        return state.entrySet().stream().map(e -> "Key: " + e.getKey() + " Value: " + e.getValue()).collect(Collectors.joining(","));
    }

    public <R extends Comparable<R>> SparseVecFP<R> mapValues(Function<E,R> f) {
        SparseVecFP<R> newVec = new SparseVecFP<R>();
        Stream<Map.Entry<Integer, E>> entryStream = state.entrySet().stream();
        entryStream.forEach(e -> newVec.add(e.getKey(), f.apply(e.getValue())));
        return newVec;
    }

    // note that this will throw an error if we try to add negative indices.
    // I am unsure how authority wants this function to be implemented.
    public SparseVecFP<E> mapIndices(Function<Integer, Integer> f) {
        SparseVecFP<E> newVec = new SparseVecFP<E>();
        Stream<Map.Entry<Integer, E>> entryStream = state.entrySet().stream();
        entryStream.forEach(e -> newVec.add(f.apply(e.getKey()), e.getValue()));
        return newVec;
    }
    public static void main(String[] args) {
        SparseVecFP<Integer> test = new SparseVecFP<Integer>();
        test.add(3, 42);
        test.add(10, 5);
        System.out.println(test);
        test.add(12, 5);
        test.add(10, 42);
        System.out.println(test);
        
        int i=0;
        for (Object o : test.toArray()) {
            System.out.print("index is " + i);
            System.out.println(", object is " + o);
            ++i;
        }

        test.removeAt(5);
        test.removeAt(12);
        test = test.mapValues((v -> v*2));
        System.out.println(test);
        SparseVecFP<Integer> newTest = new SparseVecFP<Integer>();
        test.state.entrySet().stream().filter((e -> e.getValue() < 10)).forEach((e -> newTest.add(e.getKey(), e.getValue())));
        System.out.println(newTest);
        System.out.println(newTest.state.keySet());
    }
}
