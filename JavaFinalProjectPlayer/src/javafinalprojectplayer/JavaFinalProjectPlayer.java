/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javafinalprojectplayer;

/**
 *
 * @author meinaxie
 */

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author meinaxie
 */
public class JavaFinalProjectPlayer  {

    /**
     * @param args the command line arguments
     */
    
    static JFrame startgame;
    static JFrame initial;
    static int score;
    static int timer;
    static JButton[] bunnies;
    
    public static void main(String[] args) {
        
        
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        
        System.out.print("Enter Server: "); 
        
        String server = myObj.nextLine();  // Read server ?server is IP or Port
        
        Scanner sin;
        
        while (true) { //if host IP is not valid, continue asking 
            try {
                Socket sock = new Socket(server, 5190);
                PrintStream sout = new PrintStream(sock.getOutputStream());
                sin = new Scanner(sock.getInputStream()); 
                break;
            } catch (IOException ex) {
                System.out.println("Socket could not connect!");
                System.out.print("Enter Server: "); 
                server = myObj.nextLine();  // Read server ?server is IP or Port
                
            }
        } 
        
        
        //***********LAYOUT for START PAGE*************************************************************
        //display two icons P1 and P2 
        //when both clients are connected, start button will be present to be clickable 
        //once clicked, the new frame with game will load 
        
        //JFrame BorderLayout
        initial = new JFrame("Bunnies");
        initial.setLayout(new BorderLayout());
        initial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initial.setSize(500, 400);
        JPanel initialPanel = new JPanel();
        initialPanel.setLayout(new BorderLayout());
        initial.add(initialPanel, BorderLayout.CENTER);
        
        JButton startButton = new JButton("Start Game");
        startButton.setSize(200,200);
        initialPanel.add(startButton, BorderLayout.CENTER);
        
        initial.setVisible(true);
        
        String ready = sin.nextLine();
        System.out.println(ready);
        startButton.addActionListener(new StartGame()); //make start button clickable
        
        //***********LAYOUT for START PAGE***************************************************************



        //**********LAYOUT for GAME********************************************************************
        //JFrame BorderLayout 
        startgame = new JFrame("Pick your bunnies");
        startgame.setLayout(new BorderLayout());
        startgame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startgame.setSize(500, 400);

        // JPanel of score and timer NORTHEAST and NORTHWEST
        JPanel scoretimer = new JPanel();
        scoretimer.setLayout(new BorderLayout());
        scoretimer.setSize(500, 100);
        startgame.add(scoretimer, BorderLayout.NORTH);

        JLabel scorel = new JLabel(" "+score+" ");
        scorel.setFont(new Font("Serif", Font.BOLD, 50));
        scorel.setForeground(Color.PINK);
        scorel.setBackground(Color.gray);
        scorel.setOpaque(true);
        scoretimer.add(scorel, BorderLayout.WEST);

        JLabel timerl = new JLabel(" "+timer+" ");
        timerl.setFont(new Font("Serif", Font.BOLD, 50));
        timerl.setForeground(Color.PINK);
        timerl.setBackground(Color.gray);
        timerl.setOpaque(true);
        scoretimer.add(timerl, BorderLayout.EAST);

        //JPanel of bunnies CENTER, GridLayout 1 X 2 
        JPanel bunniesp = new JPanel();
        bunniesp.setSize(500, 200);
        bunniesp.setLayout(new GridLayout(1, 2));
        startgame.add(bunniesp, BorderLayout.CENTER);

        bunnies = new JButton[2];
        
        ImageIcon img1 = new ImageIcon("src/javafinalprojectplayer/Bunny1.PNG");
        JButton bunny1 = new JButton(img1);
        bunny1.setSize(50, 50);
        bunniesp.add(bunny1);
        bunnies[0] = bunny1;
        ImageIcon img2 = new ImageIcon("src/javafinalprojectplayer/Bunny2.PNG");
        JButton bunny2 = new JButton(img2);
        bunny2.setSize(50,50);
        bunnies[1] = bunny2;
        bunniesp.add(bunny2);
        
        
        

        //JText for client guess SOUTH 
        JPanel guessp = new JPanel();
        guessp.setSize(500, 100);
        startgame.add(guessp, BorderLayout.SOUTH);
        JTextField guessbox= new JTextField(20);
        guessp.add(guessbox);
        //Submit Button SOUTHEAST
        JButton submit = new JButton("Submit");
        submit.addActionListener(new SubmitGuess());
        guessp.add(submit);
        
        startgame.setVisible(false);

        //**********START GAME********************************************************************
    }

    
}


class StartGame implements ActionListener {

    StartGame() {
        JavaFinalProjectPlayer.score = 0;
        JavaFinalProjectPlayer.timer = 0;
    }
    @Override
        public void actionPerformed(ActionEvent arg0){
            //dispose of initial window and open the game window
            JavaFinalProjectPlayer.initial.dispose();
            JavaFinalProjectPlayer.startgame.setVisible(true);
      
        }

}


class Bunny implements ActionListener {
    Bunny() {}
    
    //changes boolean true false, change icon Image with/wout bunny
    @Override
        public void actionPerformed(ActionEvent arg0){
            return;
        }
}


class SubmitGuess implements ActionListener {
    SubmitGuess() {}
    
    @Override
        public void actionPerformed(ActionEvent arg0) {
            
            //**********LAYOUT for GAME***********************
            //JFrame BorderLayout 
            // JPanel of opponent's guess NORTH
            //JPanel of StartButton  SOUTH
            //JPanel of bunnies CENTER, GridLayout 1 X 2 oppenent's guess on left, your guess on right
            //start again button
            
            return;
        }
}
   
class StartAgain implements ActionListener{
    StartAgain(){}
    
    @Override
        public void actionPerformed(ActionEvent arg0){
            
        }
        
    void won(){ //display won message to client 
        JLabel label = new JLabel("You Won!!!", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setForeground(Color.RED);
        label.setBackground(Color.ORANGE);
        label.setOpaque(true);

        //jf.add(label); 
        //frame.setSize(300,150);
        //jf.setVisible(true);
    }
    void lose(){ //display lsot message to client
        JLabel label = new JLabel("You Lost...", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setForeground(Color.RED);
        label.setBackground(Color.ORANGE);
        label.setOpaque(true);

        //jf.add(label); 
        //frame.setSize(300,150);
        //jf.setVisible(true);
    }
    void tie(){
        //text.setText("");
        // reset buttons/bunnies
    }
}
       
