import java.net.*;
import java.io.*;
import java.util.*;

public class TextClient {
    public static void main(String[] args) {
        String command = "initial";
        try (
            Scanner s = new Scanner(System.in);
            Socket socket=new Socket("localhost",4713);
        ){
            // localhost är alias för IP-adress för den lokala datorn d.v.s. den datorn 
            // du kör detta program (vilket i detta fall är samma dator som serverprogrammet körs på)
            while(command != "") {
                BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter ut=new PrintWriter(socket.getOutputStream());
                command = s.nextLine();
                ut.println(command);
                ut.flush();
                System.out.println(in.readLine());
            }
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
}
