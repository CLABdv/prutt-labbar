import lunarLanderPackage.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LunarLanderSimulatorG extends JFrame implements ActionListener, KeyListener, LunarLanderListener{
    private String simState = "Start Simulation";
    private JTextArea text;
    private LunarLander ll;
    private JButton controlButton;
    private String mode="manual";
    public LunarLanderSimulatorG(String initMode){ // boilerplate to set up window when we construct object of class
        setTitle("Lunar Lander");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); // for everything to not resize all the time

        text = new JTextArea("");
        text.setEditable(false);
        text.setFocusable(false);
        panel.add(text); 
        panel.setFocusable(true);
        panel.setPreferredSize(new Dimension(500,500));

        controlButton = new JButton(simState);
        controlButton.addActionListener(this);
        controlButton.setFocusable(false);
        panel.add(controlButton,BorderLayout.SOUTH); 

        add(panel);
        panel.requestFocusInWindow();
        pack(); // for window not to get messed up when floating it
        setVisible(true);

        panel.addKeyListener(this);
        ll = new LunarLander();
        ll.setListener(this);
        

        setSize(500,500);
        mode = initMode;
    }
    public void onShipUpdate(ShipEvent e) {
        double h = e.getHeight();
        double v = e.getVelocity();
        text.setText("Height: " + h + "\n" +
        "Velocity: " + v + "\n" +
        "Fuel: " + e.getFuel() + "\n" + 
        "State: " + e.getState()+"\n");  
        if (e.getState() == "Crashed")
        {
            simState = "Exit";
            controlButton.setText(simState);
        }
        if (mode == "autopilot")
        {
            double reach=0;
            double vtemp=v;
            while(simState == "Start Engine" && vtemp <= 0)
            {
                reach+=vtemp;
                vtemp+=0.5;
                if (h+reach <= -v)
                {
                    System.out.println("reach = "+ reach);
                    simState = "Shut Down Engine";
                    controlButton.setText(simState);
                    ll.startEngine();
                }
            }
            if (simState != "Crashed" && simState != "Landed" && v >= -1.0) // we overshot the landing (maybe)
            {
                System.out.println("turning off engine");
                simState = "Start Engine";
                controlButton.setText(simState);
                ll.shutDownEngine();
            }
        }
    }
    
    // "när användaren trycker ner och släpper" tolkas som att när användaren 
    // (Trycker ner och släpper tangent) så gör vi något en gång, dvs inte på både nertryck och släpp
    // notera att startengine / stopengine är buggad, man får super mycket speed/position om man spammar
    public void keyPressed(KeyEvent e)
    {
        // pass
    }

    public void keyReleased(KeyEvent e)
    {
        if (simState == "Start Simulation" || simState == "Exit")
        {
            return;
        }
        int keyCode = e.getKeyCode();
        switch (keyCode){
            case KeyEvent.VK_UP:
                simState="Shut Down Engine";
                ll.startEngine();
                break;
            case KeyEvent.VK_DOWN:
                simState="Start Engine";
                ll.shutDownEngine();
                break;
            default:
                break;
        }
        controlButton.setText(simState);
    }

    public void keyTyped(KeyEvent e)
    {
        //pass
    }
    
    public void actionPerformed(ActionEvent e)
    {
        switch (simState)
        {
            case "Start Simulation":
                ll.run();
                simState = "Start Engine";
                break;
            case "Shut Down Engine":
                ll.shutDownEngine();
                simState = "Start Engine";
                break;
            case "Start Engine":
                ll.startEngine();
                simState = "Shut Down Engine";
                break;
            case "Exit":
                System.exit(0);
                break;
        }
        controlButton.setText(simState);
    }
    
    public static void main(String[] args) {
        if (args.length == 0) {
            new LunarLanderSimulatorG("manual");
        }
        else if (args.length == 1 && args[0].equals("autopilot")) {
            new LunarLanderSimulatorG("autopilot"); 
        }
        else {
            System.out.println("Invalid arguments"+ args[0]);
            return;
        }
    }
}
