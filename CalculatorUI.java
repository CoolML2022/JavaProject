import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;



public class CalculatorUI extends JFrame{

    final private Font maiFont = new Font("Arial", Font.BOLD, 18);
    JTextField inputTextField, outputTextField;
    private String mainString;
    
    public void Initialize(){

        //*********** Screen Panel ************//
        JLabel outputJLabel = new JLabel("Result");
        outputJLabel.setFont(maiFont);
        inputTextField = new JTextField();
        inputTextField.setFont(maiFont);        
        outputTextField = new JTextField();
        outputJLabel.setFont(maiFont);
        outputJLabel.setFont(maiFont);

        JPanel screenPanel = new JPanel();
        screenPanel.setLayout(new GridLayout(3, 1, 5, 5));
        screenPanel.add(inputTextField);
        screenPanel.add(outputJLabel);
        screenPanel.add(outputTextField);

        //********** Button Panel **********//
        JButton[] numberButtons = new JButton[10];

        for(int i = 0; i < numberButtons.length; i++){

            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(maiFont);

            numberButtons[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    mainString = mainString + String.valueOf(i);
                }
            });

        }


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(128, 128, 255));
        mainPanel.add(screenPanel, BorderLayout.NORTH);
        

        add(mainPanel);
        setTitle("Calculator");
        setSize(500, 600);
        setMinimumSize(new Dimension(300, 400));
        setVisible(true);
    }



    public static void main(String[] args) {
        CalculatorUI calculatorUI = new CalculatorUI();
        calculatorUI.Initialize();
    }
}
