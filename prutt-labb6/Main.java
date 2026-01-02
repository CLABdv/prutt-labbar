import java.util.stream.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        NumberedItem[] array = {new NumberedItem<Integer>(-10,5),
                                new NumberedItem<Integer>(10,2),
                                new NumberedItem<Integer>(20,0),
                                new NumberedItem<Integer>(-20,3),
                                new NumberedItem<Integer>(11,-20),
                                new NumberedItem<Integer>(15,9),
                                new NumberedItem<Integer>(17,7)};
        List<NumberedItem<Integer>> list = Arrays.asList(array);
        Stream<NumberedItem<Integer>> stream = list.stream().sorted();
        stream.forEach(x -> System.out.println(x)); 
        System.out.println("\n");
        list.stream().sorted().map(x -> x.mapValue(y -> y*2)).forEach(x -> System.out.println(x));
    }
}
