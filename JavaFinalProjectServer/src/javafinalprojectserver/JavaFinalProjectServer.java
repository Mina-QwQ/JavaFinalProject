/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javafinalprojectserver;

/**
 *
 * @author meinaxie
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class JavaFinalProjectServer {

    /**
     * @param args the command line arguments
     */
    static Vector<Socket> clients = new Vector<>();
    static int clickedStartButton = 0;
    static int total = 0;
    static int gotans = 0;
    
    public static void main(String[] args) {
        int clientCount = 0;
        Socket player1;
        Socket player2;
        PrintStream p1sout;
        PrintStream p2sout;
        try {
            ServerSocket ss = new ServerSocket(5190);
            while (clientCount < 2) { //connect up to two clients
                Socket client_sock = ss.accept();
                new ProcessConnection(client_sock).start();
                clients.add(client_sock);
                clientCount++;
            }
            System.out.println("here");
            player1 = clients.get(0);
            player2 = clients.get(1);
            p1sout = new PrintStream(player1.getOutputStream());
            p2sout = new PrintStream(player2.getOutputStream());
            /*
            Socket winner = whowon(player1, player2);
            while ((winner != player1) && (winner != player2)){
                p1sout.println("You lost"); //send to all clients
                p2sout.println("You won"); //send to all clients
                winner = whowon(clients.get(0), clients.get(1));
            }
            if (winner == player1){
                p1sout.print("You won");
                p2sout.print("You lost");
            }else{
                p1sout.print("tie");
                p2sout.print("tie");
            }*/
            
        } catch (IOException ex) {}
        
      

          
    }
    
        
    static Socket whowon(Socket player1, Socket player2){
        try{
            Scanner p1Input = new Scanner(player1.getInputStream());
            int bunny1 = p1Input.nextInt();
            int guess1 = p1Input.nextInt();
            
            Scanner p2Input = new Scanner(player2.getInputStream());
            int bunny2 = p2Input.nextInt();
            int guess2 = p2Input.nextInt();
            
            int ans = bunny1 + bunny2;
            if ((ans == guess1) && (ans != guess2)){
                return player1;
            }
            else if((ans != guess1) && (ans == guess1)){
                return player2;
            }
            
            
            //send each client their oppoenent's guess to display all bunnies 

        }catch (IOException ex) {
            System.out.println("Could not get input stream");
        }
        
        return null;
    }
  
}
        
        
//            ServerSocket ss = new ServerSocket(5190);
//            
//            
//            
//            Socket player1 = ss.accept();
//            System.out.println("player 1 is in");
//
//            Socket player2 = ss.accept();
//            System.out.println("player 2 is in");
//                
//            PrintStream p1sout = new PrintStream(player1.getOutputStream());
//            PrintStream p2sout = new PrintStream(player2.getOutputStream());
//            Scanner p1sin = new Scanner(player1.getInputStream());
//            Scanner p2sin = new Scanner(player2.getInputStream());
//            
//            //tell both players they are ready to play each other and set start button to green
//            //on players' windows 
//
//            p1sout.println("hi");
//            p2sout.println("hi"); // when both player is online
//            
//            p1sin.nextLine();
//            p2sin.nextLine(); // when both players click start
//            
//            System.out.println("here");
//            
//            p1sout.println("start");
//            p2sout.println("start");
//            
//            
            


class ProcessConnection extends Thread {
    Socket client_sock;
    String username;
    PrintStream sout;
    ProcessConnection(Socket newClientSock) {
        client_sock = newClientSock;
    }
    public void run() {
        try {
           
            //listen to client messages 
            Scanner sin = new Scanner(client_sock.getInputStream());
            
            //store all outputs for easy server access
            sout = new PrintStream(client_sock.getOutputStream());
            
            //check if there are two clients connected...
            if (JavaFinalProjectServer.clients.size() == 2) {
                PrintStream eachSout;
                for (int i = 0; i < JavaFinalProjectServer.clients.size(); i++){
                    eachSout = new PrintStream(JavaFinalProjectServer.clients.get(i).getOutputStream());
                    eachSout.println("two clients connected"); //send to all clients
                    //System.out.println("two clients connected");
                } 
            } 
            String line;
            int correct = 0;
            
            line = sin.nextLine(); // 'start' from clicking start button
            while (correct != 1){
                JavaFinalProjectServer.clickedStartButton++;
                //System.out.println(JavaFinalProjectServer.clickedStartButton);
            
                if (JavaFinalProjectServer.clickedStartButton == 2) { //if both clicked start
                    PrintStream eachSout;
                    for (int i = 0; i < JavaFinalProjectServer.clients.size(); i++){
                        eachSout = new PrintStream(JavaFinalProjectServer.clients.get(i).getOutputStream());
                        eachSout.println("both clients clicked start"); //send to all clients
                        System.out.println("both clients clicked start");
                    } 
                    JavaFinalProjectServer.clickedStartButton = 0;
                }
                /*while (JavaFinalProjectServer.clickedStartButton != 2){
                    continue;
                }*/
                System.out.println("starting of getting ans");

                int numBunnies = sin.nextInt();
                System.out.println("Num of Bunnies "+numBunnies);
                JavaFinalProjectServer.total += numBunnies;
                int guess = sin.nextInt();
                JavaFinalProjectServer.gotans++;
                System.out.println(JavaFinalProjectServer.gotans);
                while(JavaFinalProjectServer.gotans < 2){
                    continue;
                }
                if(guess == JavaFinalProjectServer.total){
                    sout.print(1);
                    correct = 1;
                    // stop the clients
                }
                else{
                    sout.print(0);
                    correct = 0;
                }
                System.out.println("Guess: "+JavaFinalProjectServer.total);
                JavaFinalProjectServer.total = 0;
            }
            
           
            
        } catch(IOException e) {}
    }
    
            
}