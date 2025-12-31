import java.util.ArrayList;
import java.util.Iterator;

public class Composite extends AbstractComponent implements Iterable<Component>{
    public Composite(double weight, String name) {
        this.weight = weight;
        this.name = name;
        children = new ArrayList<>();
    }
    
    public void add(Component C) {
        children.add(C);
    }
    
    public void remove(Component c) {
        children.remove(c);
        for (Component t : children) {
            if (t instanceof Composite) {
                ((Composite)t).remove(c);
            }            
        }
    }
    
    public Component getChild(int index) {
        return children.get(index);
    }

    public Iterator<Component> iterator() {
        return new BFIterator(this);
        //return new DFIterator(this);
    }
}
