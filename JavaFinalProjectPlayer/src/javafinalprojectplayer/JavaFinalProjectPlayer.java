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
    static JFrame results;
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
        
        String ready = sin.nextLine(); // listens for "two clients connected"
        //System.out.println('ready');
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
        guessbox.setText("0");
        guessp.add(guessbox);
        
        
        //*******************WHEN BOTH CLIENTS CLICK START BUTTON*********************************
        String openGameWindow = sin.nextLine(); //listens for "both clients clicked start"
        System.out.println(openGameWindow);
        //dispose of initial window and open the game window
        initial.dispose();
        int correct = 0;
        int guess = 0;
        int oppGuess = 0;
        int total = 0;
        
        int firstGame = 1; //0 if not first game, to show previous game was a tie
        
        //game loop until someone guesses correctly 
        while(correct == 0){ 
            startgame.setVisible(true); //first game window 
            //sout.println("Both window started");
            
            
            
            //reset input selections 
            //set borders to black
            bunny1.setBorder(new LineBorder(Color.BLACK));
            bunny2.setBorder(new LineBorder(Color.BLACK));
            //set input text to 0
            guessbox.setText("0");
            

            //**********START GAME********************************************************************
            //start timer
            //sin.nextLine();
            while (timer < 10){
                try{
                    sleep(1000);
                }catch(InterruptedException e) {}
                timer += 1;
                timerl.setText(" " + timer + " ");
                //startgame.setVisible(true);
            }

            startgame.setVisible(false);
            timer = 0;

            //check how many bunnies the client selected 
            int totalBunnies = 0;
            for (int i = 0; i < bunnies.length; i++){
                if (bunnies[i] == 1) {
                    totalBunnies++;
                }
            }
            System.out.println("my bunnies: " + totalBunnies);

            
            //sout.println(guessbox.getText());
            sout.println(totalBunnies); // send to server the number of bunnies selected
            guess = Integer.valueOf(guessbox.getText());
            sout.println(guess); //send to server guess
            
            oppGuess = sin.nextInt();
            total = oppGuess + guess;
            
            //System.out.println("my bunnies: " + totalBunnies);
            System.out.println("my opponent's guess: " + oppGuess);
            System.out.println("my guess: " + guess);
            
            JFrame results = new JFrame("Results");
            results.setLayout(new BorderLayout());
            results.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            results.setSize(500, 400);

            // JPanel of WIN or LOST
            JPanel whoWon = new JPanel();
            whoWon.setLayout(new BorderLayout());
            whoWon.setSize(500, 100);
            results.add(whoWon, BorderLayout.NORTH);

            JLabel myB;
            JLabel oppB;

            // JPanel of image for MY BUNNIES GUESSES
            JPanel myBunnies = new JPanel();
            myBunnies.setLayout(new BorderLayout());
            myBunnies.setSize(200, 200);
            results.add(myBunnies, BorderLayout.WEST);
            ImageIcon img3;
            if (guess == 0) {
                img3 = new ImageIcon("src/javafinalprojectplayer/noBunnies.PNG");
            } else if (guess == 1) {
                img3 = new ImageIcon("src/javafinalprojectplayer/oneBunny1.PNG");
            } else {
                img3 = new ImageIcon("src.javafinalprojectplayer/twoBunnies.PNG");
            }
            myB = new JLabel(img3);
            
            myBunnies.add(myB);
            results.add(myBunnies);
            
            
            // JPANEL of image for OPPonent's guesses BUNNIES
            JPanel oppBunnies = new JPanel();
            oppBunnies.setLayout(new BorderLayout());
            oppBunnies.setSize(200, 200);
            results.add(oppBunnies, BorderLayout.EAST);
            ImageIcon img4;
            if (oppGuess == 0) {
                img4 = new ImageIcon("src/javafinalprojectplayer/noBunnies.PNG");
            } else if (oppGuess == 1) {
                img4 = new ImageIcon("src/javafinalprojectplayer/oneBunny1.PNG");
            } else {
                img4 = new ImageIcon("src.javafinalprojectplayer/twoBunnies.PNG");
            }
            oppB = new JLabel(img4);
            
            oppBunnies.add(oppB);
            results.add(oppBunnies);
            
            
            JLabel result; 
            
            if (total == guess && total == oppGuess) { //both guess correctly, go again
                System.out.println("both guessed correctly");
                result = new JLabel("You both guessed correctly... try again!");
                result.setFont(new Font("Serif", Font.BOLD, 20));
                result.setForeground(Color.RED);
                result.setBackground(Color.ORANGE);
                result.setOpaque(true);
                JButton tryAgain = new JButton("Try Again");
                results.add(tryAgain, BorderLayout.SOUTH);
                tryAgain.addActionListener(new StartAgain());
                whoWon.add(result);
                
            } else if (total != guess && total != oppGuess) { //both wrong
                System.out.println("both guessed wrong");

                result = new JLabel("You both guessed wrong... try again!");
                result.setFont(new Font("Serif", Font.BOLD, 20));
                result.setForeground(Color.RED);
                result.setBackground(Color.ORANGE);
                result.setOpaque(true);
                JButton tryAgain = new JButton("Try Again");
                results.add(tryAgain, BorderLayout.SOUTH);
                tryAgain.addActionListener(new StartAgain());
                whoWon.add(result);
            } else {
                correct = 1;
            }
            
            results.setVisible(true);
            
            System.out.println("Correct: "+correct);
            guess = 0;
            oppGuess = 0;
            total = 0;
            
            
            while (timer < 10){
                try{
                    sleep(1000);
                }catch(InterruptedException e) {}
                timer += 1;
                timerl.setText(" " + timer + " ");
                //startgame.setVisible(true);
            }
            timer = 0;
        
        }
        
        
 
      
    }

    
}


class StartGame implements ActionListener {

    StartGame() {
        JavaFinalProjectPlayer.score = 0;
        JavaFinalProjectPlayer.timer = 0;
    }
    @Override
        public void actionPerformed(ActionEvent arg0){
            JavaFinalProjectPlayer.sout.println("start"); //tell server start, a client clicked the button
            JButton curr = (JButton) arg0.getSource();
            curr.setText("Waiting"); // tell current player wait for other player
            curr.enable(false);
      
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
            return;
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
    
