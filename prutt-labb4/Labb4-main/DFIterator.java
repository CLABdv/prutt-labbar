import java.util.Iterator;
import java.util.LinkedList;
public class DFIterator implements Iterator<Component>{
    private LinkedList<Component> queue;
    
    // build the queue
    public DFIterator(Composite composite) {
        queue = new LinkedList<Component>();
        queue.add(composite);
        queueBuilder(composite);
    }
    
    private void queueBuilder(Composite currentNode) {
        for (Component c : currentNode.children) {
            queue.add(c);
            if (c instanceof Composite) { 
                queueBuilder((Composite) c);         
            }
        }
    }

    public boolean hasNext() {
        return !queue.isEmpty();
    }

    public Component next() {
        return queue.pop();
    }
}
