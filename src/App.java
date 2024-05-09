import java.awt.Color;

import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {

        
        System.out.println("Hello, World!");

        JFrame myFrame = new JFrame("Snake Game");

        Panel myPanel = new Panel();

        myPanel.setBackground(Color.BLACK);

        myFrame.add(myPanel);


        myFrame.pack();

        myFrame.setSize(600, 630);
        myFrame.setResizable(false);

        myFrame.setVisible(true);

        
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
