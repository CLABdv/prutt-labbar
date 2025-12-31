import java.util.ArrayList;
public class AbstractComponent implements Component {
    protected double weight;
    protected String name;
    protected ArrayList <Component> children; // this is always empty for leaf nodes

    public double getWeight() {
        double sum = weight;
        if (children != null) {
            for (Component c : children) {
                sum+=c.getWeight();
            }
        }
        return sum;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");
        if (children != null) {
            for (Component c : children) {
               sb.append(c.toString());
            }
        }
        return sb.toString();
    }
    public String getName() {
        return name;
    }
}
