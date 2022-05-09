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
    static Vector<PrintStream> send = new Vector<>();
    
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(5190);
            Socket player1 = ss.accept();
            System.out.println("player 1 is in");

            Socket player2 = ss.accept();
            System.out.println("player 2 is in");
                
            PrintStream p1sout = new PrintStream(player1.getOutputStream());
            PrintStream p2sout = new PrintStream(player2.getOutputStream());
            Scanner p1sin = new Scanner(player1.getInputStream());
            Scanner p2sin = new Scanner(player2.getInputStream());
            
            //tell both players they are ready to play each other and set start button to green
            //on players' windows 

            p1sout.println("hi");
            p2sout.println("hi"); // when both player is online
            
            p1sin.nextLine();
            p2sin.nextLine(); // when both players click start
            
            System.out.println("here");
            
            p1sout.println("start");
            p2sout.println("start");
            
            
            
            Socket winner = whowon(player1, player2);
                
            while ((winner != player1) && (winner != player2)){
                p1sout.print("You lost");
                p2sout.print("You won");
                winner = whowon(player1, player2);
            }
            
            if (winner == player1){
                p1sout.print("You won");
                p2sout.print("You lost");
            }
            else{
                p1sout.print("tie");
                p2sout.print("tie");
            }
            
        } catch (IOException ex) {
            System.out.println("Could not listen on port 5190");
        }
        
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
        
        return player1;
    }
    
}

