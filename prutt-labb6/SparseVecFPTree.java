import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

public class SparseVecFPTree <E extends Comparable<E>> extends TreeMap<Integer, E> implements SparseVec<E> {

    @Override
    public void add(E elem) {
        try {
            put(this.maxIndex()+1, elem); // appends
        } catch (Exception e) {
            put(0, elem);
        }
    }

    @Override
    public void add(int pos, E elem) {
        if(pos >= 0) put(pos, elem);
        else throw new IndexOutOfBoundsException();
    }

    public int indexOf(E elem) {
        for (Map.Entry<Integer, E> entry : this.entrySet()) {
            if (elem==entry.getValue()) {
                return entry.getKey();
            }
        }
        throw new NoSuchElementException();
    }

    public void removeAt(int pos) {
        remove(pos);
    }

    public void removeElem(E elem) {
        remove(indexOf(elem));
    }

    public int size() {
        return size();
    }

    public int minIndex() {
        return firstKey();
    }

    public int maxIndex() {
        return lastKey();
    }

    public E get(int pos) {
        return get(pos);        
    }

    public Object[] toArray() {
        Stream<Map.Entry<Integer, E>> sortedStream = entrySet().stream(); 
        return sortedStream.toArray();
    }

    // here we throw away the indices
    public List<E> sortedValues() {
        Stream<E> s = values().stream();
        return s.sorted().toList();
    }
    
    public String toString() {
        return entrySet().stream().map(e -> "Key: " + e.getKey() + " Value: " + e.getValue()).collect(Collectors.joining(","));
    }

    public <R extends Comparable<R>> SparseVecFPTree<R> mapValues(Function<E,R> f) {
        SparseVecFPTree<R> newVec = new SparseVecFPTree<R>();
        Stream<Map.Entry<Integer, E>> entryStream = entrySet().stream();
        entryStream.forEach(e -> newVec.add(e.getKey(), f.apply(e.getValue())));
        return newVec;
    }

    // note that this will throw an error if we try to add negative indices.
    // I am unsure how authority wants this function to be implemented.
    public SparseVecFPTree<E> mapIndices(Function<Integer, Integer> f) {
        SparseVecFPTree<E> newVec = new SparseVecFPTree<E>();
        Stream<Map.Entry<Integer, E>> entryStream = entrySet().stream();
        entryStream.forEach(e -> newVec.add(f.apply(e.getKey()), e.getValue()));
        return newVec;
    }
    public static void main(String[] args) {
        SparseVecFPTree<Integer> test = new SparseVecFPTree<Integer>();
        test.add(3);
        test.add(10, 5);
        System.out.println(test);
        test.add(12, 5);
        test.add(10, 42);
        System.out.println(test);
        test.removeAt(5);
        test.removeAt(12);
        test = test.mapValues((v -> v*2));
        System.out.println(test);
        SparseVecFPTree<Integer> newTest = new SparseVecFPTree<Integer>();
        test.entrySet().stream().filter((e -> e.getValue() < 10)).forEach((e -> newTest.add(e.getKey(), e.getValue())));
        System.out.println(newTest);
        System.out.println(newTest.keySet());
    }
}