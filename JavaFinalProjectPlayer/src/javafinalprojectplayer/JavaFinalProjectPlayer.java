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
import static java.lang.Thread.sleep;
import javax.swing.*;
import javax.swing.border.LineBorder;

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
    static int[] bunnies;
    static PrintStream sout;
    
    public static void main(String[] args) {
        
        
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        
        System.out.print("Enter Server: "); 
        
        String server = myObj.nextLine();  // Read server ?server is IP or Port
        
        Scanner sin;
        
        while (true) { //if host IP is not valid, continue asking 
            try {
                Socket sock = new Socket(server, 5190);
                sout = new PrintStream(sock.getOutputStream());
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
        
        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("Start Game"); //HOW TO CHANGE START BUTTON SIZE?
        buttonPanel.add(startButton);
       
        LoadingGraphic graphicPanel = new LoadingGraphic(); //JPanel
        
        
        initial.add(buttonPanel, BorderLayout.NORTH);
        initial.add(graphicPanel, BorderLayout.CENTER);
        initial.setVisible(true);
        
        Thread loadingThread = new Thread() {
            public void run() {
                while(true) {
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {}
                    graphicPanel.repaint();
                }
            }
        };
        loadingThread.start(); //starting loading graphic
        
        String ready = sin.nextLine();
        System.out.println(ready);
        loadingThread.stop(); //stop loading graphics
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

        bunnies = new int[2];
        
        ImageIcon img1 = new ImageIcon("src/javafinalprojectplayer/Bunny1.PNG");
        JButton bunny1 = new JButton(img1);
        bunny1.setSize(50, 50);
        bunniesp.add(bunny1);
        bunnies[0] = 0; // set to false
        bunny1.addActionListener(new Bunny(0));
        ImageIcon img2 = new ImageIcon("src/javafinalprojectplayer/Bunny2.PNG");
        JButton bunny2 = new JButton(img2);
        bunny2.setSize(50,50);
        bunnies[1] = 0; // set to false
        bunniesp.add(bunny2);
        bunny2.addActionListener(new Bunny(1));
        
        //JText for client guess SOUTH 
        JPanel guessp = new JPanel();
        guessp.setSize(500, 100);
        startgame.add(guessp, BorderLayout.SOUTH);
        JTextField guessbox= new JTextField(20);
        guessp.add(guessbox);
        
        //start timer
        sin.nextLine();
        while (timer < 10){
            try{
                sleep(1000);
            }catch(InterruptedException e) {}
            timer += 1;
            timerl.setText(" " + timer + " ");
            startgame.setVisible(true);
        }
        sout.println(guessbox.getText());
        startgame.setVisible(false);
        timer = 0;

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
            JavaFinalProjectPlayer.sout.println("start");
            JavaFinalProjectPlayer.startgame.setVisible(true);
      
        }

}


class Bunny implements ActionListener {
    int ind;
    Bunny(int index) {ind = index;}
    
    //changes boolean true false, change icon Image with/wout bunny
    @Override
        public void actionPerformed(ActionEvent arg0){
            JavaFinalProjectPlayer.bunnies[ind] = JavaFinalProjectPlayer.bunnies[ind] ^ 1;
            JButton curr = (JButton) arg0.getSource();
            if (JavaFinalProjectPlayer.bunnies[ind] == 1){
                curr.setBorder(new LineBorder(Color.GREEN));
            }
            else{
                curr.setBorder(new LineBorder(Color.BLACK));
            }
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
       
class LoadingGraphic extends JPanel {
    int dots;
    
    LoadingGraphic() {
        dots = 3; 
    }
    
    @Override 
    protected void paintComponent(Graphics g) {
        g.setColor(Color.PINK);
        if (dots == 3) {
            g.fillOval(this.getWidth()/2 - 80, this.getHeight()/2, 20, 20);
            g.fillOval(this.getWidth()/2, this.getHeight()/2, 20, 20);
            g.fillOval(this.getWidth()/2 + 80, this.getHeight()/2, 20, 20);
       } else if (dots == 2) {
            g.fillOval(this.getWidth()/2 - 80, this.getHeight()/2, 20, 20);
            g.fillOval(this.getWidth()/2, this.getHeight()/2, 20, 20);
       } else if (dots == 1) {
            g.fillOval(this.getWidth()/2 - 80, this.getHeight()/2, 20, 20);
       } 
       dots = (dots + 1) % 4; 
    } //end of paintComponent
    
    

}
    
