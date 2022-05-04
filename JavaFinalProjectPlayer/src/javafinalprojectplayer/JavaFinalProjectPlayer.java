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
    
    public static void main(String[] args) {
        new StartGame();
        /*
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        
        System.out.print("Enter Server: ");
        
        String server = myObj.nextLine();  // Read server ?server is IP or Port
        
        
        //create game window and display 
        jf.setLayout(new BorderLayout());
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(500, 600);
        

        try {
            Socket sock = new Socket(server, 5190);
            PrintStream sout = new PrintStream(sock.getOutputStream());
            Scanner sin = new Scanner(sock.getInputStream());           
        } catch (IOException ex) {
            System.out.println("Socket could not connect!");
        }
        
        
        
        //display two icons P1 and P2 
        //when both clients are connected, start button will be present to be clickable 
        //once clicked, the new frame with game will load 
        
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new StartGame());
        jf.add(startButton);
*/
        
    }

    
}


class StartGame implements ActionListener {
    
    JButton[] bunnies = new JButton[2]; //holds Boolean True if bunny shows up
    int score = 0;
    int timer = 0;
    StartGame() {
        
    }
    @Override
        public void actionPerformed(ActionEvent arg0){
            //**********LAYOUT for GAME***********************
            //JFrame BorderLayout 
            JFrame startgame = new JFrame("Pick your bunnies");
            startgame.setLayout(new BorderLayout());
            startgame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            startgame.setSize(500, 400);

            // JPanel of score and timer NORTHEAST and NORTHWEST
            JPanel scoretimer = new JPanel();
            scoretimer.setLayout(new BorderLayout());
            scoretimer.setSize(500, 100);
            startgame.add(scoretimer, BorderLayout.NORTH);

            JLabel scorel = new JLabel(" "+score+"");
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

            JButton bunny1 = new JButton("1");
            bunniesp.add(bunny1);
            bunnies[0] = bunny1;
            JButton bunny2 = new JButton("2");
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

            startgame.setVisible(true);
            
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
       
