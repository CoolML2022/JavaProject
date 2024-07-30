import com.sun.tools.javac.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class MainFrame extends JFrame {
    final private Font mainFont = new Font("Segoe print", Font.BOLD, 18);
    JTextField tfFirstName, tfLastName;
    JLabel WelcomeLabel;
    public void Initialize(){
        
        //***************************Label***************************//
        JLabel FirstName = new JLabel("First Name");
        FirstName.setFont(mainFont);

        tfFirstName = new JTextField();
        tfFirstName.setFont(mainFont);

        JLabel LastName = new JLabel("Last Name");
        LastName.setFont(mainFont);

        tfLastName = new JTextField();
        tfFirstName.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 1, 5 , 5));
        formPanel.add(FirstName);
        formPanel.add(tfFirstName);
        formPanel.add(LastName);
        formPanel.add(tfLastName);

        //**********************Welcome Label***********************//

        WelcomeLabel = new JLabel();
        WelcomeLabel.setFont(mainFont);


        //**********************Button Panel*+**********************//
        JButton jbutOK = new JButton("OK");
        jbutOK.setFont(mainFont);
        jbutOK.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String firstName = tfFirstName.getText();
                String lastName = tfLastName.getText();
                WelcomeLabel.setText("Hello " + firstName + " " + lastName);
            }
            
        });

        JButton jbutCLEAR = new JButton("Clear");
        jbutCLEAR.setFont(mainFont);

        jbutCLEAR.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                tfFirstName.setText("");
                tfLastName.setText("");
                WelcomeLabel.setText("");
            }
            
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 5 , 5));
        buttonsPanel.add(jbutOK);
        buttonsPanel.add(jbutCLEAR);




        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(128, 128, 255));
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(WelcomeLabel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);


        add(mainPanel);

        setTitle("Welcome");
        setSize(800, 900);
        setMinimumSize(new Dimension(300, 400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        MainFrame myFrame = new MainFrame();
        myFrame.Initialize();
    }
}
