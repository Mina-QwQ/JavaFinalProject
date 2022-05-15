/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javafinalprojectserver;

/**
 *
 * @author meinaxie and winniezheng
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
    static Socket player1;
    static Socket player2;
    //static int[] guesses;
    static boolean gameOver = false;
    
    public static void main(String[] args) {
        int clientCount = 0;
        PrintStream p1sout;
        PrintStream p2sout;
        //guesses = new int[2];
        
        
        
        try {
            ServerSocket ss = new ServerSocket(5190);
            while (clientCount < 2) { //connect up to two clients
                Socket client_sock = ss.accept();
                new ProcessConnection(client_sock).start();
                clients.add(client_sock);
                clientCount++;
            }
            //System.out.println("here");
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
            
            
            line = sin.nextLine(); // 'start' from clicking start button
            while (!JavaFinalProjectServer.gameOver){  
                JavaFinalProjectServer.clickedStartButton++;
                //System.out.println(JavaFinalProjectServer.clickedStartButton);
            
                if (JavaFinalProjectServer.clickedStartButton == 2) { //if both clicked start
                    PrintStream eachSout;
                    for (int i = 0; i < JavaFinalProjectServer.clients.size(); i++){
                        eachSout = new PrintStream(JavaFinalProjectServer.clients.get(i).getOutputStream());
                        eachSout.println("both clients clicked start"); //send to all clients
                        //System.out.println("both clients clicked start");
                    } 
                    JavaFinalProjectServer.clickedStartButton = 0;
                }
                /*while (JavaFinalProjectServer.clickedStartButton != 2){
                    continue;
                }*/
                //System.out.println("starting of getting ans");

                int numBunnies = sin.nextInt();
                //System.out.println("Num of Bunnies "+numBunnies);
                
                JavaFinalProjectServer.total += numBunnies;
                //System.out.println("Total beginning: "+JavaFinalProjectServer.total);
                int guess = sin.nextInt();
                /*JavaFinalProjectServer.gotans++;
                //System.out.println(JavaFinalProjectServer.gotans);
                while(JavaFinalProjectServer.gotans < 2){
                    //System.out.println(JavaFinalProjectServer.gotans);
                    continue;
                }*/
                
                //send to other client this client's guess
                if (JavaFinalProjectServer.player1 == client_sock) {
                    PrintStream s = new PrintStream (JavaFinalProjectServer.player2.getOutputStream());
                    s.println(guess); //send guess to opp
                    s.println(numBunnies); //send num bunnies to opp
                } else {
                    PrintStream s = new PrintStream (JavaFinalProjectServer.player1.getOutputStream());
                    s.println(guess);
                    s.println(numBunnies);
                }
                sin.nextLine(); // get new line
                String finish = sin.nextLine(); // player sent finish getting total "received opp total and bunnies"
               
                JavaFinalProjectServer.gameOver ^= (guess == JavaFinalProjectServer.total);
                
                //System.out.println("finished comparing with total: "+finish);

                
                //System.out.println("Total: " + JavaFinalProjectServer.total);
                //reset if no one wins
                JavaFinalProjectServer.gotans = 0;
                
                JavaFinalProjectServer.clickedStartButton = 0;
                String wait = sin.nextLine(); //wait to hear "clicked start/replay button"
                JavaFinalProjectServer.total = 0; // total reset
                //System.out.println("Total reset: "+JavaFinalProjectServer.total);
                //System.out.println("wait: " + wait);
                
                
                
            }
            //System.out.println("reach out of while loop");
            
           
            
        } catch(IOException e) {}
    }
    
            
}