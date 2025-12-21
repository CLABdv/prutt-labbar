import lunarLanderPackage.*;
import java.util.Scanner;
public class LunarLanderSimulator implements LunarLanderListener{
        public void onShipUpdate(ShipEvent e){
            System.out.println("Height: " + e.getHeight() + "\n" +
            "Velocity: " + e.getVelocity() + "\n" +
            "Fuel: " + e.getFuel() + "\n" + 
            "State: " + e.getState()+"\n");  
    }
    public static void main(String[] args) { 
        LunarLander lunarLander = new LunarLander();
        LunarLanderSimulator lls = new LunarLanderSimulator();
        lunarLander.setListener(lls);
        lunarLander.run();
        Scanner s = new Scanner(System.in);
        for (;;){
            char action = Character.toLowerCase(s.next().charAt(0)); // kind of messy because if we enter "abcdefg..." + "\n" it will be the same as entering "a\n"
            if (action == 'p'){
                lunarLander.startEngine();
            }
            if (action == 'a'){
                lunarLander.shutDownEngine();
            }
            if (action == 'e'){ // exit
                s.close();
                return;
            }
        } 
    }
}