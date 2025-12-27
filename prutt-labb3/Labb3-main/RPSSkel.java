import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.*;

class RPSSkel extends JFrame implements ActionListener {
    Gameboard myboard, computersboard;
    int counter; // To count ONE ... TWO  and on THREE you play
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    JButton closebutton;

    RPSSkel () {
		String name = "me";
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		closebutton = new JButton("Close");
		closebutton.addActionListener(e -> dispose()); // kopplar automatiskt nre från server när program stänger

		myboard = new Gameboard(name, this); // Must be changed
										   
		computersboard = new Gameboard("Computer");
		JPanel boards = new JPanel();
		boards.setLayout(new GridLayout(1,2));
		boards.add(myboard);
		boards.add(computersboard);
		add(boards, BorderLayout.CENTER);
		add(closebutton, BorderLayout.SOUTH);
		setSize(350, 650);
		pack(); // for some reason nothing shows up if i do not pack(). this issue is recurring
		setVisible(true);

		counter = 0;
		connectServer(name);
    }
	
	void connectServer(String name) {
        try {
			socket=new Socket("localhost",4713);
            // localhost är alias för IP-adress för den lokala datorn d.v.s. den datorn 
            // du kör detta program (vilket i detta fall är samma dator som serverprogrammet körs på)
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
			out.println(name);
            out.flush();
            System.out.println(in.readLine());
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
	
	int winner(String p1, String p2) {
		if (p1.equals(p2)) return 0; // no winner
		return switch (p1) {
			case "STEN" -> p2.equals("SAX")? 1 : 2;
			case "SAX" -> p2.equals("PASE")? 1 : 2; 
			case "PASE" -> p2.equals("STEN")? 1 : 2; 
			default -> throw new IllegalArgumentException();
		};
	}

    public static void main (String[] u) {
		new RPSSkel();
    }

	public void actionPerformed(ActionEvent e) {
		if (counter == 0)
		{
			counter++;
			myboard.resetColor();
			computersboard.resetColor();
			myboard.setLower("ETT..."); 
			computersboard.setLower("ETT...");
		}
		else if (counter == 1) {
			counter++;
			myboard.setLower("TVÅ..."); 
			computersboard.setLower("TVÅ...");
			return;
		}
		else {
			counter=0;
			String id = e.getActionCommand();
			myboard.markPlayed(id);
			String computerMove;
			try {
				out.println(id);
				out.flush();
				computerMove = in.readLine();
				computersboard.markPlayed(computerMove);
				switch (winner(id, computerMove)) {
					case 0:
						myboard.setLower("LIKA"); 
						computersboard.setLower("LIKA");
						break;
					case 1:
						myboard.setLower("VANN"); 
						myboard.wins();
						computersboard.setLower("FÖRLORADE");
						break;
					case 2:
						myboard.setLower("FÖRLORADE"); 
						computersboard.setLower("VANN");
						computersboard.wins();
						break;
						
				}
			} catch (Exception ee) {
				System.err.println(ee);
			}
		}
	}
}
