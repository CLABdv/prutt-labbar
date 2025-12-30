import java.util.ArrayList;

public class Composite extends AbstractComponent {
    public Composite(double weight, String name) {
        this.weight=weight;
        this.name=name;
        children= new ArrayList<>();
    }
    
    public void add(Component C) {
        children.add(C);
    }
    
    public void remove(Component c) {
        children.remove(c);
    }
    
    public Component getChild(int index) {
        return children.get(index);
    }
}
