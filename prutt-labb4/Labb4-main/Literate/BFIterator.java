import java.util.Iterator;
import java.util.LinkedList;
public class BFIterator implements Iterator<Component>{
    private LinkedList<Component> queue;
    public BFIterator(Composite composite) { 
        queue = new LinkedList<Component>();
        queue.add(composite);
        queueBuilder(composite);
    }
    
    private void queueBuilder(Composite currentNode) {
        for (int i = 0; i < currentNode.children.size(); i++) {
            queue.add(currentNode.getChild(i));
        }

        for (int i = 0; i < currentNode.children.size(); i++) {
            Component maybeComposite = currentNode.getChild(i);
            if (maybeComposite instanceof Composite) {
                queueBuilder((Composite) maybeComposite);                
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
