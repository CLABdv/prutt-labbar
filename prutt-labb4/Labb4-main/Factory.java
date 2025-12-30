import static org.junit.Assert.*;
import human.*;

public class Factory {
    public static void main(String[] args) {
        // Human h = new Human(){}; // this throws compilation error
        // Human m = new Man("this is not visible");
        
        Human billie = Human.create("1234567890");
        Human bob = Human.create("1234567892");
        Human bella = Human.create("1234567891");

        assertTrue(billie instanceof NonBinary);
        assertTrue(bob instanceof Man);
        assertTrue(bella instanceof Woman);
        
        System.out.println("all are correct instances");
    }
}
