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
    static JFrame jf = new JFrame("Messages");
    
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        
        System.out.print("Enter Server: ");
        
        String server = myObj.nextLine();  // Read server ?server is IP or Port
        
        System.out.print("Enter Username: ");
        String username = myObj.nextLine();
        
        
        try {
            Socket sock = new Socket(server, 5190);
            
            PrintStream sout = new PrintStream(sock.getOutputStream());
            Scanner sin = new Scanner(sock.getInputStream());
            
            sout.println(username);
            
            jf.setLayout(new BorderLayout());
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jf.setSize(500, 600);
            
            new UserInput(sout).start();
            new GetMsg(sin).start();
            
        } catch (IOException ex) {
            System.out.println("Socket could not connect!");
        }
    }
    void won(){
        JLabel label = new JLabel("You Won!!!", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setForeground(Color.RED);
        label.setBackground(Color.ORANGE);
        label.setOpaque(true);

        jf.add(label); 
        //frame.setSize(300,150);
        jf.setVisible(true);
    }
    void lose(){
        JLabel label = new JLabel("You Lost...", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setForeground(Color.RED);
        label.setBackground(Color.ORANGE);
        label.setOpaque(true);

        jf.add(label); 
        //frame.setSize(300,150);
        jf.setVisible(true);
    }
    void tie(){
        //text.setText("");
        // reset buttons/bunnies
    }
    
}

class UserInput extends Thread{
    PrintStream sout;
    UserInput(PrintStream newsout){sout = newsout;}
    public void run(){
        Scanner myObj = new Scanner(System.in);
        
        JPanel jp = new JPanel();
        jp.setSize(500,100);
        JavaHW4client.jf.add(jp, BorderLayout.SOUTH);
        
        JTextField text = new JTextField(20);
        jp.add(text);
        
        JButton jb = new JButton("SEND");
        jb.addActionListener(new ButtonListener(text));
        jp.add(jb);
        
        JavaHW4client.jf.setVisible(true);
        
    }
    class ButtonListener implements ActionListener{
        JTextField text;
        ButtonListener(JTextField newtext){text = newtext;}
        @Override
        public void actionPerformed(ActionEvent arg0){
            sout.println(text.getText());
            text.setText("");
        }
    }
}

class GetMsg extends Thread{
    Scanner sin;
    GetMsg(Scanner newsin){sin = newsin;}
    public void run(){
        
        JPanel jp = new JPanel();
        jp.setSize(500,500);
        JTextArea textArea = new JTextArea();
        //jp.add(textArea);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(500, 500));
        
        jp.add(scrollPane);
        
        JavaHW4client.jf.add(jp);
        
        JavaHW4client.jf.setVisible(true);
        while(true){
            String msg = sin.nextLine();
            textArea.append(msg+'\n');
            textArea.setCaretPosition(textArea.getDocument().getLength());
            JavaHW4client.jf.setVisible(true);
        }
    }
    
}
